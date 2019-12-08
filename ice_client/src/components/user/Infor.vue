<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>个人信息</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 个人信息区域 -->
      <el-table :data="publisherInfo" stripe>
        <el-table-column label="账号ID" prop="publisher_id"></el-table-column>
      </el-table>
      <el-table :data="publisherInfo" stripe>
        <el-table-column label="名称" prop="publisher_name"></el-table-column>
      </el-table>
      <el-table :data="publisherInfo" stripe>
        <el-table-column label="密码" prop="pwd"></el-table-column>
      </el-table>
      <el-table :data="publisherInfo" stripe>
        <el-table-column label="简介" prop="description"></el-table-column>
      </el-table>

      <!-- 编辑区域 -->
      <el-row>
        <el-col :span="4">
          <el-button type="primary" @click="showEditDialog">Edit</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 修改个人信息的对话框 -->
    <el-dialog
      title="Edit Publisher Information"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="Publisher ID">
          <el-input v-model="editForm.publisherId" disabled></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="publisherName">
          <el-input v-model="editForm.publisherName"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="pwd">
          <el-input v-model="editForm.pwd"></el-input>
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input v-model="editForm.description"></el-input>
        </el-form-item>
      </el-form>
      <!-- 对话框底部确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEditPublisherInfo">Cancel</el-button>
        <el-button type="primary" @click="editPublisherInfo">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mixin } from '../../mixins'
export default {
  mixins: [mixin],
  data () {
    // 验证简介长度的规则
    var checkDiscription = (rule, value, cb) => {
      // 验证简介的正则表达式
      const regDiscription = /^.{0,500}$/

      if (regDiscription.test(value)) {
        // 合法的简介
        return cb()
      }

      cb(new Error('Please do not exceed 500 characters'))
    }
    return {
      // 商家个人信息
      publisherInfo: [{
        publisher_id: '',
        publisher_name: '',
        pwd: '',
        description: '' }],
      // 控制修改个人信息对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的商家信息对象
      editForm: {
        publisherId: '',
        publisherName: '',
        pwd: '',
        description: ''
      },
      // 修改表单的验证规则对象
      editFormRules: {
        publisherName: [
          { required: true, message: '请输入商家名称', trigger: 'blur' }
        ],
        pwd: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        description: [
          { validator: checkDiscription, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getPublisherList()
  },
  methods: {
    // 获取商家个人信息列表
    getPublisherList () {
      this.$axios.get('/api/publisherInfo')
        .then(response => {
          // console.log(response)
          this.publisherInfo[0].publisher_id = response.data.result[0].publisher_id
          this.publisherInfo[0].publisher_name = response.data.result[0].publisher_name
          this.publisherInfo[0].pwd = response.data.result[0].pwd
          this.publisherInfo[0].description = response.data.result[0].description
          // this.publisherInfo = response.data.result[0].
        })
    },
    // 监听修改游戏对话框的关闭事件
    editDialogClosed () {
      // this.$refs.editFormRef.resetFields()
    },
    // 取消对个人信息的修改
    cancelEditPublisherInfo () {
      // 关闭对话框
      this.editDialogVisible = false
      // 提示取消修改
      this.$message('Cancel edit publisher info')
    },
    showEditDialog () {
      this.editDialogVisible = true
      this.editForm.publisherId = this.publisherInfo[0].publisher_id
      this.editForm.publisherName = this.publisherInfo[0].publisher_name
      this.editForm.pwd = this.publisherInfo[0].pwd
      this.editForm.description = this.publisherInfo[0].description
    },
    // 修改个人信息并提交
    editPublisherInfo () {
      console.log('确认修改')
      // 调用 API 接口，发起修改游戏信息的请求，根据返回的 response 进行对应的操作
      this.$axios.post('/api/updatePublisherInfo', this.editForm)
        .then(response => {
          console.log(response)
          if (response.data.status === '200') {
            // 关闭对话框
            this.editDialogVisible = false
            // 刷新数据列表
            this.getPublisherList()
            // 提示修改成功
            this.$message.success('Edit publisher info success')
          } else {
            this.$message.error('Edit publisher info fail')
            // 关闭对话框
            this.editDialogVisible = false
          }
        })
        .catch(() => {
          this.$message.error('Edit publisher info fail')
          // 关闭对话框
          this.editDialogVisible = false
        })
    }
  }
}
</script>

<style lang="less" scoped>
.el-row {
  margin-top: 30px;
}
</style>
