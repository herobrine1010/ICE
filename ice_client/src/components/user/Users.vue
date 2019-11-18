<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input
            placeholder="请输入内容"
            v-model="queryInfo.query"
            clearable
            @clear="currentPageChange(userlist, queryInfo.currentpage)"
          >
            <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>

      <!-- 用户列表区域 -->
      <el-table :data="tempList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="电话" prop="telephone"></el-table-column>
        <el-table-column label="角色" prop="role_name"></el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.mg_state" @change="userStateChanged(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.id)"
            ></el-button>
            <!-- 删除按钮 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeUserById(scope.row.id)"
            ></el-button>
            <!-- 分配角色按钮 -->
            <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.currentpage"
        :page-sizes="[1, 2, 5, 10]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="currenttotal"
      ></el-pagination>
    </el-card>

    <!-- 添加用户的对话框 -->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="telephone">
          <el-input v-model="addForm.telephone"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改用户的对话框 -->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="telephone">
          <el-input v-model="editForm.telephone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    // 验证邮箱的规则
    var checkEmail = (rule, value, callback) => {
      // 验证邮箱的正则表达式
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
      if (regEmail.test(value)) {
        // 合法的邮箱
        return callback()
      }

      callback(new Error('请输入合法的邮箱'))
    }
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
      // 获取用户列表的参数对象
      queryInfo: {
        query: '',
        // 当前的页数
        currentpage: 1,
        // 当前每页显示多少条数据
        pagesize: 10
      },
      // 当前展示总数据条数
      currenttotal: 4,
      // 总数据条数
      total: 4,
      // 控制添加用户对话框的显示与隐藏
      addDialogVisible: false,
      // 添加用户的表单数据
      addForm: {
        id: '',
        username: '',
        password: '',
        email: '',
        telephone: '',
        role_name: '',
        mg_state: false
      },
      // 添加用户的验证规则对象
      addFormRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 3,
            max: 10,
            message: '用户名的长度在3到10个字符之间',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 6,
            max: 15,
            message: '密码的长度在6到15个字符之间',
            trigger: 'blur'
          }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ],
        telephone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: checkTelephone, trigger: 'blur' }
        ]
      },
      // 控制修改用户对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的用户信息对象
      editForm: {
        id: '',
        username: '',
        password: '',
        email: '',
        telephone: '',
        role_name: '',
        mg_state: false
      },
      // 修改表单的验证规则对象
      editFormRules: {
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ],
        telephone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: checkTelephone, trigger: 'blur' }
        ]
      },
      tempList: [],
      userlist: [
        {
          id: '0',
          username: 'budi',
          password: '123456',
          email: '997219957@qq.com',
          telephone: '17717924664',
          role_name: '超级管理员',
          mg_state: true
        },
        {
          id: '1',
          username: 'tiehanhan',
          password: '123456',
          email: 'hanhan@tie.com',
          telephone: '12345654321',
          role_name: '废物',
          mg_state: false
        },
        {
          id: '2',
          username: 'Gimgoon',
          password: '123456',
          email: 'Gimgoon@gjr.com',
          telephone: '23456765432',
          role_name: '工具人',
          mg_state: true
        },
        {
          id: '3',
          username: 'Tian',
          password: '123456',
          email: 'gaotianliang@FPX.com',
          telephone: '34567876543',
          role_name: '大阴阳师',
          mg_state: false
        }
      ]
    }
  },
  created() {
    this.currentPageChange(this.userlist, this.queryInfo.currentpage)
  },
  methods: {
    // 监听 pagesize 改变的事件
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize
      this.handleCurrentChange(this.queryInfo.currentpage)
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      this.queryInfo.currentpage = newPage
      this.currentPageChange(this.userlist, newPage)
    },
    // 分页信息改变时，重新从 userlist 拉取数据到 templist
    currentPageChange(list, currentpage) {
      let from = (currentpage - 1) * this.queryInfo.pagesize
      // let from = (currentpage - 1) * this.pagesize
      let to = currentpage * this.queryInfo.pagesize
      this.tempList = []
      for (; from < to; from++) {
        if (list[from]) {
          this.tempList.push(list[from])
        }
      }
    },
    // 监听 switch 开关状态的改变
    userStateChanged(userinfo) {
      this.$message.success('更新用户状态成功')
    },
    // 搜索
    search() {
      console.log('query:' + this.queryInfo.query)
      if (this.queryInfo.query) {
        let start = 0
        let searchtotal = 0
        this.tempList = []
        do {
          if (this.userlist[start].username.includes(this.queryInfo.query)) {
            this.tempList.push(this.userlist[start])
            searchtotal++
          }
        } while (++start < this.total)
        this.currenttotal = searchtotal
        this.currentPageChange(this.tempList, this.queryInfo.currentpage)
      } else {
        this.currentPageChange(this.userlist, this.queryInfo.currentpage)
      }
    },
    // 监听添加用户对话框的关闭事件
    addDialogClosed() {
      this.$refs.addFormRef.resetFields()
    },
    // 点击按钮，添加新用户
    addUser() {
      this.$refs.addFormRef.validate(valid => {
        if (!valid) return
        // 可以发起添加用户的请求
        var addUserForm = {
          id: this.total,
          username: this.addForm.username,
          password: this.addForm.password,
          email: this.addForm.email,
          telephone: this.addForm.telephone,
          role_name: 'null',
          mg_state: false
        }
        this.userlist.push(addUserForm)
        this.currenttotal++
        this.$message.success('添加用户成功')
        this.addDialogVisible = false
        // 重新获取用户列表数据
        this.currentPageChange(this.userlist, this.queryInfo.currentpage)
      })
    },
    // 展示编辑用户的对话框
    showEditDialog(id) {
      this.editForm.id = this.userlist[id].id
      this.editForm.username = this.userlist[id].username
      this.editForm.password = this.userlist[id].password
      this.editForm.email = this.userlist[id].email
      this.editForm.telephone = this.userlist[id].telephone
      this.editForm.role_name = this.userlist[id].role_name
      this.editForm.mg_state = this.userlist[id].mg_state
      console.log(this.editForm)
      this.editDialogVisible = true
    },
    // 监听修改用户对话框的关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields()
    },
    // 修改用户信息的提交
    editUserInfo() {
      // 发起修改用户信息的数据请求
      this.userlist[this.editForm.id].email = this.editForm.email
      this.userlist[this.editForm.id].telephone = this.editForm.telephone
      // 关闭对话框
      this.editDialogVisible = false
      // 刷新数据列表
      this.currentPageChange(this.userlist, this.queryInfo.currentpage)
      // 提示修改成功
      this.$message.success('更新用户信息成功')
    },
    // 根据Id删除对应的用户信息
    async removeUserById(id) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '此操作将永久删除该用户, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      // this.userlist.$remove(this.userlist.find(user => user.id === id))
      this.userlist = this.userlist.filter(user => user.id !== id)
      this.currenttotal--
      // 刷新数据列表
      this.currentPageChange(this.userlist, this.queryInfo.currentpage)
      // 提示删除成功
      this.$message.success('删除用户成功')
    }
  }
}
</script>

<style lang="less" scoped>
</style>
