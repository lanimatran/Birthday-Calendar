import React, {Component} from 'react'
import qs from 'qs'
import AccountService from '../ApiServices/AccountService'


class VerificationPage extends Component {
    constructor(props) {
        super(props)

        this.state = {
            isUserVerified : false,
            isError : false,
            errorMessage: ''
        }

        console.log("Im being initialized")
    }


    componentDidMount() {
        var params = qs.parse(this.props.location.search, { ignoreQueryPrefix: true })
        AccountService.verify(params.token)
        .then(
            response => {
                this.setState({isUserVerified : true})
            }            
        )
        .catch(
            error => {
                console.log(error.response)
                this.setState({isError : true, errorMessage : error.response.data.message})
            }
            
        )
    }

    render() {
        return (
            <div>
                {this.state.isUserVerified && 
                    <div className='alert alert-success'>
                        <div>Verification completed successfully.</div>
                        <div> Click <a href='/login'>here</a> to sign in again</div>
                    </div>}    
                {this.state.isError && 
                    <div className='alert alert-danger'>
                        <div>{this.state.errorMessage}</div>
                        <div> Click <a href='/login'>here</a> to sign in again</div>
                    </div>}            
            </div>
            
        )
    }
}

export default VerificationPage