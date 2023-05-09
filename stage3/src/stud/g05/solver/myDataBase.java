package stud.g05.solver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//模式数据库发生器
public final class myDataBase {

    public static final byte[] costTable_15_puzzle_0 = new byte[4096],
            costTable_15_puzzle_1 = new byte[16777216],
            costTable_15_puzzle_2 = new byte[16777216];

    static {
        loadStreamCostTable("stage3/resources/database1.db", costTable_15_puzzle_0);
        loadStreamCostTable("stage3/resources/database2.db", costTable_15_puzzle_1);
        loadStreamCostTable("stage3/resources/database3.db", costTable_15_puzzle_2);
    }

    private myDataBase() { }

    private static void loadStreamCostTable(final String filename,
                                            final byte[] costTable) {
        InputStream is = myDataBase.class.getResourceAsStream(filename);
        DataInputStream dis = null;
        try {
            if (is == null) {
                is = new FileInputStream(filename);
            }
            dis = new DataInputStream(new BufferedInputStream(is));
            int i = 0;
            while (true) {
                costTable[i++] = dis.readByte();
            }
        } catch (final EOFException eofe) {

        } catch (final FileNotFoundException fnfe) {
            System.err.println("Error: Cannot find file " + filename + ".");
            System.exit(1);
        } catch (final IOException ioe) {
            System.err.println("Error: Cannot read from file " + filename + ".");
            System.exit(1);
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (final IOException ioe) { }
        }
    }
}