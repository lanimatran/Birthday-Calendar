import axios from 'axios'
import { API_URL } from '../Utility/GlobalConstants'

class BirthdayService {
    getBirthdayList() {
        return axios.get(`${API_URL}/birthdays`)
    }

    addBirthday(birthday) {
        return axios.post(`${API_URL}/birthdays`, birthday)
    }

    updateBirthday(ID, birthday) {
        console.log(birthday)
        return axios.put(`${API_URL}/birthdays/${ID}`, birthday)
    }

    deleteBirthday(ID) {
        return axios.delete(`${API_URL}/birthdays/${ID}`)
    }

    getBirthday(ID) {
        return axios.get(`${API_URL}/birthdays/${ID}`)
    }
}

export default new BirthdayService()