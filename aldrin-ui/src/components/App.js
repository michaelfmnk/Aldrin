import React from 'react';
import PropTypes from 'prop-types';
import { ConnectedRouter } from 'react-router-redux';
import { Route, Switch } from 'react-router';
import FeedContainer from 'containers/feed/FeedContainer';
import MaterialLayoutContainer from 'containers/layout/MaterialLayoutContainer';
import LoginContainer from 'containers/login/LoginContainer';
import { LOGIN, MAIN } from 'data/routes';


const App = ({ history }) => (
    <ConnectedRouter history={history}>
        <div>
            <Switch>
                <Route path={LOGIN} component={LoginContainer} />
                <MaterialLayoutContainer>
                    <Switch>
                        <Route exact path={MAIN} component={FeedContainer} />
                    </Switch>
                </MaterialLayoutContainer>

            </Switch>
        </div>
    </ConnectedRouter>
);

App.propTypes = {
    history: PropTypes.object,
};

export default App;
