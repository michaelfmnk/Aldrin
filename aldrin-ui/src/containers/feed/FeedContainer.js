import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import ImmutablePropTypes from 'react-immutable-proptypes';
import { getFeedItems } from 'selectors/feed';
import FeedItem from "components/feed/FeedItem";

const mapStateToProps = state => ({
    items: getFeedItems(state),
});

class FeedContainer extends PureComponent {

    render() {
        const {
            items
        } = this.props;
        return (
            <div>
                {
                    items.map(item => (
                        <FeedItem
                            title={item.get('title')}
                            photoUrl={item.get('url')}
                        />
                    ))
                }
            </div>
        );
    }
}

FeedContainer.propTypes = {
    items: ImmutablePropTypes.List,
};

export default connect(mapStateToProps, {})(FeedContainer);