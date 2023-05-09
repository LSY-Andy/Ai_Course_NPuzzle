package stud.g05.queue;

import java.security.SecureRandom;

public class Zobrist {
    public static int[] zobristHash4 = Zobrist.getZobrist(4);
    public static int[] zobristHash3 = Zobrist.getZobrist(3);

    /**
     * Zobrist�ĳ�һά����
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
