<template>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: '/MainIndex' }">Home</el-breadcrumb-item>
    <el-breadcrumb-item v-for="item in breadcrumbItemList" :key="item">{{item}}</el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
export default {
  name: 'BreadCrumb',
  props: ['breadcrumbItemList']
}
</script>

<style scoped>

</style>
