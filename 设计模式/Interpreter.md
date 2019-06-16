---
title:Interperter 解释器
---

# Interperter 解释器
**类行为型模式**

## 意图
+ 给定一个语言，定义它的文法的一种表示，并定义一个解释器，这个解释器使用该表示来解释语言中的句子。

## 动机
+ 如果一种特定类型的问题发生频率足够高，那么可能就值得将该问题的各个实例表述为一个简单语言中的句子。这样就可以构建一个解释器，该解释器通过解释这些句子来解决该问题。


## 适用性
**当有一个语言需要解释执行，并且你可将该语言中的句子表示为一个抽象语法的时，可使用解释器模式。而当存在以下情况时该模式效果最好：**

+ 该文法简单对于复杂的文法，文法的层次变得庞大而无法管理。此时语法分析程序生成器这样的工具是更好的选择。它们无需构建抽象语法树即可解释表达式，这样可以节省空间而且还可能节省时间。
+ 效率不是一个关键问题最高效的解释器通常不是通过直接解释语法分析树实现的，而是首先将它们转换成另一种形式。例如，正则表达式通常被转换成状态机。但即使在这种情况下，转换器仍可用解释器模式实现，该模式仍是有用的。

## 参与者
+ AbstractExpression(抽象表达式)
  + 声明一个抽象的解释操作，这个接口为抽象语法树中所有节点所共享。
+ TerminalExpression(终结符表达式)
  + 实现与文法中的终结符相关连的解释操作。
  + 一个句子中的每个终结符需要该类的一个实例。
+ NonterminalExpression(非终结符表达式)
  + 对文法中的每一条规则R::=R1R2...Rn都需要一个NonterminalExpression类。
  + 为从R1到Rn的每个符号维护一个AbstractExpression类型的实例变量。
  + 为文法中的非终结符实现解释(Interpert)操作，解释一般要递归地调用表示R1到Rn的那些对象的解释操作。
+ Context(上下文)
  + 包含解释器之外的一些全局信息。
+ Client(客户)
  + 构建(或被给定)表示该文法定义的语言中一个特定的句子的抽象语法树。该抽象语法树由NonterminalExpression和TerminalExpression的实例装配而成。
  + 调用解释操作。


## 效果
+ **易于改变和扩展文法** 因为该模式使用类来表示文法规则，你可以使用继承来改变或扩展该文法。已有的表达式可被增量式地改变，而新的表达式可定义为旧的表达式的变体。
+ **也易于实现文法** 定义抽象语法树中各个节点的类实现大体类似。这些类易于直接编写，通常它们也可以用一个编译器或语法分析程序生成器自动生成。
+ **复杂的文法难以维护** 解释器模式为文法中每一条规则定义了一个类(使用BNF定义的文法规则需要更多的类)。因此包含许多规则的文法可能难以管理和维护。可应用其他的设计模式来缓解这一问题。但当文法非常复杂的时候，其他的技术如语法分析程序或编译器生成器更为合适。
+ **增加了新的解释表达式的方式** 解释器模式使得实现新表达式“计算”变得容易。例如，你可以在表达式类上定义一个新的操作以支持优美打印或表达式的类型检查。如果你经常创建新的解释表达式的方式，那么可以考虑使用Visitor模式以避免修改这些代表文法的类。

## 代码
~~~ java
public interface Expression {
    int interpreter(Map<Character, Integer> var);
}

public class VarExpression implements Expression {
    private char key;

    public VarExpression(char key) {
        this.key = key;
    }

    @Override
    public int interpreter(Map<Character, Integer> var) {
        return var.get(key);
    }
}

public class SymbolExpression implements Expression {
    /**
     * 左右运算符两个参数
     */
    protected Expression left;
    protected Expression right;

    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpreter(Map<Character, Integer> var) {
        return 0;
    }
}

public class AddExpression extends SymbolExpression {

    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(Map<Character, Integer> var) {
        return left.interpreter(var) + right.interpreter(var);
    }
}

public class SubExpression extends SymbolExpression {
    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(Map<Character, Integer> var) {
        return left.interpreter(var) - right.interpreter(var);
    }
}

public class ExpressionMain {


    public static void main(String[] args) {
        String expStr = "a+b-c+d";
        Map<Character, Integer> var = new HashMap<>();
        var.put('a', 5);
        var.put('b', 2);
        var.put('c', 1);
        var.put('d', 6);

        Expression expression = analyse(expStr);

        int result = expression.interpreter(var);

        System.out.println(result);

        release(expression);
    }

    public static Expression analyse(String expStr) {
        Stack<Expression> expStack = new Stack<>();
        Expression left = null;
        Expression right = null;
        for (int i = 0; i < expStr.length(); i++) {
            switch (expStr.charAt(i)) {
                case '+':
                    //加法运算
                    left = expStack.pop();
                    right = new VarExpression(expStr.charAt(++i));
                    expStack.push(new AddExpression(left, right));
                    break;
                case '-':
                    //减法运算
                    left = expStack.pop();
                    right = new VarExpression(expStr.charAt(++i));
                    expStack.push(new SubExpression(left, right));
                    break;
                default:
                    //终结表达式
                    expStack.push(new VarExpression(expStr.charAt(i)));
                    break;
            }
        }
        return expStack.pop();
    }

    public static void release(Expression expression) {
        //释放树表达式的节点内存
    }
}

~~~