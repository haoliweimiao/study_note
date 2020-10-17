# define

``` c
typedef long Long;

#define TValuefields \
  Long value_;       \
  Long tt_

typedef struct TValue
{
  TValuefields;
} TValue;

#define val_(o) ((o)->value_)
#define tt_(o) ((o)->tt_)

int main()
{
  TValue *v;
  val_(v) = 2333L;
  tt_(v) = 1222L;
  printf("val_(o) %ld ", (val_(v)));
  printf("tt_(o) %ld ", (tt_(v)));
  return 0;
}
```