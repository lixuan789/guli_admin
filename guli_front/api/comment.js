import request from '@/utils/request'

export default {
  getPageList(page, limit, courseId) {
    return request({
      url: `/admin/comment/front/${page}/${limit}`,
      method: 'get',
      params: {courseId}
    })
  },
  addComment(comment) {
    return request({
      url: `/admin/comment/front/save`,
      method: 'post',
      data: comment
    })
  }

}
