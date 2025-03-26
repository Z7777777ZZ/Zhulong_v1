import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/HomePage.vue'
import LoginPage from '../views/LoginPage.vue'
import RegisterPage from '../views/RegisterPage.vue'
import WorkspacePage from '../views/WorkspacePage.vue'
import HistoryPage from '../views/HistoryPage.vue'
import ProductPage from '../views/ProductPage.vue'
import AboutPage from '../views/AboutPage.vue'
import HelpPage from '../views/HelpPage.vue'
import ProfilePage from '@/views/ProfilePage.vue'
import RechargePage from '@/views/RechargePage.vue'
import ContactPage from '@/views/ContactPage.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomePage
  },
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
    meta: {
      hideForAuth: true // 已登录用户隐藏此路由
    }
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterPage,
    meta: {
      hideForAuth: true // 已登录用户隐藏此路由
    }
  },
  {
    path: '/workspace',
    name: 'workspace',
    component: WorkspacePage,
    meta: {
      requiresAuth: true // 需要登录才能访问
    }
  },
  {
    path: '/recharge',
    name: 'recharge',
    component: RechargePage,
    meta: {
      requiresAuth: true // 需要登录才能访问
    }
  },
  {
    path: '/profile',
    name: 'profile',
    component: ProfilePage,
    meta: {
      requiresAuth: true // 需要登录才能访问
    }
  },
  {
    path: '/history',
    name: 'history',
    component: HistoryPage,
    meta: {
      requiresAuth: true // 需要登录才能访问
    }
  },
  {
    path: '/product',
    name: 'product',
    component: ProductPage
  },
  {
    path: '/about',
    name: 'about',
    component: AboutPage
  },
  {
    path: '/help',
    name: 'help',
    component: HelpPage
  },
  {
    path: '/contact',
    name: 'contact',
    component: ContactPage
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
  // 检查用户是否已登录
  const isLoggedIn = !!localStorage.getItem('user') // 假设用户信息存储在localStorage中
  
  // 如果路由需要认证且用户未登录
  if (to.matched.some(record => record.meta.requiresAuth) && !isLoggedIn) {
    // 保存用户想要访问的页面，以便登录后重定向
    localStorage.setItem('redirectPath', to.fullPath)
    // 重定向到登录页
    next({ name: 'login' })
  } 
  // 如果用户已登录且路由是为非认证用户设计的（如登录页）
  else if (isLoggedIn && to.matched.some(record => record.meta.hideForAuth)) {
    // 重定向到工作台
    next({ name: 'home' })
  } 
  else {
    // 正常导航
    next()
  }
})

export default router