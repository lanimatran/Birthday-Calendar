import React, {Component} from 'react'
import Utility from '../Utility/Utility'


class LogOutPage extends Component {

    componentDidMount() {
        if (Utility.isUserAuthenticated())
        {            
            Utility.removeToken(this.props.handler)
        }
    }

    render() {
        //let isUserLoggedIn = Utility.isUserLoggedIn()
        return (
            <div>
                <div className='alert alert-success'>
                    <div>You have logged out.</div>
                    <div> Click <a href='/login'>here</a> to sign in again</div>
                </div>
            </div>
            
        )
    }
}

export default LogOutPage