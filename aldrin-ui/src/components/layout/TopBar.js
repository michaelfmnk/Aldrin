import React, { PureComponent } from 'react';
import { AppBar, Toolbar, Typography, withStyles } from '@material-ui/core';

const styles = theme => ({
    menu: {
        position: 'absolute',
        left: '100px',
    }
});
class TopBar extends PureComponent {
    render() {
        const {
            menu
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