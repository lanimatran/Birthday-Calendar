import React, { Component } from 'react'
import Navbar from 'react-bootstrap/Navbar'
import Nav from 'react-bootstrap/Nav'
import { Link } from 'react-router-dom'
import { LinkContainer } from 'react-router-bootstrap'
import Button from 'react-bootstrap/Button'
import Utility from '../Utility/Utility'

class HeaderComponent extends Component {
    render() {
        return (
            <header>
                <Navbar collapseOnSelect bg="dark" variant="dark">
                    <Navbar.Brand>
                        <Link to="/">Birthday Calendar</Link>
                    </Navbar.Brand>
                    <Navbar.Collapse className="justify-content-end">
                        <Nav>
                            {!this.props.isUserAuthenticated && 
                            <LinkContainer to='/signup'>
                                <Button>Get Started</Button>
                            </LinkContainer>}
                            {!this.props.isUserAuthenticated && <LinkContainer to='/login'><Button variant="success">Log In</Button></LinkContainer>}
                            {this.props.isUserAuthenticated && <Navbar.Text>Welcome, {Utility.getFirstNameFromStorage()}</Navbar.Text>}
                            {this.props.isUserAuthenticated && <LinkContainer to='/logout'><Button>Log Out</Button></LinkContainer>}
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            </header>
        )
    }
    
}

export default HeaderComponent