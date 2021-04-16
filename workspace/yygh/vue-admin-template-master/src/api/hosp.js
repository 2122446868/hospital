import request from '@/utils/request'

export default {
  //医院列表
  getHospList(page,limit,serarchObj) {
    return request({
      url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
      method: 'get'
    })
  }
}
