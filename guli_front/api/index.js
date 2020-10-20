import request from '@/utils/request'
export default {
  getIndex() {
    return request({
      url: `/admin/front/index`,
      method: 'get'
    })
  }
}
