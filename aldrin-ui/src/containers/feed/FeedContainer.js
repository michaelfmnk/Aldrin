import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

@connect(undefined, {})
export default class FeedContainer extends PureComponent {
    render() {
        const {
            itemsToShow
        } = this.props;
        return (
            <div>
                {
                    itemsToShow.map(item => (
                        <FeedItem
                            title={item.get('title')}
                            photoUrl={item.get('photo')}
                        />
                    ))
                }
            </div>
        );
    }
}