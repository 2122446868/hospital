import request from '@/utils/request'

const api_name = `/api/msm`

export default {
    // 发送短信
    sendCode(phone) {
        return request({
            url: `${api_name}/send/${phone}`,
            method: `get`
        })
    }
}