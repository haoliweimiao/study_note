# constructor与destructor


GCC可以给函数若干属性，其中construction就是其中一个。具体有哪些属性，可以看GCC的文档。http://gcc.gnu.org/onlinedocs/gcc/Function-Attributes.html,在上面文档中有对于constructor与destructor的描述：

constructor
destructor
constructor (priority)
destructor (priority)
The constructor attribute causes the function to be called automatically before execution enters main ().
Similarly, the destructor attribute causes the function to be called automatically after main () has completed or exit () has been called.
Functions with these attributes are useful for initializing data that will be used implicitly during the execution of the program.
You may provide an optional integer priority to control the order in which constructor and destructor functions are run.
A constructor with a smaller priority number runs before a constructor with a larger priority number; the opposite relationship holds for destructors.
So, if you have a constructor that allocates a resource and a destructor that deallocates the same resource, both functions typically have the same priority.
The priorities for constructor and destructor functions are the same as those specified for namespace-scope C++ objects (see C++ Attributes).

These attributes are not currently implemented for Objective-C.

大致意思就是，可以给一个函数赋予constructor或destructor，其中constructor在main开始运行之前被调用，destructor在main函数结束后被调用。如果有多个constructor或destructor，可以给每个constructor或destructor赋予优先级，对于constructor，优先级数值越小，运行越早。destructor则相反。


Demo

~~~ c
#include <stdio.h>  
  
__attribute__((constructor(101))) void foo()  
{  
    printf("in constructor of foo\n");  
}  
__attribute__((constructor(102))) void foo1()  
{  
    printf("in constructor of foo1\n");  
}  
__attribute__((destructor)) void bar()  
{  
    printf("in constructor of bar\n");  
}  
  
int main()  
{  
        printf("in main\n");  
        return 0;  
}  
~~~

运行的结果为：

in constructor of foo
in constructor of foo1
in main
in constructor of bar