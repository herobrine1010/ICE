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
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.gameid)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
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
      multipleSelection: []
    }
  },
  created () {
  },
  methods: {
    // 根据分页获取对应的商品列表
    getShoppingCartList () { }
  }
}
</script>

<style lang="less" scoped>
.main {
  margin-left: 10%;
  margin-right: 10%;
}
</style>
