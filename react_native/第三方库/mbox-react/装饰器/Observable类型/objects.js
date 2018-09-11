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


class Store {
    @observable person = {
        // observable 属性:
        name: "John",
        age: 42,
        showAge: false,
    };

    // 计算属性:
    @computed
    get labelText() {
        return this.person.showAge ? "  name==> " + this.person.name + "  age==> " + this.person.age : "  name==> " + this.person.name;
    };

    @action setName = (name) => {
        this.person.name = name;
    };


    @action setAge = (age) => {
        this.person.age = age;
    };

    @action setShowAge = (b) => {
        this.person.showAge = b;
    }
}

const store = new Store();

// 对象属性没有暴露 'observe' 方法,
// 但不用担心, 'mobx.autorun' 功能更加强大
autorun(() => console.log(store.labelText));

store.setName("ChangeName");
store.setShowAge(true);
store.setAge(233);


@observer
export default class App extends Component<{}> {

    render() {
        return (
            <View style={styles.container}>
                <Text>test</Text>
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
