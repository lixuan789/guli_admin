import request from '@/utils/request'

export default {
  //添加课程信息
  addCourseInfo(courseInfoVo){
    return request({
      url: `/admin/edu/course/addCourseInfo`,//使用模板字符串进行拼接
      method: 'post',
      data:courseInfoVo
    })
  },
  //获取课程的基本信息
  getCourseInfo(courseId){
    return request({
      url: `/admin/edu/course/getCourseInfo/${courseId}`,//使用模板字符串进行拼接
      method: 'get'
    })
  },
  //更新课程的基本信息
  updateCourseInfo(courseInfoVo){
    return request({
      url: `/admin/edu/course/updateCourseInfo`,//使用模板字符串进行拼接
      method: 'put',
      data:courseInfoVo
    })
  },
  //获取课程最终发布信息
  getPublishInfo(courseId){
    return request({
      url: `/admin/edu/course/getPublishInfo/${courseId}`,//使用模板字符串进行拼接
      method: 'get'
    })
  }
}
