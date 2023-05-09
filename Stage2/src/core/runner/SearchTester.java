package core.runner;

import algs4.util.StopwatchCPU;
import core.problem.Problem;
import core.problem.ProblemType;
import core.solver.algorithm.Searcher;
import core.solver.queue.Node;
import core.solver.algorithm.heuristic.HeuristicType;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

import static core.solver.algorithm.heuristic.HeuristicType.*;

/**
 * ��ѧ���������㷨���м���������
 * arg0: ������������      resources/problems.txt
 * arg1: ��������         NPUZZLE
 * arg2: ��Ŀ���ĸ��׶�    1
 * arg3: ��С���Feeder   stud.runner.PuzzleFeeder
 */
//-Xmx5120m -Xms5120m -Xmn1280m -Xss5m
//    -Xmx1024m -Xms1024m -Xmn256m -Xss4096k
public final class SearchTester {

    public static void main(String[] args) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, IOException, InterruptedException {

        //����args[3]�ṩ����������ѧ����EngineFeeder����
        EngineFeeder feeder = (EngineFeeder)
                Class.forName(args[3])
                        .getDeclaredConstructor().newInstance();


        ////���ļ��������������������ı��� args[0]�����������ļ������·��
        Scanner scanner = new Scanner(new File(args[0]));
        ArrayList<String> problemLines = getProblemLines(scanner);

        //feeder�����������ı���ȡѰ·���������ʵ��
        ArrayList<Problem> problems = feeder.getProblems(problemLines);
        //����ʵ�����뵽ArrayList��

        //��ǰ��������� args[1]    Ѱ·���⣬�������̣�Ұ�˴���ʿ���ӵ�
        ProblemType type = ProblemType.valueOf(args[1]);
        //����ڼ��׶� args[2]
        int step = Integer.parseInt(args[2]);

        //�����������ͺ͵�ǰ�׶Σ���ȡ������������������
        //Ѱ·����ֱ�ʹ��Grid�����Euclid������Ϊ��������
        ArrayList<HeuristicType> heuristics = getHeuristicTypes(type, step);

        for (HeuristicType heuristicType : heuristics) {
            //solveProblems�������ݲ�ͬ�����������ɲ�ͬ��searcher
            //��Feeder��ȡ��ʹ�õ��������棨AStar��IDAStar�ȣ���
            if(step == 1){
//                solveProblems(problems, feeder.getIdaStar(heuristicType), heuristicType,step);
//                solveProblems(problems, feeder.getAStar(heuristicType), heuristicType,step);
                solveProblems(problems, feeder.getAStar(heuristicType), heuristicType);
            }else if(step==2) {
//                solveProblems(problems, feeder.getAStar(heuristicType), heuristicType,step);
//                solveProblems(problems, feeder.getIdaStar(heuristicType), heuristicType,step);
                solveProblems(problems, feeder.getIdaStar(heuristicType), heuristicType);
            }
            System.out.println();
        }
    }

    /**
     * �����������ͺ͵�ǰ�׶Σ���ȡ������������������
     * @param type
     * @param step
     * @return
     */
    private static ArrayList<HeuristicType> getHeuristicTypes(ProblemType type, int step) {
        //��⵱ǰ�����ڵ�ǰ�׶ο��õ��������������б�
        ArrayList<HeuristicType> heuristics = new ArrayList<>();
        //���ݲ�ͬ���������ͣ����в�ͬ�Ĳ���
        if (type == ProblemType.PATHFINDING) {
            heuristics.add(PF_GRID);
            heuristics.add(PF_EUCLID);
        }
        else {
            //NPuzzle����ĵ�һ�׶Σ�ʹ�ò���λ���ƺ������پ���
            if (step == 1 || step == 2) {
                heuristics.add(MANHATTAN);
                heuristics.add(LINEAR_CONFLICT);
//                heuristics.add(MISPLACED);
            }
        }
        return heuristics;
    }

    /**
     * ʹ�ø�����searcher��������⼯���е��������⣬ͬʱʹ�ý���������õĽ���м��
     * @param problems     ���⼯��
     * @param searcher     searcher
     * @param heuristicType ʹ����������������
     */
    private static void solveProblems(ArrayList<Problem> problems, Searcher searcher, HeuristicType heuristicType) throws IOException {
        for (Problem problem : problems) {
            // ʹ��AStar�����������
            StopwatchCPU timer1 = new StopwatchCPU();
            Deque<Node> path = searcher.search(problem);
            double time1 = timer1.elapsedTime();
            if (path == null) {
                System.out.println("No Solution" + ", processed" + time1 + "s, "+
                        "generated" + searcher.nodesGenerated() + ", nodes" +
                        "expanded" + searcher.nodesExpanded() + "nodes");
                continue;
            }

            // ��·���Ŀ��ӻ�
//            problem.showSolution(path);
            System.out.println("heuristicType: " + heuristicType + ", the size of path: " + path.size() + ", processed " + time1 + "s, " +
                    "generated " + searcher.nodesGenerated() + " nodes, " +
                    "expanded " + searcher.nodesExpanded() + " nodes");
        }
    }

    /**
     * ���ļ���������ʵ�����ַ����������ַ���������
     * @param scanner
     * @return
     */
    public static ArrayList<String> getProblemLines(Scanner scanner) {
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNext()){
            lines.add(scanner.nextLine());
        }
        return lines;
    }


}