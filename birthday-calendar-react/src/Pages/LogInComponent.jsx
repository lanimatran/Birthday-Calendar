import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik'
import * as yup from 'yup'
import AccountService from '../ApiServices/AccountService'
import ApiUtil from '../ApiServices/ApiUtil'
import Utility from '../Utility/Utility'
import { LOCAL_STORAGE_AUTHENTICATION_TOKEN_KEY, SESSION_STORAGE_AUTHENTICATION_TOKEN_KEY } from '../Utility/GlobalConstants'
//import GoogleBtn from '../Buttons/GoogleButton'

const logInValidation = yup.object().shape({
    username: yup.string()
        .required("Username is required"),
    password: yup.string()
        .required("Password is required")
})
class LogInComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            username: '',
            password: '',
            willRememberSignIn: false
        }

        this.logIn = this.logIn.bind(this)
        this.changeRememberSignInCheck = this.changeRememberSignInCheck.bind(this)
    }

    render() {
        let { username, password } = this.state
        return (
            <div className="container logInPage">

                <Formik
                    initialValues={{ username, password }}
                    onSubmit={this.logIn}
                    validationSchema={logInValidation}
                >
                    {
                        (props) => {
                            let { errors, touched } = props
                            return (
                                <div>
                                    <h1>Log In to Birthday Calendar</h1>
                                    <Form>
                                        <fieldset className="form-group">
                                            <Field type="username" name="username" placeholder="Username" />
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <Field type="password" name="password" placeholder="Password" />
                                        </fieldset>
                                        <fieldset className="form-check">
                                            <input type="checkbox" id="rememberSignInChk" checked={this.state.willRememberSignIn} onChange={this.changeRememberSignInCheck} />
                                            <label htmlFor="rememberSignInChk">&nbsp;Remember me on this device</label>
                                        </fieldset>
                                        <button className="btn-success" type="submit">Log In</button>
                                        {this.state.serverErrorMessage ? (<div className="alert alert-danger">{this.state.serverErrorMessage}</div>) : (<div></div>)}
                                        {(errors.username && touched.username) ? (<div className="alert alert-danger">{errors.username}</div>) : (<div></div>)}
                                        {(errors.password && touched.password) ? (<div className="alert alert-danger">{errors.password}</div>) : (<div></div>)}
                                    </Form>
                                </div>
                            )

                        }
                    }
                </Formik>
            </div>
        )
    }

    logIn(logInCredentials) {
        this.setState({ serverErrorMessage: null })
        AccountService.logIn(logInCredentials)
            .then(
                response => {
                    ApiUtil.setupInterceptors(response.data.token, this.props.handler)
                    Utility.saveLocalData(response.data, this.state.willRememberSignIn, this.props.handler)
                }
            )
            .catch(
                error => {
                    console.log(error)
                    Utility.defaultApiErrorHandling(error, this)
                }
            )
    }

    changeRememberSignInCheck(event) {
        this.setState({ willRememberSignIn: event.target.checked })
    }
}

export default LogInComponent