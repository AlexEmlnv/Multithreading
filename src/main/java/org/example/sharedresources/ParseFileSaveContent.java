package org.example.sharedresources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ParseFileSaveContent {
    private static final int BUFFER_SIZE_IN_BYTES = 512;
    private final File file;

    public ParseFileSaveContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file)) {
            int startPos = 0;
            int endPos = 0;
            while (startPos < content.length()) {
                endPos = Math.min(content.length(), startPos + BUFFER_SIZE_IN_BYTES);
                o.write(content.substring(startPos, endPos).getBytes());
                startPos = endPos;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
