import { fork, takeLatest, select } from 'redux-saga/effects';
import { LOGIN, VERIFY_USER } from 'actions/session';
import { successAction } from 'actions/actionTypes';
import { saveTokenToStore } from 'utils/session';
import { getToken } from 'selectors/session';


function* saveToken() {
    const token = yield select(getToken);
    saveTokenToStore(token);
}

function* watchLogin() {
    yield takeLatest(successAction(LOGIN), saveToken);
    yield takeLatest(successAction(VERIFY_USER), saveToken);
}

export default function* rootSaga() {
    yield fork(watchLogin);
}
