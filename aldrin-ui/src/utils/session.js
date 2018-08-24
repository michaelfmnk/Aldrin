import { saveData, extractData } from 'utils/localeStore';

export function saveTokenToStore(data) {
    saveData('authToken', data);
}

export function extractTokenFromStore() {
    return extractData('authToken');
}
