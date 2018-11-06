//
//  stack_template_point_test.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/3.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#include <iostream>
#include <cstdlib>
#include <ctime>
#include "stack_template_point_test.hpp"
#include "stack_template_point.hpp"

const int stack_template_point_test_number = 10;

void stack_template_point_test_print() {
  std::srand((int)std::time(0)); // randomize rand();
  std::cout << "Please enter stack size: ";
  int stacksize;
  std::cin >> stacksize;
  // create an empty stack with stacksize siots
  StackTemplatePoint<const char *> st(stacksize);

  // in basket
  const char *in[stack_template_point_test_number] = {
      " 1:Hank Gilgamesh", " 2:Kiki Ishtar",     " 3:Betty Rocker",
      " Ian Flagranti",    " 5:Wolfgang Kibble", " 6:Portia Koop",
      " 7:Joy Almondo",    " 8:Xaverie Paprika", " 9:Juan Moore",
      " 10:Misha Mache"};

  // out basket
  const char *out[stack_template_point_test_number];

  int processed = 0;
  int nextin = 0;

  while (processed < stack_template_point_test_number) {
    if (st.isEmpty()) {
      st.push(in[nextin++]);
    } else if (st.isFull()) {
      st.pop(out[processed++]);
    } else if (std::rand() % 2 && nextin < stack_template_point_test_number) {
      // 50-50 chance
      st.push(in[nextin++]);
    } else {
      st.pop(out[processed++]);
    }
  }

  for (int i = 0; i < stack_template_point_test_number; i++) {
    std::cout << out[i] << std::endl;
  }

  std::cout << "Bye.\n";
}
