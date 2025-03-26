<template>
    <div class="workspace-container">
      <!-- Sidebar -->
      <aside class="sidebar" :class="{ 'sidebar-collapsed': sidebarCollapsed, 'sidebar-open': sidebarOpen }">
        <div class="sidebar-header">
          <div class="flex items-center">
            <img src="/placeholder.svg?height=40&width=40" alt="Logo" class="h-8 w-8" />
            <span v-if="!sidebarCollapsed" class="ml-2 text-lg font-bold text-white">AIGC Detector</span>
          </div>
          <button @click="toggleSidebar" class="sidebar-toggle">
            <chevron-left-icon v-if="!sidebarCollapsed" class="h-5 w-5" />
            <chevron-right-icon v-else class="h-5 w-5" />
          </button>
        </div>
        
        <nav class="sidebar-nav">
          <router-link to="/workspace" class="nav-item">
            <layout-dashboard-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">仪表盘</span>
          </router-link>
          <router-link to="/recharge" class="nav-item">
            <search-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">充值中心</span>
          </router-link>
          <router-link to="/history" class="nav-item">
            <history-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">历史记录</span>
          </router-link>
          <router-link to="/profile" class="nav-item active">
            <user-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">个人中心</span>
          </router-link>
        </nav>
        
        <div class="sidebar-footer">
          <div class="usage-info" v-if="!sidebarCollapsed">
            <p class="text-sm text-gray-400">今日使用额度</p>
            <div class="usage-bar">
              <div class="usage-progress" :style="{ width: `${(userStats.usedCount / userStats.dailyLimit) * 100}%` }"></div>
            </div>
            <p class="text-xs text-gray-400 mt-1">{{ userStats.usedCount }}/{{ userStats.dailyLimit }} 次</p>
          </div>
          
          <div class="user-profile">
            <div class="avatar">
              <img :src="userProfile.avatar || '/placeholder.svg?height=40&width=40'" alt="User" class="h-8 w-8 rounded-full" />
            </div>
            <div v-if="!sidebarCollapsed" class="user-info">
              <p class="user-name">{{ userProfile.username }}</p>
              <p class="user-email">{{ userProfile.email }}</p>
            </div>
            <button v-if="!sidebarCollapsed" class="logout-button" @click="handleLogout">
              <log-out-icon class="h-4 w-4" />
            </button>
          </div>
        </div>
      </aside>
      
      <!-- Main Content -->
      <main class="main-content">
        <!-- Header -->
        <header class="content-header">
          <div class="flex items-center">
            <button @click="toggleMobileSidebar" class="sm:hidden mr-4">
              <menu-icon class="h-6 w-6" />
            </button>
            <h1 class="page-title">个人中心</h1>
          </div>
          
          <div class="flex items-center gap-4">
            <div class="search-bar">
              <search-icon class="search-icon" />
              <input type="text" placeholder="搜索..." class="search-input" />
            </div>
            
            <div class="notifications">
              <button class="notification-button">
                <bell-icon class="h-5 w-5" />
                <span class="notification-badge">3</span>
              </button>
            </div>
          </div>
        </header>
        
        <!-- Profile Content -->
        <div class="profile-content">
          <!-- Profile Overview Card -->
          <div class="profile-overview">
            <div class="profile-header">
              <div class="avatar-container">
                <div class="relative">
                  <img 
                    :src="avatarPreview || userProfile.avatar || '/placeholder.svg?height=120&width=120'" 
                    alt="Profile Avatar" 
                    class="avatar-image"
                  />
                  <div class="avatar-upload-overlay" @click="triggerAvatarInput">
                    <upload-icon class="h-6 w-6" />
                    <span>上传头像</span>
                  </div>
                  <input
                    type="file"
                    ref="avatarInput"
                    class="hidden"
                    accept="image/*"
                    @change="handleAvatarChange"
                  />
                </div>
              </div>
              <div class="profile-info">
                <h2 class="profile-name">{{ userProfile.username }}</h2>
                <p class="profile-role">
                  <span :class="userProfile.role === 'ADMIN' ? 'role-admin' : 'role-user'">
                    {{ userProfile.role === 'ADMIN' ? '管理员' : '普通用户' }}
                  </span>
                </p>
                <div class="profile-stats">
                  <div class="stat-item">
                    <p class="stat-value">{{ userStats.remainingQuota }}</p>
                    <p class="stat-label">剩余额度</p>
                  </div>
                  <div class="divider"></div>
                  <div class="stat-item">
                    <p class="stat-value">{{ detectionCount }}</p>
                    <p class="stat-label">检测总数</p>
                  </div>
                  <div class="divider"></div>
                  <div class="stat-item">
                    <p class="stat-value">{{ memberDays }}</p>
                    <p class="stat-label">会员天数</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <!-- Profile Tabs -->
          <div class="profile-tabs">
            <div class="tab-list">
              <button 
                v-for="tab in tabs" 
                :key="tab.id" 
                @click="() => { 
                  activeTab = tab.id; 
                  if (tab.id === 'basic') { 
                    resetForm(); 
                  } else if (tab.id === 'security') {
                    resetPasswordForm();
                  }
                }"
                class="tab-button"
                :class="{ 'active': activeTab === tab.id }"
              >
                <component :is="tab.icon" class="h-5 w-5" />
                <span>{{ tab.name }}</span>
              </button>
            </div>
            
            <!-- Basic Information Tab -->
            <div v-if="activeTab === 'basic'" class="tab-content">
              <div class="form-card">
                <h3 class="form-title">基本信息</h3>
                <div class="form-grid">
                  <div class="form-group">
                    <label class="form-label">用户名</label>
                    <div class="input-group">
                      <user-icon class="input-icon" />
                      <input 
                        type="text" 
                        v-model="profileForm.username" 
                        class="form-input" 
                        placeholder="请输入用户名"
                      />
                    </div>
                    <p v-if="validationErrors.username" class="error-message">{{ validationErrors.username }}</p>
                  </div>
                  
                  <div class="form-group">
                    <label class="form-label">电子邮箱</label>
                    <div class="input-group">
                      <mail-icon class="input-icon" />
                      <input 
                        type="email" 
                        v-model="profileForm.email" 
                        class="form-input" 
                        placeholder="请输入电子邮箱"
                      />
                    </div>
                    <p v-if="validationErrors.email" class="error-message">{{ validationErrors.email }}</p>
                  </div>
                  
                  <div class="form-group">
                    <label class="form-label">手机号码</label>
                    <div class="input-group">
                      <phone-icon class="input-icon" />
                      <input 
                        type="tel" 
                        v-model="profileForm.phone" 
                        class="form-input" 
                        placeholder="请输入手机号码"
                      />
                    </div>
                    <p v-if="validationErrors.phone" class="error-message">{{ validationErrors.phone }}</p>
                  </div>
                  
                  <div class="form-group col-span-2">
                    <label class="form-label">注册时间</label>
                    <div class="input-group">
                      <calendar-icon class="input-icon" />
                      <input 
                        type="text" 
                        :value="formatDate(userProfile.registerTime)" 
                        class="form-input" 
                        disabled
                      />
                    </div>
                  </div>
                </div>
                
                <div class="form-actions">
                  <button class="cancel-button" @click="resetForm">取消</button>
                  <button 
                    class="save-button" 
                    :disabled="isSaving"
                    @click="saveBasicInfo"
                  >
                    <loader-icon v-if="isSaving" class="animate-spin h-5 w-5 mr-2" />
                    {{ isSaving ? '保存中...' : '保存' }}
                  </button>
                </div>
              </div>
            </div>
            
            <!-- Account Security Tab -->
            <div v-else-if="activeTab === 'security'" class="tab-content">
              <div class="form-card">
                <h3 class="form-title">修改密码</h3>
                <div class="form-grid">
                  <div class="form-group">
                    <label class="form-label">当前密码</label>
                    <div class="input-group">
                      <lock-icon class="input-icon" />
                      <input 
                        :type="showCurrentPassword ? 'text' : 'password'" 
                        v-model="passwordForm.currentPassword" 
                        class="form-input" 
                        placeholder="请输入当前密码"
                      />
                      <button class="password-toggle" @click="showCurrentPassword = !showCurrentPassword">
                        <eye-icon v-if="showCurrentPassword" class="h-5 w-5" />
                        <eye-off-icon v-else class="h-5 w-5" />
                      </button>
                    </div>
                    <p v-if="validationErrors.currentPassword" class="error-message">{{ validationErrors.currentPassword }}</p>
                  </div>
                  
                  <div class="form-group">
                    <label class="form-label">新密码</label>
                    <div class="input-group">
                      <lock-icon class="input-icon" />
                      <input 
                        :type="showNewPassword ? 'text' : 'password'" 
                        v-model="passwordForm.newPassword" 
                        class="form-input" 
                        placeholder="请输入新密码"
                      />
                      <button class="password-toggle" @click="showNewPassword = !showNewPassword">
                        <eye-icon v-if="showNewPassword" class="h-5 w-5" />
                        <eye-off-icon v-else class="h-5 w-5" />
                      </button>
                    </div>
                    <p v-if="validationErrors.newPassword" class="error-message">{{ validationErrors.newPassword }}</p>
                  </div>
                  
                  <div class="form-group">
                    <label class="form-label">确认密码</label>
                    <div class="input-group">
                      <lock-icon class="input-icon" />
                      <input 
                        :type="showConfirmPassword ? 'text' : 'password'" 
                        v-model="passwordForm.confirmPassword" 
                        class="form-input" 
                        placeholder="请再次输入新密码"
                      />
                      <button class="password-toggle" @click="showConfirmPassword = !showConfirmPassword">
                        <eye-icon v-if="showConfirmPassword" class="h-5 w-5" />
                        <eye-off-icon v-else class="h-5 w-5" />
                      </button>
                    </div>
                    <p v-if="validationErrors.confirmPassword" class="error-message">{{ validationErrors.confirmPassword }}</p>
                  </div>
                </div>
                
                <div class="form-actions">
                  <button class="cancel-button" @click="resetPasswordForm">取消</button>
                  <button 
                    class="save-button" 
                    :disabled="isSaving"
                    @click="savePassword"
                  >
                    <loader-icon v-if="isSaving" class="animate-spin h-5 w-5 mr-2" />
                    {{ isSaving ? '修改中...' : '修改密码' }}
                  </button>
                </div>
              </div>
              
              <div class="form-card mt-6">
                <h3 class="form-title">安全设置</h3>
                <div class="security-options">
                  <div class="security-option">
                    <div class="option-info">
                      <div class="option-icon bg-blue-500">
                        <smartphone-icon class="h-5 w-5" />
                      </div>
                      <div class="option-details">
                        <h4 class="option-title">手机绑定</h4>
                        <p class="option-description">已绑定：{{ maskPhone(userProfile.phone) }}</p>
                      </div>
                    </div>
                    <button class="option-button">修改</button>
                  </div>
                  
                  <div class="security-option">
                    <div class="option-info">
                      <div class="option-icon bg-purple-500">
                        <mail-icon class="h-5 w-5" />
                      </div>
                      <div class="option-details">
                        <h4 class="option-title">邮箱绑定</h4>
                        <p class="option-description">已绑定：{{ maskEmail(userProfile.email) }}</p>
                      </div>
                    </div>
                    <button class="option-button">修改</button>
                  </div>
                  
                  <div class="security-option">
                    <div class="option-info">
                      <div class="option-icon bg-orange-500">
                        <shield-icon class="h-5 w-5" />
                      </div>
                      <div class="option-details">
                        <h4 class="option-title">两步验证</h4>
                        <p class="option-description">未开启</p>
                      </div>
                    </div>
                    <button class="option-button">开启</button>
                  </div>
                  
                  <div class="security-option">
                    <div class="option-info">
                      <div class="option-icon bg-green-500">
                        <activity-icon class="h-5 w-5" />
                      </div>
                      <div class="option-details">
                        <h4 class="option-title">登录设备</h4>
                        <p class="option-description">当前在线设备：1台</p>
                      </div>
                    </div>
                    <button class="option-button">查看</button>
                  </div>
                </div>
              </div>
            </div>
            
            <!-- Login History Tab -->
            <div v-else-if="activeTab === 'activity'" class="tab-content">
              <div class="form-card">
                <h3 class="form-title">登录历史</h3>
                <div class="login-history">
                  <div class="login-item" v-for="(item, index) in loginHistory" :key="index">
                    <div class="login-device">
                      <component :is="getDeviceIcon(item.device)" class="h-6 w-6 text-gray-600" />
                    </div>
                    <div class="login-details">
                      <div class="login-info">
                        <h4 class="login-location">{{ item.location }}</h4>
                        <p class="login-date">{{ formatDateTime(item.time) }}</p>
                      </div>
                      <div class="login-ip">
                        <p>IP: {{ item.ip }}</p>
                        <span :class="item.isCurrent ? 'current-device' : ''">{{ item.isCurrent ? '当前设备' : '' }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
      
      <!-- Success Notification -->
      <div
        v-if="showNotification"
        class="notification-toast"
      >
        <div class="notification-content">
          <check-circle-icon class="notification-icon" />
          <span>{{ notificationMessage }}</span>
        </div>
      </div>
    </div>
    </template>
    
    <script setup>
    import { ref, onMounted } from 'vue';
    import { useRouter } from 'vue-router';
    import {
      ChevronLeft as ChevronLeftIcon,
      ChevronRight as ChevronRightIcon,
      LayoutDashboard as LayoutDashboardIcon,
      Search as SearchIcon,
      History as HistoryIcon,
      User as UserIcon,
      LogOut as LogOutIcon,
      Menu as MenuIcon,
      Bell as BellIcon,
      Upload as UploadIcon,
      Mail as MailIcon,
      Phone as PhoneIcon,
      Calendar as CalendarIcon,
      Lock as LockIcon,
      Eye as EyeIcon,
      EyeOff as EyeOffIcon,
      Shield as ShieldIcon,
      Bell as NotificationIcon,
      MapPin as MapPinIcon,
      Activity as ActivityIcon,
      Smartphone as SmartphoneIcon,
      CheckCircle as CheckCircleIcon,
      // X as XIcon,
      Monitor as MonitorIcon,
      Tablet as TabletIcon,
      Smartphone as MobileIcon,
      Globe as WebIcon,
      Loader as LoaderIcon
    } from 'lucide-vue-next';
    import { getUserProfile, updateUserProfile, updateUserPassword } from '../api/profile';
    import { formatDate } from '../utils/formatDate';
    import auth from '../store/auth';
    
    const router = useRouter();
    const sidebarCollapsed = ref(false);
    const sidebarOpen = ref(false);
    const activeTab = ref('basic');
    const avatarPreview = ref(null);
    const avatarInput = ref(null);
    const isSaving = ref(false);
    const showCurrentPassword = ref(false);
    const showNewPassword = ref(false);
    const showConfirmPassword = ref(false);
    const validationErrors = ref({});
    
    const userProfile = ref({
      id: null,
      username: '',
      email: '',
      phone: '',
      avatar: '',
      role: 'USER',
      registerTime: null,
      lastLoginTime: null
    });
    
    const userStats = ref({
      totalQuota: 0,
      usedQuota: 0,
      remainingQuota: 0,
      dailyFreeQuota: 0,
      usedCount: 0,
      dailyLimit: 0
    });
    
    const detectionCount = ref(0);
    const memberDays = ref(0);
    
    const profileForm = ref({
      username: '',
      email: '',
      phone: ''
    });
    
    const passwordForm = ref({
      currentPassword: '',
      newPassword: '',
      confirmPassword: ''
    });
    
    const tabs = [
      { id: 'basic', name: '基本信息', icon: UserIcon },
      { id: 'security', name: '账号安全', icon: ShieldIcon },
      { id: 'notification', name: '通知设置', icon: NotificationIcon },
      { id: 'activity', name: '登录历史', icon: MapPinIcon }
    ];
    
    const toggleSidebar = () => {
      sidebarCollapsed.value = !sidebarCollapsed.value;
    };
    
    const toggleMobileSidebar = () => {
      sidebarOpen.value = !sidebarOpen.value;
    };
    
    const handleLogout = () => {
      router.push('/');
    };
    
    const triggerAvatarInput = () => {
      avatarInput.value.click();
    };
    
    const handleAvatarChange = (event) => {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          avatarPreview.value = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    };
    
    const resetForm = () => {
      profileForm.value.username = userProfile.value.username;
      profileForm.value.email = userProfile.value.email;
      profileForm.value.phone = userProfile.value.phone;
      validationErrors.value = {};
    };
    
    const validateBasicInfo = () => {
      const errors = {};
      
      if (!profileForm.value.username.trim()) {
        errors.username = '用户名不能为空';
      }
      
      if (!profileForm.value.email.trim()) {
        errors.email = '邮箱不能为空';
      } else if (!/^\S+@\S+\.\S+$/.test(profileForm.value.email)) {
        errors.email = '邮箱格式不正确';
      }
      
      if (profileForm.value.phone && !/^\d{11}$/.test(profileForm.value.phone)) {
        errors.phone = '手机号格式不正确';
      }
      
      validationErrors.value = errors;
      return Object.keys(errors).length === 0;
    };
    
    const validatePassword = () => {
      const errors = {};
      
      if (!passwordForm.value.currentPassword) {
        errors.currentPassword = '当前密码不能为空';
      }
      
      if (!passwordForm.value.newPassword) {
        errors.newPassword = '新密码不能为空';
      } else if (passwordForm.value.newPassword.length < 6) {
        errors.newPassword = '新密码不能少于6个字符';
      }
      
      if (!passwordForm.value.confirmPassword) {
        errors.confirmPassword = '请确认新密码';
      } else if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
        errors.confirmPassword = '两次输入的密码不一致';
      }
      
      validationErrors.value = errors;
      return Object.keys(errors).length === 0;
    };
    
    const saveBasicInfo = async () => {
      if (!validateBasicInfo()) {
        return;
      }
      
      isSaving.value = true;
      
      try {
        const payload = {
          username: profileForm.value.username,
          email: profileForm.value.email,
          phone: profileForm.value.phone || ''
        };
        
        if (avatarPreview.value) {
          payload.avatar = avatarPreview.value;
        }
        
        await updateUserProfile(payload);
        
        getUserProfileData();
        
        showSuccessNotification('个人资料已更新');
      } catch (error) {
        console.error('更新个人资料失败:', error);
        alert(error.message || '更新个人资料失败，请稍后重试');
      } finally {
        isSaving.value = false;
      }
    };
    
    const savePassword = async () => {
      if (!validatePassword()) {
        return;
      }
      
      isSaving.value = true;
      
      try {
        await updateUserPassword({
          currentPassword: passwordForm.value.currentPassword,
          newPassword: passwordForm.value.newPassword
        });
        
        passwordForm.value = {
          currentPassword: '',
          newPassword: '',
          confirmPassword: ''
        };
        
        showSuccessNotification('密码已更新，请重新登录');
        
        // 延迟登出，让用户看到通知
        setTimeout(() => {
          auth.logout(); // 直接调用auth模块的登出方法，会自动清除token并跳转到首页
        }, 1500);
      } catch (error) {
        console.error('更新密码失败:', error);
        alert(error.message || '更新密码失败，请稍后重试');
      } finally {
        isSaving.value = false;
      }
    };
    
    const getUserProfileData = async () => {
      try {
        const response = await getUserProfile();
        if (response.success) {
          const data = response.data;
          
          userProfile.value = {
            id: data.id,
            username: data.username,
            email: data.email,
            phone: data.phone || '',
            avatar: data.avatar,
            role: data.role,
            registerTime: data.registerTime,
            lastLoginTime: data.lastLoginTime
          };
          
          userStats.value = {
            totalQuota: data.totalQuota,
            usedQuota: data.usedQuota,
            remainingQuota: data.remainingQuota,
            dailyFreeQuota: data.dailyFreeQuota,
            usedCount: data.usedQuota,
            dailyLimit: data.dailyFreeQuota
          };
          
          detectionCount.value = data.detectionCount;
          memberDays.value = data.memberDays;
          
          profileForm.value = {
            username: userProfile.value.username,
            email: userProfile.value.email,
            phone: userProfile.value.phone
          };
        } else {
          console.error('获取用户资料失败:', response.message);
          alert('获取用户信息失败，请稍后重试');
        }
      } catch (error) {
        console.error('获取用户资料失败:', error);
        alert('获取用户信息失败，请稍后重试');
      }
    };
    
    const formatDateTime = (dateString) => {
      if (!dateString) return '';
      return formatDate(dateString, true);
    };
    
    const maskEmail = (email) => {
      if (!email) return '';
      const parts = email.split('@');
      if (parts.length !== 2) return email;
      
      const name = parts[0];
      const domain = parts[1];
      
      let maskedName = name;
      if (name.length > 3) {
        maskedName = name.substring(0, 1) + '*'.repeat(name.length - 2) + name.substring(name.length - 1);
      } else if (name.length > 1) {
        maskedName = name.substring(0, 1) + '*'.repeat(name.length - 1);
      }
      
      return `${maskedName}@${domain}`;
    };
    
    const maskPhone = (phone) => {
      if (!phone) return '';
      if (phone.length !== 11) return phone;
      
      return phone.substring(0, 3) + '****' + phone.substring(7);
    };
    
    const getDeviceIcon = (device) => {
      switch (device.toLowerCase()) {
        case 'desktop':
          return MonitorIcon;
        case 'tablet': 
          return TabletIcon;
        case 'mobile':
          return MobileIcon;
        default:
          return WebIcon;
      }
    };
    
    const resetPasswordForm = () => {
      passwordForm.value = {
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
      };
      validationErrors.value = {};
    };
    
    const loginHistory = ref([
      {
        device: 'desktop',
        location: '上海市',
        time: '2023-12-01 14:30:45',
        ip: '192.168.1.1',
        isCurrent: true
      },
      {
        device: 'mobile',
        location: '北京市',
        time: '2023-11-28 10:15:22',
        ip: '192.168.2.2',
        isCurrent: false
      },
      {
        device: 'tablet',
        location: '广州市',
        time: '2023-11-25 16:45:33',
        ip: '192.168.3.3',
        isCurrent: false
      }
    ]);
    
    const showNotification = ref(false);
    const notificationMessage = ref('');
    
    const showSuccessNotification = (message) => {
      notificationMessage.value = message;
      showNotification.value = true;
      setTimeout(() => {
        showNotification.value = false;
      }, 3000);
    };
    
    onMounted(async () => {
      try {
        // 检查登录状态
        if (!auth.state.isLoggedIn) {
          console.log('用户未登录，跳转到登录页');
          localStorage.setItem('redirectPath', '/profile');
          router.push('/login');
          return;
        }
        
        // 已登录，先获取用户信息
        const userData = await auth.fetchUserInfo();
        if (!userData) {
          console.log('获取用户信息失败，可能是token失效');
          localStorage.setItem('redirectPath', '/profile');
          router.push('/login');
          return;
        }
        
        // 获取个人资料
        await getUserProfileData();
      } catch (error) {
        console.error('个人中心页面初始化错误:', error);
        router.push('/login');
      }
    });
    </script>
    
    <style scoped>
    /* Base Styles */
    .workspace-container {
      display: flex;
      min-height: 100vh;
      background-color: #f7f9fc;
    }
    
    /* Sidebar Styles */
    .sidebar {
      width: 260px;
      background: linear-gradient(180deg, #1a202c 0%, #2d3748 100%);
      color: white;
      display: flex;
      flex-direction: column;
      transition: width 0.3s ease;
      overflow: hidden;
    }
    
    .sidebar-collapsed {
      width: 80px;
    }
    
    .sidebar-header {
      padding: 1.5rem;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    }
    
    .sidebar-toggle {
      background: rgba(255, 255, 255, 0.1);
      border: none;
      border-radius: 0.375rem;
      width: 2rem;
      height: 2rem;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      cursor: pointer;
      transition: background 0.2s;
    }
    
    .sidebar-toggle:hover {
      background: rgba(255, 255, 255, 0.2);
    }
    
    .sidebar-nav {
      flex: 1;
      padding: 1.5rem 1rem;
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
    }
    
    .nav-item {
      display: flex;
      align-items: center;
      padding: 0.75rem 1rem;
      border-radius: 0.5rem;
      color: rgba(255, 255, 255, 0.7);
      text-decoration: none;
      transition: all 0.2s;
    }
    
    .nav-item:hover {
      background: rgba(255, 255, 255, 0.1);
      color: white;
    }
    
    .nav-item.active {
      background: rgba(255, 255, 255, 0.1);
      color: white;
    }
    
    .nav-icon {
      width: 1.25rem;
      height: 1.25rem;
      margin-right: 1rem;
    }
    
    .sidebar-footer {
      padding: 1.5rem;
      border-top: 1px solid rgba(255, 255, 255, 0.1);
    }
    
    .usage-info {
      margin-bottom: 1.5rem;
    }
    
    .usage-bar {
      height: 4px;
      background: rgba(255, 255, 255, 0.1);
      border-radius: 2px;
      overflow: hidden;
      margin-top: 0.5rem;
    }
    
    .usage-progress {
      height: 100%;
      background: linear-gradient(90deg, #3182ce 0%, #805ad5 100%);
      border-radius: 2px;
    }
    
    .user-profile {
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }
    
    .avatar {
      width: 2rem;
      height: 2rem;
      border-radius: 9999px;
      overflow: hidden;
      background: white;
    }
    
    .user-info {
      flex: 1;
      overflow: hidden;
    }
    
    .user-name {
      font-weight: 500;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    
    .user-email {
      font-size: 0.75rem;
      color: rgba(255, 255, 255, 0.7);
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    
    .logout-button {
      background: none;
      border: none;
      color: rgba(255, 255, 255, 0.7);
      cursor: pointer;
      padding: 0.25rem;
      border-radius: 0.25rem;
      transition: all 0.2s;
    }
    
    .logout-button:hover {
      background: rgba(255, 255, 255, 0.1);
      color: white;
    }
    
    /* Mobile Sidebar */
    @media (max-width: 640px) {
      .sidebar {
        position: fixed;
        z-index: 40;
        top: 0;
        left: 0;
        height: 100vh;
        transform: translateX(-100%);
      }
      
      .sidebar.sidebar-open {
        transform: translateX(0);
      }
    }
    
    /* Main Content Styles */
    .main-content {
      flex: 1;
      overflow: auto;
    }
    
    .content-header {
      background: white;
      padding: 1.5rem 2rem;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid #e2e8f0;
      position: sticky;
      top: 0;
      z-index: 10;
    }
    
    .page-title {
      font-size: 1.5rem;
      font-weight: 700;
    }
    
    .search-bar {
      position: relative;
    }
    
    .search-icon {
      position: absolute;
      left: 0.75rem;
      top: 50%;
      transform: translateY(-50%);
      width: 1.25rem;
      height: 1.25rem;
      color: #a0aec0;
    }
    
    .search-input {
      padding: 0.5rem 1rem 0.5rem 2.5rem;
      border: 1px solid #e2e8f0;
      border-radius: 0.5rem;
      width: 16rem;
      font-size: 0.875rem;
    }
    
    .notification-button {
      position: relative;
      background: none;
      border: none;
      width: 2.5rem;
      height: 2.5rem;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 0.5rem;
      color: #4a5568;
      cursor: pointer;
      transition: all 0.2s;
    }
    
    .notification-button:hover {
      background: #f7fafc;
    }
    
    .notification-badge {
      position: absolute;
      top: 0.25rem;
      right: 0.25rem;
      width: 1.25rem;
      height: 1.25rem;
      background: #e53e3e;
      color: white;
      border-radius: 9999px;
      font-size: 0.75rem;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    
    /* Profile Content */
    .profile-content {
      padding: 2rem;
      display: flex;
      flex-direction: column;
      gap: 2rem;
    }
    
    /* Profile Overview */
    .profile-overview {
      background: white;
      border-radius: 0.75rem;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
      overflow: hidden;
    }
    
    .profile-header {
      display: flex;
      padding: 2rem;
      flex-direction: column;
      align-items: center;
      text-align: center;
    }
    
    @media (min-width: 768px) {
      .profile-header {
        flex-direction: row;
        text-align: left;
        align-items: flex-start;
      }
    }
    
    .avatar-container {
      margin-bottom: 1.5rem;
    }
    
    @media (min-width: 768px) {
      .avatar-container {
        margin-bottom: 0;
        margin-right: 2rem;
      }
    }
    
    .avatar-image {
      width: 120px;
      height: 120px;
      border-radius: 9999px;
      object-fit: cover;
      border: 4px solid white;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    }
    
    .avatar-upload-overlay {
      position: absolute;
      inset: 0;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background: rgba(0, 0, 0, 0.5);
      color: white;
      opacity: 0;
      border-radius: 9999px;
      transition: opacity 0.2s;
      cursor: pointer;
      gap: 0.5rem;
      font-size: 0.875rem;
    }
    
    .avatar-container:hover .avatar-upload-overlay {
      opacity: 1;
    }
    
    .profile-info {
      flex: 1;
    }
    
    .profile-name {
      font-size: 1.5rem;
      font-weight: 700;
      margin-bottom: 0.5rem;
    }
    
    .profile-role {
      margin-bottom: 1.5rem;
    }
    
    .role-admin, .role-user {
      display: inline-block;
      padding: 0.25rem 0.75rem;
      border-radius: 9999px;
      font-size: 0.75rem;
      font-weight: 500;
    }
    
    .role-admin {
      background-color: #fed7d7;
      color: #9b2c2c;
    }
    
    .role-user {
      background-color: #c6f6d5;
      color: #276749;
    }
    
    .profile-stats {
      display: flex;
      justify-content: center;
      gap: 1.5rem;
    }
    
    @media (min-width: 768px) {
      .profile-stats {
        justify-content: flex-start;
      }
    }
    
    .stat-item {
      text-align: center;
    }
    
    .stat-value {
      font-size: 1.5rem;
      font-weight: 700;
      color: #4a5568;
    }
    
    .stat-label {
      font-size: 0.875rem;
      color: #718096;
    }
    
    .divider {
      width: 1px;
      background: #e2e8f0;
    }
    
    /* Profile Tabs */
    .profile-tabs {
      display: flex;
      flex-direction: column;
      gap: 1.5rem;
    }
    
    .tab-list {
      display: flex;
      border-bottom: 1px solid #e2e8f0;
      overflow-x: auto;
      -webkit-overflow-scrolling: touch;
    }
    
    .tab-button {
      display: flex;
      align-items: center;
      padding: 1rem 1.5rem;
      border-bottom: 2px solid transparent;
      font-weight: 500;
      color: #718096;
      transition: all 0.2s;
      white-space: nowrap;
      gap: 0.5rem;
    }
    
    .tab-button:hover {
      color: #4a5568;
    }
    
    .tab-button.active {
      color: #3182ce;
      border-bottom-color: #3182ce;
    }
    
    /* Form Cards */
    .form-card {
      background: white;
      border-radius: 0.75rem;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
      padding: 1.5rem;
    }
    
    .form-title {
      font-size: 1.25rem;
      font-weight: 600;
      margin-bottom: 1.5rem;
      padding-bottom: 1rem;
      border-bottom: 1px solid #e2e8f0;
    }
    
    .form-grid {
      display: grid;
      grid-template-columns: 1fr;
      gap: 1.5rem;
    }
    
    @media (min-width: 768px) {
      .form-grid {
        grid-template-columns: repeat(2, 1fr);
      }
    }
    
    .col-span-2 {
      grid-column: span 1;
    }
    
    @media (min-width: 768px) {
      .col-span-2 {
        grid-column: span 2;
      }
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
    
    .input-group {
      position: relative;
      display: flex;
      align-items: center;
    }
    
    .input-icon {
      position: absolute;
      left: 0.75rem;
      width: 1.25rem;
      height: 1.25rem;
      color: #a0aec0;
    }
    
    .form-input {
      width: 100%;
      padding: 0.75rem 1rem 0.75rem 2.5rem;
      border: 1px solid #e2e8f0;
      border-radius: 0.5rem;
      font-size: 0.875rem;
      transition: border-color 0.2s;
    }
    
    .form-input:focus {
      outline: none;
      border-color: #3182ce;
      box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
    }
    
    .form-input:disabled {
      background-color: #f7fafc;
      cursor: not-allowed;
    }
    
    .password-toggle {
      position: absolute;
      right: 0.75rem;
      background: none;
      border: none;
      color: #a0aec0;
      cursor: pointer;
    }
    
    .error-message {
      font-size: 0.75rem;
      color: #e53e3e;
      margin-top: 0.25rem;
    }
    
    .form-actions {
      display: flex;
      justify-content: flex-end;
      gap: 1rem;
      margin-top: 2rem;
      padding-top: 1.5rem;
      border-top: 1px solid #e2e8f0;
    }
    
    .cancel-button {
      padding: 0.5rem 1.25rem;
      font-weight: 500;
      color: #4a5568;
      background: white;
      border: 1px solid #e2e8f0;
      border-radius: 0.375rem;
      cursor: pointer;
      transition: all 0.2s;
    }
    
    .cancel-button:hover {
      background: #f7fafc;
    }
    
    .save-button {
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 0.5rem 1.25rem;
      font-weight: 500;
      color: white;
      background: linear-gradient(to right, #3182ce, #805ad5);
      border: none;
      border-radius: 0.375rem;
      cursor: pointer;
      transition: all 0.2s;
    }
    
    .save-button:hover:not(:disabled) {
      opacity: 0.9;
    }
    
    .save-button:disabled {
      opacity: 0.7;
      cursor: not-allowed;
    }
    
    /* Security Options */
    .security-options {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
    
    .security-option {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 1rem;
      border-radius: 0.5rem;
      border: 1px solid #e2e8f0;
      transition: border-color 0.2s;
    }
    
    .security-option:hover {
      border-color: #cbd5e0;
    }
    
    .option-info {
      display: flex;
      align-items: center;
      gap: 1rem;
    }
    
    .option-icon {
      width: 2.5rem;
      height: 2.5rem;
      border-radius: 0.5rem;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
    }
    
    .option-details {
      display: flex;
      flex-direction: column;
    }
    
    .option-title {
      font-weight: 500;
      margin-bottom: 0.25rem;
    }
    
    .option-description {
      font-size: 0.875rem;
      color: #718096;
    }
    
    .option-button {
      padding: 0.5rem 1rem;
      background: #f7fafc;
      border: 1px solid #e2e8f0;
      border-radius: 0.375rem;
      font-size: 0.875rem;
      font-weight: 500;
      color: #4a5568;
      cursor: pointer;
      transition: all 0.2s;
    }
    
    .option-button:hover {
      background: #edf2f7;
    }
    
    /* Login History */
    .login-history {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
    
    .login-item {
      display: flex;
      gap: 1rem;
      padding: 1rem;
      border-radius: 0.5rem;
      border: 1px solid #e2e8f0;
      transition: border-color 0.2s;
    }
    
    .login-item:hover {
      border-color: #cbd5e0;
    }
    
    .login-device {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 3rem;
      height: 3rem;
      background: #f7fafc;
      border-radius: 0.5rem;
    }
    
    .login-details {
      flex: 1;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    
    .login-info {
      display: flex;
      flex-direction: column;
    }
    
    .login-location {
      font-weight: 500;
      margin-bottom: 0.25rem;
    }
    
    .login-date {
      font-size: 0.875rem;
      color: #718096;
    }
    
    .login-ip {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
      font-size: 0.875rem;
    }
    
    .current-device {
      display: inline-block;
      padding: 0.25rem 0.5rem;
      background: #ebf8ff;
      border-radius: 0.25rem;
      color: #2c5282;
      font-weight: 500;
      margin-top: 0.25rem;
    }
    
    /* Notification */
    .notification-toast {
      position: fixed;
      bottom: 2rem;
      right: 2rem;
      background-color: #4ade80;
      color: white;
      padding: 1rem 1.5rem;
      border-radius: 0.5rem;
      display: flex;
      align-items: center;
      box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
      z-index: 50;
      animation: slideIn 0.3s ease-out;
    }
    
    .notification-content {
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }
    
    .notification-icon {
      width: 1.25rem;
      height: 1.25rem;
    }
    
    @keyframes slideIn {
      from {
        transform: translateX(100%);
        opacity: 0;
      }
      to {
        transform: translateX(0);
        opacity: 1;
      }
    }
    </style>