<template>
  <div>
    <div class="carouse-img">
    <Carousel :carouselImgUrl="carouselImgUrl"/>
    </div>
    <SortMenu/>
    <Goods v-for="(i,index) in rowNumber" :goodsInfo="getGoodsInfo(index)"  :key="i" class="list-item"/>
  </div>
</template>

<script>
import Carousel from '../common/Carousel'
import SortMenu from '../common/SortMenu'
import Goods from '../common/Goods'
export default {
  components: { Carousel, SortMenu, Goods },
  data () {
    return {
      // 每次加载的行数
      rowNumber: 0,
      // 商品列表
      goodsList: [],
      // 每次传给Goods组件的信息
      // 判断是否加载完所有游戏
      isAllGames: false,
      carouselImgUrl: [
        'http://localhost:8021/images/main_page/1.jpg',
        'http://localhost:8021/images/main_page/2.jpg',
        'http://localhost:8021/images/main_page/3.jpg'
      ]
    }
  },
  mounted () {
    // 对整个页面滚轮进行监听，每发生一次滚轮事件，执行一次方法
    document.getElementById('main').addEventListener('scroll', this.handleScroll)
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
      console.log('sortGames')
      this.$axios.get('/api/getGames', { params: { reset: reset } })
        .then(response => {
          console.log('getGames response', response)
          if (response.data.status === '200') {
            // console.log('response.data.result', response.data.result)
            if (response.data.result.length === 0) {
              console.log('加载完毕')
              this.isAllGames = true
            } else {
              this.rowNumber += 3
              console.log('rowNumber', this.rowNumber)
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
                  gameInfo.imgSrc = '/images/default/default_cover.jpg'
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
            }
          }
        })
    },
    getSortGames() {
      this.$axios.get('/api/getGames', { params: { reset: true } })
        .then(() => {
          console.log('getSortGames')
          this.$axios.get('/api/sortGames', { params: { flag: this.$router.currentRoute.params.sortInfo } })
            .then(response => {
              console.log('getSortGames response', response)
              if (response.data.status === '200') {
                // console.log('response.data.result', response.data.result)
                if (response.data.result.length === 0) {
                  console.log('加载完毕')
                  this.isAllGames = true
                } else {
                  this.goodsList = []
                  this.rowNumber += 3
                  console.log('rowNumber', this.rowNumber)
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
                      gameInfo.imgSrc = '/images/default/default_cover.jpg'
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
                }
              }
            })
        })
    },
    // 滚动事件触发函数
    handleScroll () {
      if (this.isAllGames) {
        return
      }
      // 兼容性，获取页面滚动距离
      // console.log('滚动')
      // document.getElementById('main').scrollTop 当前页面滚动距离
      // document.body.scrollHeight 页面高度
      // document.getElementById('main').scrollHeight 滚动区域高度
      let scrollLength = document.getElementById('main').scrollTop + document.body.scrollHeight + 1
      // console.log(scrollLength)
      // console.log(document.getElementById('main').scrollHeight)
      // 判断是否滚动到底部
      if (scrollLength >= document.getElementById('main').scrollHeight) {
        console.log('滚动到底部')
        // TODO:滚动到底部，调用请求商品API
        this.getGames(false)
      }
    }
  },
  created () {
    this.isAllGames = false
    this.rowNumber = 0
    this.goodsList = []
    this.getSortGames()
  },
  watch: {
    $route (newRouter, oldRouter) {
      // console.log(this.$route.path)
      // console.log(newRouter.path)
      // console.log(newRouter.path.indexOf('MainIndex'))
      this.isAllGames = false
      this.rowNumber = 0
      this.goodsList = []
      this.getSortGames()
    }
  },
  destroyed () {
    // 移除监听
    document.getElementById('main').removeEventListener('scroll', this.handleScroll)
  }
}
</script>
<style scoped>
  .carouse-img{
    padding-left: 8%;
    padding-right: 12%;
  }
</style>
