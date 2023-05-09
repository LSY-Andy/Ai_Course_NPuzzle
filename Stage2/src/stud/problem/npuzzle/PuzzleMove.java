package stud.problem.npuzzle;

import core.problem.Action;

public class PuzzleMove extends Action {

    public PuzzleMove(Direction direction){  //构造函数
        this.direction =direction;
    }

    private static int count = 1;
    @Override
    public int stepCost() {
        return 1;  //cost固定是1
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        PuzzleMove another = (PuzzleMove) obj;
            //两个Node对象的状态相同，则认为是相同的
        return this.direction.equals(another.direction);
    }
    @Override
    public void draw() {
        System.out.println("   ↓");
        System.out.println("   ↓-(#, "+getDirection() + ")");
        System.out.println("   ↓");
    }

    private Direction direction;


    public Direction getDirection() {    //取方向
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public static int getCount() {    //取数
        return count;
    }

    public static void setCount(int count) {  PuzzleMove.count = count;
    }


}
