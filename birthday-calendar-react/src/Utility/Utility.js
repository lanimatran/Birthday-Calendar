import { SERVER_CONNECTION_ERROR_MESSAGE, UNKNOWN_ERROR_ERROR_MESSAGE, STORAGE_AUTHENTICATION_TOKEN_KEY, STORAGE_FIRST_NAME_KEY } from "./GlobalConstants"

class Utility {
    getFullNameFromComponents(firstName, lastName, middleName, suffix) {
        var fullName = firstName + ' ' + lastName
        fullName = (middleName && middleName !== '') ? (fullName + ', ' + middleName) : fullName
        fullName = (suffix && suffix !== '') ? (fullName + ' ' + suffix) : fullName
        return fullName
    }

    getBirthdayFromComponents(birthmonth, birthday) {
        return birthmonth + '/' + birthday
    }

    getFirstNameFromStorage() {
        let firstName = sessionStorage.getItem(STORAGE_FIRST_NAME_KEY)
        if (firstName) return firstName;
        return '';
    }

    defaultApiErrorHandling(error, object) {
        if (error.response) {
            object.setState({ serverErrorMessage: error.response.data.message })
        } else if (error.request) {
            object.setState({ serverErrorMessage: SERVER_CONNECTION_ERROR_MESSAGE })
        } else {
            object.setState({ serverErrorMessage: UNKNOWN_ERROR_ERROR_MESSAGE })
        }
    }

    isUserAuthenticated() {
        if (sessionStorage.getItem(STORAGE_AUTHENTICATION_TOKEN_KEY)) return true;
        return false;
    }

    saveLocalData(data, willSaveTokenToLocalStorage, authenticationChangeHandler) {
        if (willSaveTokenToLocalStorage) {
            localStorage.setItem(STORAGE_AUTHENTICATION_TOKEN_KEY, data.token)
            localStorage.setItem(STORAGE_FIRST_NAME_KEY, data.firstName)
        }
        sessionStorage.setItem(STORAGE_AUTHENTICATION_TOKEN_KEY, data.token)
        sessionStorage.setItem(STORAGE_FIRST_NAME_KEY, data.firstName)
        authenticationChangeHandler()
    }

    removeToken(authenticationChangeHandler) {
        sessionStorage.clear()
        localStorage.clear()
        authenticationChangeHandler()
    }
}

export default new Utility()