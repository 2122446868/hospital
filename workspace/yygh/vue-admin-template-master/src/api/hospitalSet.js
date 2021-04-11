import request from '@/utils/request'

export default {
    //医院设置 条件分页查询
    getHospSetfList(current, limit, serachObj) {
        return request({
            url: `/admin/hosp/hospitalSet/findPage/${current}/${limit}`,
            method: 'post',
            data: serachObj   //使用json
        })
    },
    // 逻辑删除医院设置
    deleteHospSet(id) {
        return request({
            url: `/admin/hosp/hospitalSet/${id}`,
            method: 'delete'
        })
    },
    // 批量删除医院设置
    removeRows(idList) {
        return request({
            url: `/admin/hosp/hospitalSet/batchRemoveHOspitalSet`,
            method: 'delete',
            data: idList
        })
    },
    // 医院设置锁定和解锁
    lockHospSet(id, status) {
        return request({
            url: `/admin/hosp/hospitalSet/lockHospitalSet/${id}/${status}`,
            method: "put",

        })
    },
    // 添加医院设置
    saveHospitalSet(hospitalSet) {
        return request({
            url: `/admin/hosp/hospitalSet/saveHospitalSet`,
            method: "post",
            data: hospitalSet

        })
    },
    // 根据id获取医院设置
    findByIdHospitalSet(id) {
        return request({
            url: `/admin/hosp/hospitalSet/${id}`,
            method: "get",

        })
    },
    // 修改医院设置
    updateHospitalSet(hospitalSet) {
        return request({
            url: `/admin/hosp/hospitalSet/updateHospitalSet`,
            method: "post",
            data: hospitalSet

        })

    }


}