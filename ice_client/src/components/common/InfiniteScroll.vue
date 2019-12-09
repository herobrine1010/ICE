<template>
    <ul
      class="list"
      v-infinite-scroll="load"
      infinite-scroll-disabled="disabled"
      infinite-scroll-distance="10px">
      <Goods v-for="(i,index) in rowNumber" :goodsInfo="[goodsList[index*4], goodsList[index*4+1], goodsList[index*4+2], goodsList[index*4 + 3]]"  :key="i" class="list-item"/>
    </ul>
</template>

<script>
import Goods from './Goods'
export default {
  components: { Goods },
  data () {
    return {
      // 每次加载的行数
      rowNumber: 2,
      loading: false,
      // 商品列表
      goodsList: [
        { 'id': 1,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 2,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 3,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 4,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 5,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 6,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 7,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 8,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 9,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 10,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 11,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 12,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 13,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 14,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 15,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 16,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 17,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 18,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 19,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 20,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 21,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 22,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 23,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 24,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 25,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 26,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 27,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 28,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 29,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 30,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 31,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 32,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 33,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 34,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 35,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] },
        { 'id': 36,
          'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
          'name': '名称',
          'value': '￥20',
          'tag': ['标签1', '标签2'] }
      ],
      goodsNumber: 0
    }
  },
  computed: {
    noMore () {
      return this.goodsList.length >= this.goodsNumber
    },
    disabled () {
      return this.loading || this.noMore
    }
  },
  methods: {
    // TODO:获取商品总数
    getGoodsNumber() {

    },
    load () {
      this.loading = true
      // TODO: 滚动到底部时请求数据，调用请求数据API
      setTimeout(() => {
        this.rowNumber += 2
        this.loading = false
      }, 500)
    }
  }
}
</script>

<style scoped>
.infinite-list{
  height: 50%;
  overflow: auto;
}
</style>
