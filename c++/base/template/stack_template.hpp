//
//  stack_template.hpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/11/3.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#ifndef stack_template_hpp
#define stack_template_hpp

#include <stdio.h>

template <class StackType>
class StackTemplate {
private:
  enum { MAX = 10 };    // constant specific to class
  StackType items[MAX]; // holds stack items
  int top;              // index for top stack item

public:
  StackTemplate();
  bool isEmpty();
  bool isFull();
  bool push(const StackType &item); // add item to stack
  bool pop(StackType &item);        // pop top into item
};

template <class StackType> StackTemplate<StackType>::StackTemplate() {
    top = 0;
}

template <class StackType> bool StackTemplate<StackType>::isEmpty() {
    return top == 0;
}

template <class StackType> bool StackTemplate<StackType>::isFull() {
    return top == MAX;
}
template <class StackType>
bool StackTemplate<StackType>::push(const StackType &item) {
    if (top < MAX) {
        items[top++] = item;
        return true;
    } else {
        return false;
    }
}

template <class StackType> bool StackTemplate<StackType>::pop(StackType &item) {
    if (top > 0) {
        item = items[--top];
        return true;
    } else {
        return false;
    }
}

#endif /* stack_template_hpp */
