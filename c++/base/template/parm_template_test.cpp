//
//  parm_template_test.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/4.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#include <iostream>
#include "parm_template_test.hpp"
#include "stack_template.hpp"
#include "parm_template.hpp"

void parm_template_test_print() {
  using std::cout;
  using std::cin;
  using std::endl;
  Crab<StackTemplate> nebula;

  // Stack must match template <template T> class Thing
  int ni;
  double nb;
  cout << "Enter int double pairs,such as 4 3.5 (0 0 to end):\n";
  while (cin >> ni >> nb && ni > 0 && nb > 0) {
    if (!nebula.push(ni, nb)) {
      break;
    }
  }

  while (nebula.pop(ni, nb)) {
    cout << ni << " , " << nb << endl;
  }
  cout << "Done.\n";
}
