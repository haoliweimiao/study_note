//
//  parm_template.hpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/4.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#ifndef parm_template_hpp
#define parm_template_hpp

#include <stdio.h>

template <template <class T> class Thing> class Crab {
private:
  Thing<int> s1;
  Thing<double> s2;

public:
  Crab(){};
  // assumes the thing class has push() and pop() methods
  bool push(int a, double x) { return s1.push(a) && s2.push(x); }
  bool pop(int &a, double &x) { return s1.pop(a) && s2.pop(x); }
};
#endif /* parm_template_hpp */
