package org.example.sharedresources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ParseFileGetContent {
    private static final int BUFFER_SIZE_IN_BYTES = 512;
    private final File file;

    public ParseFileGetContent(File file) {
        this.file = file;
    }

    public String getContent(String charsetName) {
        String output = "";
        try (InputStream input = new FileInputStream(file)) {
            var dataBuffer = new byte[BUFFER_SIZE_IN_BYTES];
            while (input.read(dataBuffer) != -1) {
                if (charsetName == null) {
                    output += new String(dataBuffer);
                } else {
                    output += new String(dataBuffer, charsetName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
