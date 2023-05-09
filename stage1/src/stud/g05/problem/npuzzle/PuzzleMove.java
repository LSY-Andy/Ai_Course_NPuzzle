package stud.g05.problem.npuzzle;

import core.problem.Action;

/**
 * Description:
 *
 * @date:2022/10/3 19:09
 * @author:Karthus77
 */
public class PuzzleMove extends Action {
    private final PuzzleDirection direction;

    public PuzzleMove(PuzzleDirection direction) {
        this.direction = direction;
    }

    public PuzzleDirection getDirection() {
        return direction;
    }


    private String dir;
    @Override
    public void draw() {
        System.out.println("   ↓");
        System.out.println("   ↓-(#, "+getDirection() + ")");
        System.out.println("   ↓");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        PuzzleMove another = (PuzzleMove) obj;
        //两个Node对象的状态相同，则认为是相同的
        return this.direction.equals(another.direction);
    }
    @Override
    public int stepCost() {
        return 1;
    }
}

