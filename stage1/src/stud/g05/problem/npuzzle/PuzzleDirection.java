package stud.g05.problem.npuzzle;

import java.util.EnumMap;
import java.util.List;


public enum PuzzleDirection {
    N('��'),  //�ϱ� north
    E('��'),  //�Ҷ� east
    S('��'),  //���� south
    W('��');  //���� west
    /**
     * ���캯��
     * @param symbol ö����Ĵ������--��ͷ
     */
    PuzzleDirection(char symbol){
        this.symbol = symbol;
    }
    private final char symbol;
    public char symbol(){
        return symbol;
    }
    public static final int SCALE = 1;       //��Ԫ��ĳ���
    public static final List<PuzzleDirection> FOUR_DIRECTIONS = List.of(PuzzleDirection.values());
    public static int cost(PuzzleDirection dir){
        return SCALE;
    }
    //���������ƶ�������λ����
    private static final EnumMap<PuzzleDirection, int[]> DIRECTION_OFFSET = new EnumMap<>(PuzzleDirection.class);
    static{
        //�кţ�������꣩���������кţ��������꣩������
        DIRECTION_OFFSET.put(N, new int[]{-1, 0});
        DIRECTION_OFFSET.put(W, new int[]{0, -1});
        DIRECTION_OFFSET.put(S, new int[]{1, 0});
        DIRECTION_OFFSET.put(E, new int[]{0, 1});
    }
    public static int[] offset(PuzzleDirection dir){
        return DIRECTION_OFFSET.get(dir);
    }
}
