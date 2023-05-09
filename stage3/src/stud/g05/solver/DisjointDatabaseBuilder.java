package stud.g05.solver;

import  stud.problem.npuzzle.SubPuzzleState;
import  stud.problem.npuzzle.PuzzlePoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class DisjointDatabaseBuilder {

    private int size;
    private int n;
    private int classes;

    public static int[] positions = {-1, 0, 0, 1, 2, 1, 2, 0, 1, 3, 4, 2, 3, 5, 4, 5};
    public static int[] positions3 = {-1, 0, 1, 2, 3, 0, 1, 2, 3};
    public static int[] positions4 = {-1, 0, 0, 1, 2, 1, 2, 0, 1, 3, 4, 2, 3, 5, 4, 5};


    private final Queue<SubPuzzleState> stateQueue = new ArrayDeque<SubPuzzleState>();

    public DisjointDatabaseBuilder(int size, int classes, int n) {
        this.size = size;
        this.classes = classes;
        this.n = n;
    }

    public int[] breadFirstSreach(SubPuzzleState root, int num) {
        stateQueue.clear();
        stateQueue.add(root);
        HashSet<Integer> stateSet = new HashSet<>();
        stateSet.add(root.hashCode());
        SubPuzzleState state, child;
        int[] cost = new int[num];

        while (!stateQueue.isEmpty()) {
            state = stateQueue.poll();
            for (int i = 0; i < root.getN(); i++) {
                for (int d = 0; d < 4; d++) {
                    if (state.applicable(i, d)) {
                        child = state.move(i, d);
                        if (!stateSet.contains(child.hashCode())) {
                            stateQueue.add(child);
                            stateSet.add(child.hashCode());
                            cost[child.hashCode()] = child.getCost();
                        }
                    }
                }
            }
        }
        System.out.println("Map size = " + stateSet.size());
        stateSet.clear();
        return cost;
    }


    public void save(int[] cost, String filename) {
        FileWriter writeFile = null;
        try {
            File file = new File("C:\\Users\\oucsh\\Desktop\\Final_NPuzzle_g05\\stage3\\resources\\" + filename);
            if(!file.exists()) {
                file.createNewFile();
            }
            writeFile = new FileWriter(file);
            for (int val: cost) {
                writeFile.write(val+ "\t");
            }
            writeFile.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writeFile != null) {
                    writeFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void bulidPuzzle3() {
        HashMap[] maps = new HashMap[2];
        DisjointDatabaseBuilder dpd = new DisjointDatabaseBuilder(3, 2,4);
        PuzzlePoint[] points = {
                new PuzzlePoint(0, 0, 1),
                new PuzzlePoint(0, 1, 2),
                new PuzzlePoint(0, 2, 3),
                new PuzzlePoint(1, 0, 4),
        };
        SubPuzzleState root = new SubPuzzleState(3, 4, 4, points);
        int[] cost = dpd.breadFirstSreach(root, root.getNum());
        dpd.save(cost,"database13.db");
        PuzzlePoint[] points2 = {
                new PuzzlePoint(1, 1, 5),
                new PuzzlePoint(1, 2, 6),
                new PuzzlePoint(2, 0, 7),
                new PuzzlePoint(2, 1, 8),
        };
        SubPuzzleState root2 = new SubPuzzleState(3, 4, 4, points2);
        cost = dpd.breadFirstSreach(root2, root2.getNum());
        dpd.save(cost,"database23.db");
        System.out.println("===================");
    }

    public static void bulidPuzzle4_663() {
        DisjointDatabaseBuilder dpd = new DisjointDatabaseBuilder(4, 3,6);
        PuzzlePoint[] points = {
                new PuzzlePoint(0, 0, 1),
                new PuzzlePoint(1, 0, 5),
                new PuzzlePoint(1, 1, 6),
                new PuzzlePoint(2, 0, 9),
                new PuzzlePoint(2, 1, 10),
                new PuzzlePoint(3, 0, 13),
        };
        PuzzlePoint[] points2 = {

                new PuzzlePoint(1, 2, 7),
                new PuzzlePoint(1, 3, 8),
                new PuzzlePoint(2, 2, 11),
                new PuzzlePoint(2, 3, 12),
                new PuzzlePoint(3, 1, 14),
                new PuzzlePoint(3, 2, 15),
        };
        PuzzlePoint[] points3 = {
                new PuzzlePoint(0, 1, 2),
                new PuzzlePoint(0, 2, 3),
                new PuzzlePoint(0, 3, 4),
        };
        SubPuzzleState root = new SubPuzzleState(4, 6, 6, points);
        int[] cost = dpd.breadFirstSreach(root, root.getNum());
        dpd.save(cost,"database14.db");
        System.out.println("build database14.db finish");
        System.out.println("===================");

        SubPuzzleState root2 = new SubPuzzleState(4, 6, 6, points2);
        cost = dpd.breadFirstSreach(root2, root2.getNum());
        dpd.save(cost,"database24.db");
        System.out.println("build database24.db finish");
        System.out.println("===================");

        SubPuzzleState root3 = new SubPuzzleState(4, 3, 6, points3);
        cost = dpd.breadFirstSreach(root3, root3.getNum());
        dpd.save(cost,"database34.db");
        System.out.println("database34.db finish");
        System.out.println("===================");
    }

    public static void main(String[] args) {
        bulidPuzzle3();
        bulidPuzzle4_663();
    }
}
