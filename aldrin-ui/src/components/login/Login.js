import React from 'react';
import PropTypes from 'prop-types';
import { Field } from 'redux-form/immutable';
import { Button, FormHelperText, withStyles } from '@material-ui/core';

import TextField from 'inputs/forms/TextField';
import { email, required } from 'validators/validationInputFields';

const styles = theme => ({
    input: {
        marginBottom: '10px',
    },
    button: {
        marginTop: 2 * theme.spacing.unit,
    },
});
const Login = (props) => {
    const {
        error,
        classes,
        pristine,
        submitting,
        onLogin,
        handleSubmit,
    } = props;
    return (
        <form onSubmit={handleSubmit(onLogin)}>
            {
                error &&
                (
                    <FormHelperText error>
                        {{ error }}
                    </FormHelperText>
                )
            }
            <div>
                <Field
                    fullWidth
                    name="login"
                    type="text"
                    component={TextField}
                    validate={[required, email]}
                    label="Email"
                    className={classes.input}
                />
            </div>
            <div>
                <Field
                    fullWidth
                    name="password"
                    type="password"
                    component={TextField}
                    label="Password"
                    className={classes.input}
                />
            </div>
            <div>
                <Button
                    fullWidth
                    variant="raised"
                    color="primary"
                    type="submit"
                    disabled={pristine || submitting}
                    className={classes.button}
                >
                    Login
                </Button>
            </div>
        </form>
    );
};

Login.propTypes = {
    error: PropTypes.string,
    classes: PropTypes.object,
    pristine: PropTypes.bool.isRequired,
    submitting: PropTypes.bool.isRequired,
    onLogin: PropTypes.func.isRequired,
    handleSubmit: PropTypes.func.isRequired,
};

export default withStyles(styles)(Login);
