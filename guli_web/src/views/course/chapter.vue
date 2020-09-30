<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="发布课程"/>
    </el-steps>

    <el-button type="text" @click="dialogChapter">添加章节</el-button>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li
        v-for="chapter in chapterList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}

          <span class="acts">
                <el-button type="text" @click="dialogVideo(chapter.id)">添加课时</el-button>
                <el-button style="" type="text" @click="editChapter(chapter.id)">编辑</el-button>
                <el-button type="text" @click="deleteChapter(chapter.id)">删除</el-button>
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                <el-button type="text" @click="editVideo(video.id)">编辑</el-button>
                <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/api/vod/video/upload'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import chapter from '@/api/chapter'
import teacher from '@/api/teacher'
import video from '@/api/video'

export default {
  name: 'chapter',
  data() {
    return {
      BASE_API:process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false, // 保存按钮是否禁用
      chapterList:[],
      dialogChapterFormVisible:false,//章节弹框
      dialogVideoFormVisible:false,
      courseId:'',
      chapter:{
        id:'',
        title:'',
        sort:0
      },
      video:{
        id:'',
        title:'',
        sort:0,
        free:true,
        videoSourceId:'',
        videoOriginalName:''
      },
      fileList:[]
    }
  },

  created() {
    console.log('chapter created')
    if (this.$route.params &&this.$route.params.id){
      this.courseId=this.$route.params.id
      this.getAllChapter()
    }
  },

  methods: {
    handleVodUploadSuccess(response, file, fileList){//视频上传成功
      this.video.videoSourceId=response.data.videoId
      this.video.videoOriginalName=file.name
    },
    handleVodRemove(file, fileList){//文件列表移除文件
      video.deleteAliyunVideo(this.video.videoSourceId)
      .then(response=>{
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
        this.video.videoSourceId=''
        this.video.videoOriginalName=''
      })
    },
    beforeVodRemove(file, fileList){//删除文件之前
      return this.$confirm(`确定移除 ${ file.name }？`)
    },
    handleUploadExceed(files, fileList){//文件超出个数限制
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    deleteChapter(id){
      this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//删除成功
        chapter.deleteChapter(id).then(response=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getAllChapter()
        })
      })
    },
    editChapter(id){
      this.dialogChapterFormVisible=true
      chapter.getChapter(id)
      .then(response=>{
        this.chapter=response.data.item
      })
    },
    addChapter(){
      this.chapter.courseId=this.courseId
      chapter.addChapter(this.chapter)
      .then(response=>{
        this.$message({
          type: 'success',
          message: '添加成功!'
        });
        this.dialogChapterFormVisible=false
        this.getAllChapter()
      })
    },
    updateChapter(){
      chapter.updateChapter(this.chapter)
      .then(response=>{
        this.$message({
          type: 'success',
          message: '更新成功!'
        });
        this.dialogChapterFormVisible=false
        this.getAllChapter()
      })
    },
    saveOrUpdate(){
      if (this.chapter.id){
        this.updateChapter()
      }else {
        this.addChapter()
      }
    },
    dialogChapter(){
      this.dialogChapterFormVisible=true
      this.chapter={}//每次弹框清空消息
      this.chapter.sort=0
    },
    dialogVideo(chapterId){
      this.fileList=[]
      this.dialogVideoFormVisible=true
      this.video={}
      this.video.sort=0
      this.video.chapterId=chapterId //把chapterId赋值给video
    },
    addVideo(){
      this.video.courseId=this.courseId
      video.addVideo(this.video)
      .then(response=>{
        this.$message({
          type: 'success',
          message: '增加讲师成功!'
        });
        this.dialogVideoFormVisible=false
        this.getAllChapter()
      })
    },
    updateVideo(){
      video.updateVideo(this.video)
      .then(response=>{
        this.$message({
          type: 'success',
          message: '更新成功!'
        });
        this.dialogVideoFormVisible=false
        this.getAllChapter()
      })
    },
    saveOrUpdateVideo(){
      if (this.video.id){
        this.updateVideo()
      }else {
        this.addVideo()
      }
    },
    editVideo(id){
      this.dialogVideoFormVisible=true
      video.getVideo(id)
        .then(response=>{
          this.video=response.data.item
        })
    },
    deleteVideo(id){
      this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//删除成功
        video.deleteVideo(id).then(response=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getAllChapter()
        })
      })
    },
    previous() {
      console.log('previous')
      this.$router.push({ path: '/course/info/'+this.courseId })
    },

    next() {
      console.log('next')
      this.$router.push({ path: '/course/publish/'+this.courseId })
    },
    getAllChapter(){
      chapter.getAllChapter(this.courseId)
        .then(response=>{
          this.chapterList=response.data.list
        })
    }

  }
}
</script>

<style scoped>
  .chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .chanpterList li{
    position: relative;
  }
  .chanpterList p{
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }
  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>
