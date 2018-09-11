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
    @observable maps = observable.map();


    @action setMap = (key, value) => {
        this.maps.set(key, value);
    };

    @action deleteMap = (key) => {
        this.maps.delete(key);
    }
}

const store = new Store();

autorun(() => console.log(store.maps.entries()));

store.setMap("key1", "value1");
store.setMap("key1", "value2");
store.setMap("key2", "value2");
store.setMap("key3", "value3");
store.deleteMap("key2");
console.log("values ===> " + store.maps.values());
console.log("keys ===> " + store.maps.keys());
console.log("key=key1 ==> " + store.maps.get("key1"));
console.log("maps size ===> " + store.maps.size);
let tojs = store.maps.toJS();
console.log(tojs);
console.log(tojs.key1);


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
