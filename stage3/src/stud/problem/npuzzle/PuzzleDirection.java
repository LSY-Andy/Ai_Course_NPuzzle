package stud.problem.npuzzle;

import java.util.EnumMap;
import java.util.List;

/**
 * 
 * ��ͼ�п����ƶ���8�����򣬼����ͷ����
 */
public enum PuzzleDirection {
    N('1'),  //��
    NE('1'), //����
    E('1'),  //��
    SE('1'), //����
    S('1'),  //��
    SW('1'), //����
    W('1'),  //��
    NW('1'); //����

    private final char symbol;

    /**
     * ���캯��
     * @param symbol ö����Ĵ������--��ͷ
     */
    PuzzleDirection(char symbol){
        this.symbol = symbol;
    }

    public char symbol(){
        return symbol;
    }

    public static final int SCALE = 10;       //��Ԫ��ı߳�
    public static final double ROOT2 = 1.4;   //2��ƽ����

    /**
     * �ƶ���������ֲ�ͬ�����4������8�����򣩡�
     */
    public static final List<PuzzleDirection> FOUR_PUZZLE_DIRECTIONS = List.of(PuzzleDirection.N, PuzzleDirection.E, PuzzleDirection.S, PuzzleDirection.W);
    public static final List<PuzzleDirection> EIGHT_PUZZLE_DIRECTIONS = List.of(PuzzleDirection.values());
    
    /**
     * ��ͬ����ĺ�ɢֵ
     */
    public static int cost(PuzzleDirection dir){
        return FOUR_PUZZLE_DIRECTIONS.contains(dir) ? SCALE : (int) (SCALE * ROOT2);
    }

    //���������ƶ�������λ����
    private static final EnumMap<PuzzleDirection, int[]> DIRECTION_OFFSET = new EnumMap<>(PuzzleDirection.class);
    static{
        //�кţ�������꣩���������кţ��������꣩������
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
