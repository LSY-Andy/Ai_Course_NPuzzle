package stud.g05.problem.npuzzle;

import stud.g05.queue.Zobrist;
import core.problem.Action;
import core.problem.State;

public class PuzzleBoard extends State {
    private int[][] puzzle; //棋盘状态
    private int size; //棋盘大小
    @Override
    public void draw() {

    }
    @Override
    public State next(Action action) {
        return null;
    }

    @Override
    public Iterable<? extends Action> actions() {
        return null;
    }

    /**
     * Zobrist哈希随机数生成 【改变hashcode可以用来比较Zobrist哈希与普通哈希】
     */
    @Override
    public int hashCode() {
        int num=0;
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                num ^= Zobrist.getZobrist()[i * size + j][puzzle[i][j]]; // 随机数做异或运算
            }
        }
        return num;
    }
}
