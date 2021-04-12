import request from '@/utils/request'

export default {
  //数据字典
  findByParentId(id) {
    return request({
      url: `/admin/cmn/dict/findByParentId/${id}`,
      method: 'get'
    })
  }
}
