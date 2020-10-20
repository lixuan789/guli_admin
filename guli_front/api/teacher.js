import request from '@/utils/request'
export default {
  getTeacherList(page,limit) {
    return request({
      url: `/admin/teacher/front/list/${page}/${limit}`,
      method: 'post'
    })
  },
  getById(id) {
    return request({
      url: `/admin/teacher/front/${id}`,
      method: 'get'
    })
  }
}
