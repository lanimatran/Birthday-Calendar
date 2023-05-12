import React, { Component } from 'react'

const defaultTimeBeforeRedirectionInSeconds = 10

class SuccessfulRegistrationPage extends Component{
    constructor(props){
        super(props)
        this.state = {
            timeLeft : defaultTimeBeforeRedirectionInSeconds
        }
    }

    componentDidMount() {
        var timer = setInterval(
            () => 
            {
                this.setState({timeLeft: this.state.timeLeft - 1})
                if (this.state.timeLeft <= 0) {
                    clearInterval(timer)
                    this.props.history.push('/login')
                }
            }
        , 1000)
    }

    render() {
        return (
            <div>
                <h1>Registered succesfully</h1>
                <div className='alert alert-success'>
                    A verification email has been sent. 
                    Please follow the instruction to complete your registration process.
                </div>
                <div> You will be automatically redirected to Log In page in {this.state.timeLeft} seconds.</div>
                <div> If not, please click <a href='/login'>here</a></div>
            </div>
        )
    }
}

export default SuccessfulRegistrationPage