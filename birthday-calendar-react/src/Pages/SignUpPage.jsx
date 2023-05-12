import React, { Component } from 'react'
import Button from 'react-bootstrap/Button'
import * as yup from 'yup'
import { Formik, Form, Field } from 'formik'
import AccountService from '../ApiServices/AccountService';

const signUpValidation = yup.object().shape({
    firstName: yup.string()
        .required('First Name is required'),
    lastName: yup.string()
        .required('Last Name is required'),
    username: yup.string()
        .required("Username is required")
        .test('Unique Username', 'Username is already in use. Please log in or reset password.',
            (username) => new Promise(
                (resolve) => {
                    AccountService.checkExistingUsername(username)
                        .then(
                            () => resolve(true)
                        )
                        .catch(
                            () => resolve(false)
                        )
                })),
    email: yup.string()
        .required("Email is required")
        .email('Invalid email')
        .test('Unique Email', 'Email is already in use. Please log in or reset password.',
            (email) => new Promise(
                (resolve) => {
                    AccountService.checkExistingEmail(email)
                        .then(
                            () => resolve(true)
                        )
                        .catch(
                            () => resolve(false)
                        )
                })),
    password: yup.string()
        .min(8, 'Passwords has to be at least 8 characters long')
        .required("Password is required"),
    confirmPassword: yup.string()
        .oneOf([yup.ref('password')], 'Passwords must match')
        .required("Please confirm your Password")
});

class SignUpPage extends Component {
    constructor(props) {
        super(props)

        this.onSubmit = this.onSubmit.bind(this)
    }

    render() {
        let initialState = {
            username: '',
            email: '',
            password: '',
            confirmPassword: '',
            firstName: '',
            lastName: ''
        }
        return (
            <div className="container">
                <Formik
                    initialValues={initialState}
                    validationSchema={signUpValidation}
                    onSubmit={this.onSubmit}
                    validateOnChange={false}
                    validateOnBlur={true}
                >
                    {
                        (props) => {
                            let { errors, touched } = props
                            return (
                                // Formik's <Form> is not compatible with React Bootstrap's <Form>. The entire section is therefore written using classic tags
                                <Form>
                                    <div className='row'>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>First Name</label>
                                                <Field className="form-control" type="text" name="firstName" placeholder="First Name" />
                                                {(errors.firstName && touched.firstName) ? (<div className="alert alert-danger">{errors.firstName}</div>) : (<div> </div>)}
                                            </fieldset>
                                        </div>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>Last Name</label>
                                                <Field className="form-control" type="text" name="lastName" placeholder="Last Name" />
                                                {(errors.lastName && touched.lastName) ? (<div className="alert alert-danger">{errors.lastName}</div>) : (<div></div>)}
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div className='row'>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>Username</label>
                                                <Field className="form-control" type="text" name="username" placeholder="Username" />
                                                {errors.username && touched.username && <div className="alert alert-danger">{errors.username}</div>}
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div className='row'>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>Email</label>
                                                <Field className="form-control" type="email" name="email" placeholder="Email Address" />
                                                {errors.email && touched.email && <div className="alert alert-danger">{errors.email}</div>}
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div className='row'>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>Password</label>
                                                <Field className="form-control" type="password" name="password" placeholder="Password" />
                                                {errors.password && touched.password && <div className="alert alert-danger">{errors.password}</div>}
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div className='row'>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>ConfirmPassword</label>
                                                <Field className="form-control" type="password" name="confirmPassword" placeholder="Confirm Password" />
                                                {errors.confirmPassword && touched.confirmPassword && <div className="alert alert-danger">{errors.confirmPassword}</div>}
                                            </fieldset>
                                        </div>
                                    </div>
                                    <Button variant="primary" type="submit">Sign Up</Button>
                                </Form>
                            )
                        }
                    }
                </Formik>
            </div>

        )
    }

    onSubmit(values) {
        let { firstName, lastName, username, email, password } = values
        let user = { firstName, lastName, username, email, password }
        AccountService.signUp(user)
            .then(
                response => {
                    this.props.history.push('/successfulRegistration')
                }
            )
            .catch(
                error => {
                    console.log('failed')
                    console.log(error)
                }
            )
    }
}

export default SignUpPage