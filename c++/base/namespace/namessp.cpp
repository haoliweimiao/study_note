//
//  namessp.cpp
//  c_study_demo
//
//  Created by 毫厘微 on 2018/9/26.
//  Copyright © 2018年 haoliwei. All rights reserved.
//

#include "namessp.hpp"
#include "iostream"
#include "namesp.hpp"

void other(void);
void another(void);

void namessp_test() {
    debts::Debt golf = {{"Benny", "Goatsniff"}, 120.0};
    debts::showDebt(golf);
    other();
    another();
}

void other(){
    pers::Person dg = {"Doodles","Glister"};
    pers::showPerson(dg);
    std::cout << std::endl;
    
    debts::Debt zippy[3];
    int i = 0;
    for (i = 0; i < 3; i++) {
        debts::getDebt(zippy[i]);
    }
    
    for (i = 0; i < 3; i++) {
        debts::showDebt(zippy[i]);
    }
    
    std::cout << "Total debt: $" << debts::sumDebts(zippy, 3) << std::endl;
}

void another(void){
    pers::Person collector = {"Milo","Rightshift"};
    pers::showPerson(collector);
    std::cout << std::endl;
}
