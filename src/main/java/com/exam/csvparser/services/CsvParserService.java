package com.exam.csvparser.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

@Service
public class CsvParserService {

    public List<String> parseCsvFile() throws Exception {
        Reader reader = new FileReader(new ClassPathResource("players.csv").getFile());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        return csvParser.getRecords().stream()
                .map(record -> record.get("id"))
                .toList();
    }
}
