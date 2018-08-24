export const getIsLoggedIn = state => state.getIn(['session', 'loggedIn']);
export const getIsLoggingIn = state => state.getIn(['session', 'loggingIn']);
export const getToken = state => state.getIn(['session', 'token']);
