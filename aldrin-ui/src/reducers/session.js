import { fromJS } from 'immutable';
import { startAction, successAction, failAction, UNAUTHORIZED } from 'actions/actionTypes';
import { LOGIN, RESTORE_AUTH } from 'actions/session';

const defaultState = fromJS({ logginIn: false, loggedIn: false });

export default function session(state = defaultState, action) {
    switch (action.type) {
        case startAction(LOGIN): {
            return fromJS({ logginIn: true, loggedIn: false });
        }
        case successAction(LOGIN): {
            console.log(action);
            return fromJS({
                logginIn: false,
                loggedIn: true,
                token: action.response.data.token,
            });
        }
        case UNAUTHORIZED:
        case failAction(LOGIN): {
            return fromJS({ logginIn: false, loggedIn: false });
        }
        case RESTORE_AUTH: {
            return fromJS({
                logginIn: false,
                loggedIn: true,
                token: action.token,
            });
        }
        default: {
            return state;
        }
    }
}
