import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import configureStore from './store';
import createBrowserHistory from 'history/createBrowserHistory'
import App from 'components/App';

const history = createBrowserHistory();
const store = configureStore(history);

const target = document.querySelector("#root");

render(
    <Provider store={store}>
        <App history={history}/>
    </Provider>,
    target
);