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
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt          Score         Error  Units
BenchmarkTiny.baseline                         3              1  thrpt   25   22547987.556 ±   53581.141  ops/s
BenchmarkTiny.baseline                         3              2  thrpt   25   11288874.648 ±   31352.948  ops/s
BenchmarkTiny.baseline                         3              5  thrpt   25    4583671.064 ±   12894.797  ops/s
BenchmarkTiny.baseline                         3             10  thrpt   25    2301940.915 ±    5992.418  ops/s
BenchmarkTiny.baseline                         3             50  thrpt   25     431387.100 ±    1215.054  ops/s
BenchmarkTiny.baseline                         3            100  thrpt   25     213078.573 ±     462.855  ops/s
BenchmarkTiny.stagedNoCompile                  3              1  thrpt   25  124821951.358 ±  478374.984  ops/s
BenchmarkTiny.stagedNoCompile                  3              2  thrpt   25   65494023.534 ± 1028783.099  ops/s
BenchmarkTiny.stagedNoCompile                  3              5  thrpt   25   27390693.255 ±  112084.532  ops/s
BenchmarkTiny.stagedNoCompile                  3             10  thrpt   25   12291076.513 ±   35993.162  ops/s
BenchmarkTiny.stagedNoCompile                  3             50  thrpt   25    2725459.373 ±    7828.020  ops/s
BenchmarkTiny.stagedNoCompile                  3            100  thrpt   25    1383879.559 ±    6406.177  ops/s
BenchmarkTiny.stagedWithCompilation            3              1  thrpt   25        209.848 ±       4.649  ops/s
BenchmarkTiny.stagedWithCompilation            3              2  thrpt   25        209.645 ±       3.843  ops/s
BenchmarkTiny.stagedWithCompilation            3              5  thrpt   25        207.240 ±       4.806  ops/s
BenchmarkTiny.stagedWithCompilation            3             10  thrpt   25        211.934 ±       5.785  ops/s
BenchmarkTiny.stagedWithCompilation            3             50  thrpt   25        217.074 ±       4.248  ops/s
BenchmarkTiny.stagedWithCompilation            3            100  thrpt   25        216.104 ±       4.356  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt          Score         Error  Units
BenchmarkTiny.baseline                         8              1  thrpt   25   42231824.845 ±  697202.293  ops/s
BenchmarkTiny.baseline                         8              2  thrpt   25   22592745.792 ±   54295.656  ops/s
BenchmarkTiny.baseline                         8              5  thrpt   25    9134413.880 ±  251518.425  ops/s
BenchmarkTiny.baseline                         8             10  thrpt   25    4358994.445 ±  278729.884  ops/s
BenchmarkTiny.baseline                         8             50  thrpt   25     870073.970 ±   67318.496  ops/s
BenchmarkTiny.baseline                         8            100  thrpt   25     389902.613 ±   10851.622  ops/s
BenchmarkTiny.stagedNoCompile                  8              1  thrpt   25   85158778.334 ± 4330497.318  ops/s
BenchmarkTiny.stagedNoCompile                  8              2  thrpt   25   41785363.503 ± 2269573.662  ops/s
BenchmarkTiny.stagedNoCompile                  8              5  thrpt   25   18124433.443 ±  162975.352  ops/s
BenchmarkTiny.stagedNoCompile                  8             10  thrpt   25    9160442.158 ±   35513.593  ops/s
BenchmarkTiny.stagedNoCompile                  8             50  thrpt   25    1816654.605 ±    7404.619  ops/s
BenchmarkTiny.stagedNoCompile                  8            100  thrpt   25     912486.167 ±    5312.220  ops/s
BenchmarkTiny.stagedWithCompilation            8              1  thrpt   25        216.469 ±       3.746  ops/s
BenchmarkTiny.stagedWithCompilation            8              2  thrpt   25        216.539 ±       4.839  ops/s
BenchmarkTiny.stagedWithCompilation            8              5  thrpt   25        218.541 ±       4.246  ops/s
BenchmarkTiny.stagedWithCompilation            8             10  thrpt   25        221.766 ±       3.714  ops/s
BenchmarkTiny.stagedWithCompilation            8             50  thrpt   25        218.212 ±       3.549  ops/s
BenchmarkTiny.stagedWithCompilation            8            100  thrpt   25        217.793 ±       4.487  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt          Score         Error  Units
BenchmarkTiny.baseline                        30              1  thrpt   25    7902725.092 ±   18211.043  ops/s
BenchmarkTiny.baseline                        30              2  thrpt   25    3984561.355 ±   47587.530  ops/s
BenchmarkTiny.baseline                        30              5  thrpt   25    1653021.216 ±   12131.367  ops/s
BenchmarkTiny.baseline                        30             10  thrpt   25     819312.340 ±    2855.693  ops/s
BenchmarkTiny.baseline                        30             50  thrpt   25     163170.237 ±     556.917  ops/s
BenchmarkTiny.baseline                        30            100  thrpt   25      79542.116 ±     271.514  ops/s
BenchmarkTiny.stagedNoCompile                 30              1  thrpt   25   46466725.486 ±  134764.784  ops/s
BenchmarkTiny.stagedNoCompile                 30              2  thrpt   25   23090933.580 ±  145065.214  ops/s
BenchmarkTiny.stagedNoCompile                 30              5  thrpt   25    9175286.443 ±   27736.033  ops/s
BenchmarkTiny.stagedNoCompile                 30             10  thrpt   25    4411878.981 ±   40366.483  ops/s
BenchmarkTiny.stagedNoCompile                 30             50  thrpt   25     909957.555 ±    5188.682  ops/s
BenchmarkTiny.stagedNoCompile                 30            100  thrpt   25     456829.618 ±     976.415  ops/s
BenchmarkTiny.stagedWithCompilation           30              1  thrpt   25        219.661 ±       4.198  ops/s
BenchmarkTiny.stagedWithCompilation           30              2  thrpt   25        218.312 ±       5.118  ops/s
BenchmarkTiny.stagedWithCompilation           30              5  thrpt   25        219.620 ±       4.203  ops/s
BenchmarkTiny.stagedWithCompilation           30             10  thrpt   25        217.927 ±       4.518  ops/s
BenchmarkTiny.stagedWithCompilation           30             50  thrpt   25        217.674 ±       4.480  ops/s
BenchmarkTiny.stagedWithCompilation           30            100  thrpt   25        214.247 ±       3.985  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt          Score         Error  Units
BenchmarkTiny.baseline                        32              1  thrpt   25   17377190.713 ±  215398.561  ops/s
BenchmarkTiny.baseline                        32              2  thrpt   25    8888929.528 ±  108164.015  ops/s
BenchmarkTiny.baseline                        32              5  thrpt   25    3651501.573 ±   87474.351  ops/s
BenchmarkTiny.baseline                        32             10  thrpt   25    1814417.610 ±   33555.379  ops/s
BenchmarkTiny.baseline                        32             50  thrpt   25     365495.617 ±    7251.206  ops/s
BenchmarkTiny.baseline                        32            100  thrpt   25     159497.158 ±    6173.538  ops/s
BenchmarkTiny.stagedNoCompile                 32              1  thrpt   25   58297898.385 ±  441079.365  ops/s
BenchmarkTiny.stagedNoCompile                 32              2  thrpt   25   29949837.863 ±  125019.828  ops/s
BenchmarkTiny.stagedNoCompile                 32              5  thrpt   25   12257768.106 ±  100400.707  ops/s
BenchmarkTiny.stagedNoCompile                 32             10  thrpt   25    6092592.068 ±   41666.415  ops/s
BenchmarkTiny.stagedNoCompile                 32             50  thrpt   25    1197296.321 ±    9789.146  ops/s
BenchmarkTiny.stagedNoCompile                 32            100  thrpt   25     610093.636 ±    3868.430  ops/s
BenchmarkTiny.stagedWithCompilation           32              1  thrpt   25        217.174 ±       4.830  ops/s
BenchmarkTiny.stagedWithCompilation           32              2  thrpt   25        217.524 ±       5.465  ops/s
BenchmarkTiny.stagedWithCompilation           32              5  thrpt   25        216.663 ±       5.274  ops/s
BenchmarkTiny.stagedWithCompilation           32             10  thrpt   25        217.309 ±       4.285  ops/s
BenchmarkTiny.stagedWithCompilation           32             50  thrpt   25        216.993 ±       4.586  ops/s
BenchmarkTiny.stagedWithCompilation           32            100  thrpt   25        216.226 ±       4.419  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt          Score         Error  Units
BenchmarkTiny.baseline                       100              1  thrpt   25    4977355.506 ±   12637.852  ops/s
BenchmarkTiny.baseline                       100              2  thrpt   25    2498569.053 ±    5334.064  ops/s
BenchmarkTiny.baseline                       100              5  thrpt   25     999714.163 ±    7648.761  ops/s
BenchmarkTiny.baseline                       100             10  thrpt   25     501405.496 ±    1547.805  ops/s
BenchmarkTiny.baseline                       100             50  thrpt   25      99721.607 ±     308.836  ops/s
BenchmarkTiny.baseline                       100            100  thrpt   25      49405.688 ±     288.465  ops/s
BenchmarkTiny.stagedNoCompile                100              1  thrpt   25   25832979.399 ±  465113.292  ops/s
BenchmarkTiny.stagedNoCompile                100              2  thrpt   25   12771291.609 ±   69255.137  ops/s
BenchmarkTiny.stagedNoCompile                100              5  thrpt   25    5237088.672 ±   26358.297  ops/s
BenchmarkTiny.stagedNoCompile                100             10  thrpt   25    2551996.216 ±   32119.882  ops/s
BenchmarkTiny.stagedNoCompile                100             50  thrpt   25     502389.066 ±    9457.351  ops/s
BenchmarkTiny.stagedNoCompile                100            100  thrpt   25     239930.961 ±    3115.138  ops/s
BenchmarkTiny.stagedWithCompilation          100              1  thrpt   25        213.189 ±       4.491  ops/s
BenchmarkTiny.stagedWithCompilation          100              2  thrpt   25        213.098 ±       4.891  ops/s
BenchmarkTiny.stagedWithCompilation          100              5  thrpt   25        213.031 ±       3.967  ops/s
BenchmarkTiny.stagedWithCompilation          100             10  thrpt   25        211.652 ±       4.719  ops/s
BenchmarkTiny.stagedWithCompilation          100             50  thrpt   25        209.318 ±       4.322  ops/s
BenchmarkTiny.stagedWithCompilation          100            100  thrpt   25        206.098 ±       4.626  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt          Score         Error  Units
BenchmarkTiny.baseline                       128              1  thrpt   25    5077198.873 ±   47308.103  ops/s
BenchmarkTiny.baseline                       128              2  thrpt   25    2569622.317 ±   23297.533  ops/s
BenchmarkTiny.baseline                       128              5  thrpt   25    1044115.670 ±    9515.896  ops/s
BenchmarkTiny.baseline                       128             10  thrpt   25     516865.719 ±    3966.179  ops/s
BenchmarkTiny.baseline                       128             50  thrpt   25     102042.074 ±     643.614  ops/s
BenchmarkTiny.baseline                       128            100  thrpt   25      50366.521 ±     766.785  ops/s
BenchmarkTiny.stagedNoCompile                128              1  thrpt   25   22025838.103 ±  140155.220  ops/s
BenchmarkTiny.stagedNoCompile                128              2  thrpt   25   11576117.130 ±  159931.407  ops/s
BenchmarkTiny.stagedNoCompile                128              5  thrpt   25    4690776.931 ±   36485.014  ops/s
BenchmarkTiny.stagedNoCompile                128             10  thrpt   25    2364136.845 ±   25931.932  ops/s
BenchmarkTiny.stagedNoCompile                128             50  thrpt   25     461306.823 ±    5462.770  ops/s
BenchmarkTiny.stagedNoCompile                128            100  thrpt   25     224454.557 ±    2114.486  ops/s
BenchmarkTiny.stagedWithCompilation          128              1  thrpt   25        212.361 ±       4.608  ops/s
BenchmarkTiny.stagedWithCompilation          128              2  thrpt   25        210.886 ±       4.403  ops/s
BenchmarkTiny.stagedWithCompilation          128              5  thrpt   25        211.199 ±       4.586  ops/s
BenchmarkTiny.stagedWithCompilation          128             10  thrpt   25        208.662 ±       4.743  ops/s
BenchmarkTiny.stagedWithCompilation          128             50  thrpt   25        207.320 ±       4.649  ops/s
BenchmarkTiny.stagedWithCompilation          128            100  thrpt   25        202.981 ±       4.475  ops/s
```


```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt        Score        Error  Units
BenchmarkTiny.baseline                       500              1  thrpt   25  1316104.932 ±   7353.972  ops/s
BenchmarkTiny.baseline                       500              2  thrpt   25   645952.963 ±   3669.979  ops/s
BenchmarkTiny.baseline                       500              5  thrpt   25   252584.889 ±    823.610  ops/s
BenchmarkTiny.baseline                       500             10  thrpt   25   125170.223 ±    411.153  ops/s
BenchmarkTiny.baseline                       500             50  thrpt   25    24608.731 ±     87.416  ops/s
BenchmarkTiny.baseline                       500            100  thrpt   25    12208.267 ±     51.415  ops/s
BenchmarkTiny.stagedNoCompile                500              1  thrpt   25  7280816.872 ± 159530.650  ops/s
BenchmarkTiny.stagedNoCompile                500              2  thrpt   25  3681836.284 ±  40359.854  ops/s
BenchmarkTiny.stagedNoCompile                500              5  thrpt   25  1493276.660 ±  16135.937  ops/s
BenchmarkTiny.stagedNoCompile                500             10  thrpt   25   731847.136 ±   7389.629  ops/s
BenchmarkTiny.stagedNoCompile                500             50  thrpt   25   142956.621 ±   1585.273  ops/s
BenchmarkTiny.stagedNoCompile                500            100  thrpt   25    69465.778 ±    918.678  ops/s
BenchmarkTiny.stagedWithCompilation          500              1  thrpt   25      187.795 ±      5.030  ops/s
BenchmarkTiny.stagedWithCompilation          500              2  thrpt   25      185.641 ±      4.124  ops/s
BenchmarkTiny.stagedWithCompilation          500              5  thrpt   25      177.799 ±      3.793  ops/s
BenchmarkTiny.stagedWithCompilation          500             10  thrpt   25      178.615 ±      4.321  ops/s
BenchmarkTiny.stagedWithCompilation          500             50  thrpt   25      173.198 ±      4.543  ops/s
BenchmarkTiny.stagedWithCompilation          500            100  thrpt   25      161.730 ±      4.165  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt        Score        Error  Units
BenchmarkTiny.baseline                       512              1  thrpt   25  1320838.289 ±  11342.312  ops/s
BenchmarkTiny.baseline                       512              2  thrpt   25   656915.498 ±   4419.940  ops/s
BenchmarkTiny.baseline                       512              5  thrpt   25   266710.665 ±   1918.661  ops/s
BenchmarkTiny.baseline                       512             10  thrpt   25   131805.039 ±    845.468  ops/s
BenchmarkTiny.baseline                       512             50  thrpt   25    25658.206 ±    138.255  ops/s
BenchmarkTiny.baseline                       512            100  thrpt   25    12665.847 ±    103.180  ops/s
BenchmarkTiny.stagedNoCompile                512              1  thrpt   25  7132102.998 ±  67278.263  ops/s
BenchmarkTiny.stagedNoCompile                512              2  thrpt   25  3758564.750 ±  53251.537  ops/s
BenchmarkTiny.stagedNoCompile                512              5  thrpt   25  1498029.771 ±  22089.538  ops/s
BenchmarkTiny.stagedNoCompile                512             10  thrpt   25   753372.537 ±   5629.793  ops/s
BenchmarkTiny.stagedNoCompile                512             50  thrpt   25   146474.539 ±   1028.320  ops/s
BenchmarkTiny.stagedNoCompile                512            100  thrpt   25    72122.170 ±    932.560  ops/s
BenchmarkTiny.stagedWithCompilation          512              1  thrpt   25      184.286 ±      5.157  ops/s
BenchmarkTiny.stagedWithCompilation          512              2  thrpt   25      184.310 ±      4.193  ops/s
BenchmarkTiny.stagedWithCompilation          512              5  thrpt   25      182.663 ±      4.650  ops/s
BenchmarkTiny.stagedWithCompilation          512             10  thrpt   25      181.010 ±      3.976  ops/s
BenchmarkTiny.stagedWithCompilation          512             50  thrpt   25      172.574 ±      4.404  ops/s
BenchmarkTiny.stagedWithCompilation          512            100  thrpt   25      161.731 ±      3.927  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt        Score        Error  Units
BenchmarkTiny.baseline                      1000              1  thrpt   25   690712.169 ±   4094.401  ops/s
BenchmarkTiny.baseline                      1000              2  thrpt   25   343989.569 ±   1772.890  ops/s
BenchmarkTiny.baseline                      1000              5  thrpt   25   135047.661 ±   1701.929  ops/s
BenchmarkTiny.baseline                      1000             10  thrpt   25    68395.829 ±    304.236  ops/s
BenchmarkTiny.baseline                      1000             50  thrpt   25    13362.587 ±     51.123  ops/s
BenchmarkTiny.baseline                      1000            100  thrpt   25     6636.169 ±     61.792  ops/s
BenchmarkTiny.stagedNoCompile               1000              1  thrpt   25  3863486.094 ±  10879.302  ops/s
BenchmarkTiny.stagedNoCompile               1000              2  thrpt   25  1986606.427 ±  19575.453  ops/s
BenchmarkTiny.stagedNoCompile               1000              5  thrpt   25   789438.668 ±   5632.059  ops/s
BenchmarkTiny.stagedNoCompile               1000             10  thrpt   25   388620.383 ±   5487.225  ops/s
BenchmarkTiny.stagedNoCompile               1000             50  thrpt   25    77926.995 ±    745.814  ops/s
BenchmarkTiny.stagedNoCompile               1000            100  thrpt   25    37121.565 ±    353.618  ops/s
BenchmarkTiny.stagedWithCompilation         1000              1  thrpt   25      158.191 ±      5.253  ops/s
BenchmarkTiny.stagedWithCompilation         1000              2  thrpt   25      158.998 ±      4.325  ops/s
BenchmarkTiny.stagedWithCompilation         1000              5  thrpt   25      158.230 ±      4.419  ops/s
BenchmarkTiny.stagedWithCompilation         1000             10  thrpt   25      155.848 ±      4.064  ops/s
BenchmarkTiny.stagedWithCompilation         1000             50  thrpt   25      143.592 ±      3.718  ops/s
BenchmarkTiny.stagedWithCompilation         1000            100  thrpt   25      130.287 ±      3.269  ops/s
```

```
Benchmark                            (arraySize)  (rightsCount)   Mode  Cnt        Score        Error  Units
BenchmarkTiny.baseline                      1024              1  thrpt   25   673129.373 ±   5045.058  ops/s
BenchmarkTiny.baseline                      1024              2  thrpt   25   340755.791 ±   2588.739  ops/s
BenchmarkTiny.baseline                      1024              5  thrpt   25   134777.277 ±    765.936  ops/s
BenchmarkTiny.baseline                      1024             10  thrpt   25    66616.231 ±    662.635  ops/s
BenchmarkTiny.baseline                      1024             50  thrpt   25    13408.673 ±    162.730  ops/s
BenchmarkTiny.baseline                      1024            100  thrpt   25     6821.702 ±     25.895  ops/s
BenchmarkTiny.stagedNoCompile               1024              1  thrpt   25  3745537.917 ±  77409.904  ops/s
BenchmarkTiny.stagedNoCompile               1024              2  thrpt   25  1922269.371 ±  22510.816  ops/s
BenchmarkTiny.stagedNoCompile               1024              5  thrpt   25   765266.021 ±   4152.492  ops/s
BenchmarkTiny.stagedNoCompile               1024             10  thrpt   25   380724.880 ±   4817.924  ops/s
BenchmarkTiny.stagedNoCompile               1024             50  thrpt   25    75058.598 ±    275.223  ops/s
BenchmarkTiny.stagedNoCompile               1024            100  thrpt   25    36714.859 ±    361.585  ops/s
BenchmarkTiny.stagedWithCompilation         1024              1  thrpt   25      158.022 ±      4.292  ops/s
BenchmarkTiny.stagedWithCompilation         1024              2  thrpt   25      157.544 ±      4.656  ops/s
BenchmarkTiny.stagedWithCompilation         1024              5  thrpt   25      154.960 ±      4.602  ops/s
BenchmarkTiny.stagedWithCompilation         1024             10  thrpt   25      153.612 ±      3.857  ops/s
BenchmarkTiny.stagedWithCompilation         1024             50  thrpt   25      142.974 ±      3.414  ops/s
BenchmarkTiny.stagedWithCompilation         1024            100  thrpt   25      128.865 ±      3.001  ops/s
```


Ggplots generated by [mkplot.r](benchmark/mkplot.r)

![benchmark rc=1](benchmark/images/bench-1.png?raw=true "Benchmark rightsCount=1")
![benchmark rc=2](benchmark/images/bench-2.png?raw=true "Benchmark rightsCount=2")
![benchmark rc=5](benchmark/images/bench-5.png?raw=true "Benchmark rightsCount=5")
![benchmark rc=50](benchmark/images/bench-50.png?raw=true "Benchmark rightsCount=50")
![benchmark rc=100](benchmark/images/bench-100.png?raw=true "Benchmark rightsCount=100")
