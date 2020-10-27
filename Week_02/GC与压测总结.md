# GC与压测总结

```
相关命令：
- javac GCLogAnalysis.java
- java -XX:+UseSerialGC -XX:+PrintGCDetails -Xloggc:gc.serial.log GCLogAnalysis
  - DefNew, Tenured
- java -XX:+UseParallelGC -XX:+PrintGCDetails -Xloggc:gc.parallel.log GCLogAnalysis
  - PSYoungGen, ParOldGen
- java -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:gc.cms.log GCLogAnalysis
- java -XX:+UseG1GC  -XX:MaxGCPauseMillis=10 -XX:+PrintGC -Xloggc:gc.g1.log GCLogAnalysis
```

- 串行GC：文件 gc.serial.log
  - GC次数少，但是消耗的时间多，随着对内存的增大，GC时间越来越长，100+M的新生代内存需要100+ms 的 GC 时间。
  - 基本上不会再用了，暂停时间太长了，业务应该无法接受。
- 并行GC：文件 gc.parallel.log
  - 新生代的GC效率高，几乎都可以达到90%以上的回收率，老年代的使用率也不怎么降，证明是真的老年代数据，而非临时数据，使用情况比较理想；即使是几百M的新生代GC也在几十毫秒内完成GC。
- CMS GC：文件 gc.cms.log
  - GC次数多，但是每次的时间都很小，几毫秒，很少发生 fullGC；新生代大小没有变化，清理发生的时机也是一样的
- G1 GC：文件 gc.g1.log
  - 新生代的GC清理空间在40-50%左右，相对前面几个GC来说是比较少的；
  - 通过设置  -XX:MaxGCPauseMillis=10 也会出现大于10ms 的情况，而且使用的内存更多了

# 总结

1. 串行GC可以明确淘汰了，内存大的时候太过耗时
2. 并行GC 和 CMS GC 都可以用，使用率和GC时间都是比较理想
3. G1 GC没有预想的那么好，也可能是参数没配置好