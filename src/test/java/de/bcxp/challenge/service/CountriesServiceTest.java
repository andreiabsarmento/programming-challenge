package de.bcxp.challenge.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountriesServiceTest {

    private CountriesService countriesService;

    @BeforeEach
    void setUp() {
        countriesService = new CountriesService();
        countriesService.csvFileReader = Mockito.spy(countriesService.csvFileReader);
    }

    @Test
    void whenTheResultIsMalta() throws IOException {
        assertEquals("Malta", countriesService.read());
    }

    @Test
    void whenReadCsvFileThrowsException() throws IOException {
        Mockito.doThrow(new IOException())
                .when(countriesService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertThrows(IOException.class, () -> countriesService.read());
    }

    @Test
    void whenReadCsvFileIsEmpty() throws IOException {
        Mockito.doReturn(List.of())
                .when(countriesService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertNull(countriesService.read());
    }

    @Test
    void whenPopulationIsNotANumber() throws IOException{
        CSVRecord headersMock = createMockCsvRecord("country", "population", "area");
        CSVRecord invalidRecord = createMockCsvRecord("Portugal", "potato", "54833");
        Mockito.doReturn(List.of(headersMock, invalidRecord))
                .when(countriesService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertNull(countriesService.read());
    }

    @Test
    void whenAreaIsNotANumber() throws IOException{
        CSVRecord headersMock = createMockCsvRecord("country", "population", "area");
        CSVRecord invalidRecord = createMockCsvRecord("Portugal", "54984", "potato");
        Mockito.doReturn(List.of(headersMock, invalidRecord))
                .when(countriesService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertNull(countriesService.read());
    }

    @Test
    void whenAreaIsEqualToZero() throws IOException{
        CSVRecord headersMock = createMockCsvRecord("country", "population", "area");
        CSVRecord invalidRecord = createMockCsvRecord("Portugal", "54984", "0");
        Mockito.doReturn(List.of(headersMock, invalidRecord))
                .when(countriesService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertThrows(ArithmeticException.class, () ->  countriesService.read());
    }

    private CSVRecord createMockCsvRecord(String country, String population, String area) {
        CSVRecord recordMock = Mockito.mock(CSVRecord.class);
        Mockito.doReturn(country)
                .when(recordMock)
                .get(0);
        Mockito.doReturn(population)
                .when(recordMock)
                .get(3);
        Mockito.doReturn(area)
                .when(recordMock)
                .get(4);
        return recordMock;
    }

}