<template>
<div>
  <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
    <el-breadcrumb-item :to="{ path: '/MainIndex' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>分类</el-breadcrumb-item>
    <el-breadcrumb-item>{{cateName}}</el-breadcrumb-item>
  </el-breadcrumb>
  <Goods v-for="(i,index) in rowNumber" :goodsInfo="getGoodsInfo(index)"  :key="i" class="list-item"/>
</div>
</template>

<script>
import Goods from '../common/Goods'
export default {
  components: { Goods },
  data () {
    return {
      // 当前id
      categoryId: 0,
      // 当前分类
      cateName: '',
      // 每次加载的行数
      rowNumber: 0,
      // 商品列表
      goodsList: [],
      // 每次传给Goods组件的信息
      // 判断是否加载完所有游戏
      isAllGames: false
    }
  },
  mounted () {
    // 对整个页面滚轮进行监听，每发生一次滚轮事件，执行一次方法
    document.getElementById('main').addEventListener('scroll', this.handleScroll)
  },
  methods: {
    getCateName() {
      this.cateName = this.$router.currentRoute.params.name
    },
    getCategoryId() {
      console.log('categoryName:', this.$router.currentRoute.params.name)
      switch (this.$router.currentRoute.params.name) {
        case 'Action':
          this.categoryId = 1
          break
        case 'Adventure':
          this.categoryId = 2
          break
        case 'Arcade':
          this.categoryId = 3
          break
        case 'Board Games':
          this.categoryId = 4
          break
        case 'Racing':
          this.categoryId = 5
          break
        case 'Role-Playing Games':
          this.categoryId = 6
          break
        case 'Shooter':
          this.categoryId = 7
          break
        case 'Simulation':
          this.categoryId = 8
          break
        case 'Sports':
          this.categoryId = 9
          break
        case 'Strategy':
          this.categoryId = 10
          break
      }
    },
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
    searchGamesByCate(reset) {
      console.log('getCateGames')
      this.$axios.get('/api/searchGamesByCate', { params: { cateId: this.categoryId, reset: reset } })
        .then(response => {
          // console.log('getCateGames response', response)
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
    },
    getGames(reset) {
      console.log('getGames')
      this.$axios.get('/api/getGames', { params: { reset: reset } })
        .then(response => {
          // console.log('getGames response', response)
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
    this.getCateName()
    this.getCategoryId()
    this.isAllGames = false
    this.rowNumber = 0
    this.goodsList = []
    this.searchGamesByCate(true)
  },
  watch: {
    $route (newRouter, oldRouter) {
      // console.log(this.$route.path)
      // console.log(newRouter.path)
      // console.log(newRouter.path.indexOf('MainIndex'))
      this.getCateName()
      this.getCategoryId()
      this.isAllGames = false
      this.rowNumber = 0
      this.goodsList = []
      this.searchGamesByCate(true)
    }
  },
  destroyed () {
    // 移除监听
    document.getElementById('main').removeEventListener('scroll', this.handleScroll)
  }
}
</script>
<style scoped>

</style>
