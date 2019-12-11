import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import store from './store/index'
// 导入全局样式表
import './assets/css/global.css'
// 导入富文本编辑器
import VueQuillEditor from 'vue-quill-editor'
// require styles 导入富文本编辑器对应的样式
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
// 导入地区选择器
import VDistpicker from 'v-distpicker'
// 导入国际化语言切换 vue-i18n
// import VueI18n from 'vue-i18n'

import axios from 'axios'
// 配置请求的根路径
axios.defaults.baseURL = 'http://127.0.0.1:8888/api/private/v1/'
axios.interceptors.request.use(config => {
  console.log(config)
  config.headers.Authorization = window.sessionStorage.getItem('token')
  // 在最后必须 return config
  return config
})
Vue.prototype.$axios = axios
axios.defaults.baseURL = ''

Vue.config.productionTip = false

// 将富文本编辑器注册为全局可用的组件
Vue.use(VueQuillEditor)
// 将地区选择器注册为全局可用的组件
Vue.component('v-distpicker', VDistpicker)
// 将国际化语言切换 vue-i18n 注册为全局组件
// Vue.use(VueI18n)
// 使用多文件管理不同的语言是一个好习惯：
// const i18n = new VueI18n({
//  // locale: LangStorage.getLang('zh'),  // 语言标识，后面会用做切换和将用户习惯存储到本地浏览器
//  locale: 'zh-CN', // 语言标识
//  messages: {
//    'zh-CN': require('./common/lang/zh'),
//    'en-US': require('./common/lang/en')
//  }
// })

Vue.filter('dateFormat', function (originVal) {
  const dt = new Date(originVal)

  const y = dt.getFullYear()
  const m = (dt.getMonth() + 1 + '').padStart(2, '0')
  const d = (dt.getDate() + 1 + '').padStart(2, '0')

  const hh = (dt.getHours() + '').padStart(2, '0')
  const mm = (dt.getMinutes() + '').padStart(2, '0')
  const ss = (dt.getSeconds() + '').padStart(2, '0')

  return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
})

new Vue({
//  i18n,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
