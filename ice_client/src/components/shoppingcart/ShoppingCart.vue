<template>
  <div class="main">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item>Shopcart</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-table :data="shoppingCartList" tooltip-effect="dark" style="width: 100%">
        <el-table-column label="Cover" prop="cover">
          <template slot-scope="scope">
            <el-image :src="'http://localhost:8021'+scope.row.cover" class="cart-cover" @click.native="enterGoodsDetail(scope.row.gameid)"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="Game" prop="gamename"></el-table-column>
        <el-table-column label="Console" prop="consolename"></el-table-column>
        <el-table-column label="Category" prop="category"></el-table-column>
        <el-table-column label="Price" prop="price"></el-table-column>
        <el-table-column label="Options" width="130px">
          <template slot-scope="scope">
            <el-button
              type="warning"
              icon="el-icon-sell"
              size="mini"
              @click="showBuyDialog(scope.row.gameid)"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="deleteShopcart(scope.row.gameid)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 购买游戏的对话框 -->
    <el-dialog
      title="Buy Game"
      :visible.sync="buyDialogVisible"
      width="30%"
      @close="buyDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="buyForm" ref="buyFormRef" label-width="100px">
        <el-form-item label="Title">{{ buyForm.title }}</el-form-item>
        <el-form-item label="Console">{{ buyForm.consolename }}</el-form-item>
        <el-form-item label="Price">¥ {{ buyForm.price }}</el-form-item>
        <el-form-item label="Tel">
          <el-input v-model="userForm.tel"></el-input>
        </el-form-item>
        <el-form-item label="Address">
          <el-radio-group v-model="userForm.address" size="small">
            <el-radio-button v-for="con in userInfo.address" :label="con" :key="con">{{con}}</el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <!-- 对话框底部确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelBuyGame">Cancel</el-button>
        <el-button type="primary" @click="buyGame">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data () {
    return {
      shoppingCartList: [
        {
          gameid: '1',
          gamename: 'SuperMario',
          cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
          consolename: 'PS4',
          category: 'Adventure',
          price: '19.90'
        },
        {
          gameid: '1',
          gamename: 'SuperMario',
          cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
          consolename: 'PS4',
          category: 'Adventure',
          price: '19.90'
        },
        {
          gameid: '1',
          gamename: 'SuperMario',
          cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
          consolename: 'PS4',
          category: 'Adventure',
          price: '19.90'
        },
        {
          gameid: '1',
          gamename: 'SuperMario',
          cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
          consolename: 'PS4',
          category: 'Adventure',
          price: '19.90'
        },
        {
          gameid: '1',
          gamename: 'SuperMario',
          cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
          consolename: 'PS4',
          category: 'Adventure',
          price: '19.90'
        }],
      multipleSelection: [],
      // 购买游戏按钮弹出的对话框-----------------------------------------------------------------------------------
      // 控制购买游戏对话框的显示与隐藏
      buyDialogVisible: false,
      // 查询到的游戏信息对象
      buyForm: {
        id: '',
        title: '',
        price: '',
        consolename: '',
        category: ''
      },
      // 用户地址信息与地址选择
      userInfo: {
        address: []
      },
      // 用户选择的地址信息
      userForm: {
        tel: '',
        address: ''
      }
    }
  },
  created () {
    this.getShoppingCartList()
    this.getUserAddress()
  },
  methods: {
    // 根据分页获取对应的商品列表
    getShoppingCartList () {
      this.shoppingCartList = []
      this.$axios.get('/api/getMyCart')
        .then(response => {
          console.log('getMyChart')
          console.log(response)
          for (let index in response.data.result) {
            let cart = {
              gameid: response.data.result[index].gameId,
              gamename: response.data.result[index].title,
              cover: response.data.result[index].coverPath,
              consolename: response.data.result[index].consoleName,
              category: response.data.result[index].cateName,
              price: response.data.result[index].price
            }
            if (response.data.result[index].coverPath === null) {
              // cart.cover = 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg'
              cart.cover = '/images/default/default_cover.jpg'
            }
            this.shoppingCartList.push(cart)
          }
        })
    },
    enterGoodsDetail (goodsId) {
      // window.sessionStorage.setItem('activePath', '/GoodsDetail')
      this.$router.push('/GoodsDetail/' + goodsId + '')
    },
    // 购买游戏按钮相关------------------------------------------------------------------------
    // 获取当前选择的购物车项的游戏信息
    getCurrentCartGameInfo(gameId) {
      for (let item of this.shoppingCartList) {
        if (item.gameid === gameId) {
          this.buyForm.id = gameId
          this.buyForm.title = item.gamename
          this.buyForm.consolename = item.consolename
          this.buyForm.price = item.price
        }
      }
    },
    // 展示购买的对话框
    showBuyDialog (gameId) {
      this.getCurrentCartGameInfo(gameId)
      this.buyDialogVisible = true
    },
    // 监听修改游戏对话框的关闭事件
    buyDialogClosed () {
      // this.$refs.buyFormRef.resetFields()
    },
    // 取消对游戏的购买
    cancelBuyGame () {
      // 关闭对话框
      this.buyDialogVisible = false
      // 提示取消修改
      this.$message('Cancel buy game')
    },
    // 获得地址
    getUserAddress() {
      this.$axios.post('/api/getAddress')
        .then(response => {
          console.log('address', response)
          for (let index in response.data.result) {
            this.userInfo.address.push(response.data.result[index])
          }
        })
    },
    getPlatFormId(consoleName) {
      switch (consoleName) {
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
        case 'xbox 360':
          return 7
        case 'xbox one':
          return 8
      }
    },
    // 购买游戏
    buyGame () {
      // !!!!!!!!!!!!!!!!!!!!!!!在这里需要添加一个验证，验证是否已经单选游戏平台，若未选择，前端报错，不可提交，示例代码如下注释!!!!!!!!!!!!!!!!!!!!!!
      // if (this.buyForm.consoles === '') {
      //   return this.$message.error('Please choose game consoles')
      // }
      // console.log(this.buyForm.consoles)
      console.log('create order')
      let order = {
        address: this.userForm.address,
        consoleId: this.getPlatFormId(this.buyForm.consolename),
        contactTel: this.userForm.tel,
        gameId: this.buyForm.id,
        price: this.buyForm.price
      }
      console.log(this.buyForm.consolename)
      console.log('cid')
      console.log(order.consoleId)
      this.$axios.post('/api/createOrder', order)
        .then(response => {
          console.log(response)
          if (response.data.status === '200') {
            this.$axios.get('/api/removeFromCart', { params: { gameId: this.buyForm.id } })
              .then(response => {
                if (response.data.status === '200') {
                  this.getShoppingCartList()
                }
              })
            this.$message.success('Buy game success')
          } else {
            this.$message.error('Buy game  fail')
          }
        })
        .catch(() => {
          this.$message.error('Buy game fail')
        })
      // 关闭对话框
      this.buyDialogVisible = false
    },
    // 删除游戏信息并提交
    deleteShopcart (gameid) {
      this.$confirm('This operation will permanently delete the game, do you want to continue?', 'Prompt', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        this.$axios.get('/api/removeFromCart', { params: { gameId: gameid } })
          .then(response => {
            if (response.data.status === '200') {
              this.getShoppingCartList()
              this.$message({
                type: 'success',
                message: 'Delete success'
              })
            } else {
              this.$message.error('Delete fail')
            }
          })
          .catch(() => {
            this.$message.error('Delete fail')
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
.cart-cover {
  cursor:pointer;
  height: 100px;
  width: 100px;
}
</style>
