<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row>
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable>
            <el-button slot="append" icon="el-icon-search" @click="searchOrders(queryInfo.query)"></el-button>
          </el-input>
        </el-col>
      </el-row>

      <!-- 订单列表数据 -->
      <el-table :data="orderList" stripe>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="Order ID">
                <span>{{ props.row.orderid }}</span>
              </el-form-item>
              <el-form-item label="Game">
                <span>{{ props.row.gamename }}</span>
              </el-form-item>
              <el-form-item label="Console">
                <span>{{ props.row.consolename }}</span>
              </el-form-item>
              <el-form-item label="Price">
                <span>{{ props.row.price }}</span>
              </el-form-item>
              <el-form-item label="Status">
                <span>{{ props.row.status }}</span>
              </el-form-item>
              <el-form-item label="Buyer">
                <span>{{ props.row.username }}</span>
              </el-form-item>
              <el-form-item label="Address">
                <span>{{ props.row.address }}</span>
              </el-form-item>
              <el-form-item label="Date">
                <span>{{ props.row.date }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
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
        <el-table-column label="Options">
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
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[5, 10, 15]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <!-- 修改订单信息的对话框 -->
    <el-dialog
      title="Edit Order Information"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="Order ID">
          <el-input v-model="editForm.orderid" disabled></el-input>
        </el-form-item>
        <el-form-item label="Price" prop="price">
          <el-input v-model="editForm.price"></el-input>
        </el-form-item>
        <el-form-item label="Address">
          <el-input v-model="editForm.address"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="contacttel">
          <el-input v-model="editForm.contacttel"></el-input>
        </el-form-item>
      </el-form>
      <!-- 对话框底部确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEditOrderInfo">Cancel</el-button>
        <el-button type="primary" @click="editOrderInfo">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
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
    // 验证手机号的规则
    var checkTelephone = (rule, value, callback) => {
      // 验证手机号的正则表达式
      const regTelephone = /^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$/

      if (regTelephone.test(value)) {
        // 合法的手机号
        return callback()
      }

      callback(new Error('Please enter legal telephone!'))
    }
    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      total: 1,
      orderList: [
        // {
        //   orderid: '47',
        //   gamename: 'SuperMario',
        //   consolename: 'PS4',
        //   price: '19.90',
        //   status: 0,
        //   username: 'budi',
        //   address: 'Tongji University',
        //   contacttel: '17717924664',
        //   date: '2019-11-28'
        // }
      ],
      editDialogVisible: false,
      editForm: {
        orderid: '',
        gamename: '',
        consolename: '',
        price: '',
        status: null,
        username: '',
        address: '',
        contacttel: '',
        date: ''
      },
      // 修改表单的验证规则对象
      editFormRules: {
        price: [
          { required: true, message: 'Please enter game price', trigger: 'blur' },
          { validator: checkPrice, trigger: 'blur' }
        ],
        contacttel: [
          { required: true, message: 'Please enter telephone', trigger: 'blur' },
          { validator: checkTelephone, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getOrderList()
    this.getOrdersNumber()
  },
  methods: {
    // 获取订单总数
    getOrdersNumber () {
      this.$axios.get('/api//orderNumber')
        .then(response => {
          console.log(response)
          this.total = response.data.result[0]
        })
    },
    // 向orderList中放入数据
    putDataIntoOrderList (response) {
      let result = response.data.result
      for (let index in result) {
        this.orderList.push({})
        this.orderList[index].orderid = result[index].order_id
        this.orderList[index].gamename = result[index].game_name
        this.orderList[index].consolename = result[index].console_name
        this.orderList[index].price = result[index].price
        this.orderList[index].status = result[index].status
        this.orderList[index].username = result[index].user_name
        this.orderList[index].address = result[index].address
        this.orderList[index].contacttel = result[index].contact_tel
        this.orderList[index].date = result[index].order_date.split('T')[0]
      }
    },
    // 根据分页获取对应的商品列表
    getOrderList () {
      this.orderList = []
      this.$axios.get('/api//initOrderList', { params: { pageSize: this.queryInfo.pagesize } })
        .then(response => {
          // console.log(response)
          this.putDataIntoOrderList(response)
        })
    },
    jumpPage (targetPage) {
      this.orderList = []
      this.$axios.get('/api/jumpToOrderPage', { params: { targetPage: targetPage, pageSize: this.queryInfo.pagesize } })
        .then(response => {
          console.log(response)
          this.putDataIntoOrderList(response)
        })
    },
    searchOrders (query) {
      this.orderList = []
      this.$axios.get('/api/searchOrder', { params: { query: query, currentPage: 0, pageSize: this.queryInfo.pagesize } })
        .then(response => {
          console.log(response)
          this.getOrdersNumber()
          this.putDataIntoOrderList(response)
        })
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      if (this.queryInfo.query === '') {
        this.getOrderList()
      } else {
        this.searchOrders(this.queryInfo.query)
      }
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.jumpPage(newPage - 1)
    },
    // 展示编辑订单的对话框
    showEditDialog (orderid) {
      for (let index in this.orderList) {
        if (orderid === this.orderList[index].orderid) {
          console.log(this.orderList[index])
          this.editForm.orderid = this.orderList[index].orderid
          this.editForm.gamename = this.orderList[index].gamename
          this.editForm.consolename = this.orderList[index].consolename
          this.editForm.price = this.orderList[index].price
          this.editForm.status = this.orderList[index].status
          this.editForm.username = this.orderList[index].username
          this.editForm.address = this.orderList[index].address
          this.editForm.contacttel = this.orderList[index].contacttel
          this.editForm.date = this.orderList[index].date
          console.log(this.editForm)
          break
        }
      }
      // 调用 API 接口，获取选中订单的信息，存储到修改表格 editForm 中
      // // console.log(id)
      // const { data: res } = await this.$http.get('orders/' + id)

      // if (res.meta.status !== 200) {
      //   return this.$message.error('查询订单信息失败！')
      // }
      this.editDialogVisible = true
    },
    // 监听修改订单对话框的关闭事件
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 取消对游戏信息的修改
    cancelEditOrderInfo () {
      // 关闭对话框
      this.editDialogVisible = false
      // 提示取消修改
      this.$message('Cancel edit order info')
    },
    // 修改订单信息并提交
    editOrderInfo () {
      this.$axios.get('/api/alterOrder', { params: { order_id: this.editForm.orderid, price: this.editForm.price, address: this.editForm.address, contact_tel: this.editForm.contacttel } })
        .then(response => {
          // 刷新数据列表
          this.getOrderList()
          if (response.data.status === '200') {
            // 提示修改成功
            this.$message.success('Edit order info success')
          } else {
            this.$message.error('Edit order info fail')
          }
        })
        .catch(() => {
          this.$message.error('Edit order info fail')
        })
      // 关闭对话框
      this.editDialogVisible = false
    },
    deliver (orderid) {
      console.log(orderid)
      this.$axios.get('/api/deliverOrder', { params: { order_id: orderid } })
        .then(response => {
          if (response.data.status === '200') {
            this.getOrderList()
            this.$message.success('Deliver order success')
          } else {
            this.$message.error('Deliver order fail')
          }
        }).catch(() => {
          this.$message.error('Deliver order fail')
        })

      // 调用 API 接口，获取选中订单的信息
      // // console.log(id)
      // const { data: res } = await this.$http.get('orders/' + id)

      // if (res.meta.status !== 200) {
      //   return this.$message.error('查询订单信息失败！')
      // }
      // res.data.status = 1
      // this.getOrderList()
    }
  }
}
</script>

<style lang="less" scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
