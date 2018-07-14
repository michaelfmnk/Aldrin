import React, { PureComponent } from 'react';
import { Card, CardMedia, withStyles } from '@material-ui/core';
import PropTypes from 'prop-types';

const styles = theme => ({
    card: {
        maxWidth: 400,
    },
    media: {
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
});

class FeedItem extends PureComponent {
    render() {
        const {
            title,
            photoUrl
        } = this.props;
        const { classes } = this.props;
        return (
            <Card className={classes.card}>
                { title }
                <CardMedia
                    className={classes.media}
                    title={title}
                    image={photoUrl}/>
            </Card>
        );
    }
}

FeedItem.propTypes = {
  title: PropTypes.string.isRequired,
  classes: PropTypes.object.isRequired,
  photoUrl: PropTypes.string.isRequired
};

export default withStyles(styles)(FeedItem);