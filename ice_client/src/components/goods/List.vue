<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="Please enter content" v-model="queryInfo.query" clearable @click="getGameList">
            <el-button slot="append" icon="el-icon-search" @click="getGameList"></el-button>
          </el-input>
        </el-col>
      </el-row>

      <!-- 游戏商品列表数据 -->
      <el-table :data="gamelist" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="Title" prop="title"></el-table-column>
        <el-table-column label="Price" prop="price"></el-table-column>
        <el-table-column label="Discount" prop="discount">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.discount === 'On'">On</el-tag>
            <el-tag type="info" v-else>Off</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Average Rate" prop="average_rate"></el-table-column>
        <el-table-column label="Release Date" prop="release_date"></el-table-column>
        <el-table-column label="Pre Order" prop="pre_order">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.pre_order === 'On'">On</el-tag>
            <el-tag type="info" v-else>Off</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Consoles" prop="consoles" width="200px">
          <template slot-scope="scope">
            <el-tag
              class="tag_console"
              size="mini"
              type="warning"
              v-for="item in scope.row.consoles"
              :key="item"
              effect="dark"
            >{{ item }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Category" prop="category"></el-table-column>
        <el-table-column label="Options" width="130px">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.gameid)"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeGameById(scope.row.gameid)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background
      ></el-pagination>
    </el-card>

    <!-- 修改游戏商品信息的对话框 -->
    <el-dialog
      title="Edit Game Information"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="Game ID">
          <el-input v-model="editForm.gameid" disabled></el-input>
        </el-form-item>
        <el-form-item label="Title" prop="title">
          <el-input v-model="editForm.title"></el-input>
        </el-form-item>
        <el-form-item label="Price" prop="price">
          <el-input v-model="editForm.price"></el-input>
        </el-form-item>
        <el-form-item label="Discount">
          <el-radio-group v-model="editForm.discount" size="small">
            <el-radio-button label="On"></el-radio-button>
            <el-radio-button label="Off"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Pre Order">
          <el-radio-group v-model="editForm.pre_order" size="small">
            <el-radio-button label="On"></el-radio-button>
            <el-radio-button label="Off"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Consoles">
          <el-checkbox-group v-model="editForm.consoles" size="small">
            <el-checkbox-button v-for="con in consoleOptions" :label="con" :key="con">{{con}}</el-checkbox-button>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="Discription" prop="discription">
          <el-input v-model="editForm.discription"></el-input>
        </el-form-item>
      </el-form>
      <!-- 对话框底部确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEditGameInfo">Cancel</el-button>
        <el-button type="primary" @click="editGameInfo">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    const consoleOptions = ['PS3', 'PS4', 'PS Vita', 'PSP', 'Nintendo Switch', 'Nintendo 3DS', 'Xbox 360', 'Xbox one']

    // 验证价格的规则
    var checkPrice = (rule, value, cb) => {
      // 验证价格的正则表达式
      const regPrice = /^[0-9]+\.[0-9]{2}$/

      if (regPrice.test(value)) {
        // 合法的价格
        return cb()
      }

      cb(new Error('Please enter legal price, two decimal places after the decimal point'))
    }

    // 验证简介长度的规则
    var checkDiscription = (rule, value, cb) => {
      // 验证简介的正则表达式
      const regDiscription = /^.{0,200}$/

      if (regDiscription.test(value)) {
        // 合法的简介
        return cb()
      }

      cb(new Error('Please do not exceed 200 characters'))
    }

    return {
      // 获取游戏商品列表的参数对象
      queryInfo: {
        query: '',
        // 当前的页数
        pagenum: 1,
        // 当前每页显示多少条数据
        pagesize: 10
      },
      // 总数据条数
      total: 1,
      // 控制修改游戏对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的游戏信息对象
      editForm: {
        gameid: '12345',
        title: 'SUPERMARIO',
        price: '19.90',
        discount: 'On',
        average_rate: '4.8',
        release_date: '1985-09-13',
        pre_order: 'Off',
        rate_count: '100',
        discription: 'The game takes place in the Mushroom Kingdom, and Mario begins his new adventure in order to rescue Princess Peach, kidnapped by Kuba.',
        cover: '/tmp_uploads/30f08d52c551ecb447277eae232304b8',
        pictures: [
          '/tmp_uploads/30f08d52c551ecb447277eae232304b1',
          '/tmp_uploads/30f08d52c551ecb447277eae232304b2',
          '/tmp_uploads/30f08d52c551ecb447277eae232304b3'
        ],
        consoles: [
          'PS4',
          'PS Vita',
          'Xbox one'
        ],
        category: 'Adventure',
        tags: [
          'CARTOON',
          'POPULAR'
        ]
      },
      // 修改表单的验证规则对象
      editFormRules: {
        title: [
          { required: true, message: 'Please enter game name', trigger: 'blur' }
        ],
        price: [
          { required: true, message: 'Please enter game price', trigger: 'blur' },
          { validator: checkPrice, trigger: 'blur' }
        ],
        discription: [
          { validator: checkDiscription, trigger: 'blur' }
        ]
      },
      // 游戏商品列表
      gamelist: [
        {
          gameid: '12345',
          title: 'SUPERMARIO',
          price: '19.90',
          discount: 'On',
          average_rate: '4.8',
          release_date: '1985-09-13',
          pre_order: 'Off',
          rate_count: '100',
          discription: 'The game takes place in the Mushroom Kingdom, and Mario begins his new adventure in order to rescue Princess Peach, kidnapped by Kuba.',
          cover: '/tmp_uploads/30f08d52c551ecb447277eae232304b8',
          pictures: [
            '/tmp_uploads/30f08d52c551ecb447277eae232304b1',
            '/tmp_uploads/30f08d52c551ecb447277eae232304b2',
            '/tmp_uploads/30f08d52c551ecb447277eae232304b3'
          ],
          consoles: [
            'PS4',
            'PS Vita',
            'Xbox one'
          ],
          category: 'Adventure',
          tags: [
            'CARTOON',
            'POPULAR'
          ]
        }
      ],
      consoleOptions
    }
  },
  created () {
    this.getGameList()
  },
  methods: {
    // 根据分页获取对应的商品列表
    getGameList () { },
    // 监听 pagesize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getGameList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getGameList()
    },
    // 展示编辑游戏的对话框
    showEditDialog (gameid) {
      // 调用 API 接口，获取选中游戏商品的信息，存储到修改表格 editForm 中
      // // console.log(id)
      // const { data: res } = await this.$http.get('users/' + id)

      // if (res.meta.status !== 200) {
      //   return this.$message.error('查询用户信息失败！')
      // }

      console.log(this.editForm)
      this.editDialogVisible = true
    },
    // 监听修改游戏对话框的关闭事件
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 取消对游戏信息的修改
    cancelEditGameInfo () {
      // 关闭对话框
      this.editDialogVisible = false
      // 提示取消修改
      this.$message('Cancel edit game info')
    },
    // 修改游戏信息并提交
    editGameInfo () {
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
      this.getGameList()
      // 提示修改成功
      this.$message.success('Edit game info success')
    },
    // 根据 Id 删除对应的用户信息
    async removeGameById (gameid) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        'This operation will permanently delete the item, whether to continue?',
        'Alert',
        {
          confirmButtonText: 'confirm',
          cancelButtonText: 'cancel',
          type: 'warning'
        }
      ).catch(err => err)
      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('Already cancel the delete')
      }
      // 删除函数----------------------------------------
      // 提示删除成功
      console.log(gameid)
      this.$message.success('delete game successful')
      this.getGameList()
    }
  }
}
</script>

<style lang="less" scoped>
.tag_console {
  margin-right: 5px;
}
</style>
