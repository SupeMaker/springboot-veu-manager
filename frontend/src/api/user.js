import request from '@/utils/request'
// import emptyAvatar from '@/assets/images/empty_avatar.jpg'
// import md5 from 'js-md5'

/**
 * 获取用户自身信息
 */
export function getInfo() {
  return request.get('user-api/users/me')
}

export function getUsers(data) {
  const params = new URLSearchParams(data)
  // ordeBy由驼峰转换成下划线
  if(params.has('orderBy')) {
    params.set('orderBy', params.get('orderBy').replace(/([A-Z])/g, '_$1').toLowerCase())
  }
  const url = 'user-api/users?${params.toString()}'
  // const url = '/vue-element-admin/user/login?${params.toString()}'
  return request.get(url)
}
