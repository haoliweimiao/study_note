# thread

``` c
#include <threads.h>

#define COUNT 10000000L
long counter = 0;

void incFunc(void) { for (long i = 0; i < COUNT; ++i) ++counter; }

void decFunc(void) { for (long i = 0; i < COUNT; ++i) --counter; }

int main(){
    clock_t cl = clock();
    thrd_t th1, th2;
    if (thrd_create(&th1, (thrd_start_t) incFunc, NULL) != thrd_success
        || thrd_create(&th2, (thrd_start_t) decFunc, NULL) != thrd_success) {
        fprintf(stderr, "Error creating thread/n");
        return -1;
    }
    thrd_join(th1, NULL);
    thrd_join(th2, NULL);
    LOG_I("Counter: %ld \t", counter);
    LOG_I("CPU time: %ld ms\n", (clock() - cl) * 1000L / CLOCKS_PER_SEC);

    return 0;
}
```

## LOCK
``` c
#include <threads.h>

#define COUNT 10000000L
mtx_t mtx;                              // 为访问counter而设立的互斥
long counter = 0;

void incFunc(void) {
    for (long i = 0; i < COUNT; ++i) {
        mtx_lock(&mtx);
        ++counter;
        mtx_unlock(&mtx);
    }
}

void decFunc(void) {
    for (long i = 0; i < COUNT; ++i) {
        mtx_lock(&mtx);
        --counter;
        mtx_unlock(&mtx);
    }
}

int main(){
    if (mtx_init(&mtx, mtx_plain) != thrd_success)
    {
        fprintf(stderr, "Error initializing the mutex.\n");
        return -1;
    }
    clock_t cl = clock();
    thrd_t th1, th2;
    if (thrd_create(&th1, (thrd_start_t) incFunc, NULL) != thrd_success
        || thrd_create(&th2, (thrd_start_t) decFunc, NULL) != thrd_success) {
        fprintf(stderr, "Error creating thread/n");
        return -1;
    }

    thrd_join(th1, NULL);
    thrd_join(th2, NULL);
    LOG_I("Counter: %ld \t", counter);
    LOG_I("CPU time: %ld ms\n", (clock() - cl) * 1000L / CLOCKS_PER_SEC);
    // 如例14-2所示，启动线程，等待它们完成，打印输出
    mtx_destroy(&mtx);
    return 0;
}

```