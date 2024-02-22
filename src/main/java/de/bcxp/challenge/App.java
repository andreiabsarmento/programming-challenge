package de.bcxp.challenge;

import de.bcxp.challenge.service.CountriesService;
import de.bcxp.challenge.service.WeatherService;

import java.io.IOException;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    /**
     * This is the main entry method of your program.
     *
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        try {
            WeatherService weatherService = new WeatherService();
            System.out.printf("Day with smallest temperature spread: %s%n", weatherService.read());

        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
        try {
            CountriesService countriesService = new CountriesService();
            System.out.printf("Country with highest population density: %s%n", countriesService.read());

        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }
}
