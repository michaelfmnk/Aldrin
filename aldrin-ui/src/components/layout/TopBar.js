import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { AppBar, Toolbar, Typography, withStyles } from '@material-ui/core';

const styles = () => ({
    menu: {
        position: 'absolute',
        left: '100px',
    },
});
class TopBar extends PureComponent {
    static propTypes = {
        classes: PropTypes.object.isRequired, // eslint-disable-line
        theme: PropTypes.object.isRequired, // eslint-disable-line
    };

    render() {
        const {
            menu,
        } = this.props.classes;
        return (
            <AppBar position="absolute">
                <Toolbar>
                    <div className={menu}>
                        <Typography variant="title" color="inherit">
                            Aldrin
                        </Typography>
                    </div>
                </Toolbar>
            </AppBar>
        );
    }
}

export default withStyles(styles)(TopBar);
