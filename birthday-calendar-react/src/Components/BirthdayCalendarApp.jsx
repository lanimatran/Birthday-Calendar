import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import LogInComponent from '../Pages/LogInComponent'
import HeaderComponent from './HeaderComponent'
import HomePage from '../Pages/HomePage'
import SignUpPage from '../Pages/SignUpPage'
import SuccessfulRegistrationPage from '../Pages/SuccessfulRegistrationPage'
import LogOutPage from '../Pages/LogOutPage'
import { STORAGE_AUTHENTICATION_TOKEN_KEY } from '../Utility/GlobalConstants'
import BirthdayPage from '../Pages/BirthdayPage'
import ApiUtil from '../ApiServices/ApiUtil'
import Utility from '../Utility/Utility'
import { AuthenticatedRoute, NonAuthenticatedRoute } from './Routes'
import VerificationPage from '../Pages/VerificationPage'

class BirthdayCalendarApp extends Component {
    constructor(props) {
        super(props)

        this.initializeInterceptor()

        this.state = {
            isUserAuthenticated: Utility.isUserAuthenticated()
        }

        this.initializeInterceptor = this.initializeInterceptor.bind(this)
        this.handleAuthenticationChange = this.handleAuthenticationChange.bind(this)
        console.log(this.state.isUserAuthenticated)
    }

    initializeInterceptor() {
        var token = null
        if (sessionStorage.getItem(STORAGE_AUTHENTICATION_TOKEN_KEY)) token = sessionStorage.getItem(STORAGE_AUTHENTICATION_TOKEN_KEY)
        else if (localStorage.getItem(STORAGE_AUTHENTICATION_TOKEN_KEY)) {
            token = localStorage.getItem(STORAGE_AUTHENTICATION_TOKEN_KEY)
            sessionStorage.setItem(STORAGE_AUTHENTICATION_TOKEN_KEY, token)
        }
        if (token) {
            ApiUtil.setupInterceptors(token, this.handleAuthenticationChange)
        }
    }

    handleAuthenticationChange() {
        console.log(this)
        this.setState({ isUserAuthenticated: Utility.isUserAuthenticated() })
    }

    render() {
        console.log("App is being rendered")
        return (
            <React.StrictMode>
                <div className="BirthdayApp App">
                    <Router>
                        <>
                            <HeaderComponent isUserAuthenticated={this.state.isUserAuthenticated} />
                            <Switch>
                                <AuthenticatedRoute authenticated={this.state.isUserAuthenticated} path="/" exact component={HomePage} />
                                <NonAuthenticatedRoute authenticated={this.state.isUserAuthenticated} path="/login" render={(props) => <LogInComponent {...props} handler={this.handleAuthenticationChange} />} />
                                <NonAuthenticatedRoute authenticated={this.state.isUserAuthenticated} path="/signup" component={SignUpPage} />
                                <NonAuthenticatedRoute authenticated={this.state.isUserAuthenticated} path="/successfulRegistration" component={SuccessfulRegistrationPage} />
                                <Route path="/logout" render={(props) => <LogOutPage {...props} handler={this.handleAuthenticationChange} />} />
                                <AuthenticatedRoute authenticated={this.state.isUserAuthenticated} path="/birthday/:id" component={BirthdayPage} />
                                <NonAuthenticatedRoute authenticated={this.state.isUserAuthenticated} path="/verify" component={VerificationPage} />
                                <AuthenticatedRoute authenticated={this.state.isUserAuthenticated} path="/birthday" component={BirthdayPage} />
                            </Switch>
                        </>
                    </Router>
                </div>
            </React.StrictMode>
        )
    }
}

export default BirthdayCalendarApp