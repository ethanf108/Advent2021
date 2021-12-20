package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Advent20 {

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    static class Point {

        final int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point p) {
                return x == p.x && y == p.y;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + this.x;
            hash = 17 * hash + this.y;
            return hash;
        }
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        final String enhance = lines[0];
        List<Point> points = new ArrayList<>();
        {
            int y = 0;
            for (String line : lines) {
                if (line.length() == 512 || line.isBlank()) {
                    continue;
                }
                int x = 0;
                for (char c : line.toCharArray()) {
                    if (c == '#') {
                        points.add(new Point(x, y));
                    }
                    x++;
                }
                y++;
            }
        }
        for (int step = 1; step <= 50; step++) {
            List<Point> np = new ArrayList<>();
            final int minX = points.parallelStream().mapToInt(Point::x).min().getAsInt();
            final int maxX = points.parallelStream().mapToInt(Point::x).max().getAsInt();
            final int minY = points.parallelStream().mapToInt(Point::y).min().getAsInt();
            final int maxY = points.parallelStream().mapToInt(Point::y).max().getAsInt();
            for (int x = minX - 1; x <= maxX + 1; x++) {
                for (int y = minY - 1; y <= maxY + 1; y++) {
                    String pos = "";
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if (points.contains(new Point(x + dx, y + dy)) || ((x + dx < minX || x + dx > maxX || y + dy < minY || y + dy > maxY) && step % 2 == 0)) {
                                pos += "1";
                            } else {
                                pos += "0";
                            }
                        }
                    }
                    if (enhance.charAt(Integer.parseInt(pos, 2)) == '#') {
                        np.add(new Point(x, y));
                    }
                }
            }
            points = np;
        }
        System.out.println(points.size());
    }
}
