package co.edu.uptc.model.persistence.serializer;

import java.io.StringReader;
import java.io.StringWriter;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import co.edu.uptc.interfaces.ISerializer;

public class CsvSerializer<T> implements ISerializer<T> {

    private final Class<T> type;

    public CsvSerializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public String serialize(T entity) {
        try {
            StringWriter sw = new StringWriter();
            StatefulBeanToCsv<T> writer = new StatefulBeanToCsvBuilder<T>(sw)
                    .withSeparator(';')
                    .build();
            writer.write(entity);
            return sw.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException("Error serializando a CSV", e);
        }
    }

    @Override
    public T deserialize(String line) {
        try {
            CSVReader reader = new CSVReader(new StringReader(line));
            CsvToBean<T> csv = new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .withSeparator(';')
                    .build();
            return csv.parse().get(0);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando CSV", e);
        }
    }
}
