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
          <el-input placeholder="Please enter content" v-model="queryInfo.query" clearable>
            <el-button slot="append" icon="el-icon-search" @click="searchGames(queryInfo.query)"></el-button>
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
        <el-form-item label="Description" prop="description">
          <el-input v-model="editForm.description"></el-input>
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
// import qs from 'qs'
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
    var checkdescription = (rule, value, cb) => {
      // 验证简介的正则表达式
      const regdescription = /^.{0,200}$/

      if (regdescription.test(value)) {
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
        gameid: '',
        title: '',
        price: '',
        discount: '',
        pre_order: '',
        description: '',
        consoles: []
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
        description: [
          { validator: checkdescription, trigger: 'blur' }
        ]
      },
      // 游戏商品列表
      gamelist: [
        // {
        //   gameid: '',
        //   title: '',
        //   price: '',
        //   discount: '',
        //   average_rate: '',
        //   release_date: '',
        //   pre_order: '',
        //   rate_count: '',
        //   description: '',
        //   cover: '',
        //   pictures: [],
        //   consoles: [],
        //   category: 'Adventure',
        //   tags: []
        // }
      ],
      consoleOptions
    }
  },
  created () {
    this.getGamesNumber()
    this.getGameList()
  },
  methods: {
    // 获取游戏总数
    getGamesNumber() {
      this.$axios.get('/api/gameNumber')
        .then(response => {
          this.total = response.data.result[0]
        })
    },
    //  向gamelist中放入数据
    putDataIntoGameList(response) {
      for (let item in response.data.result) {
        this.gamelist.push({})
        this.gamelist[item].gameid = response.data.result[item].game_id
        this.gamelist[item].title = response.data.result[item].title
        this.gamelist[item].price = response.data.result[item].price
        if (response.data.result[item].discount === false) {
          this.gamelist[item].discount = 'Off'
        } else {
          this.gamelist[item].discount = 'On'
        }
        if (response.data.result[item].average_rate === -1) {
          this.gamelist[item].average_rate = '无评分'
        } else {
          this.gamelist[item].average_rate = response.data.result[item].average_rate
        }
        this.gamelist[item].release_date = response.data.result[item].release_date.split('T')[0]
        if (response.data.result[item].pre_order === false) {
          this.gamelist[item].pre_order = 'Off'
        } else {
          this.gamelist[item].pre_order = 'On'
        }
        this.gamelist[item].rate_count = response.data.result[item].rate_count
        this.gamelist[item].description = response.data.result[item].description
        this.gamelist[item].cover = response.data.result[item].cover
        this.gamelist[item].picture = response.data.result[item].picture
        this.gamelist[item].consoles = []
        for (let consolesItem in response.data.result[item].consoles) {
          this.gamelist[item].consoles.push('')
          this.gamelist[item].consoles[consolesItem] = response.data.result[item].consoles[consolesItem].consoleName
        }
        this.gamelist[item].category = response.data.result[item].category.cateName
        this.gamelist[item].tags = response.data.result[item].tags
      }
    },
    // 根据分页获取对应的商品列表
    getGameList () {
      this.gamelist = []
      this.$axios.get('/api/initGamesList', { params: { pageSize: this.queryInfo.pagesize } })
        .then(response => {
          // console.log(response)
          this.putDataIntoGameList(response)
          console.log(this.gamelist)
        })
    },
    // 页面跳转请求数据
    jumpPage (targetPage) {
      this.gamelist = []
      this.$axios.get('/api/jumpToGamePage', { params: { targetPage: targetPage, pageSize: this.queryInfo.pagesize } })
        .then(response => {
          console.log(response)
          this.putDataIntoGameList(response)
        })
    },
    // 搜索商品
    searchGames (query) {
      this.gamelist = []
      this.$axios.get('/api/searchPublishedGames', { params: { query: query, currentPage: 0, pageSize: this.queryInfo.pagesize } })
        .then(response => {
          console.log('search', response)
          this.putDataIntoGameList(response)
          this.getGamesNumber()
          console.log(this.gamelist)
        })
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      console.log(this.queryInfo.query)
      if (this.queryInfo.query === '') {
        this.getGamesNumber()
        this.getGameList()
      } else {
        console.log(this.queryInfo.query)
        this.searchGames(this.queryInfo.query)
        this.getGamesNumber()
      }
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.jumpPage(newPage - 1)
    },
    // eslint-disable-next-line camelcase
    getConsoleId(console_name) {
      // eslint-disable-next-line camelcase
      switch (console_name) {
        case 'PS3':
          return 1
        case 'PS4':
          return 2
        case 'PS Vita':
          return 3
        case 'PSP':
          return 4
        case 'Nintendo Switch':
          return 5
        case 'Nintendo 3DS':
          return 6
        case 'Xbox 360':
          return 7
        case 'Xbox one':
          return 8
      }
    },
    // 展示编辑游戏的对话框
    showEditDialog (gameid) {
      // 调用 API 接口，获取选中游戏商品的信息，存储到修改表格 editForm 中
      // // console.log(id)
      // const { data: res } = await this.$http.get('users/' + id)

      // if (res.meta.status !== 200) {
      //   return this.$message.error('查询用户信息失败！')
      // }
      console.log('show gamelist', this.gamelist)
      for (let index in this.gamelist) {
        if (this.gamelist[index].gameid === gameid) {
          this.editForm.gameid = this.gamelist[index].gameid
          this.editForm.title = this.gamelist[index].title
          this.editForm.price = this.gamelist[index].price
          this.editForm.discount = this.gamelist[index].discount
          this.editForm.pre_order = this.gamelist[index].pre_order
          this.editForm.description = this.gamelist[index].description
          console.log(this.editForm.description)
          this.editForm.consoles = this.gamelist[index].consoles
          console.log(this.editForm)
          break
        }
      }
      this.editForm.gameid = gameid
      this.editDialogVisible = true
    },
    // 监听修改游戏对话框的关闭事件
    editDialogClosed () {
      // this.$refs.editFormRef.resetFields()
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
      console.log('editForm', this.editForm)
      let editdata = {
        game_id: this.editForm.gameid,
        title: this.editForm.title,
        price: this.editForm.price,
        discount: false,
        pre_order: false,
        description: this.editForm.description,
        list_console_id: []
      }
      console.log('editdata', editdata)
      for (let index in this.editForm.consoles) {
        editdata.list_console_id.push(this.getConsoleId(this.editForm.consoles[index]))
      }
      if (this.editForm.discount === 'On') {
        editdata.discount = true
      }
      if (this.editForm.pre_order === 'On') {
        editdata.pre_order = true
      }
      this.$axios.post('/api/modifyGame', editdata)
        .then(resopnse => {
          if (resopnse.data.status === '200') {
            // 刷新数据列表
            this.getGameList()
            console.log('modifyGame', resopnse)
            // 提示修改成功
            this.$message.success('Edit game info success')
          } else {
            this.$message.error('Edit game info fail')
          }
        })
        .catch(() => {
          this.$message.error('Edit game info fail')
        })
      // 关闭对话框
      this.editDialogVisible = false
    },
    // 根据 Id 删除对应的用户信息
    async removeGameById (gameid) {
      // console.log(this.gamelist)
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
      // console.log(gameid)
      this.$axios.delete('/api/deleteGame', { params: { gameId: gameid } })
        .then(response => {
          if (response.data.status === '204') {
            this.$message.success('delete game successful')
            this.getGameList()
          } else {
            this.$message.success('delete game fail')
          }
        }
        )
        .catch(() => {
          this.$message.success('delete game fail')
        })
    }
  }
}
</script>

<style lang="less" scoped>
.tag_console {
  margin-right: 5px;
}
</style>
