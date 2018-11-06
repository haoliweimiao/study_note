//
//  stack_template_test.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/3.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#include <iostream>
#include <string>
#include <cctype>
#include "stack_template_test.hpp"
#include "stack_template.hpp"

void stack_template_test_print() {
  StackTemplate<std::string> st; // create an empty stack
  char ch;
  std::string po;

  std::string notityMessage = "Please enter A to add a purchase order,\nP to "
                         "process a PO, or Q to quit.\n";
  std::cout << notityMessage;

  while (std::cin >> ch && std::toupper(ch) != 'Q') {
    while (std::cin.get() != '\n') {
      continue;
    }

    if (!std::isalpha(ch)) {
      std::cout << '\a';
      continue;
    }

    switch (ch) {
    case 'A':
    case 'a':
      std::cout << "Enter a PO number to add: ";
      std::cin >> po;
      if (st.isFull()) {
        std::cout << "stack already full\n";
      } else {
        st.push(po);
      }
      break;

    case 'P':
    case 'p':
      if (st.isEmpty()) {
        std::cout << "stack already empty\n";
      } else {
        st.pop(po);
        std::cout << "PO #" << po << " popped\n";
        break;
      }
    }
    std::cout << notityMessage;
    std::cout << "Bye.\n";
  }
}
