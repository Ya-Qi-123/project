import axios from 'axios'

const service = axios.create({
    //baseURL: 'http://192.168.10.37:8080',
    //baseURL:'http://localhost:8080',
    timeout: 10000
})
export default service