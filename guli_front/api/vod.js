import request from '@/utils/request'

export default {
  getPlayAuth(vid) {
    return request({
      url: `/api/vod/video/getPlayAuth/${vid}`,
      method: 'get'
    })
  }

}
