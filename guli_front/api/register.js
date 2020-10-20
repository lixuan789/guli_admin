import request from '@/utils/request'
export default {
  //注册
  register(registerVo) {
    return request({
      url: `/ucenter/member/register`,
      method: 'post',
      data:registerVo
    })
  },
  //根据手机号码发送短信
  sendCode(phone) {
    return request({
      url: `/msm/send/${phone}`,
      method: 'get'
    })
  }
}
