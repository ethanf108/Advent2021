package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Advent21 {

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    private static int die = 1;

    private static int roll() {
        return die++;
    }

    public static void main_1(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        int p1 = Integer.parseInt(lines[0].substring(28));
        int p2 = Integer.parseInt(lines[1].substring(28));
        int p1s = 0, p2s = 0;
        while (true) {
            for (int i = 0; i < 3; i++) {
                p1 += roll();
                while (p1 > 10) {
                    p1 -= 10;
                }
            }
            p1s += p1;
            if (p1s >= 1000) {
                System.out.println(p2s * (die - 1));
                return;
            }
            for (int i = 0; i < 3; i++) {
                p2 += roll();
                while (p2 > 10) {
                    p2 -= 10;
                }
            }
            p2s += p2;
            if (p2s >= 1000) {
                System.out.println(p1s * (die - 1));
                return;
            }
        }
    }

    private static record Score(int p1, int p1s, int p2, int p2s, boolean p1Turn) {

    }

    public static void main_2(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        final int p1_ = Integer.parseInt(lines[0].substring(28));
        final int p2_ = Integer.parseInt(lines[1].substring(28));
        Map<Score, Long> scores = new HashMap<>();
        int p1w = 0, p2w = 0;
        scores.put(new Score(p1_, 0, p2_, 0, true), 1L);
        while (!scores.isEmpty()) {
            Map<Score, Long> ns = new HashMap<>();
            for (Score s : scores.keySet()) {
                for (int r1 = 1; r1 <= 3; r1++) {
                    for (int r2 = 1; r2 <= 3; r2++) {
                        for (int r3 = 1; r3 <= 3; r3++) {
                            if (s.p1Turn()) {
                                int p1 = s.p1() + r1 + r2 + r3;
                                while (p1 > 10) {
                                    p1 -= 10;
                                }
                                int p1s = s.p1s() + p1;
                                if (p1s >= 21) {
                                    p1w += scores.get(s);
                                    continue;
                                }
                                ns.merge(new Score(p1, p1s, s.p2(), s.p2s(), !s.p1Turn()), scores.get(s), Long::sum);
                            } else {
                                int p2 = s.p2() + r1 + r2 + r3;
                                while (p2 > 10) {
                                    p2 -= 10;
                                }
                                int p2s = s.p2s() + p2;
                                if (p2s >= 21) {
                                    p2w += scores.get(s);
                                    continue;
                                }
                                ns.merge(new Score(s.p1(), s.p1s(), p2, p2s, !s.p1Turn()), scores.get(s), Long::sum);
                            }
                        }
                    }
                }
            }
            scores = ns;
        }
        System.out.println(Math.max(p1w, p2w));
    }
}
