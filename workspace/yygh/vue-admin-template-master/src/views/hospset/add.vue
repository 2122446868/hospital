<template>
    
  <div class="app-container">
          医院设置添加       <el-form label-width="120px">
               <el-form-item label="医院名称">
                    <el-input v-model="hospitalSet.hosname" />
                 </el-form-item
      >
               <el-form-item label="医院编号">
                    <el-input v-model="hospitalSet.hoscode" />
                 </el-form-item
      >
               <el-form-item label="api基础路径">
                    <el-input v-model="hospitalSet.apiUrl" />
                 </el-form-item
      >
               <el-form-item label="联系人姓名">
                    <el-input v-model="hospitalSet.contactsName" />
                 </el-form-item
      >
               <el-form-item label="联系人手机">
                    <el-input v-model="hospitalSet.contactsPhone" />
                 </el-form-item
      >
               <el-form-item>
                    <el-button type="primary" @click="saveOrUpdate()"
          >保存</el-button
        >
         <el-button type="primary" @click="backUp()">返回</el-button>
                 </el-form-item
      >
            </el-form
    >
       
  </div>
</template>

<script>
// 引入接口定义的js文件
import hospset from "@/api/hosp/hospitalSet";
export default {
  data() {
    return {
      hospitalSet: {},
    };
  },
  created() {
    //页面渲染之前执行
    //获取路由id值 调用接口得到医院设置信息
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id;
      console.log(id);
      this.findByIdHospitalSet(id);
    }
  },
  methods: {
    // 根据id获取医院设置
    findByIdHospitalSet(id) {
      hospset.findByIdHospitalSet(id).then((response) => {
        console.log(response.data);
        this.hospitalSet = response.data;
      });
    },

    //添加
    saveOrUpdate() {
      hospset.saveHospitalSet(this.hospitalSet).then((response) => {
        //提示
        this.$message({
          type: "success",
          message: "添加成功!",
        }); //跳转列表页面，使用路由跳转方式实现
        this.$router.push({ path: "/hospSet/list" });
      });
    },
    backUp() {
      this.$router.push({ path: "/hospSet/list" });
    },
  },
};
</script>