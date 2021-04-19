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
      url: `/admin/hosp/hospital/list/${page}/${limit}`,
      method: 'get',
      prarms: serarchObj
    })
  },
  //根据dictCode查询所有子节点 （所有省）
  findByDictCode(dictCode) {
    return request({
      url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
      method: 'get'

    })
  },
  //根据id查询下级数据字典
  findChildId(id) {
    return request({
      url: `/admin/cmn/dict/findByParentId/${id}`,
      method: 'get'
    })
  },
  // 更新状态
  updateStatus(id, status) {
        return request({
          url: `/admin/hosp/hospital/updateStatus/${id}/${status}`,
          method: 'get'
    })
  },
  // 查看医院详情
  show(id) {
        return request({
          url: `/admin/hosp/hospital/show/${id}`,
          method: 'get'
    })
  }

}
