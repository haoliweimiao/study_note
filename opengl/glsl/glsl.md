# glsl

+ [着色器版本规范](#着色器版本规范)
+ [变量和变量类型](#变量和变量类型)
+ [变量构造器](#变量构造器)
+ [向量和矩阵分量](#向量和矩阵分量)

## 着色器版本规范

OPENGL ES 3.0顶点着色器和片段着色器第一行总是声明着色器版本。
~~~ glsl
#version 300 es
~~~
没有声明版本号的着色器被认定为使用OpenGL ES着色语言的1.0版本。着色语言的1.0版本用于OpenGL ES 2.0。

## 变量和变量类型

|变量分类|类型|描述|
|:-|:-|:-|
|标量|float int uint bool|浮点 整数 无符号整数 布尔值|
|浮点向量|float vec2 vec3 vec4|有1、2、3、4个分量的基于浮点的向量类型|
|整数向量|int ivec2 ivec3 ivec4|有1、2、3、4个分量的基于整数的向量类型|
|无符号整数向量|uint uvec2 uvec3 uvec4|有1、2、3、4个分量的基于无符号整数的向量类型|
|布尔向量|bool bvec2 bvec3 bvec4|有1、2、3、4个分量的基于布尔的向量类型|
|矩阵|mat2(mat2 * 2),mat2 * 3,mat2 * 4,mat3(mat3 * 3),mat3 * 2,mat3 * 4,mat4 * 2,mat4 * 3,mat4(mat4 * 4)||

## 变量构造器
~~~ glsl
float myFloat = 1.0;
float myFloat2 = 1;// Error: invalid type conversion
bool myBool = true;
int myInt = 1;
int myInt2 = 1.0;// Error: invalid type conversion
myFloat = float(myBool);// Convert from bool -> float
myFloat = float(myInt);// Convert from int -> float
myBool = bool(myInt);// Convert from int -> bool


vec4 myVec4 = vec4(1.0); // myVec4 = (1.0,1.0,1.0,1.0);
vec3 myVec3 = vec3(1.0,0.0,0.5); // myVec3 = (1.0,0.0,0.5);
vec3 temp = vec3(myVec3); // temp = myVec3 = (1.0,0.0,0.5);
vec2 myVec2 = vec2(myVec3); // myVec2 = (myVec3.x,myVec3.y);
myVec4 = vec4(myVec2,temp); // myVec4 = (myVec2.x, myVec2.y , temp.x, temp.y);
~~~

## 向量和矩阵分量

向量的单独分量可以用两种方式表示:使用"."运算符或者通过数组下标。

1. 根据组成向量的分量数量，每个分量可以通过使用{x,y,z,w},{r,g,b,a},{s,t,p,q}。三种不同命名方案的原因是向量可以互换的用于表示数学上的向量、颜色、纹理的坐标。不同的命名约定只是为了方便，但是不能在访问向量时混合使用命名约定。(如 .xgz)

~~~ glsl
vec3 v3 = vec3(0.0, 1.0, 2.0);
vec3 temp;

temp = v3.xyz; //temp = (0.0, 1.0, 2.0);
temp = v3.xxx; //temp = (0.0, 0.0, 0.0);
temp = v3.zyx; //temp = (2.0, 1.0, 0.0);
~~~

2. 向量还可以使用数组下标"[]"运算符访问。

~~~ glsl
mat4 m4 = mat4(1.0);
vec4 v4 = m4[0];
float m1_1 = m4[0][0];
float m2_2 = m4[2].z;
~~~