<template>
<div>
  <BreadCrumb :breadcrumbItemList="breadcrumbItemList"/>
  <el-row>
    <el-col :span="12" class="goods-image">
  <Carousel :carouselImgUrl="carouselImgUrl"/>
  </el-col>
  <el-col :span="12">
<el-row></el-row>
  </el-col>
  </el-row>
<h1 v-for="i in 30" :key="i">GoodsDetail</h1>
</div>
</template>

<script>
import BreadCrumb from './BreadCrumb'
import Carousel from './Carousel'
import { mapGetters } from 'vuex'
export default {
  name: 'GoodsDetail',
  components: { BreadCrumb, Carousel },
  data () {
    return {
      breadcrumbItemList: ['类别一'],
      carouselImgUrl: [
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg'
      ]
    }
  },
  computed: {
    ...mapGetters([
      'goodsList'
    ])
  }
}
</script>

<style scoped>
.goods-image{
  padding-left: 18%;
  padding-right: 5%;
}
</style>
