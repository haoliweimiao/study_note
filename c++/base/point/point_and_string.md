# Point And String
指针和字符串

+ 字符串常量的类型就是char *
+ 字符数组名的类型也是char *，就是一个地址

~~~ c++
char *p = "Please input your name:\n";
cout << p;//printf(p)亦可

char name[20];
char * pName = name;
cin >> pName;
cout << "Your name is" << pName;
~~~


~~~ c++
    using namespace std;
    char s1[100] = "12345";
    char s2[100] = "abcdefg";
    char s3[100] = "ABCDE";
    cout << "s1: " << s1 << endl;
    cout << "s2: " << s2 << endl;
    cout << "s3: " << s3 << endl;

    // s1 = "12345abc"
    strncat(s1, s2, 3);
    cout << "1： " << s1 << endl;

    // s3前三个字符拷贝到s1,s1 = "ABC45abc"
    strncpy(s1, s3, 3);
    cout << "2： " << s1 << endl;
    //s2 = "ABCDE"，s3 length 小于6，将尾部的\0拷贝到s2里面去了
    strncpy(s2, s3, 6);
    cout << "3： " << s2 << endl;
    //比较s1 s2前3个字符，比较结果是相等，输出0
    cout << "4： " << strncmp(s1, s3, 3) << endl;

    //在s1中查找'B'第一次出现的位置
    char *p = strchr(s1, 'B');
    if (p) //if(p != NULL)
    {
        //输出 5: 1,B，(p - s1 => 'B' 出现的位置，(p地址-s1起始地址)/sizeof(char))
        cout << "5: " << p - s1 << "," << *p << endl;
    }
    else
    {
        cout << "5: Not Found" << endl;
    }

    //在s1中查找"45a"第一次出现的位置,s1="ABC45abc"
    p = strstr(s1, "45a");
    if (p) //if(p != NULL)
    {
        //输出 6: 3,45abc，(p - s1 => "45a" 出现的位置，(p地址-s1起始地址)/sizeof(char))
        cout << "6: " << p - s1 << "," << *p << endl;
    }
    else
    {
        cout << "6: Not Found" << endl;
    }

    //以下颜色strtok用法
    cout << "strtok usage demo: " << endl;
    char str[] = "- This, a sample string, OK.";
    //下面要从str逐个抽取出被" ,.-"这几个字符分割的字符串"
    p = strtok(str, " ,.-"); //第一个字符为空格
    while (p)                //只要p!=NULL，就说明找到了一个子串
    {
        cout << p << endl;
        //后续调用，第一个参数必须为空
        p = strtok(NULL, " ,.-");
    }
~~~