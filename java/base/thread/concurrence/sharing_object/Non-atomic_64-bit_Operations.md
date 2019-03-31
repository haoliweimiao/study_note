---
title:非原子性的64位操作
---
# 非原子性的64位操作 

+ >当一个线程在没有同步的情况下读取变量，它可能得到一个过期值。但是至少它可以看到某个线程在那里设定的一个真实数值，而不是一个凭空而来的值。这样的安全保证被称为是最低限的安全值

+ >When a thread reads a variable without synchronization, it may see a stale value, but at least it sees a value that was actually placed there by some thread rather than some random value. This safety guarantee is called out‐of‐thin‐air safety.
