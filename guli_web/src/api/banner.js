import request from '@/utils/request'


export default {
  listPage(page,limit) {
    return request({
      url: `/cms/banner/getPage/${page}/${limit}`,//使用模板字符串进行拼接
      method: 'get'
    })
  },
  getById(id){
    return request({
      url: `/cms/banner/get/${id}`,//使用模板字符串进行拼接
      method: 'get'
    })
  },
  save(banner){
    return request({
      url: `/cms/banner/save`,//使用模板字符串进行拼接
      method: 'post',
      data:banner
    })
  },
  updateById(banner){
    return request({
      url: `/cms/banner/update`,//使用模板字符串进行拼接
      method: 'put',
      data:banner
    })
  },
  removeById(id) {
    return request({
      url: `/cms/banner/remove/${id}`,//使用模板字符串进行拼接
      method: 'delete'
    })
  }
}

