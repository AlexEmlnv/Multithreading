package org.example.sharedresources;

import java.io.*;

public class ParseFileSaveContent {
    private static final int BUFFER_SIZE_IN_BYTES = 512;
    private final File file;

    public ParseFileSaveContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
