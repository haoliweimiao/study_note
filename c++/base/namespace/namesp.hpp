//
//  namesp.hpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/9/26.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#ifndef namesp_hpp
#define namesp_hpp

#include <stdio.h>
#include <string>

#endif /* namesp_hpp */

// create the pers and debts namespaces

namespace pers {
    struct Person {
        std::string fname;
        std::string lname;
    };
    void getPerson(Person&);
    void showPerson(const Person&);
}


namespace debts {
    struct Debt {
        pers::Person name;
        double amount;
    };
    void getDebt(Debt&);
    void showDebt(const Debt&);
    double sumDebts(const Debt ar[], int n);
}


