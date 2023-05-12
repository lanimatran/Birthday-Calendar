import React, { Component } from 'react'

class GlobalErrorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            errorList: this.props.errorList?this.props.errorList:[]
        }
    }

    clearErrorList() {
        this.setState({
            errorList: this.props.errorList?this.props.errorList:[]
        })
    }
}