import request from '@/utils/request'
export default {
  createStatistics(day) {
    return request({
      url: `/statistics/daily/${day}`,
      method: 'post'
    })
  },
  showChart(begin,end,type){
    return request({
      url: `/statistics/daily/show-chart/${begin}/${end}/${type}`,
      method: 'get'
    })
  }
}
