# stb_image库相关错误

+ [multiple definition](#multiple_definition)

## multiple_definition
multiple definition of stb_image.h:902

在c文件中修改引用方法
~~~ c
#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
~~~