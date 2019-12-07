<template>
  <div class="main">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的购物车</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <template>
        <el-table
          ref="multipleTable"
          :data="shoppingCartList"
          tooltip-effect="dark"
          style="width: 100%"
          show-summary
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection"></el-table-column>
          <el-table-column prop="gamename" label="Game"></el-table-column>
          <el-table-column label="Price">
            <template slot-scope="scope">
              {{ scope.row.price }}
            </template>
          </el-table-column>
          <el-table-column label="Count">
            <template slot-scope="scope">
              <el-input-number
                v-model="scope.row.count"
                @change="handleChange(scope.row.price, scope.row.count, scope.row.total_price)"
                :min="1"
                :max="5"
              ></el-input-number>
            </template>
          </el-table-column>
          <el-table-column prop="total_price" label="Total"></el-table-column>
        </el-table>
      </template>
    </el-card>
  </div>
</template>
<script>
export default {
  data () {
    return {
      shoppingCartList: [{
        gamename: 'SuperMario',
        price: '19.90',
        count: 3,
        total_price: '19.90'
      },
      {
        gamename: 'SuperMario',
        price: '19.90',
        count: 1,
        total_price: '19.90'
      },
      {
        gamename: 'SuperMario',
        price: '19.90',
        count: 1,
        total_price: '19.90'
      },
      {
        gamename: 'SuperMario',
        price: '19.90',
        count: 1,
        total_price: '19.90'
      },
      {
        gamename: 'SuperMario',
        price: '19.90',
        count: 1,
        total_price: '19.90'
      },
      {
        gamename: 'SuperMario',
        price: '19.90',
        count: 1,
        total_price: '19.90'
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
