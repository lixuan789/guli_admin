<template>
  <div class="app-container">
    <el-table
      :data="tableData"
      style="width: 100%">

      <el-table-column
        label="名称"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.title }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="图片"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">
            <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.imageUrl" >
            </el-image>
          </span>
        </template>
      </el-table-column>

      <el-table-column
        label="地址"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.linkUrl }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <router-link :to="'/banner/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>

</template>

<script>
import banner from '@/api/banner'
import teacher from '@/api/teacher'
export default {
  data() {
    return {
      page:1,
      total:0,
      limit:5,
      tableData: []
    }
  },
  created() {
    this.getList()
  },
  methods:{
    getList(page =1) {
      this.page=page
      banner.listPage(this.page,this.limit)
        .then(reponse=>{
          console.log(reponse)
          this.total=reponse.data.total
          this.tableData=reponse.data.list
        })
        .catch(error=>{
          console.log(error)
        })
    },
    removeDataById(id){
      this.$confirm('此操作将永久删除该轮播图, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//删除成功
        banner.removeById(id).then(response=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getList()
        })
      })
    }
  }
}
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
