//
//  namesp.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/9/26.
//  Copyright © 2018年 haoliwei. All rights reserved.
//
#include <iostream>
#include "namesp.hpp"

namespace pers {
    void getPerson(Person& rp) {
        std::cout << "Enter first name: ";
        std::cin >> rp.fname;
        std::cout << "Enter last name: ";
        std::cin >> rp.lname;
    }

    void showPerson(const Person& rp) {
        std::cout << rp.fname << " , " << rp.lname;
    }
}

namespace debts {
    void getDebt(Debt & rd){
        pers::getPerson(rd.name);
        std::cout << "Enter debt: ";
        std::cin >> rd.amount;
    }
    
    void showDebt(const Debt & rd){
        pers::showPerson(rd.name);
        std::cout << ": $ " << rd.amount << std::endl;
    }
    
    double sumDebts(const Debt ar[] , int n){
        double total = 0;
        for (int i = 0; i < n; i++) {
            total += ar[i].amount;
        }
        return total;
    }
}
