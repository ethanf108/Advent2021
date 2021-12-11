
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent6 {

    private static final Class<?>[] NO_REMOVE_IMPORT = new Class<?>[]{Stream.class, List.class};

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    public static class si implements Iterator<String> {

        private final String[] ll;
        private int i = 0;

        public si(String[] d) {
            this.ll = d;
        }

        @Override
        public boolean hasNext() {
            return i < ll.length;
        }

        @Override
        public String next() {
            return ll[i++];
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

    static final int[] lll = {1098932, 1017365, 936784, 843075, 795747, 703142, 669074, 593335, 556666};

    public static void main_2(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        Long[] f = new Long[9];
        Arrays.fill(f, 0L);
        Arrays.stream(lines[0].split(",")).mapToInt(Integer::parseInt).forEach(n -> f[n]++);
        LinkedList<Long> fish = new LinkedList<>(Arrays.asList(f));
        for (int day = 0; day < 256; day++) {
            long nz = fish.poll();
            fish.offer(nz);
            fish.set(6, fish.get(6) + nz);
        }
        System.out.println(fish.parallelStream().reduce(Long::sum).get());
    }

    public static int fish(int days, int n) {
        if (days <= 0) {
            return 1;
        } else if (n == 0) {
            return fish(days - 1, 6) + fish(days - 1, 8);
        } else {
            return fish(days - 1, n - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(fish(156, 0));
    }

    public static void main_(String[] args) throws IOException {
        final String[] lines = readFile().split("\r?\n");
        List<Integer> fish = Arrays.stream(lines[0].split(",")).map(Integer::parseInt).collect(Collectors.toList());
        for (int day = 0; day < 100; day++) {
            int nf = 0;
            for (int i = 0; i < fish.size(); i++) {
                fish.set(i, fish.get(i) - 1);
                if (fish.get(i) == -1) {
                    fish.set(i, 6);
                    nf++;
                }
            }
            while (nf-- > 0) {
                fish.add(8);
            }
            //System.out.println(day + " " + fish.size());
        }
        System.out.println(fish.size());
        long t = 0;
        for (int i : fish) {
            t += lll[i];
        }
        System.out.println(t);
    }
}
