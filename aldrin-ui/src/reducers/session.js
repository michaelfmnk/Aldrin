import { fromJS } from 'immutable';
import { startAction, successAction, failAction, UNAUTHORIZED } from 'actions/actionTypes';
import { LOGIN, RESTORE_AUTH, REGISTER, VERIFY_USER } from 'actions/session';

const defaultState = fromJS({ logginIn: false, loggedIn: false });

export default function session(state = defaultState, action) {
    switch (action.type) {
        case startAction(LOGIN): {
            return fromJS({ logginIn: true, loggedIn: false });
        }
        case successAction(VERIFY_USER):
        case successAction(LOGIN): {
            return fromJS({
                logginIn: false,
                loggedIn: true,
                token: action.response.data.token,
            });
        }
        case UNAUTHORIZED:
        case failAction(LOGIN): {
            return fromJS({
                logginIn: false,
                loggedIn: false,
                loginError: action.response.data.detail,
            });
        }
        case RESTORE_AUTH: {
            return fromJS({
                logginIn: false,
                loggedIn: true,
                token: action.token,
            });
        }
        case successAction(REGISTER): {
            return state.merge({
                registerError: null,
                verifying: true,
                registeredUserId: action.response.data.id,
            });
        }
        case failAction(REGISTER): {
            return state.merge({ registerError: action.response.data.detail });
        }
        default: {
            return state;
        }
    }
}
