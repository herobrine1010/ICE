<template>
  <el-dropdown size="small" trigger="click" @command="handleCommand">
  <el-button size="mini" type="info" plain>
    排序<i class="el-icon-arrow-down el-icon--right"></i>
  </el-button>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item command="a">按上架时间</el-dropdown-item>
      <el-dropdown-item command="b">按浏览量</el-dropdown-item>
      <el-dropdown-item command="c">按购买量</el-dropdown-item>
      <el-dropdown-item command="d">按价格升序</el-dropdown-item>
      <el-dropdown-item command="e">按价格降序</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  name: 'SortMenu',
  methods: {
    handleCommand (command) {
      this.$message('click on item ' + command)
    }
  }
}
</script>

<style scoped>
  .el-button{
    margin-top: 5px;
    margin-left: 25px;
    padding-left: 25px;
    padding-right: 25px;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #545c64;
  }
  li.el-dropdown-menu__item:hover{
    display: block;
    border: #545c64;
    color: #545c64;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>
