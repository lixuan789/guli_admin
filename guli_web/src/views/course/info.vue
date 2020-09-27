<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="发布课程"/>
    </el-steps>

    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类 TODO -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="请选择" @change="getTwoSubject">
          <el-option
            v-for="subject in oneSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in twoSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>

      <!-- 课程讲师 TODO -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>


      <!-- 课程封面 TODO -->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/api/oss/upload'"
          class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>

      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>

      <!-- 课程简介 TODO -->
      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import subject from '@/api/subject'
import teacher from '@/api/teacher'
import Tinymce from '@/components/Tinymce'
import course from '@/api/course'
export default {
  name: 'info',
  components: { Tinymce },
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      BASE_API:process.env.VUE_APP_BASE_API,
      OSS_PATH:process.env.OSS_PATH,
      courseInfo:{
        id:'',
        title: '',
        subjectId: '',
        teacherId: '',
        lessonNum: 0,
        description: '',
        cover: 'https://lixuan-file.oss-cn-beijing.aliyuncs.com/avatar/cover.jpg',
        price: 0,
        subjectParentId:''
      },
      teacherList:[],//讲师列表
      oneSubjectList:[],//一级课程分类
      twoSubjectList:[]//二级课程分类

    }
  },

  created() {
    this.init()
  },
  watch:{
    $route(to,from){//监听路由的变化
      this.init()
    }
  },
  methods: {
    init(){
      this.getTeacher()//获取所有的讲师
      this.getOneSubject()
      //判断跳转过来的路径中有没有id
      if (this.$route.params&&this.$route.params.id){//有id则进行查找，回显数据、修改
        const id=this.$route.params.id
        course.getCourseInfo(id)
        .then(response=>{
          this.courseInfo=response.data.courseInfo
          for (let i=0;i<this.oneSubjectList.length;i++){
            let value=this.oneSubjectList[i].id
            if (value==this.courseInfo.subjectParentId){
              this.twoSubjectList=this.oneSubjectList[i].children
            }
          }
        })
      }else {//没有则进行保存
        this.courseInfo={}//清空表单
        this.courseInfo.cover='https://lixuan-file.oss-cn-beijing.aliyuncs.com/avatar/cover.jpg'
        this.courseInfo.lessonNum=0
        this.courseInfo.price=0
      }
    },
    handleAvatarSuccess(res,file){//上传成功
      this.courseInfo.cover = res.data.url
    },
    beforeAvatarUpload(file){//上传之前
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    getTeacher(){
      teacher.list()
      .then(response=>{
        this.teacherList=response.data.items
      })
    },
    getOneSubject(){
      subject.list()
      .then(response=>{
        this.oneSubjectList=response.data.list
      })
    },
    getTwoSubject(value){//value为一级分类的id
      for (let i=0;i<this.oneSubjectList.length;i++){
        if (value==this.oneSubjectList[i].id){
          this.twoSubjectList=this.oneSubjectList[i].children
          this.courseInfo.subjectId=''
        }
      }
    },
    saveOrUpdate() {
      if (this.courseInfo.id){
        course.updateCourseInfo(this.courseInfo)
        .then(response=>{
          this.$message({
            type: 'success',
            message: '更新信息成功!'
          });
          this.$router.push({ path: '/course/chapter/'+this.courseInfo.id })
        })
      }else {
        course.addCourseInfo(this.courseInfo)
          .then(response=>{
            this.$message({
              type: 'success',
              message: '添加信息成功!'
            });
            this.$router.push({ path: '/course/chapter/'+response.data.courseId })
          })
      }
    }
  }
}
</script>

<style scoped>
.editor-content{
  margin-top: 20px;
}
</style>
