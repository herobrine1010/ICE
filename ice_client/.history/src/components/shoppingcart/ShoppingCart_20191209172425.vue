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
        <el-form-item label="Title">
          <!-- <el-input v-model="buyForm.title" disabled></el-input> -->
          {{ buyForm.title }}
        </el-form-item>
        <el-form-item label="Price">
          <!-- <el-input v-model="buyForm.price" disabled></el-input> -->
          ¥ {{ buyForm.price }}
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
        consoles: [],
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
