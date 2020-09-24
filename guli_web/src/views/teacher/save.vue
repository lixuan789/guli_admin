<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>

        <!--
    v-show：是否显示上传组件
    :key：类似于id，如果一个页面多个图片上传控件，可以做区分
    :url：后台上传的url地址
    @close：关闭上传组件
    @crop-upload-success：上传成功后的回调 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          url="/api/oss/upload"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>

      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import teacher from '@/api/teacher'
import ImageCropper from '@/components/ImageCropper'//导入上传图像所需要的组件
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data(){
    return {
      teacher: {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar:''
      },
      // BASE_API:process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false, // 保存按钮是否禁用,
      imagecropperShow: false,
      imagecropperKey: 0
    }
  },
  created(){
    this.init()
  },
  watch:{
    $route(to,from){//监听路由的变化
      this.init()
    }
  },
  methods: {
    init(){
      //判断跳转过来的路径中有没有id
      if (this.$route.params&&this.$route.params.id){//有id则进行查找，回显数据、修改
        const id=this.$route.params.id
        this.getTeacherById(id)
      }else {//没有则进行保存
        this.teacher={}//清空表单
        this.teacher.avatar='https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191'
      }
    },
    saveOrUpdate() {//保存或者更新数据
      this.saveBtnDisabled = true
      if (this.teacher.id){
        this.updateTeacher()
      }else {
        this.saveData()
      }
    },

    // 保存讲师
    saveData() {
      teacher.save(this.teacher)
      .then(response=>{
        this.$message({
          type: 'success',
          message: '保存成功!'
        });
      }).then(response=>{
        this.$router.push({path:'/teacher/list'})
      })
    },
    getTeacherById(id){//获取讲师
      teacher.getById(id)
        .then(response=>{
            this.teacher=response.data.item
        }).catch(error => {
        this.$message({
          type: 'error',
          message: '获取数据失败'
        })
      })
    },
    updateTeacher(){
      teacher.updateById(this.teacher)
        .then(response=>{
          this.$message({
            type: 'success',
            message: '更新数据成功'
          })
        }).then(response=>{
          this.$router.push({path:'/teacher/list'})
      })
    },
    cropSuccess(resData) {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      console.log(resData)
      this.teacher.avatar = resData.url
    },
    close() {
      this.imagecropperShow = false
      this.imagecropperKey=this.imagecropperKey+1
    }

  }
}
</script>

<style scoped>
.avatar{
  width: 200px;
  height: 200px;
  border-radius: 50%;
}
</style>
