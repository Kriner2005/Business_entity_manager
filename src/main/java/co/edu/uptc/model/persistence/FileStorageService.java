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
        this.filePath = Paths.get(filePath);
        this.serializer = serializer;
        ensureFileExists();
    }

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
