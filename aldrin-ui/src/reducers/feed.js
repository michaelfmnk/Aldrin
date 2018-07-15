import { feed } from 'data/fakedata';
import { fromJS } from 'immutable';
import { LIKE_FEED_ITEM } from 'actions/feed';
const defaultState = fromJS({
    items: feed
});

export default function(state = defaultState, action) {
    switch (action.type) {
        case LIKE_FEED_ITEM: {
            const index = state.get('items').findIndex(item => item.get('id') === action.itemId);
            const liked = state.getIn(['items', index, 'liked']);
            return state.setIn(['items', index, 'liked'], !liked);
        }
        default: {
            return state;
        }
    }
};