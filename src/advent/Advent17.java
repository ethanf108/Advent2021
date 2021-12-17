package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Advent17 {

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    static int sx, ex, sy, ey;

    static int calc(int xv, int yv) {
        int xp = 0;
        int yp = 0;
        int my = 0;
        while (true) {
            xp += xv;
            yp += yv;
            my = yp > my ? yp : my;
            if (xv < 0) {
                xv++;
            } else if (xv > 0) {
                xv--;
            }
            yv--;
            if (xp >= sx && xp <= ex && yp >= sy && yp <= ey) {
                return my;
            }
            if (xp > ex || yp < sy) {
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        final String line = readFile().split("\r?\n")[0];
        List<Integer> tttt = new ArrayList<>(4);
        for (String s : line.split("[^0-9]+=")) {
            if (s.isBlank()) {
                continue;
            }
            for (String l : s.split("\\.\\.")) {
                tttt.add(Integer.parseInt(l));
            }
        }

        sx = tttt.get(0);
        ex = tttt.get(1);
        sy = tttt.get(2);
        ey = tttt.get(3);
        int max = 0;
        for (int x = -1000; x <= 1000; x++) {
            for (int y = -1000; y <= 1000; y++) {
                final int res = calc(x, y);
                if (res != -1) {
                    max++;
                }
            }
        }
        System.out.println(max);
    }
}
