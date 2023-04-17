package stud.g05.queue;

import java.security.SecureRandom;

public class Zobrist {
    private static int[][] zobrist;

    public Zobrist(int size) {
        SecureRandom rand = new SecureRandom();
        int totalSize = size * size;
        zobrist = new int[totalSize][];
        for (int i = 0; i < totalSize; i++) { // ¹¹ÔìÆåÅÌ
            zobrist[i] = new int[totalSize];
            for (int j = 0; j < totalSize; j++) {
                zobrist[i][j] = rand.nextInt();
            }
        }
    }

    public static int[][] getZobrist() {
        return zobrist;
    }
}
