package de.bcxp.challenge.service;

import java.io.IOException;

/**
 * A Service to read files
 */
public interface ReadService {

    /**
     *
     * @return The best outcome of the read process
     * @throws IOException if file not found
     */
    String read() throws IOException;
}
