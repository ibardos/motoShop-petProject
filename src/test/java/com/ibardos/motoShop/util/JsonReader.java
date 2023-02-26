package com.ibardos.motoShop.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonReader {

    private final String fileName;

    /**
     * Constructs a JsonReader with a specified file.
     * @param fileName name of the file to read
     */
    public JsonReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the text content of the file specified in constructor.
     * @return text content of previously specified file
     * @throws IOException in case of non-existing file
     */
    public String read() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        return bufferedReader.readLine();
    }
}
