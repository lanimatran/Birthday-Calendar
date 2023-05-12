import React, { Component } from "react";
import * as yup from 'yup'
import { Formik, Form, Field } from 'formik'
import Button from 'react-bootstrap/Button'
import BirthdayService from "../ApiServices/BirthdayService";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

const BIRTH_MONTH = [
    { description: 'January', value: 1 },
    { description: 'February', value: 2 },
    { description: 'March', value: 3 },
    { description: 'April', value: 4 },
    { description: 'May', value: 5 },
    { description: 'June', value: 6 },
    { description: 'July', value: 7 },
    { description: 'August', value: 8 },
    { description: 'September', value: 9 },
    { description: 'October', value: 10 },
    { description: 'November', value: 11 },
    { description: 'December', value: 12 },
]

const BIRTH_DAY = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]

const birthdayValidation = yup.object().shape({
    firstName: yup.string()
        .required('First Name is required'),
    lastName: yup.string()
        .required('Last Name is required'),
    birthMonth: yup.string()
        .required('First Name is required'),
    birthDay: yup.string()
        .required('Last Name is required'),
    willRemindCustomTime: yup.boolean(),
    customReminder: yup.date()
        .when("willRemindCustomTime",
            {
                is: true,
                then: yup.date().required("Must specify custom date").nullable()
            }).nullable()
});

class BirthdayPage extends Component {
    constructor(props) {
        super(props)

        this.state = {
            firstName: '',
            lastName: '',
            birthMonth: 1,
            birthDay: 1,
            willRemindOneDayPrior: false,
            willRemindOneWeekPrior: false,
            willRemindCustomTime: false,
            customReminder: null
        }

        this.changeWillRemindCustomTime = this.changeWillRemindCustomTime.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.cancel = this.cancel.bind(this)
    }

    componentDidMount() {
        var id = this.props.match.params.id
        if (id > 0) {
            BirthdayService.getBirthday(id)
                .then(
                    response => {
                        this.setState({
                            firstName: response.data.firstName,
                            lastName: response.data.lastName,
                            birthMonth: response.data.birthMonth,
                            birthDay: response.data.birthDay,
                            willRemindOneDayPrior: response.data.willRemindOneDayPrior,
                            willRemindOneWeekPrior: response.data.willRemindOneWeekPrior,
                            willRemindCustomTime: response.data.willRemindCustomTime,
                            customReminder: (response.data.customReminder) ? (new Date(response.data.customReminder)) : null
                        })
                    }
                )
                .catch(
                    error => {
                        console.log(error)
                    }
                )
        }
    }

    render() {
        return (
            <div className="container">
                <Formik
                    initialValues={this.state}
                    validationSchema={birthdayValidation}
                    onSubmit={this.onSubmit}
                    validateOnChange={false}
                    validateOnBlur={true}
                    enableReinitialize={true}
                >
                    {
                        (props) => {
                            let { values, errors, touched, setFieldValue } = props
                            return (
                                // Formik's <Form> is not compatible with React Bootstrap's <Form>. The entire section is therefore written using classic tags
                                <Form>
                                    <div className='row'>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>First Name</label>
                                                <Field className="form-control" type="text" name="firstName" placeholder="First Name" />
                                                {(errors.lastName && touched.firstName) && (<div className="alert alert-danger">{errors.firstName}</div>)}
                                            </fieldset>
                                        </div>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>Last Name</label>
                                                <Field className="form-control" type="text" name="lastName" placeholder="Last Name" />
                                                {(errors.lastName && touched.lastName) && (<div className="alert alert-danger">{errors.lastName}</div>)}
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div className='row'>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>Birth Month</label>
                                                <Field as="select" name="birthMonth">
                                                    {
                                                        BIRTH_MONTH.map(
                                                            month => <option key={month.value} value={month.value}>{month.description}</option>
                                                        )
                                                    }
                                                </Field>
                                            </fieldset>
                                        </div>
                                        <div className='col'>
                                            <fieldset className="form-group">
                                                <label>Birth Day</label>
                                                <Field as="select" name="birthDay">
                                                    {
                                                        BIRTH_DAY.map(
                                                            day => <option key={day} value={day}>{day}</option>
                                                        )
                                                    }
                                                </Field>
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div className='row'>
                                        <fieldset className="form-check">
                                            <Field type="checkbox" id="willRemindOneDayPrior" name="willRemindOneDayPrior" />
                                            <label htmlFor="willRemindOneDayPrior">&nbsp;Remind me one day prior to this birthday</label>
                                        </fieldset>
                                    </div>
                                    <div className='row'>
                                        <fieldset className="form-check">
                                            <Field type="checkbox" id="willRemindOneWeekPrior" name="willRemindOneWeekPrior" />
                                            <label htmlFor="willRemindOneWeekPrior">&nbsp;Remind me one week prior to this birthday</label>
                                        </fieldset>
                                    </div>
                                    <div className='row'>
                                        <fieldset className="form-check">
                                            <Field type="checkbox" id="willRemindCustomTime" name="willRemindCustomTime" checked={values.willRemindCustomTime} />
                                            <label htmlFor="willRemindCustomTime">&nbsp;Remind me at a custom time</label>
                                            <DatePicker
                                                autoComplete="off"
                                                selected={values.customReminder}
                                                showTimeSelect
                                                timeIntervals={15}
                                                dateFormat="MMMM d, yyyy h:mm aa"
                                                className="form-control"
                                                name="customReminder"
                                                onChange={
                                                    date => {
                                                        setFieldValue('customReminder', date)
                                                        if (date) setFieldValue('willRemindCustomTime', true)
                                                        else {
                                                            setFieldValue('willRemindCustomTime', false)
                                                        }
                                                    }
                                                }
                                            />
                                            {(errors.customReminder && touched.customReminder) ? (<div className="alert alert-danger">{errors.customReminder}</div>) : (<div> </div>)}
                                        </fieldset>
                                    </div>
                                    <div className='row'>
                                        <Button variant="success" type="submit">Save</Button>
                                        <Button variant="primary" onClick={this.cancel}>Cancel</Button>
                                    </div>
                                </Form>
                            )
                        }
                    }
                </Formik>
            </div>
        )
    }

    changeWillRemindCustomTime(event) {
        this.setState({ willRemindCustomTime: event.target.checked })
    }

    onSubmit(values) {
        var ID = this.props.match.params.id
        if (ID > 0) {
            BirthdayService.updateBirthday(ID, values)
                .then(
                    response => {
                        console.log(response)
                        this.props.history.push('/')
                    }
                )
                .catch(
                    error => {
                        console.log(error)
                    }
                )
        }
        else {
            console.log("I got here")
            BirthdayService.addBirthday(values)
                .then(
                    response => {
                        console.log(response)
                        this.props.history.push('/')
                    }
                )
                .catch(
                    error => {
                        console.log(error.response)
                    }
                )
        }

    }

    cancel() {
        this.props.history.push(`/`)
    }
}

export default BirthdayPage