<template>
  <div class="main">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的订单</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片化的标签页 -->
    <el-tabs type="border-card">
      <el-tab-pane label="待发货">
        <!-- 订单列表数据 -->
        <el-table :data="orderList1" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="Order ID" prop="orderid"></el-table-column>
          <el-table-column label="Game" prop="gamename"></el-table-column>
          <el-table-column label="Price" prop="price"></el-table-column>
          <el-table-column label="Status" prop="status">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status === 0">payed</el-tag>
              <el-tag type="success" v-if="scope.row.status === 1">delivered</el-tag>
              <el-tag type="success" v-if="scope.row.status === 2">received</el-tag>
              <el-tag type="success" v-if="scope.row.status === 3">commented</el-tag>
              <el-tag type="success" v-if="scope.row.status === 4">completed</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Date" prop="date"></el-table-column>
          <!-- <el-table-column label="Options">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-edit"
                @click="showEditDialog(scope.row.orderid)"
              ></el-button>
              <el-button
                v-if="scope.row.status === 0"
                size="mini"
                type="success"
                icon="el-icon-truck"
                @click="deliver(scope.row.orderid)"
              ></el-button>
              <el-button
                v-if="scope.row.status !== 0"
                size="mini"
                type="info"
                icon="el-icon-truck"
                disabled
              ></el-button>
            </template>
          </el-table-column>-->
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-sizes="[5, 10, 15]"
          :page-size="queryInfo.pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total1"
        ></el-pagination>
      </el-tab-pane>
      <el-tab-pane label="待收货">
        <!-- 订单列表数据 -->
        <el-table :data="orderList2" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="Order ID" prop="orderid"></el-table-column>
          <el-table-column label="Game" prop="gamename"></el-table-column>
          <el-table-column label="Price" prop="price"></el-table-column>
          <el-table-column label="Status" prop="status">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status === 0">payed</el-tag>
              <el-tag type="success" v-if="scope.row.status === 1">delivered</el-tag>
              <el-tag type="success" v-if="scope.row.status === 2">received</el-tag>
              <el-tag type="success" v-if="scope.row.status === 3">commented</el-tag>
              <el-tag type="success" v-if="scope.row.status === 4">completed</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Date" prop="date"></el-table-column>
          <!-- <el-table-column label="Options">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-edit"
                @click="showEditDialog(scope.row.orderid)"
              ></el-button>
              <el-button
                v-if="scope.row.status === 0"
                size="mini"
                type="success"
                icon="el-icon-truck"
                @click="deliver(scope.row.orderid)"
              ></el-button>
              <el-button
                v-if="scope.row.status !== 0"
                size="mini"
                type="info"
                icon="el-icon-truck"
                disabled
              ></el-button>
            </template>
          </el-table-column>-->
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-sizes="[5, 10, 15]"
          :page-size="queryInfo.pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total2"
        ></el-pagination>
      </el-tab-pane>
      <el-tab-pane label="已收货">
        <!-- 订单列表数据 -->
        <el-table :data="orderList3" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="Order ID" prop="orderid"></el-table-column>
          <el-table-column label="Game" prop="gamename"></el-table-column>
          <el-table-column label="Price" prop="price"></el-table-column>
          <el-table-column label="Status" prop="status">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status === 0">payed</el-tag>
              <el-tag type="success" v-if="scope.row.status === 1">delivered</el-tag>
              <el-tag type="success" v-if="scope.row.status === 2">received</el-tag>
              <el-tag type="success" v-if="scope.row.status === 3">commented</el-tag>
              <el-tag type="success" v-if="scope.row.status === 4">completed</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Date" prop="date"></el-table-column>
          <!-- <el-table-column label="Options">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-edit"
                @click="showEditDialog(scope.row.orderid)"
              ></el-button>
              <el-button
                v-if="scope.row.status === 0"
                size="mini"
                type="success"
                icon="el-icon-truck"
                @click="deliver(scope.row.orderid)"
              ></el-button>
              <el-button
                v-if="scope.row.status !== 0"
                size="mini"
                type="info"
                icon="el-icon-truck"
                disabled
              ></el-button>
            </template>
          </el-table-column>-->
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-sizes="[5, 10, 15]"
          :page-size="queryInfo.pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total3"
        ></el-pagination>
      </el-tab-pane>
      <el-tab-pane label="已评价">
        <!-- 订单列表数据 -->
        <el-table :data="orderList4" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="Order ID" prop="orderid"></el-table-column>
          <el-table-column label="Game" prop="gamename"></el-table-column>
          <el-table-column label="Price" prop="price"></el-table-column>
          <el-table-column label="Status" prop="status">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status === 0">payed</el-tag>
              <el-tag type="success" v-if="scope.row.status === 1">delivered</el-tag>
              <el-tag type="success" v-if="scope.row.status === 2">received</el-tag>
              <el-tag type="success" v-if="scope.row.status === 3">commented</el-tag>
              <el-tag type="success" v-if="scope.row.status === 4">completed</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Date" prop="date"></el-table-column>
          <!-- <el-table-column label="Options">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-edit"
                @click="showEditDialog(scope.row.orderid)"
              ></el-button>
              <el-button
                v-if="scope.row.status === 0"
                size="mini"
                type="success"
                icon="el-icon-truck"
                @click="deliver(scope.row.orderid)"
              ></el-button>
              <el-button
                v-if="scope.row.status !== 0"
                size="mini"
                type="info"
                icon="el-icon-truck"
                disabled
              ></el-button>
            </template>
          </el-table-column> -->
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-sizes="[5, 10, 15]"
          :page-size="queryInfo.pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total4"
        ></el-pagination>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  data () {
    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      total1: 1,
      orderList1: [
        {
          orderid: '47',
          gamename: 'SuperMario',
          consolename: 'PS4',
          price: '19.90',
          status: 1,
          username: 'budi',
          address: 'Tongji University',
          contacttel: '17717924664',
          date: '2019-11-28'
        }
      ],
      total2: 1,
      orderList2: [
        {
          orderid: '47',
          gamename: 'SuperMario',
          consolename: 'PS4',
          price: '19.90',
          status: 2,
          username: 'budi',
          address: 'Tongji University',
          contacttel: '17717924664',
          date: '2019-11-28'
        }
      ],
      total3: 1,
      orderList3: [
        {
          orderid: '47',
          gamename: 'SuperMario',
          consolename: 'PS4',
          price: '19.90',
          status: 3,
          username: 'budi',
          address: 'Tongji University',
          contacttel: '17717924664',
          date: '2019-11-28'
        }
      ],
      total4: 1,
      orderList4: [
        {
          orderid: '47',
          gamename: 'SuperMario',
          consolename: 'PS4',
          price: '19.90',
          status: 4,
          username: 'budi',
          address: 'Tongji University',
          contacttel: '17717924664',
          date: '2019-11-28'
        }
      ]
    }
  },
  created () {
    this.getOrderList()
  },
  methods: {
    // 根据分页获取对应的商品列表
    getOrderList () { },
    // 监听 pagesize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getOrderList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getOrderList()
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
