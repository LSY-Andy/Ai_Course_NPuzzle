package stud.g05.problem.npuzzle;

import stud.g05.queue.Zobrist;
import core.problem.Action;
import core.problem.State;

public class PuzzleBoard extends State {
    private int[][] puzzle; //����״̬
    private int size; //���̴�С
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
     * Zobrist��ϣ��������� ���ı�hashcode���������Ƚ�Zobrist��ϣ����ͨ��ϣ��
     */
    @Override
    public int hashCode() {
        int num=0;
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                num ^= Zobrist.getZobrist()[i * size + j][puzzle[i][j]]; // ��������������
            }
        }
        return num;
    }
}
