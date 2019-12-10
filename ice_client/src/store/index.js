import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const goods = {
  state: {
    imgSrc: 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
    name: '名称',
    value: '￥20',
    tag: ['标签1', '标签2']
  },
  getters: {
    imgSrc: state => {
      let imgSrc = state.imgSrc
      if (!imgSrc) {
        imgSrc = JSON.parse(window.localStorage.getItem('imgSrc') || null)
      }
      return imgSrc
    },
    name: state => {
      let name = state.name
      if (!name) {
        name = JSON.parse(window.localStorage.getItem('name') || null)
      }
      return name
    },
    value: state => {
      let value = state.value
      if (!value) {
        value = JSON.parse(window.localStorage.getItem('value') || null)
      }
      return value
    },
    tag: state => {
      let tag = state.tag
      if (!tag) {
        tag = JSON.parse(window.localStorage.getItem('tag') || null)
      }
      return tag
    }
  },
  mutations: {
    setImgSrc: (state, imgSrc) => { state.imgSrc = imgSrc },
    setName: (state, name) => { state.name = name },
    setTag: (state, tag) => { state.tag = tag }
  }
}

const user = {
  state: {
    username: '',
    avator: ''
  },
  getters: {
    username: state => {
      let username = state.username
      if (!username) {
        username = JSON.parse(window.localStorage.getItem('username') || null)
      }
      return username
    },
    avator: state => {
      let avator = state.avator
      if (!avator) {
        avator = JSON.parse(window.localStorage.getItem('avator') || null)
      }
      return avator
    }
  },
  mutations: {
    setUsername: (state, username) => { state.username = username },
    setAvator: (state, avator) => { state.avator = avator }
  }
}

const store = new Vuex.Store({
  modules: {
    user,
    goods
  },
  state: {
    HOST: 'http://localhost:8021',
    loginIn: false, // 是否登录
    searchword: '', // 搜索关键词
    goodsList: [
      { 'id': 1,
        'imgSrc': 'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        'name': '名称',
        'value': '￥20',
        'tag': ['标签1', '标签2'] },
      {

      }
    ], // 商品列表
    index: 0 // 列表中的序号
  },
  getters: {
    loginIn: state => {
      let loginIn = state.loginIn
      if (!loginIn) {
        loginIn = JSON.parse(window.sessionStorage.getItem('loginIn') || null)
      }
      return loginIn
    },
    index: state => {
      let index = state.index
      if (!index) {
        index = JSON.parse(window.sessionStorage.getItem('index') || null)
      }
      return index
    },
    goodsList: state => {
      let goodsList = state.goodsList
      if (!goodsList.length) {
        goodsList = JSON.parse(window.sessionStorage.getItem('goodsList') || null)
      }
      return goodsList
    },
    searchword: state => state.searchword
  },
  mutations: {
    setLoginIn: (state, loginIn) => { state.loginIn = loginIn },
    setIndex: (state, index) => { state.index = index },
    setGoodsList: (state, goodsList) => { state.goodsList = goodsList },
    setSearchword: (state, searchword) => { state.searchword = searchword }
  },
  actions: {}
})
export default store
