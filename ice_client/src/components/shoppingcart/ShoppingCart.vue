<template>
  <div class="main">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的购物车</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-table :data="shoppingCartList" tooltip-effect="dark" style="width: 100%">
        <el-table-column label="Cover" prop="cover" >
          <template slot-scope="scope">
            <el-image :src="scope.row.cover" class="cart-cover"></el-image>
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
        title: 'SUPERMARIO',
        price: '19.90',
        consolename: 'PS4',
        category: 'Adventure'
      },
      // 用户地址信息与地址选择
      userInfo: {
        address: [
          '上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区1',
          '上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区2',
          '上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区3',
          '上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区4'
        ]
      },
      // 用户选择的地址信息
      userForm: {
        address: []
      }
    }
  },
  created () {
    this.getShoppingCartList()
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
              cart.cover = 'http://ww1.sinaimg.cn/large/007oltUXly1g9rveswx8mj306o06ot94.jpg'
            }
            this.shoppingCartList.push(cart)
          }
        })
    },
    // 购买游戏按钮相关------------------------------------------------------------------------
    // 展示编辑游戏的对话框
    showBuyDialog () {
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
    // 购买游戏
    buyGame () {
      // !!!!!!!!!!!!!!!!!!!!!!!在这里需要添加一个验证，验证是否已经单选游戏平台，若未选择，前端报错，不可提交，示例代码如下注释!!!!!!!!!!!!!!!!!!!!!!
      // if (this.buyForm.consoles === '') {
      //   return this.$message.error('Please choose game consoles')
      // }
      // console.log(this.buyForm.consoles)
      // 关闭对话框
      this.buyDialogVisible = false
      // 刷新数据列表
      // this.getGameList()
      // 提示修改成功
      this.$message.success('Buy game success')
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
  .cart-cover{
    height: 100px;
    width: 100px;
  }
</style>
