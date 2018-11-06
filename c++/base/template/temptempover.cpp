//
//  temptempover.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/9/22.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#include "temptempover.hpp"
#include "iostream"

template <typename T>
void show_array(T arr[], int n);

template <typename T>
void show_array(T* arr[], int n);

struct debts{
    char name[50];
    double amount;
};


void temptempover_test() {
  int things[6] = {13, 31, 103, 301, 310, 120};
  struct debts mr_E[3] = {
      {"Ima Wolfe", 2400.0}, {"Ura Foxe", 1300.0}, {"Iby Stout", 1800.0}};

  double* pd[3];

  // set pointers to the amount members of the structures in mr_E
  for (int i = 0; i < 3; i++) {
    pd[i] = &mr_E[i].amount;
  }
  std::cout << "Listing Mr. E's counts of things:\n";

  // things is an array of int
  show_array(things, 6);  // using template A
  std::cout << "Listing Mr. E's debts:\n";

  // pd is an array of pointers to double
  show_array(pd, 3);
}

template <typename T>
void show_array(T arr[], int n) {
  std::cout << "template A\n";
  for (int i = 0; i < n; i++) {
    std::cout << arr[i] << ' ';
  }
  std::cout << std::endl;
}

template <typename T>
void show_array(T* arr[], int n) {
  std::cout << "template B\n";
  for (int i = 0; i < n; i++) {
    std::cout << *arr[i] << ' ';
  }
  std::cout << std::endl;
}
