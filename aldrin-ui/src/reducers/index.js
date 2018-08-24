import { combineReducers } from 'redux-immutable';
import { routerReducer } from 'react-router-redux';
import { reducer as form } from 'redux-form/immutable';

import entities from 'reducers/entities';

export default combineReducers({
    rooting: routerReducer,
    entities,
    form,
});
