<template>
  <!-- Header Navigation -->
  <header class="header">
    <div class="container mx-auto px-4 py-3 flex items-center justify-between">
      <div class="flex items-center">
        <div class="logo-container">
          <img src="/placeholder.svg?height=40&width=40" alt="Logo" class="h-10 w-10" />
          <span class="ml-2 text-xl font-bold bg-gradient-to-r from-blue-500 to-purple-600 bg-clip-text text-transparent">AIGC Detector</span>
        </div>
      </div>
      
      <nav class="hidden md:flex items-center space-x-8">
        <router-link to="/" class="nav-link" :class="{ 'active': $route.path === '/' }">首页</router-link>
        <router-link to="/product" class="nav-link" :class="{ 'active': $route.path === '/product' }">产品介绍</router-link>
        <router-link to="/about" class="nav-link" :class="{ 'active': $route.path === '/about' }">关于我们</router-link>
        <router-link to="/help" class="nav-link" :class="{ 'active': $route.path === '/help' }">帮助中心</router-link>
        <router-link v-if="isLoggedIn" to="/workspace" class="nav-link" :class="{ 'active': $route.path === '/workspace' }">我的工作台</router-link>
      </nav>
      
      <div class="flex items-center space-x-4">
        <template v-if="isLoggedIn">
          <div class="flex items-center">
            <img src="/placeholder.svg?height=32&width=32" alt="User" class="h-8 w-8 rounded-full mr-2" />
            <span class="text-sm font-medium mr-2">{{ user.username }}</span>
            <button @click="handleLogout" class="logout-btn">
              退出
            </button>
          </div>
        </template>
        <template v-else>
          <router-link to="/login" class="login-btn">登录</router-link>
          <router-link to="/register" class="register-btn">注册</router-link>
        </template>
        <button class="md:hidden menu-btn" @click="toggleMobileMenu">
          <menu-icon class="h-6 w-6" />
        </button>
      </div>
    </div>
    
    <!-- Mobile Menu -->
    <div v-if="mobileMenuOpen" class="mobile-menu md:hidden">
      <router-link to="/" class="block py-2 px-4 hover:bg-gray-100">首页</router-link>
      <router-link to="/product" class="block py-2 px-4 hover:bg-gray-100">产品介绍</router-link>
      <router-link to="/about" class="block py-2 px-4 hover:bg-gray-100">关于我们</router-link>
      <router-link to="/help" class="block py-2 px-4 hover:bg-gray-100">帮助中心</router-link>
      <router-link v-if="isLoggedIn" to="/workspace" class="block py-2 px-4 hover:bg-gray-100">我的工作台</router-link>
      <template v-if="!isLoggedIn">
        <router-link to="/login" class="block py-2 px-4 hover:bg-gray-100">登录</router-link>
        <router-link to="/register" class="block py-2 px-4 hover:bg-gray-100">注册</router-link>
      </template>
      <button v-else @click="handleLogout" class="block w-full text-left py-2 px-4 hover:bg-gray-100">退出</button>
    </div>
  </header>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { Menu as MenuIcon } from 'lucide-vue-next';
import auth from '../store/auth';

const router = useRouter();
const mobileMenuOpen = ref(false);

const isLoggedIn = computed(() => auth.state.isLoggedIn);
const user = computed(() => auth.state.user || {});

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
};

const handleLogout = () => {
  auth.logout();
  router.push('/');
};
</script>

<style scoped>
/* Header Styles */
.header {
  background-color: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 50;
}

.logo-container {
  display: flex;
  align-items: center;
}

.nav-link {
  font-weight: 500;
  color: #4a5568;
  transition: color 0.2s;
  position: relative;
}

.nav-link:hover, .nav-link.active {
  color: #3182ce;
}

.nav-link.active::after {
  content: '';
  position: absolute;
  bottom: -4px;
  left: 0;
  width: 100%;
  height: 2px;
  background: linear-gradient(to right, #3182ce, #805ad5);
}

.menu-btn {
  background: none;
  border: none;
  color: #4a5568;
  cursor: pointer;
}

.login-btn {
  padding: 0.5rem 1.25rem;
  font-weight: 500;
  color: #3182ce;
  border: 1px solid #3182ce;
  border-radius: 0.375rem;
  transition: all 0.2s;
}

.login-btn:hover {
  background-color: rgba(49, 130, 206, 0.1);
}

.register-btn {
  padding: 0.5rem 1.25rem;
  font-weight: 500;
  color: white;
  background: linear-gradient(to right, #3182ce, #805ad5);
  border-radius: 0.375rem;
  transition: all 0.2s;
}

.register-btn:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

.logout-btn {
  padding: 0.5rem 1rem;
  font-weight: 500;
  color: #e53e3e;
  border: 1px solid #e53e3e;
  border-radius: 0.375rem;
  transition: all 0.2s;
  font-size: 0.875rem;
}

.logout-btn:hover {
  background-color: rgba(229, 62, 62, 0.1);
}

.mobile-menu {
  background-color: white;
  border-top: 1px solid #e2e8f0;
}
</style> 