//
//  stack_template_point.hpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/3.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#ifndef stack_template_point_hpp
#define stack_template_point_hpp

#include <stdio.h>
template <class T> class StackTemplatePoint {
private:
  enum { SIZE = 10 }; // constant specific to class
  int stacksize;
  T *items; // holds stack items
  int top;  // index for top stack item

public:
  explicit StackTemplatePoint(int ss = SIZE);
  StackTemplatePoint(const StackTemplatePoint &st);
  ~StackTemplatePoint() { delete[] items; }
  bool isEmpty() { return top == 0; };
  bool isFull() { return top == stacksize; };
  bool push(const T &item); // add item to stack
  bool pop(T &item);        // pop top into item
  StackTemplatePoint &operator=(const StackTemplatePoint &st);
};

template <class T>
StackTemplatePoint<T>::StackTemplatePoint(int ss) : stacksize(ss), top(0) {
  items = new T[stacksize];
}

template <class T>
StackTemplatePoint<T>::StackTemplatePoint(const StackTemplatePoint &st) {
  stacksize = st.stacksize;
  top = st.top;
  items = new T[stacksize];
  for (int i = 0; i < top; i++) {
    items[i] = st.items[i];
  }
}

template <class T> bool StackTemplatePoint<T>::push(const T &item) {
  if (top < stacksize) {
    items[top++] = item;
    return true;
  } else {
    return false;
  }
}

template <class T> bool StackTemplatePoint<T>::pop(T &item) {
  if (top > 0) {
    item = items[--top];
    return true;
  } else {
    return false;
  }
}

template <class T>
StackTemplatePoint<T> &StackTemplatePoint<T>::
operator=(const StackTemplatePoint<T> &st) {
  if (this == &st) {
    return *this;
  }

  delete[] items;
  stacksize = st.stacksize;
  top = st.top;
  items = new T[stacksize];
  for (int i = 0; i < top; i++) {
    items[i] = st.items[i];
  }
  return *this;
}

#endif /* stack_template_point_hpp */
