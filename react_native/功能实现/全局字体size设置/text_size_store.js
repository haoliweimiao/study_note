'use strict';

import {Dimensions} from 'react-native';

let px_width = Dimensions.get('window').width / 720;
let px_height = Dimensions.get('window').height / 1280;

import {observable, autorun, computed, action, useStrict, runInAction} from 'mobx';
import {observer} from 'mobx-react';

//mobx的严格模式
useStrict(true);

let smallSize = 24 * px_width;
let normalSize = 32 * px_width;
let largeSize = 48 * px_width;


class Store {
    @observable
    textSize = largeSize;


    @action
    changeTextSize = (textStyleStr) => {
        if (textStyleStr === 'smallSize') {
            this.textSize = smallSize;
            console.log("change text size ====>  " + textStyleStr);
        } else if (textStyleStr === 'normalSize') {
            this.textSize = normalSize;
            console.log("change text size ====>  " + textStyleStr);
        } else if (textStyleStr === 'largeSize') {
            this.textSize = largeSize;
            console.log("change text size ====>  " + textStyleStr);
        }
    }

}

const store = new Store();

export default store;