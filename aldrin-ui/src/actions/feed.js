export const LIKE_FEED_ITEM = 'LIKE_FEED_ITEM';

export const likePostItem = itemId => {
    return {
        type: LIKE_FEED_ITEM,
        itemId,
    };
};