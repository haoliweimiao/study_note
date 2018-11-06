//
//  many_friend_template_test.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/6.
//  Copyright © 2018年 haoliwei. All rights reserved.
//  通过在类内部声明模版，可以创建非约束友元函数，即每个函数具体化都是每个类具体化的友元。
//

#include <iostream>
#include "many_friend_template_test.hpp"

template <class T> class ManyFriend {
private:
  T item;

public:
  ManyFriend(const T &i) : item(i) {}

  template <class C, class D> friend void show2(C &, D &);
};

template <class C, class D> void show2(C &c, D &d) {
  std::cout << c.item << " , " << d.item << std::endl;
}

void many_friend_template_test_print() {
  ManyFriend<int> hif1(10);
  ManyFriend<int> hif2(20);
  ManyFriend<double> hfdb(10.5);
  std::cout << "hfi1,hfi2: ";
  show2(hif1, hif2);
  std::cout << "hidb,hif2: ";
  show2(hfdb, hif2);
    
//    hfi1,hfi2: 10 , 20
//    hidb,hif2: 10.5 , 20
}
