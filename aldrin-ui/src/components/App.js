import React from 'react';
import '../App.css';
import { ConnectedRouter } from 'react-router-redux';
import { Route, Switch } from 'react-router';
import FeedContainer from "../containers/feed/FeedContainer";
import {
    MAIN,
    FEED,
} from 'data/routes';


const App = ({ history }) => (
    <ConnectedRouter history={history}>
        <div>
            <Switch>
                <Route to={MAIN} component={FeedContainer} />
            </Switch>
        </div>
    </ConnectedRouter>
);

export default App;