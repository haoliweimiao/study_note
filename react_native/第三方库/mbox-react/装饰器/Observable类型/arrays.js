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
    @observable todos = [
        {title: "Spoil tea", completed: true},
        {title: "Make coffee", completed: false}
    ];

    @action changeCompleted = (itemNumber, b) => {
        this.todos[itemNumber].completed = b;
    };

    @action addTodo = (item) => {
        this.todos.push(item);
    }
}

const store = new Store();

autorun(() => {
    console.log("Remaining:", store.todos
        .filter(todo => !todo.completed)
        .map(todo => todo.title)
        .join(", ")
    );
});

store.changeCompleted(0, false);

store.addTodo({title: 'Take a nap', completed: false});
store.addTodo({title: 'heheda', completed: false});
store.addTodo({title: 'Take a x', completed: false});


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
