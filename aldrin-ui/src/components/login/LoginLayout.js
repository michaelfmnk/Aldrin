import React from 'react';
import PropTypes from 'prop-types';
import { Card, Paper, withStyles } from '@material-ui/core';
import Login from 'components/login/Login';

const styles = () => ({
    card: {
        width: '20em',
        margin: '100px auto',
        padding: '15px',
    },
});

const LoginLayout = (props) => {
    const {
        classes,
    } = props;

    return (
        <Paper style={{ height: '100%' }}>
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
