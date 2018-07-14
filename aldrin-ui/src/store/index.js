import { routerMiddleware } from 'react-router-redux';
import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import rootReducer from '../reducers';
import { fromJS } from 'immutable';
import { composeWithDevTools } from 'redux-devtools-extension/logOnlyInProduction';
export default function configureStore(history) {
    const enhancers = [

    ];

    const middleware = [
        thunk,
        routerMiddleware(history)
    ];

    const composedEnhancers = compose(
        composeWithDevTools(applyMiddleware(...middleware)),
        ...enhancers
    );

    const store = createStore(
        rootReducer,
        fromJS({}),
        composedEnhancers
    );

    return store;
}