'use strict';
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
import {observer} from 'mobx-react';
import textSizeStore from './js/store/text_size_store';


@observer
export default class App extends Component<{}> {
    render() {
        return (
            <View style={styles.container}>
                <Text style={[styles.welcome, {fontSize: textSizeStore.textSize}]} onPress={() => {
                    textSizeStore.changeTextSize('smallSize');
                }}>
                    smallSize
                </Text>
                <Text style={[styles.welcome, {fontSize: textSizeStore.textSize}]} onPress={() => {
                    textSizeStore.changeTextSize('normalSize');
                }}>
                    normalSize
                </Text>
                <Text style={[styles.welcome, {fontSize: textSizeStore.textSize}]} onPress={() => {
                    textSizeStore.changeTextSize('largeSize');
                }}>
                    largeSize
                </Text>
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
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});