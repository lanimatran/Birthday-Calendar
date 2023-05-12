import Axios from "axios"
import AccountService from "./AccountService"
import { STORAGE_AUTHENTICATION_TOKEN_KEY } from '../Utility/GlobalConstants'
import createAuthRefreshInterceptor from 'axios-auth-refresh';
import Utility from "../Utility/Utility";

class ApiUtil {
    createJwtToken(token) {
        return 'Bearer ' + token
    }

    setupInterceptors(token, authenticationChangeHandler) {
        console.log("setting up interceptors")
        const requestInterceptor = this.setupRequestInterceptor(token)
        const refreshAuthLogic = failedRequest => 
        {
            Axios.interceptors.request.eject(requestInterceptor)
            return AccountService.refreshToken(token)
            .then(response => {
                console.log("Got a response")
                if (localStorage.getItem(STORAGE_AUTHENTICATION_TOKEN_KEY))  localStorage.setItem(STORAGE_AUTHENTICATION_TOKEN_KEY, response.data.token)
                sessionStorage.setItem(STORAGE_AUTHENTICATION_TOKEN_KEY, response.data.token)
                this.setupInterceptors(response.data.token, authenticationChangeHandler)
                authenticationChangeHandler();
                return Promise.resolve();
            })
            .catch(error => {
                Utility.removeToken(authenticationChangeHandler)
                return Promise.reject(error)
            })
        }            
        createAuthRefreshInterceptor(Axios, refreshAuthLogic);
    } 

    setupRequestInterceptor(token) {
        let header = this.createJwtToken(token)
        const interceptor = Axios.interceptors.request.use(
            (config) => {
                config.headers.authorization = header
                return config
            }
        )
        console.log("interceptor returned is " + interceptor)
        return interceptor
    }

    setupResponseInterceptor(token, handler, requestInterceptor) {
        const interceptor = Axios.interceptors.response.use(
            response => response,
            error =>
            {
                if (error.response.status !== 401) return Promise.reject(error);
                Axios.interceptors.response.eject(interceptor)
                Axios.interceptors.request.eject(requestInterceptor)
                AccountService.refreshToken(token)
                .then(
                    response => {
                        this.setupInterceptors(response.data.token)
                        if (localStorage.getItem(STORAGE_AUTHENTICATION_TOKEN_KEY))  localStorage.setItem(STORAGE_AUTHENTICATION_TOKEN_KEY, response.data.token)
                        sessionStorage.setItem(STORAGE_AUTHENTICATION_TOKEN_KEY, response.data.token)
                    }
                )
                .catch(
                    error => {
                        console.log("error again")
                        sessionStorage.clear()
                        localStorage.clear()
                        console.log(requestInterceptor)
                        handler()                        
                        return Promise.reject(error);
                    }
                )
            }
        )
        return interceptor
    }
}

export default new ApiUtil()