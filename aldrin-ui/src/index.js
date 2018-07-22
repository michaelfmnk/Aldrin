import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import configureStore from 'store';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import createBrowserHistory from 'history/createBrowserHistory';
import MuiPickersUtilsProvider from 'material-ui-pickers/utils/MuiPickersUtilsProvider';
import App from 'components/App';
import MomentUtils from 'material-ui-pickers/utils/moment-utils';


const history = createBrowserHistory();
const store = configureStore(history);
const theme = createMuiTheme();
const target = document.querySelector('#root');

render(
    <Provider store={store}>
        <MuiThemeProvider theme={theme}>
            <MuiPickersUtilsProvider utils={MomentUtils}>
                <App history={history} />
            </MuiPickersUtilsProvider>
        </MuiThemeProvider>
    </Provider>,
    target,
);
