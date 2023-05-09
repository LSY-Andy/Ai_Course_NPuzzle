package stud.problem.npuzzle;

//一个三元组(x,y,value)，表示当前SubPuzzleState中位置(x,y)上的值为value
public class PuzzlePoint {

    // 行列坐标和位将牌值
    private int row;
    private int col;
    private int val;

    /**
     * 构造函数
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
