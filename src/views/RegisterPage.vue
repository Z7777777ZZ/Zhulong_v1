<template>
    <div class="register-container">
      <div class="register-card">
        <div class="register-header">
          <div class="flex items-center justify-center mb-6">
            <img src="/placeholder.svg?height=40&width=40" alt="Logo" class="h-10 w-10" />
            <span class="ml-2 text-xl font-bold bg-gradient-to-r from-blue-500 to-purple-600 bg-clip-text text-transparent">AIGC Detector</span>
          </div>
          <h1 class="register-title">创建账号</h1>
          <p class="register-subtitle">注册一个新账号以使用我们的服务</p>
        </div>
        
        <form @submit.prevent="handleRegister" class="register-form">
          <!-- 错误提示 -->
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
                v-model="formData.username" 
                class="form-input" 
                placeholder="请输入用户名（4-20个字符）"
                required
              />
            </div>
            <p v-if="validationErrors.username" class="error-text">{{ validationErrors.username }}</p>
          </div>
          
          <div class="form-group">
            <label for="email" class="form-label">邮箱</label>
            <div class="input-container">
              <mail-icon class="input-icon" />
              <input 
                type="email" 
                id="email" 
                v-model="formData.email" 
                class="form-input" 
                placeholder="请输入邮箱"
                required
              />
            </div>
            <p v-if="validationErrors.email" class="error-text">{{ validationErrors.email }}</p>
          </div>
          
          <div class="form-group">
            <label for="phone" class="form-label">手机号（选填）</label>
            <div class="input-container">
              <phone-icon class="input-icon" />
              <input 
                type="tel" 
                id="phone" 
                v-model="formData.phone" 
                class="form-input" 
                placeholder="请输入手机号"
              />
            </div>
            <p v-if="validationErrors.phone" class="error-text">{{ validationErrors.phone }}</p>
          </div>
          
          <div class="form-group">
            <label for="password" class="form-label">密码</label>
            <div class="input-container">
              <lock-icon class="input-icon" />
              <input 
                :type="showPassword ? 'text' : 'password'" 
                id="password" 
                v-model="formData.password" 
                class="form-input" 
                placeholder="请输入密码（6-20个字符）"
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
            <p v-if="validationErrors.password" class="error-text">{{ validationErrors.password }}</p>
          </div>
          
          <div class="form-group">
            <label for="confirmPassword" class="form-label">确认密码</label>
            <div class="input-container">
              <lock-icon class="input-icon" />
              <input 
                :type="showPassword ? 'text' : 'password'" 
                id="confirmPassword" 
                v-model="formData.confirmPassword" 
                class="form-input" 
                placeholder="请再次输入密码"
                required
              />
            </div>
            <p v-if="validationErrors.confirmPassword" class="error-text">{{ validationErrors.confirmPassword }}</p>
          </div>
          
          <div class="terms-agreement">
            <input type="checkbox" id="terms" v-model="formData.agreeTerms" class="terms-checkbox" required />
            <label for="terms" class="terms-label">我已阅读并同意<a href="#" class="terms-link">服务条款</a>和<a href="#" class="terms-link">隐私政策</a></label>
          </div>
          <p v-if="validationErrors.agreeTerms" class="error-text">{{ validationErrors.agreeTerms }}</p>
          
          <button type="submit" class="register-button" :disabled="isLoading">
            <loader-icon v-if="isLoading" class="animate-spin h-5 w-5 mr-2" />
            {{ isLoading ? '注册中...' : '注册' }}
          </button>
        </form>
        
        <div class="login-prompt">
          已有账号? <a href="#" @click.prevent="goToLogin" class="login-link">立即登录</a>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, reactive, inject } from 'vue';
  import { useRouter } from 'vue-router';
  import { 
    User as UserIcon, 
    Mail as MailIcon,
    Phone as PhoneIcon,
    Lock as LockIcon, 
    Eye as EyeIcon, 
    EyeOff as EyeOffIcon,
    Loader as LoaderIcon
  } from 'lucide-vue-next';
  
  // 注入auth服务
  const auth = inject('auth');
  const router = useRouter();
  
  // 表单数据
  const formData = reactive({
    username: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: '',
    agreeTerms: false
  });
  
  // 状态变量
  const showPassword = ref(false);
  const isLoading = ref(false);
  const errorMessage = ref('');
  const validationErrors = reactive({});
  
  // 切换密码显示
  const togglePassword = () => {
    showPassword.value = !showPassword.value;
  };
  
  // 表单验证
  const validateForm = () => {
    validationErrors.username = '';
    validationErrors.email = '';
    validationErrors.phone = '';
    validationErrors.password = '';
    validationErrors.confirmPassword = '';
    validationErrors.agreeTerms = '';
    
    let isValid = true;
    
    // 用户名验证
    if (!formData.username) {
      validationErrors.username = '请输入用户名';
      isValid = false;
    } else if (formData.username.length < 4 || formData.username.length > 20) {
      validationErrors.username = '用户名长度必须在4-20个字符之间';
      isValid = false;
    }
    
    // 邮箱验证
    if (!formData.email) {
      validationErrors.email = '请输入邮箱';
      isValid = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      validationErrors.email = '请输入有效的邮箱地址';
      isValid = false;
    }
    
    // 手机号验证（如果填写了）
    if (formData.phone && !/^1[3-9]\d{9}$/.test(formData.phone)) {
      validationErrors.phone = '请输入有效的手机号';
      isValid = false;
    }
    
    // 密码验证
    if (!formData.password) {
      validationErrors.password = '请输入密码';
      isValid = false;
    } else if (formData.password.length < 6 || formData.password.length > 20) {
      validationErrors.password = '密码长度必须在6-20个字符之间';
      isValid = false;
    }
    
    // 确认密码验证
    if (!formData.confirmPassword) {
      validationErrors.confirmPassword = '请确认密码';
      isValid = false;
    } else if (formData.password !== formData.confirmPassword) {
      validationErrors.confirmPassword = '两次输入的密码不一致';
      isValid = false;
    }
    
    // 服务条款验证
    if (!formData.agreeTerms) {
      validationErrors.agreeTerms = '请阅读并同意服务条款和隐私政策';
      isValid = false;
    }
    
    return isValid;
  };
  
  // 处理注册
  const handleRegister = async () => {
    if (!validateForm()) {
      return;
    }
    
    isLoading.value = true;
    errorMessage.value = '';
    
    try {
      const registerData = {
        username: formData.username,
        email: formData.email,
        password: formData.password,
        phone: formData.phone || null
      };
      
      const success = await auth.register(registerData);
      
      if (!success && auth.state.error) {
        errorMessage.value = auth.state.error;
      }
    } catch (error) {
      errorMessage.value = '注册失败，请稍后重试';
      console.error('注册错误:', error);
    } finally {
      isLoading.value = false;
    }
  };
  
  // 跳转到登录页
  const goToLogin = () => {
    router.push('/login');
  };
  </script>
  
  <style scoped>
  .register-container {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #f6f9fc 0%, #edf2f7 100%);
    padding: 2rem;
  }
  
  .register-card {
    width: 100%;
    max-width: 500px;
    background-color: white;
    border-radius: 1rem;
    box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 5px 10px -5px rgba(0, 0, 0, 0.04);
    padding: 2.5rem;
  }
  
  .register-header {
    text-align: center;
    margin-bottom: 2rem;
  }
  
  .register-title {
    font-size: 1.75rem;
    font-weight: 700;
    margin-bottom: 0.5rem;
  }
  
  .register-subtitle {
    color: #718096;
    font-size: 0.875rem;
  }
  
  .register-form {
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
  
  .terms-agreement {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
  
  .terms-checkbox {
    width: 1rem;
    height: 1rem;
    border-radius: 0.25rem;
    border: 1px solid #e2e8f0;
    accent-color: #3182ce;
  }
  
  .terms-label {
    font-size: 0.875rem;
    color: #4a5568;
  }
  
  .terms-link {
    color: #3182ce;
    text-decoration: none;
  }
  
  .terms-link:hover {
    text-decoration: underline;
  }
  
  .register-button {
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
  
  .register-button:hover:not(:disabled) {
    opacity: 0.9;
    transform: translateY(-1px);
  }
  
  .register-button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
  
  .login-prompt {
    text-align: center;
    font-size: 0.875rem;
    color: #718096;
    margin-top: 1.5rem;
  }
  
  .login-link {
    color: #3182ce;
    font-weight: 500;
    text-decoration: none;
  }
  
  .login-link:hover {
    text-decoration: underline;
  }
  
  .error-message {
    background-color: #FEE2E2;
    color: #DC2626;
    padding: 0.75rem;
    border-radius: 0.5rem;
    margin-bottom: 0.5rem;
    font-size: 0.875rem;
  }
  
  .error-text {
    color: #DC2626;
    font-size: 0.75rem;
    margin-top: 0.25rem;
  }
  </style>