import java.util.concurrent.*;

/**
 * ThreadLocal 提供了线程本地的实例。它与普通变量的区别在于，每个使用该变量的线程 都会初始化一个完全独立的实例副本。ThreadLocal
 * 变量通常被private static修饰。 当一个线程结束时，它所使用的所有 ThreadLocal 相对的实例副本都可被回收。
 * 
 * 
 * ThreadLocal 并不解决线程间共享数据的问题 ThreadLocal 通过隐式的在不同线程内创建独立实例副本避免了实例线程安全的问题
 * 每个线程持有一个 Map 并维护了 ThreadLocal 对象与具体实例的映射，该 Map 由于只被持有它的线程访问，故不存在线程安全以及锁的问题
 * ThreadLocalMap 的 Entry 对 ThreadLocal 的引用为弱引用，避免了 ThreadLocal 对象无法被回收的问题
 * ThreadLocalMap 的 set 方法通过调用 replaceStaleEntry 方法回收键为 null 的 Entry
 * 对象的值（即为具体实例）以及 Entry 对象本身从而防止内存泄漏 ThreadLocal 适用于变量在线程间隔离且在方法间共享的场景
 */
public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        int threads = 3;
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        InnerClass innerClass = new InnerClass();
        for (int i = 1; i <= threads; i++) {
            new Thread(() -> {
                for (int j = 0; j < 4; j++) {
                    innerClass.add(String.valueOf(j));
                    innerClass.print();
                }
                innerClass.set("hello world");
                countDownLatch.countDown();
            }, "thread - " + i).start();
        }
        countDownLatch.await();

        // String s = "你们这些逗比";
        // char[] a = s.toCharArray();
        // String sb = new String();
        // for (char cc : a) {
        // sb += Integer.toBinaryString(cc);
        // }
        // System.out.print(sb);
    }

    private static class InnerClass {
        public void add(String newStr) {
            StringBuilder str = Counter.counter.get();
            Counter.counter.set(str.append(newStr));
        }

        public void print() {
            System.out.printf("Thread name:%s , ThreadLocal hashcode:%s, Instance hashcode:%s, Value:%s\n",
                    Thread.currentThread().getName(), Counter.counter.hashCode(), Counter.counter.get().hashCode(),
                    Counter.counter.get().toString());
        }

        public void set(String words) {
            Counter.counter.set(new StringBuilder(words));
            System.out.printf("Set, Thread name:%s , ThreadLocal hashcode:%s,  Instance hashcode:%s, Value:%s\n",
                    Thread.currentThread().getName(), Counter.counter.hashCode(), Counter.counter.get().hashCode(),
                    Counter.counter.get().toString());
        }
    }

    private static class Counter {
        private static ThreadLocal<StringBuilder> counter = new ThreadLocal<StringBuilder>() {
            @Override
            protected StringBuilder initialValue() {
                return new StringBuilder();
            }
        };
    }
}