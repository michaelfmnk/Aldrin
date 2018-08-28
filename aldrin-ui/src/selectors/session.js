export const getIsLoggedIn = state => state.getIn(['session', 'loggedIn']);
export const getIsLoggingIn = state => state.getIn(['session', 'loggingIn']);
export const getToken = state => state.getIn(['session', 'token']);
export const getLoginError = state => state.getIn(['session', 'loginError']);
export const getRegisterError = state => state.getIn(['session', 'registerError']);
export const getIsVerifying = state => state.getIn(['session', 'verifying']);
export const getRegisteredUserId = state => state.getIn(['session', 'registeredUserId']);
