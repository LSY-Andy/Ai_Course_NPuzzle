package stud.problem.npuzzle;

import core.problem.Action;
import core.problem.State;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Random;
import static core.solver.algorithm.heuristic.HeuristicType.*;

/**
 * PathFinding问题的状态
 * 位置状态，表示寻路机器人在什么位置
 */
public class PuzzleBoard extends State {
    public final int[][] state;//状态
    public final int size;//问题规模
    public final int[] blank;//空白格位置
    public static int[][][] zobrist;
    //分组状态码，确定每个分组对应唯一值
    private int[] stateCode = null;
    public static int[][] db;
    public static int[] subsets;
    public static int[] positions;
    public static int classes;
    public int hash_code=0;

    public PuzzleBoard(int[][] state, int size) {
        this.size=size;
        this.state=new int[size][size];
        this.blank=new int[2];
        for(int i=0;i<size;++i)
        {
            for(int j=0;j<size;++j)
            {
                this.state[i][j]=state[i][j];
                if(state[i][j]==0)
                {
                    this.blank[0]=i;
                    this.blank[1]=j;
                }
            }
        }
    }
    public PuzzleBoard(PuzzleBoard p){
        this.size=p.size;
        this.state=new int[p.size][p.size];
        for(int i=0;i<p.size;++i)
        {
            for(int j=0;j<p.size;++j)
            {
                this.state[i][j]=p.state[i][j];
            }
        }
        this.blank=new int[2];
        this.blank[0]=p.blank[0];
        this.blank[1]=p.blank[1];
    }


    @Override
    public void draw() {
        drawLine();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("| %2d ", this.state[i][j]);
            }
            System.out.println("|");
            drawLine();
        }
    }
    public void drawLine() {
        for (int i = 0; i < size; i++)
            System.out.print("+----");
        System.out.println("+");
    }

    @Override
    //当前状态采用action而进入的下一个状态
    public State next(Action action) {
        //当前Action所带来的位移量
        PuzzleDirection dir = ((PuzzleMove)action).getDirection();
        int[] offsets = PuzzleDirection.offset(dir);

        //生成新状态所在的点
        PuzzleBoard nextState=new PuzzleBoard(this);
        //下一状态空白格的位置(i,j)

        int i=nextState.blank[0]+offsets[0];
        int j=nextState.blank[1]+offsets[1];
        //空白格state(blank[0],blank[1])与state(i,j)交换
        nextState.state[this.blank[0]][this.blank[1]]=nextState.state[i][j];
        nextState.state[i][j]=0;

        nextState.blank[0]=i;
        nextState.blank[1]=j;
//        System.out.println("cur draw");
//        this.draw();
//        System.out.println("nextState draw");
//        nextState.draw();
//        System.out.printf("blank=%d %d ij=%d %d\n",blank[0],blank[1],i,j);
//        System.out.printf("%d %d %c\n",offsets[0],offsets[1],((Move) action).getDirection().symbol());
//        System.out.println("end------------");

        return nextState;
    }

    @Override
    public Iterable<? extends Action> actions() {
        Collection<PuzzleMove> puzzleMoves = new ArrayList<>();
        for (PuzzleDirection d : PuzzleDirection.FOUR_PUZZLE_DIRECTIONS)
            puzzleMoves.add(new PuzzleMove(d));
        return puzzleMoves;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof PuzzleBoard) {
            PuzzleBoard another = (PuzzleBoard) obj;
            //两个Position对象的state，则认为是相同的
            return hashCode()==another.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if(hash_code==0)
        {
            int hash = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (state[i][j] != 0)  {
                        hash ^= zobrist[i][j][state[i][j]];
                    }
                }
            }
            hash_code=hash;
        }
        return hash_code;
    }
    public static void init(int size, PuzzleBoard goal) {
        System.out.print("position init---");
        Random r = new Random();
        zobrist = new int[size][size][size * size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                for(int k = 0; k < size * size; k++) {
                    zobrist[i][j][k] = r.nextInt();
                }
            }
        }
        switch (size) {
            case 3:
                db = PuzzleProblem.puzzle3;
                subsets= PuzzleProblem.subsets3;
                positions= PuzzleProblem.positions3;
                classes=2;
                break;
            case 4:
                db = PuzzleProblem.puzzle4;
                subsets= PuzzleProblem.subsets4;
                positions= PuzzleProblem.positions4;
                classes=3;
                break;
            default:
                break;
        }
        System.out.println("init done");
    }


    //状态的奇偶性
    public int parity() {
        // 根据Puzzle奇偶阶数分类讨论
        int n=(this.size%2==0)?blank[0]:0;
        for(int i=0;i<size;++i)
        {
            for(int j=0;j<size;++j)
            {
                //遍历v[i][j]之后的数，找到比v[i][j]小的数
                for(int ti=i,tj=j+1;ti<size;++ti)
                {
                    for(;tj<size;++tj)
                    {
                        if(this.state[ti][tj]<this.state[i][j]
                                &&this.state[ti][tj]!=0&&this.state[i][j]!=0)
                        {
                            ++n;
                        }
                    }
                    tj=0;
                }
            }
        }
        return n%2;
    }



    //枚举映射，存放不同类型的启发函数
    private static final EnumMap<HeuristicType, Predictor> predictors = new EnumMap<>(HeuristicType.class);
    static{
        predictors.put(MISPLACED,
                (state, goal) -> ((PuzzleBoard)state).misPlaced((PuzzleBoard)goal));
        predictors.put(MANHATTAN,
                (state, goal) -> ((PuzzleBoard)state).manhattan((PuzzleBoard)goal));
        //DISJOINT_PATTERN
        predictors.put(HeuristicType.DISJOINT_PATTERN,
                (state, goal) -> ((PuzzleBoard)state).getDisjointPattern((PuzzleBoard)goal));
    }
    
    public static Predictor predictor(HeuristicType type){
        return predictors.get(type);
    }

    //曼哈顿距离
    private int manhattan(PuzzleBoard goal) {
        int mht=0;
        for(int i=0;i<this.size;++i)
        {
            for(int j=0;j<this.size;++j)
            {
                if(this.state[i][j]==0)continue;
                int gi=0,gj=0;
                for(boolean flag=false;gi<this.size;++gi)
                {
                    for(gj=0;gj<this.size;++gj)
                    {
                        if(goal.state[gi][gj]==this.state[i][j])
                        {
                            flag=true;
                            break;
                        }
                    }
                    if(flag)break;
                }
                int t=((i-gi)>0?i-gi:gi-i)+((j-gj)>0?j-gj:gj-j);
                mht+=t;
            }
        }
        return mht;
    }
    //不在位将牌数
    private int misPlaced(PuzzleBoard goal) {
        int mp=0;
        for(int i=0;i<this.size;++i)
        {
            for(int j=0;j<this.size;++j)
            {
                if(this.state[i][j]!=goal.state[i][j])
                {
                    ++mp;
                }
            }
        }
        return mp;
    }
    //不相交模式数据库
    private int getDisjointPattern(PuzzleBoard goal) {
        if (stateCode == null)
            stateCode = getStateCode();
        return disjointPattern();
    }
    private int disjointPattern() {
        int disjointPattern = 0;
        for (int i = 0; i < classes; i++) {
            disjointPattern += db[i][stateCode[i]];
        }
        return disjointPattern;
    }
    private int[] getStateCode() {
        int[] stateCode = new int[classes];
        if(size==3)
        {
            for(int i=0;i<size;++i)
            {
                for(int j=0;j<size;++j)
                {
                    if(state[i][j]==0)continue;
                    stateCode[subsets[state[i][j]]] += (i * size + j) * Math.pow(size * size, positions[state[i][j]]);
                }
            }
        }
        else{
            for (int pos = size*size-1; pos >= 0; --pos) {
                final int tile = state[pos/size][pos%size];
                if (tile != 0) {
                    final int subsetNumber = subsets[tile];
                    switch (subsetNumber) {
                        case 2:
                            stateCode[2] |= pos << (positions[tile] << 2);
                            break;
                        case 1:
                            stateCode[1] |= pos << (positions[tile] << 2);
                            break;
                        default:
                            stateCode[0] |= pos << (positions[tile] << 2);
                            break;
                    }
                }
            }
        }

        return stateCode;
    }
}
