package org.example.nonblockingalgo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void when2ThreadsIncrementCount2Times() throws InterruptedException{
        var count = new CASCount();
        var first = new Thread(count::increment);
        var second = new Thread(count::increment);
        /* Запускаем нити. */
        first.start();
        second.start();
        /* Заставляем главную нить дождаться выполнения наших нитей. */
        first.join();
        second.join();
        /* Проверяем результат. */
        assertThat(count.get()).isEqualTo(2);
    }

    @Test
    public void when2ThreadsIncrementCount3Times() throws InterruptedException{
        var count = new CASCount();
        var first = new Thread(() -> {
            count.increment();
            count.increment();
        });
        var second = new Thread(count::increment);
        /* Запускаем нити. */
        first.start();
        second.start();
        /* Заставляем главную нить дождаться выполнения наших нитей. */
        first.join();
        second.join();
        /* Проверяем результат. */
        assertThat(count.get()).isEqualTo(3);
    }
}