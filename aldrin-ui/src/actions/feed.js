export const LIKE_FEED_ITEM = 'LIKE_FEED_ITEM';

export const likePostItem = postId => {
    return {
        type: LIKE_FEED_ITEM,
        postId,
    };
};