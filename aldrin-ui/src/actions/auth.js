import { CALL_API } from 'middleware/api';

export const LOGIN = 'LOGIN';

export const login = (credentials) => {
    console.log(credentials);
    return {
        [CALL_API]: {
            type: LOGIN,
            endpoint: '/aldrin-api/auth/login',
            method: 'post',
            body: credentials,
        },
    };
};
