package de.bcxp.challenge.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;

/**
 * A Service to read a csv file of weather
 */
public class WeatherService implements ReadService {

    private static final String PATH_OF_CSV_FILE = "./src/main/resources/de/bcxp/challenge/weather.csv";
    CsvFileReader csvFileReader = new CsvFileReader();

    /**
     *
     * @return the day with the smallest temperature spread
     * @throws IOException if the file not found
     */
    @Override
    public String read() throws IOException {
        //Read the file
        List<CSVRecord> csvRecordList = csvFileReader.readCsvFile(PATH_OF_CSV_FILE, CSVFormat.DEFAULT);


        int result = Integer.MAX_VALUE;
        String currentDate = null;
        //For each csv record
        for (int i = 1; i < csvRecordList.size(); i++) {
            CSVRecord currentRecord = csvRecordList.get(i);
            String day = currentRecord.get(0);
            int tempMax = Integer.parseInt(currentRecord.get(1));
            int tempMin = Integer.parseInt(currentRecord.get(2));
            int deltaTemperature = tempMax - tempMin;
            //If the current delta is less than the previous stored delta
            if (deltaTemperature < result) {
                result = deltaTemperature;
                currentDate = day;
            }
        }
        return currentDate;
    }
}
