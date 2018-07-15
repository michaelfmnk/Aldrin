import { fromJS } from 'immutable';
import { normalize, schema } from 'normalizr';
import { feed } from 'data/fakedata';
const initialState = fromJS({
    users: {},
    posts: {},
    comments: {}
});

export default function entitiesReducer(state = initialState, action) {
    const user = new schema.Entity('users');
    const comment = new schema.Entity('comments', {
        author: user
    });
    const postArray = new schema.Array(new schema.Entity('posts', {
        author: user,
        comments: [comment]
    }));
    const newState = fromJS(normalize(feed, postArray).entities);
    return newState;
}