package com.exam.csvparser.general;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvGeneralTest {

    Map<String, String> PLAYERS = new HashMap<>() {
        {
            put("14", "Ikeee");
            put("25", "Roni");
            put("47", "JabariWow");
            put("67", "MarShonWin");
            put("71", "Lorenzos");
            put("90", "OmriIs");
            put("1", "Alexi");
            put("119", "Tylero");
        }
    };
    String[] HEADERS = { "id", "nickname"};
    @Test
    public void shouldReadFile() throws IOException {
        Reader in = new FileReader("src/test/resources/players.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(in);

        for (CSVRecord record : records) {
            String author = record.get("id");
            String title = record.get("nickname");
            assertEquals(PLAYERS.get(author), title);
        }
    }
}
