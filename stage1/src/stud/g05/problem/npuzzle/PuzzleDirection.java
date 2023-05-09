package stud.g05.problem.npuzzle;

import java.util.EnumMap;
import java.util.List;


public enum PuzzleDirection {
    N('↑'),  //上北 north
    E('→'),  //右东 east
    S('↓'),  //下南 south
    W('←');  //左西 west
    /**
     * 构造函数
     * @param symbol 枚举项的代表符号--箭头
     */
    PuzzleDirection(char symbol){
        this.symbol = symbol;
    }
    private final char symbol;
    public char symbol(){
        return symbol;
    }
    public static final int SCALE = 1;       //单元格的长度
    public static final List<PuzzleDirection> FOUR_DIRECTIONS = List.of(PuzzleDirection.values());
    public static int cost(PuzzleDirection dir){
        return SCALE;
    }
    //各个方向移动的坐标位移量
    private static final EnumMap<PuzzleDirection, int[]> DIRECTION_OFFSET = new EnumMap<>(PuzzleDirection.class);
    static{
        //列号（或横坐标）增加量；行号（或纵坐标）增加量
        DIRECTION_OFFSET.put(N, new int[]{-1, 0});
        DIRECTION_OFFSET.put(W, new int[]{0, -1});
        DIRECTION_OFFSET.put(S, new int[]{1, 0});
        DIRECTION_OFFSET.put(E, new int[]{0, 1});
    }
    public static int[] offset(PuzzleDirection dir){
        return DIRECTION_OFFSET.get(dir);
    }
}
