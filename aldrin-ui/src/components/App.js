import React from 'react';
import '../App.css';
import { ConnectedRouter } from 'react-router-redux';
import { Route, Switch } from 'react-router';
import FeedContainer from "containers/feed/FeedContainer";
import MaterialLayoutContainer from 'containers/layout/MaterialLayoutContainer';
import {
    MAIN,
} from 'data/routes';


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

export default App;