import request from '@/utils/request'

export default {
  //医院列表
  getHospList(page, limit, serarchObj) {
    return request({
      url: `/admin/hosp/hospital/list/${page}/${limit}`,
      method: 'get',
      prarms: serarchObj
    })
  },
  //根据dictCode查询所有子节点
  findByDictCode(dictCode) {
    return request({
      url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
      method: 'get'

    })
  },
  //根据id查询下级数据字典
  findChildId(id) {
    return ({
      url: `/admin/cmn/dict/findByParentId/${id}`,
      method: 'get'
    })
  }
}
