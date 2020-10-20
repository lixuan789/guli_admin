import request from '@/utils/request'
export default {
  getCourseList(page,limit,courseFrontQeryVo) {
    return request({
      url: `/admin/course/front/list/${page}/${limit}`,
      method: 'post',
      data:courseFrontQeryVo
    })
  },
  getSubjectList(){
    return request({
      url: `/admin/edu/subject/list`,
      method: 'get'
    })
  },
  getById(courseId) {
    return request({
      url: `/admin/course/front/${courseId}`,
      method: 'get'
    })
  }
}
