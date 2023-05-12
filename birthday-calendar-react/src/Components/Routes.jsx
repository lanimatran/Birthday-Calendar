import React from 'react'
import {Route, Redirect} from 'react-router-dom'

// Route available for authenticated users only
function AuthenticatedRoute(props) {
    var {authenticated, ...other} = props
    return (
        authenticated?(<Route {...other}/>):<Redirect to="/login"/>
    )
}

// Route available for non-authenticated users only
function NonAuthenticatedRoute(props) {
    var {authenticated, ...other} = props
    console.log("authenticated in login is " + authenticated)
    return (
        authenticated?<Redirect to="/"/>:(<Route {...other}/>)
    )
}

export {AuthenticatedRoute, NonAuthenticatedRoute}