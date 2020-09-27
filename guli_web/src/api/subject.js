import request from '@/utils/request'


export default {
  //获取课程分类的信息，包括一级分类和二级分类
  list() {
    return request({
      url: `/admin/edu/subject/list`,//使用模板字符串进行拼接
      method: 'get'
    })
  }
}

