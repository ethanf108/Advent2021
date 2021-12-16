package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent16 {

    private static final Class<?>[] NO_REMOVE_IMPORT = new Class<?>[]{Stream.class, List.class};

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    public static class si implements Iterator<Integer> {

        private final Queue<Integer> s;
        long pos = 0;

        public si(String d) {
            s = new ArrayDeque<>();
            for (char c : d.toCharArray()) {
                String s = Integer.toBinaryString(Integer.parseInt(c + "", 16));
                while (s.length() < 4) {
                    s = "0" + s;
                }
                for (char r : s.toCharArray()) {
                    this.s.offer(Integer.parseInt(r + ""));
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !s.isEmpty();
        }

        @Override
        public Integer next() {
            pos++;
            return s.poll();
        }

        public long nextN(int n) {
            long r = 0;
            while (n-- > 0) {
                r <<= 1;
                r += next();
            }
            return r;
        }

        public long pos() {
            return pos;
        }

    }

    static int max(int... l) {
        int m = 0;
        for (int i : l) {
            if (i > m) {
                m = i;
            }
        }
        return m;
    }

    static int min(int... l) {
        int m = 99999999;
        for (int i : l) {
            if (i < m) {
                m = i;
            }
        }
        return m;
    }

    static si it;

    static long readPacket() {
        final long version = it.nextN(3);
        final long type = it.nextN(3);
        if (type == 4) {
            long l, sum = 0;
            do {
                l = it.nextN(5);
                sum <<= 4;
                sum += l & 0b1111;
            } while ((l & 0b10000) > 0);
            return sum;
        } else {
            List<Long> als = new ArrayList<>();
            if (it.next().equals(1)) {
                long numPackets = it.nextN(11);
                while (numPackets-- > 0) {
                    als.add(readPacket());
                }
            } else {
                long tPos = it.nextN(15) + it.pos;
                while (it.pos < tPos) {
                    als.add(readPacket());
                }
            }
            long result = switch ((int) type) {
                case 0 ->
                    als.stream().mapToLong(n -> n).sum();
                case 1 ->
                    als.stream().mapToLong(n -> n).reduce(1, (a, b) -> a * b);
                case 2 ->
                    als.stream().mapToLong(n -> n).min().getAsLong();
                case 3 ->
                    als.stream().mapToLong(n -> n).max().getAsLong();
                case 5 ->
                    als.get(0) > als.get(1) ? 1 : 0;
                case 6 ->
                    als.get(0) < als.get(1) ? 1 : 0;
                case 7 ->
                    als.get(0).equals(als.get(1)) ? 1 : 0;
                default ->
                    throw new IllegalArgumentException("BRUH");
            };
            return result;
        }
    }

    public static void main(String[] args) {
        final String line = readFile().split("\r?\n")[0];
        it = new si(line);
        System.out.println(readPacket());
    }
}
