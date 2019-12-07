import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import Welcome from '../components/Welcome.vue'
import Users from '../components/user/Users.vue'
import Infor from '../components/user/Infor.vue'
import Rights from '../components/power/Rights.vue'
import Roles from '../components/power/Roles.vue'
import Tag from '../components/goods/Tag.vue'
import GoodsList from '../components/goods/List.vue'
import Add from '../components/goods/Add.vue'
import Order from '../components/order/Order.vue'
import Main from '../components/Main.vue'
import GoodsDetail from '../components/common/GoodsDetail'
import MainIndex from '../components/MainIndex'
import Personpage from '../components/personpage/Personpage'

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  {
    path: '/home',
    component: Home,
    redirect: '/welcome',
    children: [
      { path: '/welcome', component: Welcome },
      { path: '/users', component: Users },
      { path: '/infor', component: Infor },
      { path: '/rights', component: Rights },
      { path: '/roles', component: Roles },
      { path: '/tags', component: Tag },
      { path: '/goods', component: GoodsList },
      { path: '/add', component: Add },
      { path: '/orders', component: Order }
    ]
  },
  {
    path: '/main',
    component: Main,
    redirect: '/MainIndex',
    children: [
      { path: '/MainIndex', component: MainIndex },
      { path: '/GoodsDetail/:id', component: GoodsDetail },
      { path: '/personpage', component: Personpage }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
