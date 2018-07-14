import { combineReducers } from 'redux-immutable';
import { routerReducer } from 'react-router-redux';
import feed from 'reducers/feed';

export default combineReducers({
    rooting: routerReducer,
    feed,
});