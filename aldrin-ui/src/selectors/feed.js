const reducerName = 'feed';

export const getFeedItems = (state) => state.getIn([reducerName, 'items']);

