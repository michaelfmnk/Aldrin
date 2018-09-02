import { fork, takeLatest, select } from 'redux-saga/effects';
import { LOGIN, VERIFY_USER, SIGN_OUT } from 'actions/session';
import { successAction } from 'actions/actionTypes';
import { saveTokenToStore, deleteTokenFromStore } from 'utils/session';
import { getToken } from 'selectors/session';


function* saveToken() {
    const token = yield select(getToken);
    saveTokenToStore(token);
}

function* watchLogin() {
    yield takeLatest(successAction(LOGIN), saveToken);
    yield takeLatest(successAction(VERIFY_USER), saveToken);
}

function* watchSignOut() {
    yield takeLatest(SIGN_OUT, deleteTokenFromStore);
}

export default function* rootSaga() {
    yield fork(watchLogin);
    yield fork(watchSignOut);
}
