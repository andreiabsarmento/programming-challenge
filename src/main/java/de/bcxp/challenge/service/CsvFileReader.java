package de.bcxp.challenge.service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * A simple reader to read a csv file
 */
public class CsvFileReader {

    /**
     *
     * @param path of csv file
     * @param csvFormat the format of csv (you can specify the delimiters and others)
     * @return a list of csv records
     * @throws IOException if the file is not a valid csv or if an error occurred
     * @throws FileNotFoundException if the file could not be found
     */
    public List<CSVRecord> readCsvFile(String path, CSVFormat csvFormat) throws IOException {
        try (FileReader reader = new FileReader(path)) {

            CSVParser parser = new CSVParser(reader, csvFormat);
            return parser.getRecords();
        }
    }
}

