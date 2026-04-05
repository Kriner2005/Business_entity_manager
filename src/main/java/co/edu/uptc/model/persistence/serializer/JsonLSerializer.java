package co.edu.uptc.model.persistence.serializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import co.edu.uptc.interfaces.ISerializer;

public class JsonLSerializer<T> implements ISerializer<T> {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final Gson gson;
    private final Class<T> type;

    public JsonLSerializer(Class<T> type) {
        this.type = type;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, t, ctx) -> new JsonPrimitive(src.format(FMT)))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, t, ctx) -> LocalDateTime.parse(json.getAsString(),
                                FMT))
                .create();
    }

    @Override
    public String serialize(T entity) {
        return gson.toJson(entity);
    }

    @Override
    public T deserialize(String line) {
        return gson.fromJson(line, type);
    }
}
