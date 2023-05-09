package stud.g05.problem.npuzzle;

import core.problem.Action;
import core.problem.State;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import stud.g05.queue.Zobrist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;

import static core.solver.algorithm.heuristic.HeuristicType.*;


public class PuzzleBoard extends State{
    public int size ; //大小
    public byte[] states; //局面
    private int col = 0; //空格的位置
    private int row = 0;
    private int hash = 0; //格局hash值
    private int misplacedDistance = 0;//错位数
    private int manhattanDistance  = 0; //曼哈顿距离

    public static Predictor predictor(HeuristicType type) {
        return predictors.get(type);
    }
    public int getSize() { return size; }
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setHash(int hash) {
        this.hash = hash;
    }
    public byte[] getStates() {
        return states;
    }
    private static final EnumMap<HeuristicType, Predictor> predictors = new EnumMap<>(HeuristicType.class);

    static {
        predictors.put(MISPLACED, (state, goal) -> ((PuzzleBoard) state).getMisplacedDistance());
        predictors.put(MANHATTAN, (state, goal) -> ((PuzzleBoard) state).getManhattanDistance());
        predictors.put(LINEAR_CONFLICT, (state, goal) -> ((PuzzleBoard) state).getLinearConflictDistance());
    }

    public PuzzleBoard(PuzzleBoard state) {
        this.size = state.getSize();
        this.states = new byte[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.states[i * size + j] = state.getStates()[i * size + j];
            }
        }
    }

    public PuzzleBoard(int size, byte[] board, boolean isRoot) {
        this.size = size;
        this.states = new byte[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.states[i * size + j] = board[i * size + j];
                if (this.states[i * size + j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
        //只有根节点需要计算曼哈顿距离、错位数，后续动态更新即可
        if (isRoot) {
            misplacedDistance = misplaced(states);
            manhattanDistance = manhattan(states);
        }
        //zobristHash
        for (int i = 0; i < size * size; i++) {
            if (states[i] != 0) {
                int index = i * size * size + states[i];
                if (size == 4) {
                    hash ^= Zobrist.zobristHash4[index];
                } else {
                    hash ^= Zobrist.zobristHash3[index];
                }
            }
        }
    }

    @Override
    public void draw() {
        if(this.getSize() == 3){
            for(int i=0;i<this.getSize();i++) {
                System.out.println("+---+---+---+");
                for(int j=0;j<this.getSize();j++)
                {
                    int index = i * size + j;
                    if(states[index] == 0){
                        System.out.print("| # ");
                    }else {
                        System.out.print("| " + states[index] + " ");
                    }
                }
                System.out.print("|");
                System.out.println();
            }
            System.out.println("+---+---+---+");
        }else{
            for(int i=0;i<this.getSize();i++) {
                System.out.println("+---+---+---+---+");
                for(int j=0;j<this.getSize();j++)
                {
                    int index = i * size + j;
                    if(states[index] == 0){
                        System.out.print("| # ");
                    }else if(states[index] >= 10){
                        System.out.print("|" + states[index] + " ");
                    }else{
                        System.out.print("| " + states[index] + " ");
                    }
                }
                System.out.print("|");
                System.out.println();
            }
            System.out.println("+---+---+---+---+");
        }
    }

    @Override
    public State next(Action action) {
        //得到当前action的位移量，由此得到新的行列
        PuzzleDirection direction = ((PuzzleMove) action).getDirection();
        int[] offsets = PuzzleDirection.offset(direction);
        int newRow = row + offsets[0];
        int newCol = col + offsets[1];

        //得到新格局
        PuzzleBoard newState = new PuzzleBoard(this);
        int newIndex = newRow * size + newCol;
        int oldIndex = row * size + col;
        byte val = states[newIndex];
        newState.states[oldIndex] = val;
        newState.states[newIndex] = 0;
        newState.setCol(newCol);
        newState.setRow(newRow);

        //动态修改曼哈顿距离,这里的abs都用的三目运算符来提高效率
        int old = (((val - 1) / size - newRow < 0) ? -((val - 1) / size - newRow) : (val - 1) / size - newRow)+(((val - 1) % size - newCol < 0) ? -((val - 1) % size - newCol) : (val - 1) % size - newCol);
        int nw = (((val - 1) / size - row < 0) ? -((val - 1) / size - row) : (val - 1) / size - row)+(((val - 1) % size - col < 0) ? -((val - 1) % size - col) : (val - 1) % size - col);
        newState.setManhattanDistance(manhattanDistance - old + nw);

        //动态修改不在位将牌
        int v=0;
        if(row * size +col +1 == val) v--;
        if(row * size +col +1 == 9) v++;
        if(newRow * size + newCol +1 == val) v++;
        if(newRow * size + newCol +1 == 9) v--;
        newState.setMisplacedDistance(misplacedDistance+v);

        //Zobrist改成一维数组
        int index1 = newRow * size * size + val;
        int index2 = row * size * size + val;

        if (size == 4) {
            newState.setHash(hash ^ Zobrist.zobristHash4[index1] ^ Zobrist.zobristHash4[index2]);
        } else {
            newState.setHash(hash ^ Zobrist.zobristHash3[index1] ^ Zobrist.zobristHash3[index2]);
        }
        return newState;
    }

    @Override
    public Iterable<? extends Action> actions() {
        Collection<PuzzleMove> moves = new ArrayList<>();
        for (PuzzleDirection d : PuzzleDirection.FOUR_DIRECTIONS)
            moves.add(new PuzzleMove(d));
        return moves;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {                    //用于比较两个棋盘是否相同
        PuzzleBoard another = (PuzzleBoard) obj;
        for (int i = 0; i < this.size * this.size; i++) {
            if (this.states[i] != another.states[i]) {
                return false;
            }
        }
        return true;
    }

    public int getMisplacedDistance() {
        return misplacedDistance;
    }
    public void setMisplacedDistance(int misplacedDistance) {
        this.misplacedDistance = misplacedDistance;
    }
    public int misplaced(byte[] states) {
        int h = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int index = i * size + j;
                if (states[index] == 0 && i * size + j + 1 == size * size) continue;
                if (states[index] != i * size + j + 1) h++;
            }
        }
        return h;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }
    public void setManhattanDistance(int manhattanDistance) {
        this.manhattanDistance = manhattanDistance;
    }
    public int manhattan(byte[] states){
        int h = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int index = i * size + j;
                if (states[index] != 0) {
                    int rowGoal = (states[index] - 1) / size;
                    int colGoal = (states[index] - 1) % size;
                    h += ((i-rowGoal < 0) ? -(i-rowGoal) : i-rowGoal) + ((j-colGoal < 0) ? -(j-colGoal) : j-colGoal);
                }
            }
        }
        return h;
    }

    public int getLinearConflictDistance() {
        return manhattanDistance+2*nLinearConflicts();
    }
    public int nLinearConflicts(){
        int conflicts = 0;
        int[] pR = new int[size*size + 1];
        int[] pC = new int[size*size + 1];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int index = r*size+c;
                pR[states[index]] = r;
                pC[states[index]] = c;
            }
        }

        for (int r = 0; r < size; r++) {
            for (int cl = 0; cl < size; cl++) {
                for (int cr = cl + 1; cr < size; cr++) {
                    if ( (r*size + cl +1)!=0 && (r*size + cr +1)!=0 && r == pR[(r*size + cl +1)]
                            && pR[(r*size + cl +1)] == pR[(r*size + cr +1)] &&
                            pC[(r*size + cl +1)] > pC[(r*size + cr +1)]) {
                        conflicts++;
                    }
                    if ((cl*size +  r +1)!=0 && (cr*size +  r +1)!=0 &&  r == pC[(cl*size +  r +1)]
                            && pC[(cl*size +  r +1)] == pC[(cr*size +  r +1)] &&
                            pR[(cl*size +  r +1)] > pR[(cr*size + r +1)]) {
                        conflicts++;
                    }
                }
            }
        }
        return conflicts;
    }
}

