import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { AppBar, Toolbar, withStyles } from '@material-ui/core';

const styles = () => ({
    toolBar: {
        backgroundColor: '#ffffff',
        height: '5em',
    },
    logo: {
        maxHeight: '3em',
    },
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
            toolBar,
            logo,
        } = this.props.classes;
        return (
            <AppBar position="absolute">
                <Toolbar className={toolBar}>
                    <div className={menu}>
                        <img
                            className={logo}
                            alt="aldrin"
                            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZt9iEbvu7dpo7nzFKkOhvdFIbla0nIEIbG6y_SdYgNqk3JgZnfw"
                        />
                    </div>
                </Toolbar>
            </AppBar>
        );
    }
}

export default withStyles(styles)(TopBar);
