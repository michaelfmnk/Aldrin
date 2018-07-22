import React from 'react';
import PropTypes from 'prop-types';
import { ConnectedRouter } from 'react-router-redux';
import { Route, Switch } from 'react-router';
import FeedContainer from 'containers/feed/FeedContainer';
import MaterialLayoutContainer from 'containers/layout/MaterialLayoutContainer';
import { MAIN } from 'data/routes';


const App = ({ history }) => (
    <ConnectedRouter history={history}>
        <div>
            <Switch>
                <MaterialLayoutContainer>
                    <Route to={MAIN} component={FeedContainer} />
                </MaterialLayoutContainer>
            </Switch>
        </div>
    </ConnectedRouter>
);

App.propTypes = {
    history: PropTypes.object,
};

export default App;
