//
//  main.cpp
//  OpenGlDemo
//
//  Created by 毫厘微 on 2019/3/2.
//  Copyright © 2019年 haoliwei. All rights reserved.
//

//glad 头文件必须在包含 glfw 头文件之前，否则回报错：OpenGL header already included, 
//remove this include, glad already provides
#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <iostream>
#include <stdio.h>
#include "../src/keyboard_utils.hpp"

/**
 * 然而，当用户改变窗口的大小的时候，视口也应该被调整。我们可以对窗口注册一个回调函数(Callback
 * Function)，
 * 它会在每次窗口大小被调整的时候被调用。这个回调函数的原型如下：
 */
void framebuffer_size_callback(GLFWwindow *window, int width, int height);

int main(int argc, char **argv) {
  //初始化GLFW
  glfwInit();
  //指定版本3.3
  glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
  glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
  glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
  // mac系统需要添加此项
  glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

  //接下来我们创建一个窗口对象，这个窗口对象存放了所有和窗口相关的数据，而且会被GLFW的其他函数频繁地用到。
  // glfwCreateWindow函数需要窗口的宽和高作为它的前两个参数。第三个参数表示这个窗口的名称（标题），这里我们使用"LearnOpenGL"，
  //当然你也可以使用你喜欢的名称。最后两个参数我们暂时忽略。这个函数将会返回一个GLFWwindow对象，我们会在其它的GLFW操作中使用到。
  //创建完窗口我们就可以通知GLFW将我们窗口的上下文设置为当前线程的主上下文了。
  GLFWwindow *window = glfwCreateWindow(800, 600, "LearnOpenGL", NULL, NULL);
  if (window == NULL) {
    std::cout << "Failed to create GLFW window" << std::endl;
    glfwTerminate();
    return -1;
  }
  glfwMakeContextCurrent(window);

  // GLAD是用来管理OpenGL的函数指针的，所以在调用任何OpenGL的函数之前我们需要初始化GLAD。
  //我们给GLAD传入了用来加载系统相关的OpenGL函数指针地址的函数。GLFW给我们的是glfwGetProcAddress，它根据我们编译的系统定义了正确的函数。
  if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress)) {
    std::cout << "Failed to initialize GLAD" << std::endl;
    return -1;
  }
  //在我们开始渲染之前还有一件重要的事情要做，我们必须告诉OpenGL渲染窗口的尺寸大小，即视口(Viewport)，
  //这样OpenGL才只能知道怎样根据窗口大小显示数据和坐标。我们可以通过调用glViewport函数来设置窗口的维度(Dimension)：
  // glViewport函数前两个参数控制窗口左下角的位置。第三个和第四个参数控制渲染窗口的宽度和高度（像素）。
  glViewport(0, 0, 800, 600);

  //注册窗口改变监听回调
  glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);

  while (!glfwWindowShouldClose(window)) {
    //输入
    processInput(window);

    //渲染指令
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT);

    //检查并调用事件，交换缓冲
    glfwSwapBuffers(window);
    glfwPollEvents();
  }

  //当渲染循环结束后我们需要正确释放/删除之前的分配的所有资源。我们可以在main函数的最后调用glfwTerminate函数来完成。
  glfwTerminate();

  return 0;
}

/**
 *  这个帧缓冲大小函数需要一个GLFWwindow作为它的第一个参数，以及两个整数表示窗口的新维度。
 *  每当窗口改变大小，GLFW会调用这个函数并填充相应的参数供你处理。
 */
void framebuffer_size_callback(GLFWwindow *window, int width, int height) {
  std::cout << "width: " << width << "height: " << height << std::endl;
  glViewport(0, 0, width, height);
}
