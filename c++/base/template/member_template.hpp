//
//  member_template.hpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/4.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#ifndef member_template_hpp
#define member_template_hpp

#include <iostream>
#include <stdio.h>

template <class T> class MemberTemplate {
  template <class V> // nested template class member
  class Hold {
  private:
    V val;

  public:
    Hold(V v = 0) : val(v) {}
    void show() const { std::cout << val << std::endl; }
    V value() const { return val; }
  };
  Hold<T> q;   // template object
  Hold<int> n; // template object
public:
  MemberTemplate(T t, int i) : q(t), n(i) {}
  template <class U> // template method
  U blab(U u, T t) {
    return (n.value() + q.value()) * u / t;
  }
  void show() const {
    q.show();
    n.show();
  }
};

#endif /* member_template_hpp */
