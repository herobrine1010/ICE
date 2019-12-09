<template>
  <div class="main">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的购物车</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-table
        ref="multipleTable"
        :data="shoppingCartList"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"></el-table-column>
        <template slot-scope="scope">
          <el-table-column label="Cover">
            <!-- <el-image :src="scope.row.cover"></el-image> -->
            {{ scope.row.gamename }}
          </el-table-column>
        </template>
        <el-table-column label="Game" prop="gamename"></el-table-column>
        <el-table-column label="Console" prop="consolename"></el-table-column>
        <el-table-column label="Category" prop="category"></el-table-column>
        <el-table-column label="Price" prop="price"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default {
  data () {
    return {
      shoppingCartList: [{
        gamename: 'SuperMario',
        cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
        consolename: 'PS4',
        category: 'Adventure',
        price: '19.90'
      },
      {
        gamename: 'SuperMario',
        cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
        consolename: 'PS4',
        category: 'Adventure',
        price: '19.90'
      },
      {
        gamename: 'SuperMario',
        cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
        consolename: 'PS4',
        category: 'Adventure',
        price: '19.90'
      },
      {
        gamename: 'SuperMario',
        cover: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
        consolename: 'PS4',
        category: 'Adventure',
        price: '19.90'
      },
      {
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
    getShoppingCartList () { },
    // 多选
    toggleSelection (rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    handleChange (price, count, total) {
      total = price * count
      console.log(price)
      console.log(count)
      console.log(total)
      // total_price = value * price
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
