//
//  pair_template_test.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/4.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#include <iostream>
#include <string>
#include "pair_template_test.hpp"
#include "pair_template.hpp"

void pair_template_test_print() {
  Pair<std::string, int> ratings[4] = {
      Pair<std::string, int>("The Purpled Duck", 5),
      Pair<std::string, int>("Jaquie's Frisco Al Fresco", 4),
      Pair<std::string, int>("Cafe Sourffle", 5),
      Pair<std::string, int>("Bertie's Eats", 5)};

  int joints = sizeof(ratings) / sizeof(Pair<std::string, int>);
  std::cout << "Rating:\t Estery\n";
  for (int i = 0; i < joints; i++) {
    std::cout << ratings[i].second() << ":\t" << ratings[i].first()
              << std::endl;
  }
  std::cout << "Oops! Revised rating:\n";
  ratings[3].first() = "Bertie's Fab Eats";
  ratings[3].second() = 6;
  std::cout << ratings[3].second() << ":\t" << ratings[3].first() << std::endl;
}
