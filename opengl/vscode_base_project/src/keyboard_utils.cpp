//
//  keyboard_utils.cpp
//  OpenGlDemo
//
//  Created by 毫厘微 on 2019/6/22.
//  Copyright © 2019年 haoliwei. All rights reserved.
//

#include "keyboard_utils.hpp"

/**
 *
 */
void processInput(GLFWwindow *window)
{
    if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
        glfwSetWindowShouldClose(window, true);
}
