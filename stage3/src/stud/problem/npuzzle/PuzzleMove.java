package stud.problem.npuzzle;

import core.problem.Action;

/**
 *
 * 寻路的移动动作，根据情况，可以NSEW四个方向，或者8个方向。
 */
public class PuzzleMove extends Action {

    private final PuzzleDirection puzzleDirection;

    public PuzzleMove(PuzzleDirection puzzleDirection) {
        this.puzzleDirection = puzzleDirection;
    }

    public PuzzleDirection getDirection() {
        return puzzleDirection;
    }

    @Override
    public void draw() {
        System.out.println("   ↓");
        System.out.println("   ↓-(#, " + puzzleDirection.symbol() + ")");
        System.out.println("   ↓");
    }

    @Override
    public int stepCost() {
        return 1;
    }

}
