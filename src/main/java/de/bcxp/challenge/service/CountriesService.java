package de.bcxp.challenge.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;

/**
 * A Service to read csv file of countries
 */
public class CountriesService implements ReadService {

    private static final String PATH_OF_CSV_FILE = "./src/main/resources/de/bcxp/challenge/countries.csv";

    CsvFileReader csvFileReader = new CsvFileReader();

    /**
     *
     * @return the name of the country with the highest number of people per square kilometre
     * @throws IOException if file not found
     */
    @Override
    public String read() throws IOException {
        //Read the file
        List<CSVRecord> csvRecordList = csvFileReader.readCsvFile(PATH_OF_CSV_FILE, CSVFormat.DEFAULT.withDelimiter(';'));

        //Specify the thousand and the decimal format
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        double result = Integer.MIN_VALUE;
        String currentCountryName = null;
        //For each csv record
        for (int i = 1; i < csvRecordList.size(); i++) {
            CSVRecord currentRecord = csvRecordList.get(i);
            String countryName = currentRecord.get(0);
            try {
                double population = decimalFormat.parse(currentRecord.get(3)).doubleValue();
                double area = decimalFormat.parse(currentRecord.get(4)).doubleValue();
                if (area == 0) {
                    throw new ArithmeticException("Cannot divide by zero.");
                }
                double populationDensity = population / area;
                //If the population density is greater than the previous stored density
                if (populationDensity > result) {
                    result = populationDensity;
                    currentCountryName = countryName;

                }
            } catch (ParseException parseException) {
                //If the one of the columns has a wrong number format
                System.err.println("Wrong number format for country: " + countryName);
            }

        }
        return currentCountryName;
    }
}
