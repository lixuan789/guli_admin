<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="downLoadUrl">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          action="/admin/edu/subject/addSubject"
          name="file"
          accept="application/vnd.ms-excel">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">{{fileUploadBtnText}}</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: 'import',
    data() {
      return{
        downLoadUrl:'',
        importBtnDisabled:false,
        fileUploadBtnText: '上传到服务器', // 按钮文字
        loading:false

      }
    },
    created(){

    },
    methods: {
      fileUploadSuccess(){//上传成功的方法
        if (response.success === true) {
          this.fileUploadBtnText = '导入成功'
          this.loading = false
          this.$message({
            type: 'success',
            message: '上传成功!'
          })
        }
      },
      fileUploadError(){//上传失败的方法
        this.fileUploadBtnText = '导入失败'
        this.loading = false
        this.$message({
          type: 'error',
          message: '导入失败'
        })
      },
      submitUpload(){//点击上传触发的方法
        this.fileUploadBtnText='正在上传'
        this.importBtnDisabled=true
        this.loading=true
        this.$refs.upload.submit()
      }

    }
  }
</script>

<style scoped>

</style>
