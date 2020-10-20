import request from '@/utils/request'
export default {
  login(loginVo) {
    return request({
      url: `/ucenter/member/login`,
      method: 'post',
      data:loginVo
    })
  },
  //根据token获取用户信息
  getLoginInfo() {
    return request({
      url: `/ucenter/member/auth/getLoginInfo`,
      method: 'get'
      // headers: {'token': cookie.get('guli_token')}
    })
    //headers: {'token': cookie.get('guli_token')}
  }
}
