<template>
  <div class="main">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item>My Order</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片化的标签页 -->
    <el-tabs type="border-card">
      <el-tab-pane label="To be delivered">
        <!-- 订单列表数据 -->
        <el-table :data="orderList0" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="Order ID" prop="orderid"></el-table-column>
          <el-table-column label="Game" prop="gamename"></el-table-column>
          <el-table-column label="Price" prop="price"></el-table-column>
          <el-table-column label="Status" prop="status">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status === 0">payed</el-tag>
              <el-tag type="warning" v-if="scope.row.status === 1">delivered</el-tag>
              <el-tag type="primary" v-if="scope.row.status === 2">received</el-tag>
              <el-tag type="success" v-if="scope.row.status === 3">commented</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Date" prop="date"></el-table-column>
          <el-table-column label="Options">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="info"
                icon="el-icon-star-off"
                @click="showScoreDialog(scope.row.orderid)"
                disabled
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-size="10"
          layout="prev, pager, next, jumper"
          :total="total0"
        ></el-pagination>
      </el-tab-pane>
      <el-tab-pane label="To be received">
        <!-- 订单列表数据 -->
        <el-table :data="orderList1" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="Order ID" prop="orderid"></el-table-column>
          <el-table-column label="Game" prop="gamename"></el-table-column>
          <el-table-column label="Price" prop="price"></el-table-column>
          <el-table-column label="Status" prop="status">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status === 0">payed</el-tag>
              <el-tag type="warning" v-if="scope.row.status === 1">delivered</el-tag>
              <el-tag type="primary" v-if="scope.row.status === 2">received</el-tag>
              <el-tag type="success" v-if="scope.row.status === 3">commented</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Date" prop="date"></el-table-column>
          <el-table-column label="Options">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-check"
                @click="confirm(scope.row.orderid)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-size="10"
          layout="prev, pager, next, jumper"
          :total="total1"
        ></el-pagination>
      </el-tab-pane>
      <el-tab-pane label="To be commented">
        <!-- 订单列表数据 -->
        <el-table :data="orderList2" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="Order ID" prop="orderid"></el-table-column>
          <el-table-column label="Game" prop="gamename"></el-table-column>
          <el-table-column label="Price" prop="price"></el-table-column>
          <el-table-column label="Status" prop="status">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status === 0">payed</el-tag>
              <el-tag type="warning" v-if="scope.row.status === 1">delivered</el-tag>
              <el-tag type="primary" v-if="scope.row.status === 2">received</el-tag>
              <el-tag type="success" v-if="scope.row.status === 3">commented</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Date" prop="date"></el-table-column>
          <el-table-column label="Options">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-star-off"
                @click="showScoreDialog(scope.row.orderid, scope.row.gameId)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-size="10"
          layout="prev, pager, next, jumper"
          :total="total2"
        ></el-pagination>
      </el-tab-pane>
      <el-tab-pane label="Finished">
        <!-- 订单列表数据 -->
        <el-table :data="orderList3" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="Order ID" prop="orderid"></el-table-column>
          <el-table-column label="Game" prop="gamename"></el-table-column>
          <el-table-column label="Price" prop="price"></el-table-column>
          <el-table-column label="Status" prop="status">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.status === 0">payed</el-tag>
              <el-tag type="warning" v-if="scope.row.status === 1">delivered</el-tag>
              <el-tag type="primary" v-if="scope.row.status === 2">received</el-tag>
              <el-tag type="success" v-if="scope.row.status === 3">commented</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Date" prop="date"></el-table-column>
          <el-table-column label="Options">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="info"
                icon="el-icon-star-off"
                @click="showScoreDialog(scope.row.orderid)"
                disabled
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页区域 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pagenum"
          :page-size="10"
          layout="prev, pager, next, jumper"
          :total="total3"
        ></el-pagination>
      </el-tab-pane>
    </el-tabs>

    <!-- 打分订单的对话框 -->
    <el-dialog
      title="Score Order"
      :visible.sync="scoreDialogVisible"
      width="50%"
      @close="scoreDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="scoreForm" :rules="scoreFormRules" ref="scoreFormRef" label-width="100px">
        <el-form-item label="Rate">
          <el-rate v-model="scoreForm.score" :colors="colors"></el-rate>
        </el-form-item>
        <el-form-item label="Comment" prop="comment">
          <el-input v-model="scoreForm.comment"></el-input>
        </el-form-item>
      </el-form>
      <!-- 对话框底部确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelScoreOrder">Cancel</el-button>
        <el-button type="primary" @click="scoreOrder">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    // 验证评论长度的规则
    var checkComment = (rule, value, cb) => {
      // 验证简介的正则表达式
      const regComment = /^.{0,200}$/

      if (regComment.test(value)) {
        // 合法的简介
        return cb()
      }

      cb(new Error('Please do not exceed 200 characters'))
    }
    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      total0: 1,
      orderList0: [
        // {
        //   orderid: '47',
        //   gamename: 'SuperMario',
        //   consolename: 'PS4',
        //   price: '19.90',
        //   status: 0,
        //   username: 'budi',
        //   address: 'Tongji University',
        //   contacttel: '17717924664',
        //   date: '2019-11-28',
        //   gameId: 0
        // }
      ],
      total1: 1,
      orderList1: [
        // {
        //   orderid: '47',
        //   gamename: 'SuperMario',
        //   consolename: 'PS4',
        //   price: '19.90',
        //   status: 1,
        //   username: 'budi',
        //   address: 'Tongji University',
        //   contacttel: '17717924664',
        //   date: '2019-11-28',
        //   gameId: 0
        // }
      ],
      total2: 1,
      orderList2: [
        // {
        //   orderid: '47',
        //   gamename: 'SuperMario',
        //   consolename: 'PS4',
        //   price: '19.90',
        //   status: 2,
        //   username: 'budi',
        //   address: 'Tongji University',
        //   contacttel: '17717924664',
        //   date: '2019-11-28'
        // }
      ],
      total3: 1,
      orderList3: [
        // {
        //   orderid: '47',
        //   gamename: 'SuperMario',
        //   consolename: 'PS4',
        //   price: '19.90',
        //   status: 3,
        //   username: 'budi',
        //   address: 'Tongji University',
        //   contacttel: '17717924664',
        //   date: '2019-11-28'
        // }
      ],
      scoreDialogVisible: false,
      scoreForm: {
        score: null,
        comment: ''
      },
      // 当前打分选择对象
      gameId: 0,
      // 当前打分选择的订单id
      orderId: 0,
      // 打分星星的颜色变化
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      // 打分表单的验证规则对象
      scoreFormRules: {
        comment: [
          { required: true, message: 'Please enter comment', trigger: 'blur' },
          { validator: checkComment, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getOrderList()
  },
  methods: {
    // 根据分页获取对应的商品列表
    getOrderList () {
      this.orderList0 = []
      this.orderList1 = []
      this.orderList2 = []
      this.orderList3 = []
      this.$axios.get('/api/initAllOrderList')
        .then(response => {
          console.log(response)
        })
      this.$axios.get('/api/getOrderList', { params: { status: 0 } })
        .then(response => {
          console.log('orderList0', response)
          for (let index in response.data.result) {
            let orderInfo = {
              orderid: response.data.result[index].order_id,
              gamename: response.data.result[index].game_name,
              consolename: response.data.result[index].console.consoleName,
              price: response.data.result[index].price,
              status: response.data.result[index].status,
              username: response.data.result[index].user_name,
              address: response.data.result[index].address,
              contacttel: response.data.result[index].contact_tel,
              date: this.$moment(response.data.result[index].order_date).format('ll'),
              gameId: response.data.result[index].game_id
            }
            this.orderList0.push(orderInfo)
          }
        })
      this.$axios.get('/api/getOrderList', { params: { status: 1 } })
        .then(response => {
          console.log('orderList1', response)
          for (let index in response.data.result) {
            let orderInfo = {
              orderid: response.data.result[index].order_id,
              gamename: response.data.result[index].game_name,
              consolename: response.data.result[index].console.consoleName,
              price: response.data.result[index].price,
              status: response.data.result[index].status,
              username: response.data.result[index].user_name,
              address: response.data.result[index].address,
              contacttel: response.data.result[index].contact_tel,
              date: this.$moment(response.data.result[index].order_date).format('ll'),
              gameId: response.data.result[index].game_id
            }
            this.orderList1.push(orderInfo)
          }
        })
      this.$axios.get('/api/getOrderList', { params: { status: 2 } })
        .then(response => {
          console.log('orderList2', response)
          for (let index in response.data.result) {
            let orderInfo = {
              orderid: response.data.result[index].order_id,
              gamename: response.data.result[index].game_name,
              consolename: response.data.result[index].console.consoleName,
              price: response.data.result[index].price,
              status: response.data.result[index].status,
              username: response.data.result[index].user_name,
              address: response.data.result[index].address,
              contacttel: response.data.result[index].contact_tel,
              date: this.$moment(response.data.result[index].order_date).format('ll'),
              gameId: response.data.result[index].game_id
            }
            this.orderList2.push(orderInfo)
          }
        })
      this.$axios.get('/api/getOrderList', { params: { status: 3 } })
        .then(response => {
          console.log('orderList3', response)
          for (let index in response.data.result) {
            let orderInfo = {
              orderid: response.data.result[index].order_id,
              gamename: response.data.result[index].game_name,
              consolename: response.data.result[index].console.consoleName,
              price: response.data.result[index].price,
              status: response.data.result[index].status,
              username: response.data.result[index].user_name,
              address: response.data.result[index].address,
              contacttel: response.data.result[index].contact_tel,
              date: this.$moment(response.data.result[index].order_date).format('ll'),
              gameId: response.data.result[index].game_id
            }
            this.orderList3.push(orderInfo)
          }
        })
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getOrderList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getOrderList()
    },
    // 展示评价对话框
    showScoreDialog (orderId, gameId) {
      this.gameId = 0
      this.orderId = orderId
      this.gameId = gameId
      this.scoreDialogVisible = true
    },
    // 监听打分订单对话框的关闭事件
    scoreDialogClosed () { },
    // 取消对订单的打分
    cancelScoreOrder () {
      // 关闭对话框
      this.scoreDialogVisible = false
      // 提示取消修改
      this.$message('Cancel score order')
    },
    // 打分订单并提交
    scoreOrder () {
      // —————————————————————————————————连接时需要的工作———————————————————————————————————
      // 1. 将打分和评论数据传入后端
      // 2. 更新订单状态 status 为 3（已完成）
      // 3. 更新订单列表显示状态
      // —————————————————————————————————————————————————————————————————————————————————
      this.$axios.get('/api/submitRate', { params: { gameId: this.gameId, rate: this.scoreForm.score } })
        .then(response => {
          console.log('rate', response)
          if (response.data.status === '200') {
            let comment = {
              gameId: this.gameId,
              content: this.scoreForm.comment,
              orderId: this.orderId
            }
            console.log(comment)
            this.$axios.post('/api/addComment', comment)
              .then(response => {
                if (response.data.status === '200') {
                  // 关闭对话框
                  this.scoreDialogVisible = false
                  // 刷新数据列表
                  this.getOrderList()
                  this.$message.success('Score order success')
                } else {
                  this.$message.error('You have commented on this game!')
                  // 关闭对话框
                  this.scoreDialogVisible = false
                }
              })
          } else {
            this.$message.error('You have commented on this game!')
          }
        })
        .catch(() => {
          this.$message.success('Score order success')
        })
    },
    // 收货
    confirm (orderId) {
      console.log(orderId)
      this.$axios.get('/api/confirmOrder', { params: { orderId: orderId } })
        .then(response => {
          console.log(response)
          this.getOrderList()
          this.$message.success('order confirmed!')
        })
        .catch(() => {
          this.$message.error('network error!')
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
.el-rate {
  margin-top: 1.5%;
}
</style>
