package src;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class theGold {

    private static final Lock lock = new ReentrantLock();

    public static void animateText(String text, double delay) {
        lock.lock();
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                System.out.flush();
                Thread.sleep((long) (delay * 1000));
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public static void singLyric(String lyric, double delay, double speed) {
        try {
            Thread.sleep((long) (delay * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        animateText(lyric, speed);
    }

    public static void singSong() {
        String[][] lyrics = {
                {"Couldn't really love you anymore", "0.1"},
                {"You've become my ceiling", "0.1"},
                {"I don't think I love you anymore", "0.1"},
                {"That gold mine changed you", "0.1"},
                {"You don't have to hold me anymore", "0.1"},
                {"Our cave's collapsing", "0.1"},
                {"I don't wanna be me anymore", "0.1"},
                {"My old man told me", "0.1"}
        };

        double[] delays = {0.3, 3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0};
        double[] speeds = {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < lyrics.length; i++) {
            final int index = i;
            Thread t = new Thread(() -> singLyric(lyrics[index][0], delays[index], speeds[index]));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        singSong();
    }
}
