<template>
  <div>
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
        <el-row class="userinfo" :gutter="20">
          <el-col :span="4">
            <el-image
              src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
              fit="cover"
              class="avator"
            ></el-image>
          </el-col>
          <el-col :span="4" class="el-col-gap">
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
          <el-col :span="14" class="el-col-gap">
            <el-card class="address-gap" v-for="item in userInfo[0].address" :key="item">
              <el-button
                size="mini"
                type="info"
                icon="el-icon-delete"
                circle
                @click="deleteAddress(item)"
              ></el-button>
              {{ item }}
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
        <el-row class="edit-part">
          <el-col :span="4">
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="fileList"
            >
              <el-button size="small" type="warning">修改个人头像</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-col>
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
          <el-form-item>
            <v-distpicker @selected="addressPickerSelected"></v-distpicker>
          </el-form-item>
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
    <div>
      <Goods
        v-for="(i,index) in rowNumber"
        :goodsInfo="getWishesInfo(index)"
        :key="i"
        class="list-item"
      />
    </div>
  </div>
</template>

<script>
import Goods from '../common/Goods'
export default {
  components: { Goods },
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
        // user_id: '997219957',
        // username: 'budi',
        // avator_url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        // tel: '17717924664',
        // birthday: '2000-04-27'
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
      },
      // 每次加载的行数
      rowNumber: 1,
      // 商品列表
      wishList: []
    }
  },
  created () {
    this.rowNumber = 1
    this.wishList = []
    this.getWishes()
    this.getUserList()
  },
  methods: {
    // 获取用户个人信息列表
    getUserList () {
      console.log('getUserList')
      console.log(this.$store.state.userId)
      this.$axios.get('/api/getUser', { params: { userId: this.$store.state.userId } })
        .then(response => {
          console.log(response)
          this.userInfo = []
          let avatarPath = ''
          if (response.data.result[0].avatarPath === null) {
            avatarPath = 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
          } else {
            avatarPath = response.data.result[0].avatarPath
          }
          let userData = {
            user_id: response.data.result[0].userId,
            username: response.data.result[0].userName,
            avator_url: avatarPath,
            tel: response.data.result[0].tel,
            birthday: response.data.result[0].birthday
          }
          this.userInfo.push(userData)
          this.editForm = userData
          console.log(this.userInfo)
        })
    },
    // 监听修改游戏对话框的关闭事件
    editDialogClosed () {
      // this.$refs.editFormRef.resetFields()
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
      let editData = {
        userName: this.editForm.username,
        avatarPath: this.editForm.avator_url,
        tel: this.editForm.tel
        // birthday: this.editForm.birthday,
        // userId: this.editForm.user_id
      }
      console.log('editUserInfo')
      this.$axios.post('/api/updateInfo', editData)
        .then(response => {
          console.log(response)
          // 刷新数据列表
          this.getUserList()
        })
      this.editDialogVisible = false
      // 提示修改成功
      this.$message.success('Edit user info success')
    },
    // 监听地址选择器选择完毕地址的操作
    addressPickerSelected (data) {
      console.log(data)
      this.addForm.address1 = data
      console.log(this.addForm.address1.province.value + ' | ' + this.addForm.address1.city.value + ' | ' + this.addForm.address1.area.value)
    },
    // 监听添加地址对话框的关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 取消对地址信息的添加
    cancelAddAddress () {
      // 关闭对话框
      this.addDialogVisible = false
      // 提示取消添加
      this.$message('Cancel add address')
    },
    // 修改收货地址并提交
    addAddress () {
      // 调用 API 接口，发起修改收货地址的请求，根据返回的 response 进行对应的操作
      // this.$refs.editFormRef.validate(async valid => {
      if (this.editForm.tel.length !== 11) {
        this.$message.error('telephone number is invalid!')
        return
      }
      let editData = {
        address: this.addForm.address2
      }
      console.log('editAddress')
      this.$axios.post('/api/updateInfo', editData)
        .then(response => {
          console.log(response)
          // 刷新数据列表
          this.getUserList()
        })
      this.editDialogVisible = false
      // 提示修改成功
      this.$message.success('Edit Address success')
      // 关闭对话框
      this.addDialogVisible = false
      // 刷新数据列表
      this.getUserList()
      // 提示修改成功
      this.$message.success('Add address success')
    },
    getWishesInfo (index) {
      let wishesInfo = []
      if ((index * 4 + 4) > this.wishList.length) {
        for (let i in this.wishList) {
          if (i >= index * 4) {
            wishesInfo.push(this.wishList[i])
          }
        }
      } else {
        for (let i in this.wishList) {
          if (i >= (index * 4) && i <= (index * 4 + 3)) {
            wishesInfo.push(this.wishList[i])
          }
        }
      }
      return wishesInfo
    },
    getWishes () {
      console.log('getWishes')
      this.$axios.post('/api/getMyWishList')
        .then(response => {
          console.log('getWishes response', response)
          if (response.data.status === '200') {
            console.log('response.data.result', response.data.result)
            if (response.data.result.length === 0) {
              console.log('加载完毕')
            } else {
              for (let index in response.data.result) {
                if ((parseInt(index) + 1) % 4 === 0) {
                  this.rowNumber += 1
                  console.log('rowNumber', this.rowNumber)
                }
                let gameInfo = {
                  id: response.data.result[index].id,
                  imgSrc: response.data.result[index].cover_path,
                  name: response.data.result[index].title,
                  value: response.data.result[index].price,
                  tag: []
                }
                // 如果cover_path不存在，则替换成默认图片
                if (response.data.result[index].cover_path === null) {
                  gameInfo.imgSrc = 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg'
                }
                // 如果标签过多，截取前两个
                if (response.data.result[index].tags_list.length > 2) {
                  for (let i in response.data.result[index].tags_list) {
                    if (i >= 2) {
                      break
                    }
                    gameInfo.tag.push(response.data.result[index].tags_list[i])
                  }
                }
                this.wishList.push(gameInfo)
              }
            }
          }
        })
    },
    // 删除地址信息
    deleteAddress (item) {
      this.$confirm('This operation will permanently delete the address, do you want to continue?', 'Prompt', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        // item中传输的是地址的字符串信息，需要匹配字符串进行删除。
        this.$message({
          type: 'success',
          message: 'Delete success'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Cancel delete'
        })
      })
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
.avator {
  border-radius: 50%;
}
.el-col-gap {
  margin-left: 30px;
}
.wish_list {
  margin-top: 30px;
  margin-bottom: 50px;
}
.address-gap {
  margin-bottom: 10px;
}
.edit-part {
  margin-top: 20px;
}
</style>
