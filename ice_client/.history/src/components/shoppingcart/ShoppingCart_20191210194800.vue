<template>
  <div class="main">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的购物车</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-table :data="shoppingCartList" tooltip-effect="dark" style="width: 100%">
        <el-table-column label="Cover" prop="cover">
          <template slot-scope="scope">
            <el-image :src="scope.row.cover"></el-image>
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
      shoppingCartList: [{
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
      }
    }
  },
  created () {
  },
  methods: {
    // 根据分页获取对应的商品列表
    getShoppingCartList () { },
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
    // 修改游戏信息并提交
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
    deleteShopcart () {
      this.$alert('这是一段内容', '标题名称', {
        confirmButtonText: '确定',
        callback: action => {
          this.$message({
            type: 'info',
            message: `action: ${action}`
          })
        }
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
</style>
