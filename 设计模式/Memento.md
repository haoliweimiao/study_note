---
title:Memento 备忘录模式
---

# Memento 备忘录模式
**对象行为模式**
*别名 Token*

## 意图
+ 在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。这样以后就可以将该对象恢复到原先保存的状态。

## 动机
+ 有时候必要记录一个对象的内部状态。为了用户取消不确定的操作或从错误中恢复过来，需要实现检查点和取消机制，而要实现这些机制，你必须先将状态信息保存在某处，这样才能将对象恢复到它们之前的状态。但是对象通常封装了其部分或者所有的状态信息，使得其对象不能被其他对象访问，也就不可能在该对象之外保存其状态。而暴露其内部又将违反封装的原则，可能有损应用的可靠性和可扩展性。