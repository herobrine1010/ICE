<template>
  <div class="main">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人主页</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row>个人信息</el-row>
      <!-- 头像区域 -->
      <el-row class="userinfo">
        <el-col :span="4">
          <el-image
            src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
            fit="fill"
          ></el-image>
        </el-col>
        <el-col :span="6" class="el-col-gap">
          <!-- 个人信息区域 -->
          <el-table :data="userInfo" stripe>
            <el-table-column label="用户昵称" prop="username"></el-table-column>
          </el-table>
          <el-table :data="userInfo" stripe>
            <el-table-column label="联系方式" prop="tel"></el-table-column>
          </el-table>
          <el-table :data="userInfo" stripe>
            <el-table-column label="生日" prop="birthday"></el-table-column>
          </el-table>
        </el-col>
        <el-col :span="12" class="el-col-gap">
          <el-card class="address-gap" v-for="item in userInfo[0].address" :key="item">
            {{ item }}
            <el-button size="mini" type="danger" icon="el-icon-delete" circle></el-button>
          </el-card>
          <!-- 收货地址信息区域 -->
          <!-- <el-table :data="userInfo" stripe>
            <el-table-column label="用户昵称" prop="username"></el-table-column>
          </el-table>
          <el-table :data="userInfo" stripe>
            <el-table-column label="联系方式" prop="tel"></el-table-column>
          </el-table>
          <el-table :data="userInfo" stripe>
            <el-table-column label="生日" prop="birthday"></el-table-column>
          </el-table>-->
        </el-col>
      </el-row>

      <!-- 编辑区域 -->
      <el-row>
        <el-col :span="4">
          <el-button type="primary" @click="editDialogVisible = true">编辑个人信息</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="success" @click="addDialogVisible = true">添加收货地址</el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card class="wish_list">愿望清单</el-card>

    <!-- 修改个人信息的对话框 -->
    <el-dialog
      title="Edit User Information"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="User ID">
          <el-input v-model="editForm.user_id" disabled></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="username">
          <el-input v-model="editForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Tel" prop="tel">
          <el-input v-model="editForm.tel"></el-input>
        </el-form-item>
      </el-form>
      <!-- 对话框底部确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEditUserInfo">Cancel</el-button>
        <el-button type="primary" @click="editUserInfo">Confirm</el-button>
      </span>
    </el-dialog>

    <!-- 添加收货地址的对话框 -->
    <el-dialog
      title="Add address"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
        <el-form-item label="Address" prop="address2">
          <el-input v-model="addForm.address2"></el-input>
        </el-form-item>
      </el-form>
      <!-- 对话框底部确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAddAddress">Cancel</el-button>
        <el-button type="primary" @click="addAddress">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    // 验证手机号的规则
    var checkTelephone = (rule, value, callback) => {
      // 验证手机号的正则表达式
      const regTelephone = /^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$/

      if (regTelephone.test(value)) {
        // 合法的手机号
        return callback()
      }

      callback(new Error('请输入合法的手机号'))
    }
    return {
      userInfo: [{
        user_id: '997219957',
        username: 'budi',
        avator_url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        tel: '17717924664',
        birthday: '2000-04-27',
        address: [
          '上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区1',
          '上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区2',
          '上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区3',
          '上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区4'
        ]
      }],
      // 控制修改个人信息对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的商家信息对象
      editForm: {
        user_id: '997219957',
        username: 'budi',
        avator_url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        tel: '17717924664',
        birthday: '2000-04-27'
      },
      // 修改表单的验证规则对象
      editFormRules: {
        username: [
          { required: true, message: '请输入用户昵称', trigger: 'blur' }
        ],
        tel: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: checkTelephone, trigger: 'blur' }
        ]
      },
      // 控制添加收货地址对话框的显示与隐藏
      addDialogVisible: false,
      // 查询到的商家信息对象
      addForm: {
        address1: '',
        address2: ''
      },
      // 修改表单的验证规则对象
      addFormRules: {
        address2: [
          { required: true, message: '请输入详细地址', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    // 获取用户个人信息列表
    getUserList () { },
    // 监听修改游戏对话框的关闭事件
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 取消对个人信息的修改
    cancelEditUserInfo () {
      // 关闭对话框
      this.editDialogVisible = false
      // 提示取消修改
      this.$message('Cancel edit user info')
    },
    // 修改个人信息并提交
    editUserInfo () {
      // 调用 API 接口，发起修改游戏信息的请求，根据返回的 response 进行对应的操作
      // this.$refs.editFormRef.validate(async valid => {
      //   if (!valid) return
      //   // 发起修改用户信息的数据请求
      //   const { data: res } = await this.$http.put(
      //     'users/' + this.editForm.id,
      //     {
      //       email: this.editForm.email,
      //       mobile: this.editForm.mobile
      //     }
      //   )

      //   if (res.meta.status !== 200) {
      //     return this.$message.error('更新用户信息失败！')
      //   }

      //   // 关闭对话框
      //   this.editDialogVisible = false
      //   // 刷新数据列表
      //   this.getUserList()
      //   // 提示修改成功
      //   this.$message.success('更新用户信息成功！')
      // })
      // 关闭对话框
      this.editDialogVisible = false
      // 刷新数据列表
      this.getUserList()
      // 提示修改成功
      this.$message.success('Edit user info success')
    },
    // 监听添加地址对话框的关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 取消对个人信息的修改
    cancelAddAddress () {
      // 关闭对话框
      this.addDialogVisible = false
      // 提示取消修改
      this.$message('Cancel add address')
    },
    // 修改个人信息并提交
    addAddress () {
      // 调用 API 接口，发起修改游戏信息的请求，根据返回的 response 进行对应的操作
      // this.$refs.editFormRef.validate(async valid => {
      //   if (!valid) return
      //   // 发起修改用户信息的数据请求
      //   const { data: res } = await this.$http.put(
      //     'users/' + this.editForm.id,
      //     {
      //       email: this.editForm.email,
      //       mobile: this.editForm.mobile
      //     }
      //   )

      //   if (res.meta.status !== 200) {
      //     return this.$message.error('更新用户信息失败！')
      //   }

      //   // 关闭对话框
      //   this.editDialogVisible = false
      //   // 刷新数据列表
      //   this.getUserList()
      //   // 提示修改成功
      //   this.$message.success('更新用户信息成功！')
      // })
      // 关闭对话框
      this.addDialogVisible = false
      // 刷新数据列表
      this.getUserList()
      // 提示修改成功
      this.$message.success('Add address success')
    }
  }
}
</script>

<style lang="less" scoped>
.main {
  margin-left: 10%;
  margin-right: 10%;
}
.userinfo {
  margin-top: 20px;
}
.el-col-gap {
  margin-left: 30px;
}
.wish_list {
  margin-top: 30px;
}
.address-gap {
  margin-bottom: 10px;
}
</style>
