package stud.problem.npuzzle;

import java.util.EnumMap;
import java.util.List;

/**
 * 
 * 地图中可以移动的8个方向，及其箭头符号
 */
public enum PuzzleDirection {
    N('1'),  //北
    NE('1'), //东北
    E('1'),  //东
    SE('1'), //东南
    S('1'),  //南
    SW('1'), //西南
    W('1'),  //西
    NW('1'); //西北

    private final char symbol;

    /**
     * 构造函数
     * @param symbol 枚举项的代表符号--箭头
     */
    PuzzleDirection(char symbol){
        this.symbol = symbol;
    }

    public char symbol(){
        return symbol;
    }

    public static final int SCALE = 10;       //单元格的边长
    public static final double ROOT2 = 1.4;   //2的平方根

    /**
     * 移动方向的两种不同情况（4个方向，8个方向）。
     */
    public static final List<PuzzleDirection> FOUR_PUZZLE_DIRECTIONS = List.of(PuzzleDirection.N, PuzzleDirection.E, PuzzleDirection.S, PuzzleDirection.W);
    public static final List<PuzzleDirection> EIGHT_PUZZLE_DIRECTIONS = List.of(PuzzleDirection.values());
    
    /**
     * 不同方向的耗散值
     */
    public static int cost(PuzzleDirection dir){
        return FOUR_PUZZLE_DIRECTIONS.contains(dir) ? SCALE : (int) (SCALE * ROOT2);
    }

    //各个方向移动的坐标位移量
    private static final EnumMap<PuzzleDirection, int[]> DIRECTION_OFFSET = new EnumMap<>(PuzzleDirection.class);
    static{
        //列号（或横坐标）增加量；行号（或纵坐标）增加量
        DIRECTION_OFFSET.put(N, new int[]{-1, 0});
        DIRECTION_OFFSET.put(NE, new int[]{1, -1});
        DIRECTION_OFFSET.put(E, new int[]{0, 1});
        DIRECTION_OFFSET.put(SE, new int[]{1, 1});
        DIRECTION_OFFSET.put(S, new int[]{1, 0});
        DIRECTION_OFFSET.put(SW, new int[]{-1, 1});
        DIRECTION_OFFSET.put(W, new int[]{0, -1});
        DIRECTION_OFFSET.put(NW, new int[]{-1, -1});
    }
    
    public static int[] offset(PuzzleDirection dir){
        return DIRECTION_OFFSET.get(dir);
    }
}
