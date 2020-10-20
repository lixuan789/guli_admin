<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="轮播图名称">
        <el-input v-model="banner.title"/>
      </el-form-item>
      <el-form-item label="轮播图排序">
        <el-input-number v-model="banner.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="轮播图链接地址">
        <el-input v-model="banner.linkUrl"/>
      </el-form-item>
      <el-form-item label="轮播图">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="banner.imageUrl"/>
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

import ImageCropper from '@/components/ImageCropper'//导入上传图像所需要的组件
import PanThumb from '@/components/PanThumb'
import banner from '@/api/banner'

export default {
  components: { ImageCropper, PanThumb },
  data(){
    return {
      banner: {
        title: '',
        sort: 0,
        imageUrl: '',
        linkUrl:''
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
        this.getBannerById(id)
      }else {//没有则进行保存
        this.banner={}//清空表单
        this.banner.imageUrl='https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191'
      }
    },
    saveOrUpdate() {//保存或者更新数据
      this.saveBtnDisabled = true
      if (this.banner.id){
        this.updateBanner()
      }else {
        this.saveData()
      }
    },

    // 保存讲师
    saveData() {
      banner.save(this.banner)
        .then(response=>{
          this.$message({
            type: 'success',
            message: '保存成功!'
          });
        }).then(response=>{
        this.$router.push({path:'/banner/list'})
      })
    },
    getBannerById(id){//获取讲师
      banner.getById(id)
        .then(response=>{
          this.banner=response.data.item
        }).catch(error => {
        this.$message({
          type: 'error',
          message: '获取数据失败'
        })
      })
    },
    updateBanner(){
      banner.updateById(this.banner)
        .then(response=>{
          this.$message({
            type: 'success',
            message: '更新数据成功'
          })
        }).then(response=>{
        this.$router.push({path:'/banner/list'})
      })
    },
    cropSuccess(resData) {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.banner.imageUrl = resData.url
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
