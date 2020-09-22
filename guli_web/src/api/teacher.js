import request from '@/utils/request'

//获取讲师列表
/*export function listPage(page,limit,teacherQueryVo) {
  return request({
    url: `/admin/edu/teacher/list/${page}/${limit}`,
    method: 'post',
    data:teacherQueryVo
  })
}

export function save(teacher) {
  return request({
    url: '/admin/edu/teacher/save',
    method: 'post',
    data:{
      teacher
    }
  })
}*/

export default {
  listPage(page,limit,teacherQueryVo) {
    return request({
      url: `/admin/edu/teacher/list/${page}/${limit}`,//使用模板字符串进行拼接
      method: 'post',
      data: teacherQueryVo
    })
  },
  save(teacher) {
    return request({
      url: '/admin/edu/teacher/save',
      method: 'post',
      data:teacher
    })
  },
  removeById(id){
    return request({
      url: `/admin/edu/teacher/remove/${id}`,
      method: 'delete'
    })
  }
}

