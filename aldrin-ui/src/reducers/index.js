import { combineReducers } from 'redux-immutable';
import { routerReducer } from 'react-router-redux';
import entities from 'reducers/entities';

export default combineReducers({
    rooting: routerReducer,
    entities,
});
