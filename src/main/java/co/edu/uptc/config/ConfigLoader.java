package co.edu.uptc.config;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigLoader {

    private static final String INTERNAL_FILE = "congif.properties";
    private static final String EXTERNAL_FILE = "config/config.properties";

    private final Properties props = new Properties();

    public ConfigLoader() {
        loadInternal();
        loadExternal();
    }

    private void loadInternal() {
        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(INTERNAL_FILE)) {

            if (is != null) {
                props.load(is);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error cargando config interna", e);
        }
    }

    private void loadExternal() {
        try {
            Path path = Path.of(EXTERNAL_FILE);

            if (Files.exists(path)) {
                try (InputStream is = Files.newInputStream(path)) {
                    props.load(is); // sobreescribe
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error cargando config externa", e);
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }
}
