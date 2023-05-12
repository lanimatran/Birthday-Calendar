import axios from 'axios'
import { API_URL } from '../Utility/GlobalConstants'

class AccountService {
    signUp(user) {
        return axios.post(`${API_URL}/users`, user)
    }

    logIn(credentials) {
        return axios.post(`${API_URL}/authenticate`, credentials)
    }

    refreshToken(token) {
        console.log("refreshing token")
        return axios.post(`${API_URL}/refresh`, { token })
    }

    checkExistingEmail(email) {
        return axios.get(`${API_URL}/checkEmail?email=${email}`)
    }

    checkExistingUsername(username) {
        return axios.get(`${API_URL}/checkUsername?username=${username}`)
    }

    verify(token) {
        return axios.post(`${API_URL}/users/verify?token=${token}`)
    }
}

export default new AccountService()