package stud.problem.npuzzle;

import java.security.SecureRandom;

public class Zobrist {
//    public static long x=0;
    public static int[] zobristHash4 = Zobrist.getZobrist(4);
    public static int[] zobristHash3 = Zobrist.getZobrist(3);
    /**
     * Zobrist改成一维数组
     * @param size
     * @return
     */
    public static int[] getZobrist(int size) {
        SecureRandom rand = new SecureRandom();
        int[] zobrist = new int[size * size * size * size];

        for (int i = 0; i < size * size * size * size; i++) {
            zobrist[i] = rand.nextInt();
        }

        return zobrist;
    }
}
