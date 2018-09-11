'use struce'
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View
} from 'react-native';

import {observable, autorun, computed, action, useStrict, runInAction} from 'mobx';
import {observer} from 'mobx-react';


// const value = observable(0);
// const number = observable(100);
//
// //内部值发生改变(value/number)改变时,执行内部代码
// autorun(() => {
//     console.log(value.get());
//     // console.log(number.get());
// });
//
// value.set(1);
// value.set(2);
// number.set(101);
//
//监控number是否为正数
// const plus = computed(() => number.get() > 0);
//
// autorun(() => {
//     console.log("plus ===> " + plus.get());
//     console.log("now number ==> " + number.get());
// });
//
// number.set(-19);
// number.set(-1);
// number.set(-1);
// number.set(1);


//mobx的严格模式
useStrict(true);

// class Store {
//     @observable number = 0;
//     @action add = () => {
//         this.number++;
//     }
// }

// class Store {
//     @observable name = '';
//     @action load = async () => {
//         const data = await getData();
//         runInAction(() => {
//             this.name = data.name;
//         });
//     }
// }

// const newStore = new Store();
// newStore.add();

class MyState {
    @observable num1 = 100;
    @observable num2 = 10;
    @observable todos = [];
    @observable map = observable.map({key: "value"});
    @observable times = 0;

    @action addNum1 = () => {
        this.num1++;
    };
    @action addNum2 = () => {
        this.num2++;
    };

    @action addMap = (key, value) => {
        this.map.set(key, value);
    };

    @action addTodo = (item) => {
        this.todos.push(item);
    };

    @action addTime = () => {
        this.times++;
    };

    @computed
    get total() {
        return this.num1 + this.num2;
    }

    @computed
    get reduce() {
        return this.num1 - this.num2;
    }
}

const newState = new MyState();


setInterval(() => {
    newState.addTime();
}, 1000);

autorun(() => {
    console.log("map ===> " + newState.map);
});

const TimeView = observer((todo) => <Text>now times ==> {todo.store.times}</Text>);

newState.addMap("key", "value");
newState.addMap("key2", "value2");
newState.addMap("key3", "value3");

autorun(() => {
    console.log("todo ===> " + newState.todos.map((item) => {
        return 'x ==> ' + item.x + '  y==> ' + item.y;
    }));
});

newState.addTodo({
    x: 2,
    y: 4
});

newState.addTodo({
    x: 4,
    y: 8
});


const AllNum = observer((todo) => <Text>num1 + num2 = {todo.store.total}</Text>);

const ReduceNum = observer((todo) => <Text>num1 - num2 = {todo.store.reduce}</Text>);


const ButtonOne = observer((todo) => (
    <Text style={styles.instructions} onPress={() => {
        newState.addNum1();
    }}>
        addnum1===> {newState.num1}
    </Text>
));

// @observer
// class ButtonOne extends Component {
//     render() {
//         return (
//             <Text style={styles.instructions} onPress={() => {
//                 newState.addNum1();
//             }}>
//                 addnum1===> {newState.num1}
//             </Text>
//         );
//     }
// }


const ButtonTwo = observer((todo) => (
    <Text style={styles.instructions} onPress={() => {
        newState.addNum2();
    }}>
        addnum2===> {newState.num2}
    </Text>
));

@observer
export default class App extends Component<{}> {
    componentWillMount() {
        setInterval(() => {
            this.secondsPassed++
        }, 1000)
    }

    render() {
        return (
            <View style={styles.container}>
                <ButtonOne store={newState}/>
                <ButtonTwo store={newState}/>
                <AllNum store={newState}/>
                <ReduceNum store={newState}/>
                <TimeView store={newState}/>
            </View>
        );
    }
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
