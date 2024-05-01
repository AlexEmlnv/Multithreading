package org.example.concurrent;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget2 implements Runnable {
    private final String url;
    private final int speed;

    public Wget2(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            int packCount = 0;
            long packAccumTime = 0;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                packCount++;
                packAccumTime += (System.nanoTime() - downloadAt);
            }
            var avrDownloadSpeed = (packCount * 512 * 1_000_000) / packAccumTime;
            if (avrDownloadSpeed > speed) {
                System.out.println("Sleeping on " + avrDownloadSpeed / speed + " ms");
                Thread.sleep(avrDownloadSpeed / speed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean urlValidator(String url) {
        UrlValidator defaultValidator = new UrlValidator();
        return url != null && !url.isEmpty() && defaultValidator.isValid(url);
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        if (!urlValidator(url) || speed <= 0) {
            System.out.println("Невалидные входные параметры!");
            return;
        }
        Thread wget = new Thread(new Wget2(url, speed));
        wget.start();
        wget.join();
    }
}
