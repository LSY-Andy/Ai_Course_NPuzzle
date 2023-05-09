- 三阶可视化：
  - 直接在命令行中使用 python n-puzzle.py运行即可
- 四阶可视化：
  - **n-puzzle.py [-h] [-m file] [-vi] [-vb] [-g size] [-i number]
                   [-gr | -un] [-t]
                   [-hf {Manhattan,Euclidian,Tiles out-of-place}]**

填入参数：python n-puzzle.py -m resources\grid.txt --map resources\grid.txt -vi --visu -vb --verbose -g 4 --generate 4

这样执行~

三阶输出结果示例：![](resources/OUC_Visualization.gif)

可选参数:
  -h, --help            show this help message and exit
  -m file, --map file
  -vi, --visu           Enable visualization
  -vb, --verbose        Enable verbose (may slow the algorithm down)
  -g size, --generate size
                        Generate a n-size puzzle
  -i number, --iteration number
                        Choose the number of scrambling moves
  -gr, --greedy         Enable greedy search
  -un, --uniform        Enable uniform-cost search
  -t, --time            Print the algorithm's execution time
  -hf {Manhattan,Euclidian,Tiles out-of-place}, --heuristic {Manhattan,Euclidian,Tiles out-of-place}
                        Heuristic function choice, (default: Manhattan)
```
三阶初始状态输入示例：（在resources中的grid.txt）
```
1 2 3
8 0 4
7 6 5
- 简易版本:simple_edition.py
```
参考来源：
* Julien Dumay 
* Aleksi Gautier 