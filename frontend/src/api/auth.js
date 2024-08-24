import request from '@/utils/request'
import md5 from 'js-md5'


/*
登录
@param {Object} data{userName, password}
*/

export function login(data) {
    const params = new URLSearchParams()
    params.append('userName', data.username)
    // 这里使用MD5加密密码
    params.append('password', md5(data.password))
    params.append('platform', 2)
    // 这里就是使用axios发送post请求
    return request.post('user-api/auth/login', params)
}

/*
退出登录
*/
export function logout() {
    return request.post('user-api/auth/logout')
}

/*
检查用户名是否存在
*/
export function checkUserName(userName) {
    const url = 'user-api/auth/user-name/exists?userName='+ userName
    return request.post(url)
}
