import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"
Vue.use(Vuex)

// const user = {
//   state: {
//     username: '',
//     avator: ''
//   }
//   // getters: {
//   //   username: state => {
//   //     let username = state.username
//   //     if (!username) {
//   //       username = JSON.parse(window.localStorage.getItem('username') || null)
//   //     }
//   //     return username
//   //   },
//   //   avator: state => {
//   //     let avator = state.avator
//   //     if (!avator) {
//   //       avator = JSON.parse(window.localStorage.getItem('avator') || null)
//   //     }
//   //     return avator
//   //   }
//   // },
//   // mutations: {
//   //   setUsername: (state, username) => { state.username = username },
//   //   setAvator: (state, avator) => { state.avator = avator }
//   // }
// }

const store = new Vuex.Store({
  plugins: [createPersistedState()],
  // modules: {
  //   user
  // },
  state: {
    HOST: 'http://localhost:8021',
    loginIn: false, // 是否登录
    userId: 0, // 搜索关键词
    carouselImgUrl: [
      'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
      'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20181213104603310708.jpg',
      'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190315115404560713.jpg',
      'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190516105901911866.jpg'
    ]
  },
  // getters: {
  //   loginIn: state => {
  //     let loginIn = state.loginIn
  //     if (!loginIn) {
  //       loginIn = JSON.parse(window.sessionStorage.getItem('loginIn') || null)
  //     }
  //     return loginIn
  //   },
  //   userId: state => {
  //     let userId = state.userId
  //     if (!userId) {
  //       userId = JSON.parse(window.sessionStorage.getItem('userId') || null)
  //     }
  //     return userId
  //   }
  // },
  mutations: {
    setLoginIn: (state, loginIn) => { state.loginIn = loginIn },
    setUserId: (state, userId) => { state.userId = userId }
  }
  // actions: {}
})
export default store
