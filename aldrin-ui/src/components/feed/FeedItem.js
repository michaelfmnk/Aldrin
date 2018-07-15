import React, { PureComponent } from 'react';
import { Card, CardMedia, CardHeader, Avatar, CardActions, IconButton, CardContent, Typography, withStyles } from '@material-ui/core';
import { Favorite } from '@material-ui/icons';
import PropTypes from 'prop-types';
import dateformat from 'dateformat';
import cx from 'classnames';
const styles = theme => ({
    card: {
        maxWidth: 600,
        margin: '5em auto',
    },
    media: {
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
    likedStyle: {
        color: 'red',
    }
});

class FeedItem extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            liked: props.liked,
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({ liked: nextProps.liked })
    }

    handleLikeClick  = (itemId) => () => {
        this.props.onLikeClick(itemId);
    };

    render() {
        const {
            id,
            title,
            photoUrl,
            authorName,
            authorAvatar,
            postDate,
            description,
        } = this.props;
        const {
            liked,
        } = this.state;
        const {
            card,
            media,
            likedStyle,
        } = this.props.classes;
        return (
            <Card className={card}>
                <CardHeader
                    avatar={
                        <Avatar
                            alt={authorName}
                            src={authorAvatar}/>
                    }
                    title={title}
                    subheader={authorName + '   |   ' + dateformat(postDate, 'fullDate')}/>
                <CardMedia
                    className={media}
                    image={photoUrl}/>
                <CardContent>
                    <Typography>
                        {description}
                    </Typography>
                </CardContent>
                <CardActions>
                    <IconButton className={cx(liked ? likedStyle : null)} onClick={this.handleLikeClick(id)} >
                        <Favorite/>
                    </IconButton>
                </CardActions>
            </Card>
        );
    }
}

FeedItem.propTypes = {
  id: PropTypes.number.isRequired,
  title: PropTypes.string.isRequired,
  classes: PropTypes.object.isRequired,
  photoUrl: PropTypes.string.isRequired,
  liked: PropTypes.bool.isRequired,
  authorName: PropTypes.string.isRequired,
  authorAvatar: PropTypes.string.isRequired,
  description: PropTypes.bool.isRequired,
  onLikeClick: PropTypes.func.isRequired,
};

export default withStyles(styles)(FeedItem);