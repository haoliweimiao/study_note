# Point

~~~ c++
int int_val=233;
int * point;
//point = int_val 的地址
point=&int_val;
//* point == int_val
* point = * point + 1;//int_val = * point = 234

* point = (int *) 0x12312132;

//指针创建与回收
int * p =new int;
delete p;//回收
delete p;//对空指针操作delete是安全的

int * pp;
int hehe = 233;
pp = &hehe;
delete pp;//非法操作，只能对new出来的指针进行delete

//双指针指向同一地址
int * ps = new int;
int * pr = ps;
delete pr;两个指针同时被清除回收

int * ps = new int;
int * pr;
//strcpy(pr,ps); 使用该方法将ps值赋予pr,pr赋值的地址，ps copy的地址

//数组
int * p[] = new int[10];
delete [] p;

//
int * p3 =new int[3];
p3[0] = 1;
p3[1] = 2;
p3[2] = 3;
p3 = p3 + 1;//p3 指向 position 1
std::cout << p3[0];// p3[0] = 2; p3[1] = 3;

//
short stacks[3] = {3,2,1};
std::cout << *stacks << *(stacks + 1);//3 2
// arrayname[i] => *(pointname + 1)
// pointername[i] => *(pointername + 1)


//struct pointer
struct man
{
    char name[20];
    int age;
};

    man * pm = new man;
    std::cout << "input your name \n";
    std::cin.get(pm->name,20);
    std::cout << "input your age: \n";
    std::cin >> pm->age;
    std::cout << "your name: "<<pm->name<<" your age: "<<pm->age;
    delete pm;



//method
int sum_arr(int arr[], int n){
    //arr 传入进入的是 arr的地址 默认指向第一个
    //若 sum_arr(arr + 4, 4);地址从arr第五位开始;arr + 4等同于&arr[4]
    int total = 0;
    std::cout << arr << sizeof arr;
    // 1=>地址 2=>4 指针变量长度 所以需要外部传入n(实际数组长度)
    //在方法外打印为整个数组长度
    for(int i = 0; i < n; i++)
    total = total = arr[i];
    return total; 
}

//
int sum_arr(const int *start,const int *end){
    const int * pt;
    int total = 0;
    for(pt = start; pt != end; pt++)
        total = total + *pt;
    
    return total;
}

//
int sloth = 3;
const int * ps = &sloth;//ps=>sloth地址 无法通过ps修改sloth的值
int * const finger = &sloth;//无法修改 finger的指向，但允许修改sloth的值
~~~