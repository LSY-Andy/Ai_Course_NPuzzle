package core.solver.algorithm.heuristic;

public enum HeuristicType {
    //Npuzzle的启发函数
    MISPLACED,  // 不在位将牌
    MANHATTAN,  // 曼哈顿距离
    LINEAR_CONFLICT,
    DISJOINT_PATTERN,


}
