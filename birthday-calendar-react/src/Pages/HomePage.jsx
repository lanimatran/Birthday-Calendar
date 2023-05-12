import React, { Component } from "react";
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import BirthdayService from "../ApiServices/BirthdayService";
import Utility from "../Utility/Utility";

class HomePage extends Component {
    constructor(props) {
        super(props)

        this.state = {
            birthdayList: [],
            isDeleteConfirmationModalShown: false
        }

        this.addBirthday = this.addBirthday.bind(this)
        this.updateBirthdayClicked = this.updateBirthdayClicked.bind(this)
        this.showDeleteConfirmationModal = this.showDeleteConfirmationModal.bind(this)
        this.hideDeleteConfirmationModal = this.hideDeleteConfirmationModal.bind(this)
        this.deleteBirthday = this.deleteBirthday.bind(this)
    }

    componentDidMount() {
        this.populateData()
    }

    populateData() {
        BirthdayService.getBirthdayList()
            .then(
                response => {
                    this.setState({ birthdayList: response.data })
                }
            )
            .catch(
                error => {
                    console.log("Hello" + error)
                }
            )

    }

    render() {
        return (
            <div>

                <div className="container">
                    <h1>List of Birthdays</h1>
                    <Table className="table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Birthday</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.birthdayList.map(birthday => {
                                    return (
                                        <tr key={birthday.id}>
                                            <td>{Utility.getFullNameFromComponents(birthday.firstName, birthday.lastName, birthday.middleName, birthday.suffix)}</td>
                                            <td>{Utility.getBirthdayFromComponents(birthday.birthMonth, birthday.birthDay)}</td>
                                            <td>
                                                <Button variant="primary" onClick={() => this.updateBirthdayClicked(birthday.id)}>Details</Button>
                                                <Button variant="danger" onClick={() => this.showDeleteConfirmationModal(birthday.id)}>Delete</Button>
                                            </td>
                                            <Modal show={this.state.isDeleteConfirmationModalShown} onHide={this.hideDeleteConfirmationModal}>
                                                <Modal.Header>
                                                    <Modal.Title>Confirmation</Modal.Title>
                                                </Modal.Header>
                                                <Modal.Body>Are you sure you want to delete this Birthday entry?</Modal.Body>
                                                <Modal.Footer>
                                                    <Button variant="secondary" onClick={this.hideDeleteConfirmationModal}>
                                                        Close
                                                    </Button>
                                                    <Button variant="primary" onClick={() => this.deleteBirthday(birthday.id)}>
                                                        Save Changes
                                                    </Button>
                                                </Modal.Footer>
                                            </Modal>
                                        </tr>
                                    )
                                }

                                )
                            }
                        </tbody>
                    </Table>
                    <button className='btn-success' onClick={this.addBirthday}>Add a new Birthday</button>
                </div>



            </div>
        )
    }

    addBirthday() {
        this.props.history.push('/birthday/-1')
    }

    updateBirthdayClicked(id) {
        this.props.history.push(`/birthday/${id}`)
    }

    deleteBirthday(ID) {
        BirthdayService.deleteBirthday(ID)
            .then(
                response => {
                    this.hideDeleteConfirmationModal()
                    this.populateData()
                }
            )
            .catch(
                error => {
                    console.log(error.response)
                }
            )
    }

    showDeleteConfirmationModal(id) {
        this.setState({ isDeleteConfirmationModalShown: true })
    }

    hideDeleteConfirmationModal() {
        this.setState({ isDeleteConfirmationModalShown: false })
    }
}

export default HomePage