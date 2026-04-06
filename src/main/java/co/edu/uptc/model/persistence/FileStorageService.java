package co.edu.uptc.model.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.interfaces.ISerializer;
import co.edu.uptc.interfaces.IFileStorage;

public class FileStorageService<T> implements IFileStorage<T> {

    private final Path filePath;
    private final ISerializer<T> serializer;

    public FileStorageService(String filePath, ISerializer<T> serializer) {
        this.filePath   = Paths.get(filePath);
        this.serializer = serializer;
        ensureFileExists();
    }

    // Agrega UNA línea al final del archivo.
    // Se usa cuando se agrega un registro nuevo — no reescribe nada.
    @Override
    public void append(T entity) {
        String line = serializer.serialize(entity);
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE)) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo en archivo: " + filePath, e);
        }
    }

    // Lee el archivo completo y devuelve todos los registros como lista.
    // Se llama una sola vez al arrancar la app.
    @Override
    public List<T> loadAll() {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    result.add(serializer.deserialize(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo: " + filePath, e);
        }
        return result;
    }

    // Borra el contenido del archivo y lo reescribe con todos los registros.
    // Se usa cuando hubo retiros: el archivo debe quedar igual que la memoria.
    @Override
    public void overwrite(List<T> entities) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath,
                StandardOpenOption.TRUNCATE_EXISTING,  // borra el contenido anterior
                StandardOpenOption.CREATE)) {
            for (T entity : entities) {
                writer.write(serializer.serialize(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reescribiendo archivo: " + filePath, e);
        }
    }

    private void ensureFileExists() {
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creando archivo: " + filePath, e);
        }
    }
}