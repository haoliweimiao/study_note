//
//  member_template_test.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/4.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#include <iostream>
#include "member_template_test.hpp"
#include "member_template.hpp"

void member_template_test_print() {
  MemberTemplate<double> guy(3.5, 3);
  std::cout << "T was set double\n";
  guy.show();
  std::cout << "V was set to T, which is double, then V was set to int\n";
  std::cout << guy.blab(10, 2.3) << std::endl;
  std::cout << "U was set to int\n";
  std::cout << guy.blab(10.0, 2.3) << std::endl;
  std::cout << "U was set to double\n";
  std::cout << "done\n";
}
