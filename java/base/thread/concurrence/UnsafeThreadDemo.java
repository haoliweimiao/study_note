import java.math.BigInteger;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * unsafe thread demo
 */
@NotThreadSafe
public class UnsafeThreadDemo {
    private int value;

    /**
     * return a unique value
     */
    public int getNext() {
        return value++;
    }
}

@ThreadSafe
public class Sequence {
    @GuardedBy("this")
    private int value;

    public synchronized int getNext() {
        return value++;
    }
}

/**
 * ++count 不是原子操作，多线程中会遗失更新(lost updates)
 */
@NotThreadSafe
public class UnsafeCountingFactorizer implements Servlet {
    private long count = 0;

    public long getCount() {
        return count;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(resp, factors);
    }
}

/**
 * 惰性初始化存在竞争条件
 */
@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null)
            instance = new ExpensiveObject();
        return instance;
    }
}

/**
 * 没有正确原子化的Servlet试图缓存它的最新结果
 * 当一个不变约束涉及多个变量时，变量间不是彼此独立的：某个变量的值会制约其他几个变量的值。
 * 因此，更新一个变量的时候，要在同一原子操作中更新其他几个。
 */
@NotThreadSafe
public class UnsafeCachingFactorizer implements Servlet {
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);

        if(i.equals(lastNumber.get())){
            encodeIntoResponse(resp, lastFactors.get());
        }else{
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }
    }
}

/**
 * 将service方法声明为synchronized,所以同一时间只有一个线程可以进入service方法
 * 现在SynchronizedFactorizer是线程安全的，但是方法过于极端，它禁止多个用户同时使用service，
 * 这将导致糟糕的响应性（性能不行）
 */
@ThreadSafe
public class SynchronizedFactorizer implements Servlet {
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    public synchronized void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);

        if(i.equals(lastNumber.get())){
            encodeIntoResponse(resp, lastFactors.get());
        }else{
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }
    }
}