<template>
    <div class="login-container">
      <div class="login-card">
        <div class="login-header">
          <div class="flex items-center justify-center mb-6">
            <img src="/placeholder.svg?height=40&width=40" alt="Logo" class="h-10 w-10" />
            <span class="ml-2 text-xl font-bold bg-gradient-to-r from-blue-500 to-purple-600 bg-clip-text text-transparent">AIGC Detector</span>
          </div>
          <h1 class="login-title">欢迎回来</h1>
          <p class="login-subtitle">登录您的账号以继续使用</p>
        </div>
        
        <form @submit.prevent="handleLogin" class="login-form">
          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>
          
          <div class="form-group">
            <label for="username" class="form-label">用户名</label>
            <div class="input-container">
              <user-icon class="input-icon" />
              <input 
                type="text" 
                id="username" 
                v-model="username" 
                class="form-input" 
                placeholder="请输入用户名"
                required
              />
            </div>
          </div>
          
          <div class="form-group">
            <div class="flex justify-between items-center">
              <label for="password" class="form-label">密码</label>
              <a href="#" class="forgot-password">忘记密码?</a>
            </div>
            <div class="input-container">
              <lock-icon class="input-icon" />
              <input 
                :type="showPassword ? 'text' : 'password'" 
                id="password" 
                v-model="password" 
                class="form-input" 
                placeholder="请输入密码"
                required
              />
              <button 
                type="button" 
                @click="togglePassword" 
                class="password-toggle"
              >
                <eye-icon v-if="!showPassword" class="h-5 w-5" />
                <eye-off-icon v-else class="h-5 w-5" />
              </button>
            </div>
          </div>
          
          <div class="remember-me">
            <input type="checkbox" id="remember" v-model="rememberMe" class="remember-checkbox" />
            <label for="remember" class="remember-label">记住我</label>
          </div>
          
          <button type="submit" class="login-button" :disabled="isLoading">
            <loader-icon v-if="isLoading" class="animate-spin h-5 w-5 mr-2" />
            {{ isLoading ? '登录中...' : '登录' }}
          </button>
        </form>
        
        <div class="divider">
          <span class="divider-text">或</span>
        </div>
        
        <div class="social-login">
          <button class="social-button">
            <github-icon class="h-5 w-5 mr-2" />
            GitHub 登录
          </button>
          <button class="social-button">
            <mail-icon class="h-5 w-5 mr-2" />
            邮箱登录
          </button>
        </div>
        
        <div class="register-prompt">
          还没有账号? <a href="#" @click.prevent="goToRegister" class="register-link">立即注册</a>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, inject } from 'vue';
  import { useRouter } from 'vue-router';
  import { 
    User as UserIcon, 
    Lock as LockIcon, 
    Eye as EyeIcon, 
    EyeOff as EyeOffIcon,
    Loader as LoaderIcon,
    Github as GithubIcon,
    Mail as MailIcon
  } from 'lucide-vue-next';

  // 注入auth服务
  const auth = inject('auth');
  const router = useRouter();

  // 登录表单数据
  const username = ref('');
  const password = ref('');
  const rememberMe = ref(false);
  const showPassword = ref(false);
  const isLoading = ref(false);
  const errorMessage = ref('');

  const togglePassword = () => {
    showPassword.value = !showPassword.value;
  };

  const handleLogin = async () => {
    if (!username.value || !password.value) {
      errorMessage.value = '请输入用户名和密码';
      return;
    }
    
    isLoading.value = true;
    errorMessage.value = '';
    
    try {
      const success = await auth.login({
        username: username.value,
        password: password.value,
        rememberMe: rememberMe.value
      });
      
      if (!success && auth.state.error) {
        errorMessage.value = auth.state.error;
      }
    } catch (error) {
      errorMessage.value = '登录失败，请稍后重试';
      console.error('登录错误:', error);
    } finally {
      isLoading.value = false;
    }
  };

  const goToRegister = () => {
    router.push('/register');
  };
  </script>
  
  <style scoped>
  .login-container {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #f6f9fc 0%, #edf2f7 100%);
    padding: 2rem;
  }
  
  .login-card {
    width: 100%;
    max-width: 450px;
    background-color: white;
    border-radius: 1rem;
    box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 5px 10px -5px rgba(0, 0, 0, 0.04);
    padding: 2.5rem;
  }
  
  .login-header {
    text-align: center;
    margin-bottom: 2rem;
  }
  
  .login-title {
    font-size: 1.75rem;
    font-weight: 700;
    margin-bottom: 0.5rem;
  }
  
  .login-subtitle {
    color: #718096;
    font-size: 0.875rem;
  }
  
  .login-form {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
  }
  
  .form-group {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .form-label {
    font-size: 0.875rem;
    font-weight: 500;
    color: #4a5568;
  }
  
  .input-container {
    position: relative;
  }
  
  .input-icon {
    position: absolute;
    left: 1rem;
    top: 50%;
    transform: translateY(-50%);
    width: 1.25rem;
    height: 1.25rem;
    color: #a0aec0;
  }
  
  .form-input {
    width: 100%;
    padding: 0.75rem 1rem 0.75rem 2.75rem;
    border: 1px solid #e2e8f0;
    border-radius: 0.5rem;
    font-size: 0.875rem;
    transition: all 0.3s;
  }
  
  .form-input:focus {
    outline: none;
    border-color: #3182ce;
    box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.2);
  }
  
  .password-toggle {
    position: absolute;
    right: 1rem;
    top: 50%;
    transform: translateY(-50%);
    background: none;
    border: none;
    color: #a0aec0;
    cursor: pointer;
  }
  
  .forgot-password {
    font-size: 0.75rem;
    color: #3182ce;
    text-decoration: none;
  }
  
  .forgot-password:hover {
    text-decoration: underline;
  }
  
  .remember-me {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
  
  .remember-checkbox {
    width: 1rem;
    height: 1rem;
    border-radius: 0.25rem;
    border: 1px solid #e2e8f0;
    accent-color: #3182ce;
  }
  
  .remember-label {
    font-size: 0.875rem;
    color: #4a5568;
  }
  
  .login-button {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0.75rem 1.5rem;
    font-weight: 600;
    color: white;
    background: linear-gradient(to right, #3182ce, #805ad5);
    border: none;
    border-radius: 0.5rem;
    cursor: pointer;
    transition: all 0.3s;
  }
  
  .login-button:hover:not(:disabled) {
    opacity: 0.9;
    transform: translateY(-1px);
  }
  
  .login-button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
  
  .divider {
    display: flex;
    align-items: center;
    margin: 1.5rem 0;
  }
  
  .divider::before,
  .divider::after {
    content: "";
    flex: 1;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .divider-text {
    padding: 0 1rem;
    color: #a0aec0;
    font-size: 0.875rem;
  }
  
  .social-login {
    display: flex;
    gap: 1rem;
    margin-bottom: 1.5rem;
  }
  
  .social-button {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0.75rem;
    font-size: 0.875rem;
    font-weight: 500;
    color: #4a5568;
    background-color: white;
    border: 1px solid #e2e8f0;
    border-radius: 0.5rem;
    cursor: pointer;
    transition: all 0.3s;
  }
  
  .social-button:hover {
    background-color: #f7fafc;
  }
  
  .register-prompt {
    text-align: center;
    font-size: 0.875rem;
    color: #718096;
  }
  
  .register-link {
    color: #3182ce;
    font-weight: 500;
    text-decoration: none;
  }
  
  .register-link:hover {
    text-decoration: underline;
  }
  
  .error-message {
    background-color: #FEE2E2;
    color: #DC2626;
    padding: 0.75rem;
    border-radius: 0.5rem;
    margin-bottom: 1rem;
    font-size: 0.875rem;
  }
  </style>