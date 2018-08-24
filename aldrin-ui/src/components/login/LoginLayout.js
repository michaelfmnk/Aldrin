import React from 'react';
import PropTypes from 'prop-types';
import { Card, Paper, withStyles } from '@material-ui/core';
import Login from 'components/login/Login';

const styles = () => ({
    paper: {
        backgroundImage: 'url(http://hdqwalls.com/wallpapers/hex-abstract-material-design-ad.jpg)',
        width: '100%',
        height: '100%',
        position: 'absolute',
        zIndex: '-1',
        overflow: 'hidden',
    },
    card: {
        width: '20em',
        margin: '15em auto',
        padding: '15px',
    },
});

const LoginLayout = (props) => {
    const {
        classes,
    } = props;

    return (
        <Paper className={classes.paper}>
            <Card className={classes.card}>
                <Login
                    {...props}
                />
            </Card>
        </Paper>
    );
};

LoginLayout.propTypes = {
    classes: PropTypes.object,
};

export default withStyles(styles)(LoginLayout);
