package stud.problem.npuzzle;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.solver.queue.Node;
import stud.g05.solver.myDataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.Scanner;

public class PuzzleProblem extends Problem {
    //public static DisjointPatternDatabase puzzle3;
    //public static DisjointPatternDatabase puzzle4;
    public static int[][] puzzle3;
    public static int[][] puzzle4;
    public static int[] subsets3;
    public static int[] positions3;
    public static int[] subsets4;
    public static int[] positions4;
    //读取文件内容，加载DisjointPattern数据库

    public static void read2(String filename, int i) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("\t");
                for (int j = 0; j < line.length; j++) {
                    puzzle3[i][j] = Integer.parseInt(line[j]);
                }
            }
            System.out.println("class-" + i + ", length: "+ puzzle3[i].length);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void load(){
        System.out.println("load puzzle3");
        puzzle3=new int[2][(int) Math.pow(9, 4)];
        read2("stage3/resources/database1.db", 0);
        read2("stage3/resources/database2.db", 1);


        puzzle4=new int[3][myDataBase.costTable_15_puzzle_1.length];
        for(int i=0;i< myDataBase.costTable_15_puzzle_0.length;++i)
        {
            puzzle4[0][i]=myDataBase.costTable_15_puzzle_0[i];
        }
        for(int i=0;i< myDataBase.costTable_15_puzzle_1.length;++i)
        {
            puzzle4[1][i]=myDataBase.costTable_15_puzzle_1[i];
        }
        for(int i=0;i< myDataBase.costTable_15_puzzle_2.length;++i)
        {
            puzzle4[2][i]=myDataBase.costTable_15_puzzle_2[i];
        }


        positions3 = new int[]{-1, 0, 1, 2, 3, 0, 1, 2, 3};
        subsets3 = new int[]{-1, 0, 0, 0, 0, 1, 1, 1, 1};
        positions4 = new int[]{-1, 0, 0, 1, 2, 1, 2, 0, 1, 3, 4, 2, 3, 5, 4, 5};
        subsets4 = new int[]{-1, 1, 0, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2};
    }

    public PuzzleProblem(State initialState, State goal, int size) {
        super(initialState, goal, size);
    }

    /**
     * 当前问题是否有解
     * 因为只有通过搜索来判断，所以先默认为true
     * @return 有解，true; 无解，false
     */
    @Override
    public boolean solvable() {
        PuzzleBoard.init(size, (PuzzleBoard) goal);
        return ((PuzzleBoard)initialState).parity() == ((PuzzleBoard)goal).parity();
    }


    @Override
    public int stepCost(State state, Action action) {
        return 1;
    }

    @Override
    public boolean applicable(State state, Action action) {
        int[] offsets = PuzzleDirection.offset(((PuzzleMove)action).getDirection());
        int row = ((PuzzleBoard)state).blank[0]+offsets[0];
        int col = ((PuzzleBoard)state).blank[1]+offsets[1];
        return row >= 0 && row < size &&
                col >= 0 && col < size ;
    }

    @Override
    public void showSolution(Deque<Node> path) {
        if (path == null){
            System.out.println("No Solution.");
            return;
        }
        initialState.draw();
        for (Node node : path) {
            PuzzleMove puzzleMove = (PuzzleMove) (node.getAction());
            puzzleMove.draw();
            PuzzleBoard puzzleBoard = (PuzzleBoard) (node.getState());
            puzzleBoard.draw();
        }
        System.out.println();
    }
}
