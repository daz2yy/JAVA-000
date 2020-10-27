# 学习笔记

**Week02 作业题目（周四）：**

**1.**使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。

- javac GCLogAnalysis.java
- java -XX:+UseSerialGC -XX:+PrintGCDetails -Xloggc:gc.serial.log GCLogAnalysis
  - DefNew, Tenured
- java -XX:+UseParallelGC -XX:+PrintGCDetails -Xloggc:gc.parallel.log GCLogAnalysis
  - PSYoungGen, ParOldGen
- java -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:gc.cms.log GCLogAnalysis
- java -XX:+UseG1GC  -XX:MaxGCPauseMillis=10 -XX:+PrintGC -Xloggc:gc.g1.log GCLogAnalysis

**2.**使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

- sb -u http://localhost:8801 -c 40 -N 30

**3.（选做）** 如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。
**4.（必做）** 根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github。

- GC与压测总结.md

**Week02 作业题目（周六）：**

**1.（选做）**运行课上的例子，以及 Netty 的例子，分析相关现象。
**2.（必做）**写一段代码，使用 HttpClient 或 OkHttp 访问 [http://localhost:8801 ](http://localhost:8801/)，代码提交到 Github。

