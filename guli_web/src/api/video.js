import request from '@/utils/request'


export default {
  //根据id获取小节
  getVideo(id){
    return request({
      url: `/admin/edu/video/getVideo/${id}`,//使用模板字符串进行拼接
      method: 'get'
    })
  },
  //增加小节
  addVideo(video){
    return request({
      url: `/admin/edu/video/addVideo`,//使用模板字符串进行拼接
      method: 'post',
      data:video
    })
  },
  //更新小节
  updateVideo(video){
    return request({
      url: `/admin/edu/video/updateVideo`,//使用模板字符串进行拼接
      method: 'put',
      data:video
    })
  },
  //删除小节
  deleteVideo(id){
    return request({
      url: `/admin/edu/video/deleteVideo/${id}`,//使用模板字符串进行拼接
      method: 'delete'
    })
  },
  //删除视频
  deleteAliyunVideo(videoId){
    return request({
      url: `/api/vod/video/deleteVideo/${videoId}`,//使用模板字符串进行拼接
      method: 'delete'
    })
  }
}

