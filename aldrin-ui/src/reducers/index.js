import { combineReducers } from 'redux-immutable';
import { routerReducer } from 'react-router-redux';
import feed from 'reducers/feed';
import entities from 'reducers/entities';

export default combineReducers({
    rooting: routerReducer,
    feed,
    entities,
});