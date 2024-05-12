package org.example.sharedresources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class ParseFileGetContent {
    private static final int BUFFER_SIZE_IN_BYTES = 512;
    private final File file;

    public ParseFileGetContent(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder("");
        try (InputStream input = new FileInputStream(file)) {
            var dataBuffer = new byte[BUFFER_SIZE_IN_BYTES];
            while (input.read(dataBuffer) != -1) {
                for (int i = 0; i < BUFFER_SIZE_IN_BYTES; i++) {
                    if (filter.test((char) dataBuffer[i])) {
                        output.append((char) dataBuffer[i]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
