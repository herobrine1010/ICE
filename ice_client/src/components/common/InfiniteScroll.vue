<template>
    <ul
      class="list"
      v-infinite-scroll="load"
      infinite-scroll-disabled="disabled"
      infinite-scroll-distance="10px">
      <Goods v-for="(i,index) in rowNumber" :goodsInfo="getGoodsInfo(index)"  :key="i" class="list-item"/>
    </ul>
</template>

<script>
import Goods from './Goods'
export default {
  components: { Goods },
  data () {
    return {
      // 每次加载的行数
      rowNumber: 0,
      loading: true,
      // 商品列表
      goodsList: [
        // { 'id': 1,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 2,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 3,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 4,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 5,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 6,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 7,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 8,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 9,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 10,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 11,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 12,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 13,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 14,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 15,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 16,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 17,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 18,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 19,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 20,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 21,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 22,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 23,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 24,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 25,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 26,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 27,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 28,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 29,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 30,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 31,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 32,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 33,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 34,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 35,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] },
        // { 'id': 36,
        //   'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        //   'name': '名称',
        //   'value': '￥20',
        //   'tag': ['标签1', '标签2'] }
      ],
      // 每次传给Goods组件的信息
      // 判断是否加载完所有游戏
      isAllGames: false
    }
  },
  computed: {
    noMore () {
      return this.isAllGames
    },
    disabled () {
      return this.loading || this.noMore
    }
  },
  methods: {
    getGoodsInfo (index) {
      let goodsInfo = []
      if ((index * 4 + 4) > this.goodsList.length) {
        for (let i in this.goodsList) {
          if (i >= index * 4) {
            goodsInfo.push(this.goodsList[i])
          }
        }
      } else {
        for (let i in this.goodsList) {
          if (i >= (index * 4) && i <= (index * 4 + 3)) {
            goodsInfo.push(this.goodsList[i])
          }
        }
      }
      return goodsInfo
    },
    getGames(reset) {
      console.log('getGames')
      this.$axios.get('/api/getGames', { params: { reset: reset } })
        .then(response => {
          console.log('getGames response', response)
          if (response.data.status === '200') {
            if (response.data.result === []) {
              console.log('加载完毕')
              this.isAllGames = true
            } else {
              for (let index in response.data.result) {
                let gameInfo = {
                  id: response.data.result[index].id,
                  imgSrc: response.data.result[index].cover_path,
                  name: response.data.result[index].title,
                  value: response.data.result[index].price,
                  tag: []
                }
                // 如果cover_path不存在，则替换成默认图片
                if (response.data.result[index].cover_path === null) {
                  gameInfo.imgSrc = 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg'
                }
                // 如果标签过多，截取前三个
                if (response.data.result[index].tags_list.length > 2) {
                  for (let i in response.data.result[index].tags_list) {
                    if (i >= 2) {
                      break
                    }
                    gameInfo.tag.push(response.data.result[index].tags_list[i])
                  }
                }
                this.goodsList.push(gameInfo)
              }
              this.loading = false
              this.rowNumber += 3
            }
          }
        })
    },
    load () {
      this.loading = true
      // TODO: 滚动到底部时请求数据，调用请求数据API
      console.log('滚动到底部')
      this.getGames(false)
    }
  },
  created () {
    this.rowNumber = 0
    this.goodsList = []
    this.getGames(true)
  }
}
</script>

<style scoped>
.infinite-list{
  height: 50%;
  overflow: auto;
}
</style>
