import java.math.BigInteger;

/**
 * 
 */
@ThreadSafe
public class Sequence {
    @GuardedBy("this")
    private int value;

    public synchronized int getNext() {
        return value++;
    }
}

/**
 * 一个无状态的Servlet 无状态的对象永远是线程安全的
 */
@ThreadSafe
public class StatelessFactorizer implements Servlet {
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
    }
}

/**
 * Servlet使用AtomicLong统计请求数
 * AtomicLong为系统已有的线程安全类
 * java.util.concurrent.atomic 包中包含了原子变量(atomtic variable)类
 * 这些类用来实现数字和对象引用的原子状态转换
 */
@ThreadSafe
public class StatelessFactorizer implements Servlet {
    private final AtomicLong count = new AtomicLong(0);

    public long getCount() {
        return count.get();
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
        encodeIntoResponse(resp, factors);
    }
}