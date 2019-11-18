<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>权限列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <el-table :data="rightsList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="权限名称" prop="authName"></el-table-column>
        <el-table-column label="路径" prop="path"></el-table-column>
        <el-table-column label="权限等级" prop="level">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.level === '0'">一级</el-tag>
            <el-tag type="success" v-else-if="scope.row.level === '1'">二级</el-tag>
            <el-tag type="warning" v-else>三级</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 权限列表
      rightsList: [
        {
          id: '0',
          authName: '商品管理',
          path: 'goods',
          level: '0'
        },
        {
          id: '1',
          authName: '订单管理',
          path: 'orders',
          level: '0'
        },
        {
          id: '2',
          authName: '权限管理',
          path: 'rights',
          level: '0'
        },
        {
          id: '3',
          authName: '商品列表',
          path: 'goods',
          level: '1'
        },
        {
          id: '4',
          authName: '添加商品',
          path: 'goods',
          level: '2'
        },
        {
          id: '5',
          authName: '订单列表',
          path: 'orders',
          level: '1'
        },
        {
          id: '6',
          authName: '添加订单',
          path: 'orders',
          level: '2'
        },
        {
          id: '7',
          authName: '用户列表',
          path: 'users',
          level: '1 '
        }
      ]
    }
  },
  created() {
    // 获取所有的权限
    this.getRightsList()
  },
  methods: {
    // 获取权限列表
    getRightsList() {

    }
  }
}
</script>

<style lang="less" scoped>
</style>
