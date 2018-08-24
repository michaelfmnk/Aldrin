import { CALL_API } from 'middleware/api';

export const LOGIN = 'LOGIN';
export const RESTORE_AUTH = 'RESTORE_AUTH';

export const login = credentials => ({
    [CALL_API]: {
        type: LOGIN,
        endpoint: '/aldrin-api/auth/login',
        method: 'post',
        body: credentials,
    },
});

export const restoreAuth = token => ({
    type: RESTORE_AUTH,
    token,
});
