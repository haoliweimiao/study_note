---
title:Abstract Factory
---

# 抽象工厂 Abstract Factory

## 意图
+ 提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。

## 代码
~~~ java
public class EmployeeDAO{
    List<EmployeeBean> getEmployees{
        //具体实例，只能使用sql，需要切换orical时需要修改此处代码
        SqlConnection conn = new SqlConnection();
        conn.setConnectString("...");

        SqlCommand command = new SqlCommand();
        command.setCommandString("...");
        command.setConnection(conn);


        SqlDataReader reader = command.excuteReader();
        while(reader.read()){

        }
    } 
}
~~~

## 修改代码
~~~ java
/**
 * 数据库连接一系列抽象接口
 */
public interface ConnectImpl{}

public interface CommandImpl{}

public interface DataReaderImpl{}

/**
 * 数据库连接一系列抽象接口的工厂创建方法
 */
public abstract class ConnectionFactory{
    ConnectImpl createConnection();
}

public abstract class CommandFactory{
    CommandImpl createCommand();
}

public abstract class DataReaderFactory{
    DataReaderImpl createDataReader();
}

/**
 * 数据库连接一系列抽象接口 sql的具体实现
 */
public class SqlConnection implements ConnectImpl{}

public class SqlCommand implements CommandImpl{}

public class SqlDataReader implements DataReaderImpl{}

/**
 * 数据库连接一系列抽象接口 sql的具体实现的工厂实现类
 */
public class SqlConnectionFactory extends SqlConnection{
    @Override
    public ConnectImpl createConnection(){
        return new SqlConnection();
    }
}
//其它的省略。。。

/**
 * 数据库连接一系列抽象接口 oracle的具体实现
 */
public class OracleConnection implements ConnectImpl{}

public class OracleCommand implements CommandImpl{}

public class OracleDataReader implements DataReaderImpl{}

public class EmployeeDAO{
    private ConnectionFactory connectionFactory;
    private CommandFactory commandFactory;
    private DataReaderFactory dataReaderFactory;

    //构造方法，省略...
    //问题 传入的工厂类型的具体实现类功能有关联，必须是一个系列的

    List<EmployeeBean> getEmployees{
        SqlConnection conn = 
        connectionFactory.createConnection();
        conn.connectString = "...";

        SqlCommand command = 
        commandFactory.createCommand();;
        command.commandString = "...";

        SqlDataReader reader = command.excuteReader();
        while(reader.read()){
            
        }
    } 
}
~~~

## 再次修改代码
~~~ java
/**
 * 数据库连接一系列抽象接口的工厂创建方法 合并到一个工厂里，确保DBFactory
 * 继承实现时编写在同一个类里（编写人员不会将不同的sql类型写在一处）
 */
public abstract class DBFactory{
    ConnectImpl createConnection();
    CommandImpl createCommand();
    DataReaderImpl createDataReader();
}

public SqlDBFactory extends DBFactory{
    @Override
    public ConnectImpl createConnection(){
        return new SqlConnection();
    }
    @Override
    public CommandImpl createCommand(){
        return new SqlCommand();
    }

    @Override
    public DataReaderImpl createDataReader(){
        return new SqlDataReader();
    }
}

public class EmployeeDAO{
    private DBFactory mDBFactory;

    //构造方法，省略...
    //问题 传入的工厂类型的具体实现类功能有关联，必须是一个系列的,使用

    List<EmployeeBean> getEmployees{
        SqlConnection conn = 
        mDBFactory.createConnection();
        conn.connectString = "...";

        SqlCommand command = 
        mDBFactory.createCommand();;
        command.commandString = "...";

        SqlDataReader reader = command.excuteReader();
        while(reader.read()){
            
        }
    } 
}
~~~