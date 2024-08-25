import request from '@/utils/request'
// import emptyAvatar from '@/assets/images/empty_avatar.jpg'
// import md5 from 'js-md5'

/**
 * 获取用户自身信息
 */
export function getInfo() {
  return request.get('user-api/user/userInfo')
}

export function getUsers(data) {
  const params = new URLSearchParams(data)
  // ordeBy由驼峰转换成下划线
  if(params.has('orderBy')) {
    params.set('orderBy', params.get('orderBy').replace(/([A-Z])/g, '_$1').toLowerCase())
  }
  const url = `user-api/user/getAllUser?${params.toString()}`
  return request.get(url)
}


/**
 * 添加用户
 * @param {*} data 
 * @returns 
 */
export function addUser(data) {
  return request.post('user-api/user/addUser', data)
}

/**
 * 更新用户信息
 * @param {*} data 
 */
export function updateUser(data) {
  return request.post('user-api/user/updateUser',data)
}

export function deleteUser(data) {
  return request.delete('user-api/user/deleteUser?userIds=' + data.join(','));
}

/**
 * 修改用户状态
 * @param {number} userId
 * @param {number} status
 */
export function changeUserStatus(userId, status) {
  const url = `user-api/user/${userId}/status?status=${status}`
  return request.put(url)
}
