import React from 'react';
import PropTypes from 'prop-types';
import { ConnectedRouter } from 'react-router-redux';
import { Route, Switch } from 'react-router';
import Main from 'components/Main';
import LoginContainer from 'containers/login/LoginContainer';
import { LOGIN, MAIN } from 'data/routes';
import { userIsAuthenticatedRedir, userIsNotAuthenticatedRedir } from 'auth';


const MainComponent = userIsAuthenticatedRedir(Main);
const LoginComponent = userIsNotAuthenticatedRedir(LoginContainer);

const App = ({ history }) => (
    <ConnectedRouter history={history}>
        <div>
            <Switch>
                <Route exact path={LOGIN} component={LoginComponent} />
                <Route path={MAIN} component={MainComponent} />
            </Switch>
        </div>
    </ConnectedRouter>
);

App.propTypes = {
    history: PropTypes.object,
};

export default App;
