package org.example.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        int symbolIndex = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r Load: " + process[symbolIndex]);
            if (symbolIndex == process.length - 1) {
                symbolIndex = 0;
            } else {
                symbolIndex++;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws  InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
    }
}
