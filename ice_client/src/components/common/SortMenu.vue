<template>
  <el-dropdown size="small" trigger="click" @command="handleCommand">
  <el-button size="mini" type="info" plain>
    Sort<i class="el-icon-arrow-down el-icon--right"></i>
  </el-button>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item :command=1>Default</el-dropdown-item>
      <el-dropdown-item :command=2>Price Descending</el-dropdown-item>
      <el-dropdown-item :command=3>Price Ascending</el-dropdown-item>
      <el-dropdown-item :command=4>Rate Descending</el-dropdown-item>
      <el-dropdown-item :command=5>Rate Ascending</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  name: 'SortMenu',
  methods: {
    handleCommand (command) {
      console.log('sort')
      this.$router.push('/MainIndex/' + command)
      // this.$message('click on item ' + command)
    }
  }
}
</script>

<style scoped>
  .el-button{
    /*margin-top: 5px;*/
    margin-bottom: 10px;
    margin-left: 125px;
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
