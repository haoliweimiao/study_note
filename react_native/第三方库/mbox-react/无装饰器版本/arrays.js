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

import {observable, autorun, computed, action, useStrict, runInAction, extendObservable} from 'mobx';
import {observer} from 'mobx-react';


//mobx的严格模式
useStrict(true);


class Store {
    constructor() {
        extendObservable(this, {
            todos: [
                {title: "Spoil tea", completed: true},
                {title: "Make coffee", completed: false}
            ],
            changeCompleted: action(function (itemNumber, completed) {
                this.todos[itemNumber].completed = completed;
            }),
            addTodo: action(function (item) {
                this.todos.push(item);
            }),
            changeItem: action(function (item, itemNumber) {
                this.todos[itemNumber] = item;
            }),
        })
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

console.log(store.todos[0].title);


const TestView = observer(({}) =>
    <Text style={styles.instructions}>{store.todos[0].title}</Text>
);


const App = observer(class App extends Component<{}> {
        render() {
            return (
                <View style={styles.container}>
                    <TestView store={store}/>
                    <Text style={styles.instructions} onPress={() => {
                        store.changeItem({title: "Spoil tea change", completed: false}, 0);
                    }}>change item 0</Text>
                </View>
            );
        }
    }
);

export default App;


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
