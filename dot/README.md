Dot product.

Starting from the idea from: https://github.com/nicolasstucki/scala-metaprogramming-exercise/blob/master/src/main/scala/Vectors.scala

  * [benchmark](benchmark) benchmark comparing the latest (highest number)
    version with a feature-complete dynamic implementation
  * [dot-1](dot-1) simple dot product macro
  * [dot-2](dot-2) dot product for any generic type A. Naive version just
    passing around functions
  * [dot-3](dot-3) dot product for any generic type A with typeclasses used for
    code organization
  * [dot-4](dot-3) dot-3 with friendlier syntax
  * [dot-5](dot-5) dot-4 for any data/accumultor type
  * [dot-6](dot-6) dot-5 using the vector api
  * [dot-7](dot-7) dot-6 without the duplication in macro implementation
  * [dot-8](dot-8) dot-7 adjusted to be used with staging
  
Benchmarks
==========

Raw data can be found in [benchmark.csv](benchmark/benchmark.csv)

```
Benchmark                           arraySize  rightsCount  Mode   Cnt          Score         Error  Units
BenchmarkTiny.baseline                      3            1  thrpt   25   20191916.191     93811.772  ops/s
BenchmarkTiny.baseline                      3            2  thrpt   25   10322934.369     46984.626  ops/s
BenchmarkTiny.baseline                      3            5  thrpt   25    4211089.601     25161.044  ops/s
BenchmarkTiny.baseline                      3           10  thrpt   25    2108360.459     14169.441  ops/s
BenchmarkTiny.baseline                      3           50  thrpt   25     391005.669      4691.615  ops/s
BenchmarkTiny.baseline                      3          100  thrpt   25     195426.418       863.961  ops/s
BenchmarkTiny.stagedNoCompile               3            1  thrpt   25  171084544.106    652368.526  ops/s
BenchmarkTiny.stagedNoCompile               3            2  thrpt   25   89653647.689   1394467.100  ops/s
BenchmarkTiny.stagedNoCompile               3            5  thrpt   25   38241991.608    127429.655  ops/s
BenchmarkTiny.stagedNoCompile               3           10  thrpt   25   19400289.481     97789.245  ops/s
BenchmarkTiny.stagedNoCompile               3           50  thrpt   25    3816744.402     13342.917  ops/s
BenchmarkTiny.stagedNoCompile               3          100  thrpt   25    1936390.560      9326.628  ops/s
BenchmarkTiny.stagedWithCompilation         3            1  thrpt   25        210.032         5.836  ops/s
BenchmarkTiny.stagedWithCompilation         3            2  thrpt   25        208.892         6.186  ops/s
BenchmarkTiny.stagedWithCompilation         3            5  thrpt   25        207.601         6.987  ops/s
BenchmarkTiny.stagedWithCompilation         3           10  thrpt   25        209.834         5.916  ops/s
BenchmarkTiny.stagedWithCompilation         3           50  thrpt   25        208.083         6.238  ops/s
BenchmarkTiny.stagedWithCompilation         3          100  thrpt   25        206.903         5.624  ops/s
```

```
Benchmark                           arraySize  rightsCount  Mode   Cnt          Score       Error   Units
BenchmarkTiny.baseline                      8          1    thrpt   25   41496237.242  578617.435   ops/s
BenchmarkTiny.baseline                      8          2    thrpt   25   20881249.770  250515.581   ops/s
BenchmarkTiny.baseline                      8          5    thrpt   25    8699905.311   77371.486   ops/s
BenchmarkTiny.baseline                      8         10    thrpt   25    4211031.845  186826.192   ops/s
BenchmarkTiny.baseline                      8         50    thrpt   25     768170.859    8899.392   ops/s
BenchmarkTiny.baseline                      8        100    thrpt   25     384076.648   22753.269   ops/s
BenchmarkTiny.stagedNoCompile               8          1    thrpt   25   52850200.129  161113.374   ops/s
BenchmarkTiny.stagedNoCompile               8          2    thrpt   25   27350668.810   86122.861   ops/s
BenchmarkTiny.stagedNoCompile               8          5    thrpt   25   11081088.346   34484.408   ops/s
BenchmarkTiny.stagedNoCompile               8         10    thrpt   25    5555714.499   13492.855   ops/s
BenchmarkTiny.stagedNoCompile               8         50    thrpt   25    1113981.234    3409.397   ops/s
BenchmarkTiny.stagedNoCompile               8        100    thrpt   25     556802.393    1120.859   ops/s
BenchmarkTiny.stagedWithCompilation         8          1    thrpt   25        182.441       6.134   ops/s
BenchmarkTiny.stagedWithCompilation         8          2    thrpt   25        182.542       5.306   ops/s
BenchmarkTiny.stagedWithCompilation         8          5    thrpt   25        180.045       5.554   ops/s
BenchmarkTiny.stagedWithCompilation         8         10    thrpt   25        173.231       5.818   ops/s
BenchmarkTiny.stagedWithCompilation         8         50    thrpt   25        179.376       5.757   ops/s
BenchmarkTiny.stagedWithCompilation         8        100    thrpt   25        180.509       5.366   ops/s
```

```
Benchmark                           arraySize  rightsCount  Mode   Cnt        Score        Error   Units
BenchmarkTiny.baseline                     30            1  thrpt   25   7314974.025   49731.189   ops/s
BenchmarkTiny.baseline                     30            2  thrpt   25   3733129.548   17886.718   ops/s
BenchmarkTiny.baseline                     30            5  thrpt   25   1508484.544    6057.251   ops/s
BenchmarkTiny.baseline                     30           10  thrpt   25    740350.371    3328.536   ops/s
BenchmarkTiny.baseline                     30           50  thrpt   25    147940.520     725.823   ops/s
BenchmarkTiny.baseline                     30          100  thrpt   25     71065.091    1269.013   ops/s
BenchmarkTiny.stagedNoCompile              30            1  thrpt   25  35403623.236  131758.591   ops/s
BenchmarkTiny.stagedNoCompile              30            2  thrpt   25  18001585.875   37043.444   ops/s
BenchmarkTiny.stagedNoCompile              30            5  thrpt   25   7279209.891   16004.825   ops/s
BenchmarkTiny.stagedNoCompile              30           10  thrpt   25   3646517.506    6673.671   ops/s
BenchmarkTiny.stagedNoCompile              30           50  thrpt   25    729788.692    1810.346   ops/s
BenchmarkTiny.stagedNoCompile              30          100  thrpt   25    364716.100     853.766   ops/s
BenchmarkTiny.stagedWithCompilation        30            1  thrpt   25       167.255       5.212   ops/s
BenchmarkTiny.stagedWithCompilation        30            2  thrpt   25       168.464       5.090   ops/s
BenchmarkTiny.stagedWithCompilation        30            5  thrpt   25       167.017       4.608   ops/s
BenchmarkTiny.stagedWithCompilation        30           10  thrpt   25       167.659       5.206   ops/s
BenchmarkTiny.stagedWithCompilation        30           50  thrpt   25       166.292       4.735   ops/s
BenchmarkTiny.stagedWithCompilation        30          100  thrpt   25       162.670       5.001   ops/s
```

```
Benchmark                           arraySize  rightsCount  Mode   Cnt         Score       Error   Units
BenchmarkTiny.baseline                     32            1  thrpt   25  16311746.497  145718.710   ops/s
BenchmarkTiny.baseline                     32            2  thrpt   25   8460916.333   62587.733   ops/s
BenchmarkTiny.baseline                     32            5  thrpt   25   3325416.649   94876.915   ops/s
BenchmarkTiny.baseline                     32           10  thrpt   25   1682623.606   30793.355   ops/s
BenchmarkTiny.baseline                     32           50  thrpt   25    336763.607    4715.916   ops/s
BenchmarkTiny.baseline                     32          100  thrpt   25    152771.671    3075.052   ops/s
BenchmarkTiny.stagedNoCompile              32            1  thrpt   25  35060414.367   89650.943   ops/s
BenchmarkTiny.stagedNoCompile              32            2  thrpt   25  17828842.886   37603.953   ops/s
BenchmarkTiny.stagedNoCompile              32            5  thrpt   25   7161416.393   27866.664   ops/s
BenchmarkTiny.stagedNoCompile              32           10  thrpt   25   3623749.376    9402.417   ops/s
BenchmarkTiny.stagedNoCompile              32           50  thrpt   25    725493.700    1054.782   ops/s
BenchmarkTiny.stagedNoCompile              32          100  thrpt   25    361264.228    1100.467   ops/s
BenchmarkTiny.stagedWithCompilation        32            1  thrpt   25       180.615       4.492   ops/s
BenchmarkTiny.stagedWithCompilation        32            2  thrpt   25       177.656       4.831   ops/s
BenchmarkTiny.stagedWithCompilation        32            5  thrpt   25       178.841       5.510   ops/s
BenchmarkTiny.stagedWithCompilation        32           10  thrpt   25       177.201       4.864   ops/s
BenchmarkTiny.stagedWithCompilation        32           50  thrpt   25       176.570       4.711   ops/s
BenchmarkTiny.stagedWithCompilation        32          100  thrpt   25       173.451       5.081   ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt         Score       Error  Units
BenchmarkTiny.baseline                       100              1  thrpt   25   4883996.023 ± 52658.784  ops/s
BenchmarkTiny.baseline                       100              2  thrpt   25   2470313.025 ± 25220.804  ops/s
BenchmarkTiny.baseline                       100              5  thrpt   25    983889.384 ±  6178.595  ops/s
BenchmarkTiny.baseline                       100             10  thrpt   25    486698.726 ±  3879.795  ops/s
BenchmarkTiny.baseline                       100             50  thrpt   25     97057.098 ±  1396.973  ops/s
BenchmarkTiny.baseline                       100            100  thrpt   25     47810.546 ±   323.457  ops/s
BenchmarkTiny.stagedNoCompile                100              1  thrpt   25  12248111.450 ± 46127.401  ops/s
BenchmarkTiny.stagedNoCompile                100              2  thrpt   25   6102291.376 ± 25214.998  ops/s
BenchmarkTiny.stagedNoCompile                100              5  thrpt   25   2451779.980 ±  9177.374  ops/s
BenchmarkTiny.stagedNoCompile                100             10  thrpt   25   1224297.727 ±  3880.921  ops/s
BenchmarkTiny.stagedNoCompile                100             50  thrpt   25    241413.823 ±   807.684  ops/s
BenchmarkTiny.stagedNoCompile                100            100  thrpt   25    120430.253 ±   460.113  ops/s
BenchmarkTiny.stagedWithCompilation          100              1  thrpt   25       193.326 ±     4.962  ops/s
BenchmarkTiny.stagedWithCompilation          100              2  thrpt   25       195.762 ±     4.471  ops/s
BenchmarkTiny.stagedWithCompilation          100              5  thrpt   25       194.992 ±     4.761  ops/s
BenchmarkTiny.stagedWithCompilation          100             10  thrpt   25       194.566 ±     5.019  ops/s
BenchmarkTiny.stagedWithCompilation          100             50  thrpt   25       191.832 ±     4.493  ops/s
BenchmarkTiny.stagedWithCompilation          100            100  thrpt   25       185.829 ±     5.443  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt         Score       Error  Units
BenchmarkTiny.baseline                       128              1  thrpt   25   5014046.092 ± 87351.233  ops/s
BenchmarkTiny.baseline                       128              2  thrpt   25   2547347.256 ± 34310.212  ops/s
BenchmarkTiny.baseline                       128              5  thrpt   25   1016239.173 ± 16588.062  ops/s
BenchmarkTiny.baseline                       128             10  thrpt   25    504807.281 ±  6645.559  ops/s
BenchmarkTiny.baseline                       128             50  thrpt   25    100423.248 ±  1769.574  ops/s
BenchmarkTiny.baseline                       128            100  thrpt   25     50299.371 ±  1656.547  ops/s
BenchmarkTiny.stagedNoCompile                128              1  thrpt   25   8424319.879 ± 42582.710  ops/s
BenchmarkTiny.stagedNoCompile                128              2  thrpt   25   4203866.771 ± 20931.371  ops/s
BenchmarkTiny.stagedNoCompile                128              5  thrpt   25   1675081.184 ±  7364.559  ops/s
BenchmarkTiny.stagedNoCompile                128             10  thrpt   25    832106.680 ±  4250.899  ops/s
BenchmarkTiny.stagedNoCompile                128             50  thrpt   25    163209.023 ±  1075.790  ops/s
BenchmarkTiny.stagedNoCompile                128            100  thrpt   25     81082.943 ±   532.113  ops/s
BenchmarkTiny.stagedWithCompilation          128              1  thrpt   25       206.782 ±     5.089  ops/s
BenchmarkTiny.stagedWithCompilation          128              2  thrpt   25       206.510 ±     4.980  ops/s
BenchmarkTiny.stagedWithCompilation          128              5  thrpt   25       205.934 ±     4.592  ops/s
BenchmarkTiny.stagedWithCompilation          128             10  thrpt   25       205.154 ±     4.914  ops/s
BenchmarkTiny.stagedWithCompilation          128             50  thrpt   25       198.134 ±     5.267  ops/s
BenchmarkTiny.stagedWithCompilation          128            100  thrpt   25       190.437 ±     4.146  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt        Score       Error  Units
BenchmarkTiny.baseline                       500              1  thrpt   25  1337420.883 ±  9741.017  ops/s
BenchmarkTiny.baseline                       500              2  thrpt   25   666770.753 ±  7958.300  ops/s
BenchmarkTiny.baseline                       500              5  thrpt   25   265260.998 ±  2562.520  ops/s
BenchmarkTiny.baseline                       500             10  thrpt   25   131678.162 ±  1588.588  ops/s
BenchmarkTiny.baseline                       500             50  thrpt   25    25438.536 ±   250.689  ops/s
BenchmarkTiny.baseline                       500            100  thrpt   25    12528.290 ±   121.321  ops/s
BenchmarkTiny.stagedNoCompile                500              1  thrpt   25  1517306.207 ±  7382.871  ops/s
BenchmarkTiny.stagedNoCompile                500              2  thrpt   25   756672.987 ±  4213.799  ops/s
BenchmarkTiny.stagedNoCompile                500              5  thrpt   25   303606.363 ±   874.803  ops/s
BenchmarkTiny.stagedNoCompile                500             10  thrpt   25   151405.159 ±   593.693  ops/s
BenchmarkTiny.stagedNoCompile                500             50  thrpt   25    30408.191 ±   138.769  ops/s
BenchmarkTiny.stagedNoCompile                500            100  thrpt   25    15017.328 ±   119.896  ops/s
BenchmarkTiny.stagedWithCompilation          500              1  thrpt   25      150.249 ±     4.415  ops/s
BenchmarkTiny.stagedWithCompilation          500              2  thrpt   25      150.143 ±     5.383  ops/s
BenchmarkTiny.stagedWithCompilation          500              5  thrpt   25      147.989 ±     4.975  ops/s
BenchmarkTiny.stagedWithCompilation          500             10  thrpt   25      147.129 ±     4.727  ops/s
BenchmarkTiny.stagedWithCompilation          500             50  thrpt   25      133.256 ±     2.740  ops/s
BenchmarkTiny.stagedWithCompilation          500            100  thrpt   25      122.218 ±     3.311  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt        Score       Error  Units
BenchmarkTiny.baseline                       512              1  thrpt   25  1372273.471 ± 12905.264  ops/s
BenchmarkTiny.baseline                       512              2  thrpt   25   670721.582 ± 18694.533  ops/s
BenchmarkTiny.baseline                       512              5  thrpt   25   270990.263 ±  2576.581  ops/s
BenchmarkTiny.baseline                       512             10  thrpt   25   135604.804 ±   945.835  ops/s
BenchmarkTiny.baseline                       512             50  thrpt   25    26387.858 ±   329.101  ops/s
BenchmarkTiny.baseline                       512            100  thrpt   25    13053.850 ±   148.922  ops/s
BenchmarkTiny.stagedNoCompile                512              1  thrpt   25  1465311.388 ±  5624.930  ops/s
BenchmarkTiny.stagedNoCompile                512              2  thrpt   25   731681.414 ±  2261.312  ops/s
BenchmarkTiny.stagedNoCompile                512              5  thrpt   25   296497.863 ±   854.861  ops/s
BenchmarkTiny.stagedNoCompile                512             10  thrpt   25   148975.447 ±   564.686  ops/s
BenchmarkTiny.stagedNoCompile                512             50  thrpt   25    29870.219 ±    95.091  ops/s
BenchmarkTiny.stagedNoCompile                512            100  thrpt   25    14928.906 ±    45.125  ops/s
BenchmarkTiny.stagedWithCompilation          512              1  thrpt   25      153.355 ±     5.320  ops/s
BenchmarkTiny.stagedWithCompilation          512              2  thrpt   25      153.195 ±     4.941  ops/s
BenchmarkTiny.stagedWithCompilation          512              5  thrpt   25      152.268 ±     5.493  ops/s
BenchmarkTiny.stagedWithCompilation          512             10  thrpt   25      151.489 ±     3.852  ops/s
BenchmarkTiny.stagedWithCompilation          512             50  thrpt   25      138.933 ±     4.195  ops/s
BenchmarkTiny.stagedWithCompilation          512            100  thrpt   25      125.444 ±     3.297  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt       Score      Error  Units
BenchmarkTiny.baseline                      1000              1  thrpt   25  690208.186 ± 3605.770  ops/s
BenchmarkTiny.baseline                      1000              2  thrpt   25  344508.044 ± 4568.040  ops/s
BenchmarkTiny.baseline                      1000              5  thrpt   25  138874.482 ± 1391.163  ops/s
BenchmarkTiny.baseline                      1000             10  thrpt   25   68507.520 ±  469.276  ops/s
BenchmarkTiny.baseline                      1000             50  thrpt   25   13665.398 ±   75.261  ops/s
BenchmarkTiny.baseline                      1000            100  thrpt   25    6751.384 ±   44.616  ops/s
BenchmarkTiny.stagedNoCompile               1000              1  thrpt   25   42127.058 ±  265.167  ops/s
BenchmarkTiny.stagedNoCompile               1000              2  thrpt   25   20924.472 ±  152.248  ops/s
BenchmarkTiny.stagedNoCompile               1000              5  thrpt   25    8410.257 ±   51.073  ops/s
BenchmarkTiny.stagedNoCompile               1000             10  thrpt   25    4202.897 ±   25.637  ops/s
BenchmarkTiny.stagedNoCompile               1000             50  thrpt   25     842.624 ±    5.035  ops/s
BenchmarkTiny.stagedNoCompile               1000            100  thrpt   25     416.883 ±    4.461  ops/s
BenchmarkTiny.stagedWithCompilation         1000              1  thrpt   25     117.289 ±    3.379  ops/s
BenchmarkTiny.stagedWithCompilation         1000              2  thrpt   25     116.081 ±    3.021  ops/s
BenchmarkTiny.stagedWithCompilation         1000              5  thrpt   25     116.721 ±    2.467  ops/s
BenchmarkTiny.stagedWithCompilation         1000             10  thrpt   25     113.693 ±    2.818  ops/s
BenchmarkTiny.stagedWithCompilation         1000             50  thrpt   25     102.161 ±    2.691  ops/s
BenchmarkTiny.stagedWithCompilation         1000            100  thrpt   25      90.522 ±    1.740  ops/s
```

```
BenchmarkTiny.baseline                      1024              1  thrpt   25  686543.130 ± 6655.043  ops/s
BenchmarkTiny.baseline                      1024              2  thrpt   25  340417.989 ± 2769.840  ops/s
BenchmarkTiny.baseline                      1024              5  thrpt   25  135601.482 ±  778.645  ops/s
BenchmarkTiny.baseline                      1024             10  thrpt   25   66964.958 ±  674.107  ops/s
BenchmarkTiny.baseline                      1024             50  thrpt   25   13370.217 ±  123.354  ops/s
BenchmarkTiny.baseline                      1024            100  thrpt   25    6624.779 ±   58.356  ops/s
BenchmarkTiny.stagedNoCompile               1024              1  thrpt   25   40094.863 ±  346.991  ops/s
BenchmarkTiny.stagedNoCompile               1024              2  thrpt   25   19932.918 ±  195.683  ops/s
BenchmarkTiny.stagedNoCompile               1024              5  thrpt   25    8047.133 ±   37.513  ops/s
BenchmarkTiny.stagedNoCompile               1024             10  thrpt   25    4016.321 ±   28.668  ops/s
BenchmarkTiny.stagedNoCompile               1024             50  thrpt   25     805.579 ±    9.161  ops/s
BenchmarkTiny.stagedNoCompile               1024            100  thrpt   25     401.312 ±    3.912  ops/s
BenchmarkTiny.stagedWithCompilation         1024              1  thrpt   25     116.468 ±    3.757  ops/s
BenchmarkTiny.stagedWithCompilation         1024              2  thrpt   25     115.985 ±    3.195  ops/s
BenchmarkTiny.stagedWithCompilation         1024              5  thrpt   25     115.052 ±    2.952  ops/s
BenchmarkTiny.stagedWithCompilation         1024             10  thrpt   25     112.166 ±    4.063  ops/s
BenchmarkTiny.stagedWithCompilation         1024             50  thrpt   25     100.999 ±    2.423  ops/s
BenchmarkTiny.stagedWithCompilation         1024            100  thrpt   25      88.598 ±    2.505  ops/s
```

Ggplots generated by [mkplot.r](benchmark/mkplot.r)

![benchmark rc=1](benchmark/images/bench-1.png?raw=true "Benchmark rightsCount=1")
![benchmark rc=2](benchmark/images/bench-2.png?raw=true "Benchmark rightsCount=2")
![benchmark rc=5](benchmark/images/bench-5.png?raw=true "Benchmark rightsCount=5")
![benchmark rc=50](benchmark/images/bench-50.png?raw=true "Benchmark rightsCount=50")
![benchmark rc=100](benchmark/images/bench-100.png?raw=true "Benchmark rightsCount=100")
