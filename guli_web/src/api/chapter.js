import request from '@/utils/request'


export default {
  //获取课程分类的信息，包括一级分类和二级分类
  getAllChapter(courseId) {
    return request({
      url: `/admin/edu/chapter/getAllChapter/${courseId}`,//使用模板字符串进行拼接
      method: 'get'
    })
  },
  //根据id获取章节
  getChapter(id){
    return request({
      url: `/admin/edu/chapter/getChapter/${id}`,//使用模板字符串进行拼接
      method: 'get'
    })
  },
  //增加章节
  addChapter(chapter){
    return request({
      url: `/admin/edu/chapter/addChapter`,//使用模板字符串进行拼接
      method: 'post',
      data:chapter
    })
  },
  //更新章节
  updateChapter(chapter){
    return request({
      url: `/admin/edu/chapter/updateChapter`,//使用模板字符串进行拼接
      method: 'put',
      data:chapter
    })
  },
  //删除章节
  deleteChapter(id){
    return request({
      url: `/admin/edu/chapter/deleteChapter/${id}`,//使用模板字符串进行拼接
      method: 'delete'
    })
  }
}

