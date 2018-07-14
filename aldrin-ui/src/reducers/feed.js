import { feed } from 'data/fakedata';
import { List, fromJS } from 'immutable';

const defaultState = fromJS({
    items: feed
});

export default function(state = defaultState, action) {
    switch (action.type) {
        default: {
            return state;
        }
    }
};