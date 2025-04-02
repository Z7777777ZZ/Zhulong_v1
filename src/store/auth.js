// src/store/auth.js
import { reactive, readonly } from 'vue'
import { login as apiLogin, register as apiRegister, getUserInfo, sendEmailVerificationCode } from '../api/auth'
import router from '../router'

const state = reactive({
  user: JSON.parse(localStorage.getItem('user')) || null,
  token: localStorage.getItem('token') || null,
  isLoggedIn: !!localStorage.getItem('user'),
  loading: false,
  error: null
})

const methods = {
  async login(loginData) {
    state.loading = true
    state.error = null
    
    try {
      const response = await apiLogin(loginData.username, loginData.password)
      
      if (response.success) {
        const userData = response.data
        state.user = {
          id: userData.id,
          username: userData.username,
          email: userData.email,
          role: userData.role
        }
        state.token = userData.token
        state.isLoggedIn = true
        
        // 存储用户信息和token
        localStorage.setItem('user', JSON.stringify(state.user))
        localStorage.setItem('token', userData.token)
        
        // 获取用户详细信息（包括额度等）
        await methods.fetchUserInfo()
        
        // 检查是否有重定向路径，如果没有则默认重定向到首页
        const redirectPath = localStorage.getItem('redirectPath') || '/'
        localStorage.removeItem('redirectPath')
        
        // 重定向到指定页面
        router.push(redirectPath)
        
        return true
      } else {
        state.error = response.message || '登录失败'
        return false
      }
    } catch (error) {
      state.error = error.message || '登录失败，请检查网络连接'
      return false
    } finally {
      state.loading = false
    }
  },
  
  async register(registerData) {
    state.loading = true
    state.error = null
    
    try {
      const response = await apiRegister(registerData)
      
      if (response.success) {
        // 注册成功后自动登录
        return await methods.login({
          username: registerData.username,
          password: registerData.password
        })
      } else {
        state.error = response.message || '注册失败'
        return false
      }
    } catch (error) {
      state.error = error.message || '注册失败，请检查网络连接'
      return false
    } finally {
      state.loading = false
    }
  },
  
  async sendVerificationCode(email) {
    state.error = null
    
    try {
      const response = await sendEmailVerificationCode(email)
      
      if (response.success) {
        return true
      } else {
        state.error = response.message || '发送验证码失败'
        return false
      }
    } catch (error) {
      state.error = error.message || '发送验证码失败，请检查网络连接'
      throw error
    }
  },
  
  async fetchUserInfo() {
    if (!state.isLoggedIn || !state.token) {
      console.warn('尝试获取用户信息但未登录或token无效')
      return null
    }
    
    try {
      console.log('正在获取用户信息...')
      const response = await getUserInfo()
      
      if (response && response.success && response.data) {
        console.log('用户信息获取成功:', response.data)
        // 更新用户信息，包括额度等
        const updatedUser = {
          ...state.user,
          ...response.data
        }
        
        // 打印更新前后的信息，方便调试
        console.log('更新前:', state.user)
        console.log('更新后:', updatedUser)
        
        // 设置状态
        state.user = updatedUser
        
        // 保存到本地存储
        localStorage.setItem('user', JSON.stringify(state.user))
        return response.data
      } else {
        console.error('获取用户信息失败:', response?.message || '未知错误')
        return null
      }
    } catch (error) {
      console.error('获取用户信息异常:', error)
      
      // 检查是否为401未授权错误
      if (error.status === 401) {
        console.warn('Token已失效，执行登出')
        // 清除登录状态但不自动跳转
        state.user = null
        state.token = null
        state.isLoggedIn = false
        localStorage.removeItem('user')
        localStorage.removeItem('token')
        // 注意这里不调用methods.logout()，因为它会自动跳转
      }
      
      return null
    }
  },
  
  logout() {
    state.user = null
    state.token = null
    state.isLoggedIn = false
    localStorage.removeItem('user')
    localStorage.removeItem('token')
    router.push('/')
  },
  
  checkAuth() {
    const user = localStorage.getItem('user')
    const token = localStorage.getItem('token')
    
    if (user && token) {
      state.user = JSON.parse(user)
      state.token = token
      state.isLoggedIn = true
      return true
    }
    
    state.user = null
    state.token = null
    state.isLoggedIn = false
    return false
  }
}

export default {
  state: readonly(state),
  ...methods
}