<template>
<div style="overflow: auto; height: 100%" id="main">
<!--  <div>-->
  <el-header height="120px">
    <el-row >
      <el-col :span="1"><div class="grid-content"></div></el-col>
      <el-col :span="6"><div class="grid-content">
        <img src="../assets/header/home_logo.jpg" width="374" height="120" alt/>
      </div></el-col>
      <el-col :span="17"><div class="grid-content bg-purple"></div></el-col>
    </el-row>
  </el-header>
  <el-menu
    id="main-menu-id"
    :default-active="activeIndex2"
    :class="headerFixed?'isFixed':''"
    mode="horizontal"
    @select="handleSelect"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#409EFE">
    <!-- 一级菜单 -->
    <el-submenu :index="item.id" v-for="(item, i) in menuList" :key="i">
      <!-- 一级菜单 -->
      <template slot="title">
        <span>{{ item.authName }}</span>
      </template>
      <!-- 二级菜单 -->
      <el-menu-item
        :index="subItem.id"
        v-for="(subItem, i) in item.children"
        :key="i"
      >
        <template slot="title">
          <span>{{ subItem.authName }}</span>
        </template>
      </el-menu-item>
    </el-submenu>
    <el-menu-item class="search-menu" index="4" >
    <el-input class="search-input" placeholder="请输入内容">
      <el-button  @click="search" class="search-button" slot="append" icon="el-icon-search"></el-button>
    </el-input>
    </el-menu-item>
    <el-menu-item index="5" >用户</el-menu-item>
    <el-menu-item index="6">订单</el-menu-item>
    <el-menu-item index="7">购物车</el-menu-item>
  </el-menu>
    <el-row  :class="[{'after-menu':!headerFixed}, {'full-menu':headerFixed}]"></el-row>
  <!-- 内容主体 -->
  <el-main>
    <!-- 路由占位符 -->
    <router-view></router-view>
  </el-main>
  <InfiniteScroll :class="{'infiniteScroll':!isMainIndex}"></InfiniteScroll>
  </div>
</template>

<script>
import InfiniteScroll from './common/InfiniteScroll'
export default {
  name: 'Main',
  components: { InfiniteScroll },
  data () {
    return {
      activeIndex: '1',
      activeIndex2: '1',
      offsetTop: 0,
      offsetHeight: 0,
      headerFixed: false,
      isMainIndex: false,
      menuList: [
        {
          id: '1',
          authName: '游戏分类',
          // TODO 获取分类列表
          children: [
            { id: '1-1', authName: '类别1', path: '类别1' },
            { id: '1-2', authName: '类别2', path: '类别2' },
            { id: '1-3', authName: '类别3', path: '类别3' }
          ]
        },
        {
          id: '2',
          authName: '游戏平台',
          // TODO 获取平台列表
          children: [
            { id: '2-1', authName: '平台1', path: '' },
            { id: '2-2', authName: '平台2', path: '' },
            { id: '2-3', authName: '平台3', path: '' }
          ]
        },
        {
          id: '3',
          authName: '发行商',
          // TODO 获取发行商列表
          children: [
            { id: '3-1', authName: '发行商1', path: '' },
            { id: '3-2', authName: '发行商2', path: '' },
            { id: '3-3', authName: '发行商3', path: '' }
          ]
        }
      ]
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      console.log(key)
      switch (key) {
        case '5': {
          this.$router.push('/personpage')
        }
      }
    },
    handleScroll () {
      // 兼容性，获取页面滚动距离
      var scrollTop = window.pageYOffset || document.getElementById('main').scrollTop || document.body.scrollTop
      // 判断页面滚动的距离是否大于吸顶元素的位置
      this.headerFixed = scrollTop > this.offsetTop
    },
    search () {
      console.log('search')
    }
  },
  mounted () {
    // 对整个页面滚轮进行监听，每发生一次滚轮事件，执行一次方法
    // window.addEventListener('scroll', this.handleScroll)
    document.getElementById('main').addEventListener('scroll', this.handleScroll)
    // DOM异步更新 对未来更新后的视图进行操作 在更新后执行
    // 监听dom渲染完成
    this.$nextTick(function () {
      let mainMenu = document.getElementById('main-menu-id')
      // 这里要得到top的距离
      this.offsetTop = mainMenu.offsetTop
    })
  },
  // 每次更新组件，检查路由变化
  updated () {
    console.log(this.isMainIndex)
    if (this.$route.path.indexOf('MainIndex') === -1) {
      this.isMainIndex = false
    } else {
      this.isMainIndex = true
    }
  },
  destroyed () {
    // 移除监听
    document.getElementById('main').removeEventListener('scroll', this.handleScroll)
  },
  watch: {
    $route (newRouter, oldRouter) {
      // console.log(this.$route.path)
      // console.log(newRouter.path)
      // console.log(newRouter.path.indexOf('MainIndex'))
      if (newRouter.path.indexOf('MainIndex') === -1) {
        this.isMainIndex = false
        console.log(this.isMainIndex)
      } else {
        this.isMainIndex = true
      }
    }
  }
}
</script>

<style scoped>
  .isFixed{
    position: fixed;
    top: 0px;
    width: 100%;
    z-index: 10;
  }
  .after-menu{
    display: none;
  }
.full-menu{
  height: 61px;
}
  .el-header{
    background-image: url("../assets/header/home_head_bg.png");
  }
  .grid-content {
    height: 120px;
  }
  .infiniteScroll{
    display: none;
  }
  .search-menu{
    border-bottom: 0 !important;
  }
  .search-menu:hover{
    background-color: #545c64 !important;
    border-bottom: 0 !important;
  }
  .search-input{
    height: 40px !important;
  }
  .search-button{
    height: 45px;
  }
</style>
