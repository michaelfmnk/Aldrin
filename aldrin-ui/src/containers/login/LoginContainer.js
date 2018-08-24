import { connect } from 'react-redux';
import LoginLayout from 'components/login/LoginLayout';
import { reduxForm } from 'redux-form/immutable';
import { login } from 'actions/session';

const handleLogin = values => login({
    login: values.get('login'),
    password: values.get('password'),
});


const mapDispatchToProps = {
    onLogin: handleLogin,
};

const LoginForm = reduxForm({
    form: 'login',
    asyncBlurFields: ['email', 'password'],
})(LoginLayout);

export default connect(undefined, mapDispatchToProps)(LoginForm);
