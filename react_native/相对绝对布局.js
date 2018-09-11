'use strict';
import React, {Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    Image
} from 'react-native';


var MenuFragment = React.createClass({
    render() {
        return (
            <View style={{flex: 1, flexDirection: 'column'}}>
                <Text>FlexBox布局</Text>

                <View style={styles.container}>
                    <View style={styles.box1}/>
                    <View style={[styles.box2]}/>
                    <View style={[styles.box3]}/>
                </View>

                <Text>position=relative,top:20</Text>
                <View style={styles.container}>
                    <View style={styles.box1}/>
                    <View style={[styles.box2, {position: 'relative', top: 20}]}/>
                    {/*<View style={styles.box3}/>*/}
                </View>

                <Text>position=absolute,top:20</Text>
                <View style={styles.container}>
                    {/*<View style={styles.box1}/>*/}
                    <View style={[styles.box2, {position: 'absolute', top: 20}]}/>
                    {/*<View style={styles.box3}/>*/}
                </View>
            </View>
        )
    }
});


const styles = {
    container: {height: 180, backgroundColor: '#CCCCCC', marginBottom: 10,},
    box1: {width: 50, height: 50, backgroundColor: '#FF0000'},
    box2: {width: 50, height: 50, backgroundColor: '#00FF00'},
    box3: {width: 50, height: 50, backgroundColor: '#0000FF'}
};

module.exports = MenuFragment;