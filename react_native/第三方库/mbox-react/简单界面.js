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

//mobx的严格模式
useStrict(true);

class MyState {
    @observable num1 = 100;//使用此标签监控要检测的数据
    @observable num2 = 10;

//@action:使用此标签监控数据改变的自定义方法(当在需要数据改变的时候执行此自定义方法，那么View层也会跟着自动变化，默认此View层已经使用@observer标签监控)
    @action addNum1 = () => {
        this.num1++;
    };
    @action addNum2 = () => {
        this.num2++;
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

const AllNum = observer((props) => <Text>num1 + num2 = {props.store.total}</Text>);

const ReduceNum = observer((props) => <Text>num1 - num2 = {props.store.reduce}</Text>);

const ButtonOne = observer((props) => (
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


const ButtonTwo = observer((props) => (
    <Text style={styles.instructions} onPress={() => {
        newState.addNum2();
    }}>
        addnum2===> {newState.num2}
    </Text>
));

@observer//使用此标签监控当数据变化是要更新的Component（组件类）
export default class App extends Component<{}> {
    render() {
        return (
            <View style={styles.container}>
                <ButtonOne store={newState}/>
                <ButtonTwo store={newState}/>
                <AllNum store={newState}/>
                <ReduceNum store={newState}/>
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
