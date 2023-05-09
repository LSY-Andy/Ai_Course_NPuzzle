package stud.problem.npuzzle;

//һ����Ԫ��(x,y,value)����ʾ��ǰSubPuzzleState��λ��(x,y)�ϵ�ֵΪvalue
public class PuzzlePoint {

    // ���������λ����ֵ
    private int row;
    private int col;
    private int val;

    /**
     * ���캯��
     */
    public PuzzlePoint(int row, int col, int val) {
        this.row = row;
        this.col = col;
        this.val = val;
    }

    public PuzzlePoint(PuzzlePoint p) {
        this.row = p.row;
        this.col = p.col;
        this.val = p.val;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
