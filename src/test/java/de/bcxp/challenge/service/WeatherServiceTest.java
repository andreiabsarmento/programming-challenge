package de.bcxp.challenge.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService();
        weatherService.csvFileReader = Mockito.spy(weatherService.csvFileReader);
    }

    @Test
    void whenTheCorrectDayIsReturned() throws IOException {
        assertEquals("14", weatherService.read());
    }

    @Test
    void whenReadCsvFileThrowsException() throws IOException {
        Mockito.doThrow(new IOException())
                .when(weatherService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertThrows(IOException.class, () -> weatherService.read());
    }

    @Test
    void whenReadCsvFileIsEmpty() throws IOException {
        Mockito.doReturn(List.of())
                .when(weatherService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertNull(weatherService.read());
    }

    @Test
    void whenMaxTemperatureIsNotANumber() throws IOException{
        CSVRecord headersMock = createMockCsvRecord("day", "MxT", "MnT");
        CSVRecord invalidRecord = createMockCsvRecord("8", "potato", "15");
        Mockito.doReturn(List.of(headersMock, invalidRecord))
                .when(weatherService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertThrows(NumberFormatException.class, () -> weatherService.read());
    }

    @Test
    void whenMaxTemperatureIsEmpty() throws IOException{
        CSVRecord headersMock = createMockCsvRecord("day", "MxT", "MnT");
        CSVRecord invalidRecord = createMockCsvRecord("8", "", "15");
        Mockito.doReturn(List.of(headersMock, invalidRecord))
                .when(weatherService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertThrows(NumberFormatException.class, () -> weatherService.read());
    }

    @Test
    void whenMinTemperatureIsNotANumber() throws IOException{
        CSVRecord headersMock = createMockCsvRecord("day", "MxT", "MnT");
        CSVRecord invalidRecord = createMockCsvRecord("8", "59", "potato");
        Mockito.doReturn(List.of(headersMock, invalidRecord))
                .when(weatherService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertThrows(NumberFormatException.class, () -> weatherService.read());
    }

    @Test
    void whenMinTemperatureIsEmpty() throws IOException{
        CSVRecord headersMock = createMockCsvRecord("day", "MxT", "MnT");
        CSVRecord invalidRecord = createMockCsvRecord("8", "45", "");
        Mockito.doReturn(List.of(headersMock, invalidRecord))
                .when(weatherService.csvFileReader)
                .readCsvFile(Mockito.anyString(),
                        Mockito.any(CSVFormat.class));
        assertThrows(NumberFormatException.class, () -> weatherService.read());
    }

    private CSVRecord createMockCsvRecord(String day, String mxT, String mnT) {
        CSVRecord recordMock = Mockito.mock(CSVRecord.class);
        Mockito.doReturn(day)
                .when(recordMock)
                .get(0);
        Mockito.doReturn(mxT)
                .when(recordMock)
                .get(1);
        Mockito.doReturn(mnT)
                .when(recordMock)
                .get(2);
        return recordMock;
    }
}
