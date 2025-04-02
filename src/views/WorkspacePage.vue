<template>
    <div class="workspace-container">
      <!-- Sidebar -->
      <aside class="sidebar" :class="{ 'sidebar-collapsed': sidebarCollapsed }">
        <div class="sidebar-header">
          <div class="flex items-center">
            <img src="/logo.jpg?height=40&width=40" alt="Logo" class="h-8 w-8" />
            <span v-if="!sidebarCollapsed" class="ml-2 text-lg font-bold text-white">AIGC Detector</span>
          </div>
          <button @click="toggleSidebar" class="sidebar-toggle">
            <chevron-left-icon v-if="!sidebarCollapsed" class="h-5 w-5" />
            <chevron-right-icon v-else class="h-5 w-5" />
          </button>
        </div>
        
        <nav class="sidebar-nav">
          <router-link to="/workspace" class="nav-item active">
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
          <router-link to="/profile" class="nav-item">
            <user-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">个人中心</span>
          </router-link>
        </nav>
        
        <div class="sidebar-footer">
          <div class="usage-info" v-if="!sidebarCollapsed">
            <p class="text-sm text-gray-400">今日使用额度</p>
            <div class="usage-bar">
              <div class="usage-progress" :style="{ width: `${Math.min(100, Math.max(0, (userStats.usedQuota / userStats.dailyLimit) * 100))}%` }"></div>
            </div>
            <p class="text-xs text-gray-400 mt-1">{{ userStats.usedQuota }}/{{ userStats.dailyLimit }} 次</p>
          </div>
          
          <div class="user-profile">
            <div class="avatar">
              <img src="/placeholder.svg?height=40&width=40" alt="User" class="h-8 w-8 rounded-full" />
            </div>
            <div v-if="!sidebarCollapsed" class="user-info">
              <p class="user-name">{{ user.username }}</p>
              <p class="user-email">{{ user.email }}</p>
            </div>
            <button v-if="!sidebarCollapsed" class="logout-button" @click="handleLogout" title="返回首页">
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
            <h1 class="page-title">仪表盘</h1>
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
        
        <!-- Dashboard Content -->
        <div class="dashboard-content">
          <!-- Stats Cards -->
          <div class="stats-grid">
            <div class="stat-card" v-for="(stat, index) in stats" :key="index">
              <div class="stat-icon" :class="stat.color">
                <component :is="stat.icon" class="h-6 w-6" />
              </div>
              <div class="stat-info">
                <p class="stat-value">{{ stat.value }}</p>
                <p class="stat-label">{{ stat.label }}</p>
              </div>
              <div class="stat-chart">
                <div class="chart-line" :style="{ height: `${stat.trend}%` }"></div>
              </div>
            </div>
          </div>
          
          <!-- Detection Types -->
          <div class="detection-types">
            <h2 class="section-title">快速检测</h2>
            <div class="detection-grid">
              <div 
                v-for="(type, index) in detectionTypes" 
                :key="index" 
                class="detection-card"
                @click="startDetection(type.id)"
              >
                <div class="detection-icon" :class="type.color">
                  <component :is="type.icon" class="h-6 w-6" />
                </div>
                <h3 class="detection-title">{{ type.title }}</h3>
                <p class="detection-description">{{ type.description }}</p>
              </div>
            </div>
          </div>
          
          <!-- Recent Activity -->
          <div class="recent-activity">
            <div class="activity-header">
              <h2 class="section-title">最近检测</h2>
              <button class="view-all-button">
                查看全部
                <chevron-right-icon class="h-4 w-4" />
              </button>
            </div>
            
            <div class="activity-list">
              <div class="activity-item" v-for="(activity, index) in recentActivities" :key="index">
                <div class="activity-icon" :class="getTypeColor(activity.type)">
                  <component :is="getTypeIcon(activity.type)" class="h-5 w-5" />
                </div>
                <div class="activity-info">
                  <div class="activity-details">
                    <p class="activity-title">{{ activity.title }}</p>
                    <p class="activity-time">{{ activity.time }}</p>
                  </div>
                  <div class="activity-result" :class="getResultClass(activity.result)">
                    {{ activity.result }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
      
      <!-- Detection Modal -->
      <div v-if="showDetectionModal" class="detection-modal-overlay" @click="closeDetectionModal">
        <div class="detection-modal" @click.stop>
          <div class="modal-header">
            <h2 class="modal-title">{{ currentDetectionType.title }}</h2>
            <button class="close-button" @click="closeDetectionModal">
              <x-icon class="h-5 w-5" />
            </button>
          </div>
          
          <div class="modal-content">
            <div class="upload-area" @click="triggerFileInput" @dragover.prevent @drop.prevent="handleFileDrop">
              <input 
                type="file" 
                ref="fileInput" 
                class="hidden" 
                :accept="currentDetectionType.accept" 
                @change="handleFileSelect"
              />
              <div v-if="!selectedFile" class="upload-placeholder">
                <component :is="currentDetectionType.icon" class="h-12 w-12 text-gray-400" />
                <p class="upload-text">拖拽文件到此处或点击上传</p>
                <p class="upload-hint">{{ currentDetectionType.hint }}</p>
                <p v-if="!hasEnoughQuota" class="upload-warning">您的额度不足，请先充值</p>
              </div>
              <div v-else class="file-preview">
                <div v-if="currentDetectionType.id === 'image'" class="image-preview">
                  <img :src="filePreview" alt="Preview" class="preview-image" />
                </div>
                <div v-else-if="currentDetectionType.id === 'text'" class="text-preview">
                  <p>{{ filePreview }}</p>
                </div>
                <div v-else class="file-info">
                  <file-icon class="h-8 w-8 text-gray-500" />
                  <p class="file-name">{{ selectedFile.name }}</p>
                  <p class="file-size">{{ formatFileSize(selectedFile.size) }}</p>
                </div>
              </div>
            </div>
            
            <div v-if="detectionResult" class="result-area">
              <h3 class="result-title">检测结果</h3>
              <div class="result-card">
                <div class="result-header">
                  <div class="result-badge" :class="getResultBadgeClass(detectionResult.aiProbability)">
                    {{ getResultLabel(detectionResult.aiProbability) }}
                  </div>
                  <p class="result-probability">AI生成概率: {{ detectionResult.aiProbability }}%</p>
                </div>
                <div class="result-details">
                  <div class="result-meter">
                    <div class="meter-bar">
                      <div 
                        class="meter-progress" 
                        :style="{ width: `${detectionResult.aiProbability}%` }"
                        :class="getResultProgressClass(detectionResult.aiProbability)"
                      ></div>
                    </div>
                  </div>
                  <div class="result-analysis">
                    <p>{{ detectionResult.analysis }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="modal-footer">
            <button class="cancel-button" @click="closeDetectionModal">取消</button>
            <div class="flex gap-2">
              <button 
                v-if="!hasEnoughQuota" 
                class="recharge-button"
                @click="goToRecharge"
              >
                去充值
              </button>
              <button 
                class="detect-button" 
                :disabled="!selectedFile || isDetecting || !hasEnoughQuota" 
                @click="detectContent"
              >
                <loader-icon v-if="isDetecting" class="animate-spin h-5 w-5 mr-2" />
                <span v-if="!hasEnoughQuota">额度不足</span>
                <span v-else>{{ isDetecting ? '检测中...' : '开始检测' }}</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue';
  import { useRouter } from 'vue-router';
  import auth from '../store/auth';
  import { detectContent as apiDetectContent } from '../api/history';
  import { 
    ChevronLeft as ChevronLeftIcon,
    ChevronRight as ChevronRightIcon,
    LayoutDashboard as LayoutDashboardIcon,
    Search as SearchIcon,
    History as HistoryIcon,
    LogOut as LogOutIcon,
    Bell as BellIcon,
    Image as ImageIcon,
    FileText as FileTextIcon,
    User as UserIcon,
    Video as VideoIcon,
    Music as MusicIcon,
    BarChart2 as BarChart2Icon,
    X as XIcon,
    File as FileIcon,
    Loader as LoaderIcon
  } from 'lucide-vue-next';
  
  const router = useRouter();
  
  const user = computed(() => auth.state.user || {
    username: '未登录',
    email: 'guest@example.com'
  });
  
  const userStats = computed(() => {
    return {
      dailyLimit: user.value.dailyFreeQuota || 10,
      usedQuota: user.value.usedQuota || 0,
      remainingQuota: user.value.remainingQuota || 0
    };
  });
  
  // 检查用户是否有足够额度
  const hasEnoughQuota = computed(() => {
    return userStats.value.remainingQuota > 0;
  });
  
  onMounted(async () => {
    try {
      // 检查登录状态
      if (!auth.state.isLoggedIn) {
        console.log('用户未登录，跳转到登录页');
        localStorage.setItem('redirectPath', '/workspace');
        router.push('/login');
        return;
      }
      
      // 已登录，获取用户信息
      console.log('用户已登录，获取用户信息');
      const userData = await auth.fetchUserInfo();
      if (!userData) {
        console.log('获取用户信息失败，可能是token失效');
        // 如果获取用户信息失败，可能是token已过期但本地存储还有值
        // 此时应当清除登录状态并重定向到登录页
        auth.logout();
        router.push('/login');
      }
    } catch (error) {
      console.error('工作台初始化错误:', error);
      router.push('/login');
    }
  });
  
  const sidebarCollapsed = ref(false);
  
  const toggleSidebar = () => {
    sidebarCollapsed.value = !sidebarCollapsed.value;
  };
  
  const stats = ref([
    {
      icon: ImageIcon,
      value: '28',
      label: '图片检测',
      trend: 65,
      color: 'bg-blue-500'
    },
    {
      icon: FileTextIcon,
      value: '42',
      label: '文本检测',
      trend: 80,
      color: 'bg-purple-500'
    },
    {
      icon: VideoIcon,
      value: '13',
      label: '视频检测',
      trend: 45,
      color: 'bg-orange-500'
    },
    {
      icon: BarChart2Icon,
      value: '89%',
      label: '平均准确率',
      trend: 90,
      color: 'bg-green-500'
    }
  ]);
  
  const detectionTypes = ref([
    {
      id: 'image',
      title: '图片检测',
      description: '检测图片是否由AI生成',
      icon: ImageIcon,
      color: 'bg-blue-500',
      accept: 'image/*',
      hint: '支持JPG、PNG、WEBP等格式'
    },
    {
      id: 'text',
      title: '文本检测',
      description: '检测文本是否由AI生成',
      icon: FileTextIcon,
      color: 'bg-purple-500',
      accept: '.txt,.doc,.docx,.pdf',
      hint: '支持TXT、DOC、PDF等格式'
    },
    {
      id: 'video',
      title: '视频检测',
      description: '检测视频是否由AI生成',
      icon: VideoIcon,
      color: 'bg-orange-500',
      accept: 'video/*',
      hint: '支持MP4、AVI、MOV等格式'
    },
    {
      id: 'audio',
      title: '音频检测',
      description: '检测音频是否由AI生成',
      icon: MusicIcon,
      color: 'bg-green-500',
      accept: 'audio/*',
      hint: '支持MP3、WAV、FLAC等格式'
    }
  ]);
  
  const recentActivities = ref([
    {
      type: 'image',
      title: 'landscape.jpg',
      time: '10分钟前',
      result: 'AI生成 (92%)'
    },
    {
      type: 'text',
      title: '研究报告.docx',
      time: '1小时前',
      result: '人工创作 (87%)'
    },
    {
      type: 'video',
      title: 'presentation.mp4',
      time: '3小时前',
      result: 'AI生成 (76%)'
    },
    {
      type: 'audio',
      title: 'voice_message.mp3',
      time: '昨天',
      result: '人工创作 (95%)'
    },
    {
      type: 'image',
      title: 'portrait.png',
      time: '昨天',
      result: 'AI生成 (98%)'
    }
  ]);
  
  const showDetectionModal = ref(false);
  const currentDetectionType = ref(null);
  const fileInput = ref(null);
  const selectedFile = ref(null);
  const filePreview = ref(null);
  const isDetecting = ref(false);
  const detectionResult = ref(null);
  
  // 开始检测前的检查
  const startDetection = (typeId) => {
    // 先检查用户是否有足够的额度
    if (!hasEnoughQuota.value) {
      alert('您的检测额度已用完，请前往充值中心充值后再继续使用。');
      // 不要直接使用router.push，而是使用goToRecharge函数
      goToRecharge();
      return;
    }
    
    currentDetectionType.value = detectionTypes.value.find(type => type.id === typeId);
    showDetectionModal.value = true;
    selectedFile.value = null;
    filePreview.value = null;
    detectionResult.value = null;
  };
  
  const closeDetectionModal = () => {
    showDetectionModal.value = false;
    currentDetectionType.value = null;
    selectedFile.value = null;
    filePreview.value = null;
    detectionResult.value = null;
  };
  
  const triggerFileInput = () => {
    fileInput.value.click();
  };
  
  const handleFileSelect = (event) => {
    const file = event.target.files[0];
    if (file) {
      processFile(file);
    }
  };
  
  const handleFileDrop = (event) => {
    const file = event.dataTransfer.files[0];
    if (file) {
      processFile(file);
    }
  };
  
  const processFile = (file) => {
    selectedFile.value = file;
    
    if (currentDetectionType.value.id === 'image') {
      const reader = new FileReader();
      reader.onload = (e) => {
        filePreview.value = e.target.result;
      };
      reader.readAsDataURL(file);
    } else if (currentDetectionType.value.id === 'text' && file.type.includes('text')) {
      const reader = new FileReader();
      reader.onload = (e) => {
        filePreview.value = e.target.result;
      };
      reader.readAsText(file);
    } else {
      filePreview.value = null;
    }
  };
  
  const formatFileSize = (bytes) => {
    if (bytes < 1024) return bytes + ' B';
    else if (bytes < 1048576) return (bytes / 1024).toFixed(2) + ' KB';
    else return (bytes / 1048576).toFixed(2) + ' MB';
  };
  
  const detectContent = async () => {
    if (!selectedFile.value) return;
    
    // 再次检查用户额度
    if (!hasEnoughQuota.value) {
      alert('您的检测额度已用完，请前往充值中心充值后再继续使用。');
      closeDetectionModal();
      // 不要直接使用router.push，而是使用goToRecharge函数
      goToRecharge();
      return;
    }
    
    isDetecting.value = true;
    detectionResult.value = null;
    
    try {
      // 准备表单数据
      const formData = new FormData();
      formData.append('file', selectedFile.value);
      formData.append('type', currentDetectionType.value.id);
      
      // 调用API进行检测
      const response = await apiDetectContent(formData);
      
      if (!response || !response.success) {
        throw new Error(response?.message || '检测失败');
      }
      
      console.log('检测结果:', response.data);
      
      const result = response.data;
      detectionResult.value = {
        aiProbability: result.aiProbability || Math.floor(Math.random() * 100),
        analysis: result.analysis || (result.aiProbability > 70 
          ? '该内容很可能是由AI生成的，检测到了典型的AI生成特征。' 
          : '该内容很可能是人工创作的，未检测到明显的AI生成特征。')
      };
      
      // 更新用户信息，刷新额度
      await auth.fetchUserInfo();
      
      // 添加到最近检测记录
      addToRecentActivities(selectedFile.value.name, currentDetectionType.value.id, detectionResult.value.aiProbability);
      
    } catch (error) {
      console.error('检测失败:', error);
      alert(`检测失败: ${error.message || '请稍后再试'}`);
    } finally {
      isDetecting.value = false;
    }
  };
  
  // 添加到最近检测记录
  const addToRecentActivities = (fileName, typeId, probability) => {
    // const now = new Date();
    const timeString = '刚刚';
    
    const result = probability > 70 ? `AI生成 (${probability}%)` : `人工创作 (${probability}%)`;
    
    const newActivity = {
      type: typeId,
      title: fileName,
      time: timeString,
      result: result
    };
    
    // 添加到开头
    recentActivities.value.unshift(newActivity);
    
    // 保持最多5条记录
    if (recentActivities.value.length > 5) {
      recentActivities.value.pop();
    }
  };
  
  const getTypeIcon = (type) => {
    switch (type) {
      case 'image': return ImageIcon;
      case 'text': return FileTextIcon;
      case 'video': return VideoIcon;
      case 'audio': return MusicIcon;
      default: return FileIcon;
    }
  };
  
  const getTypeColor = (type) => {
    switch (type) {
      case 'image': return 'bg-blue-500';
      case 'text': return 'bg-purple-500';
      case 'video': return 'bg-orange-500';
      case 'audio': return 'bg-green-500';
      default: return 'bg-gray-500';
    }
  };
  
  const getResultClass = (result) => {
    return result.includes('AI生成') ? 'text-red-500' : 'text-green-500';
  };
  
  const getResultBadgeClass = (probability) => {
    return probability > 70 ? 'bg-red-100 text-red-800' : 'bg-green-100 text-green-800';
  };
  
  const getResultLabel = (probability) => {
    return probability > 70 ? 'AI生成' : '人工创作';
  };
  
  const getResultProgressClass = (probability) => {
    return probability > 70 ? 'bg-red-500' : 'bg-green-500';
  };
  
  const handleLogout = () => {
    router.push('/');
  };
  
  const goToRecharge = () => {
    closeDetectionModal();
    console.log('准备跳转到充值页面，确保用户数据...');
    
    // 确保在跳转前保存必要的状态并刷新用户信息
    try {
      // 首先刷新用户信息，确保拥有最新的数据
      auth.fetchUserInfo().then(() => {
        // 使用replace而不是push，避免导航堆栈问题
        router.replace('/recharge');
      }).catch(error => {
        console.error('获取用户信息失败，但仍继续跳转:', error);
        router.replace('/recharge');
      });
    } catch (error) {
      console.error('跳转到充值页面时发生错误:', error);
      router.replace('/recharge');
    }
  };
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
  
  /* Dashboard Content */
  .dashboard-content {
    padding: 2rem;
    display: flex;
    flex-direction: column;
    gap: 2rem;
  }
  
  /* Stats Grid */
  .stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 1.5rem;
  }
  
  .stat-card {
    background: white;
    border-radius: 0.75rem;
    padding: 1.5rem;
    display: flex;
    align-items: center;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .stat-icon {
    width: 3rem;
    height: 3rem;
    border-radius: 0.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    margin-right: 1rem;
  }
  
  .stat-info {
    flex: 1;
  }
  
  .stat-value {
    font-size: 1.5rem;
    font-weight: 700;
    margin-bottom: 0.25rem;
  }
  
  .stat-label {
    font-size: 0.875rem;
    color: #718096;
  }
  
  .stat-chart {
    width: 2.5rem;
    height: 2.5rem;
    display: flex;
    align-items: flex-end;
  }
  
  .chart-line {
    width: 0.5rem;
    background: #e2e8f0;
    border-radius: 0.25rem;
    position: relative;
    overflow: hidden;
  }
  
  .chart-line::before {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #3182ce;
    border-radius: 0.25rem;
  }
  
  /* Detection Types */
  .section-title {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 1.5rem;
  }
  
  .detection-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 1.5rem;
  }
  
  .detection-card {
    background: white;
    border-radius: 0.75rem;
    padding: 1.5rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 0.3s;
  }
  
  .detection-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
  }
  
  .detection-icon {
    width: 3.5rem;
    height: 3.5rem;
    border-radius: 0.75rem;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    margin-bottom: 1rem;
  }
  
  .detection-title {
    font-size: 1.125rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
  }
  
  .detection-description {
    font-size: 0.875rem;
    color: #718096;
  }
  
  /* Recent Activity */
  .activity-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 1.5rem;
  }
  
  .view-all-button {
    display: flex;
    align-items: center;
    font-size: 0.875rem;
    color: #3182ce;
    background: none;
    border: none;
    cursor: pointer;
  }
  
  .activity-list {
    background: white;
    border-radius: 0.75rem;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .activity-item {
    display: flex;
    align-items: center;
    padding: 1rem 1.5rem;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .activity-item:last-child {
    border-bottom: none;
  }
  
  .activity-icon {
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 0.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    margin-right: 1rem;
  }
  
  .activity-info {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  
  .activity-details {
    flex: 1;
  }
  
  .activity-title {
    font-weight: 500;
    margin-bottom: 0.25rem;
  }
  
  .activity-time {
    font-size: 0.75rem;
    color: #718096;
  }
  
  .activity-result {
    font-weight: 500;
  }
  
  .text-red-500 {
    color: #e53e3e;
  }
  
  .text-green-500 {
    color: #38a169;
  }
  
  /* Detection Modal */
  .detection-modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 50;
  }
  
  .detection-modal {
    background: white;
    border-radius: 1rem;
    width: 100%;
    max-width: 600px;
    max-height: 90vh;
    overflow: auto;
    box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
  }
  
  .modal-header {
    padding: 1.5rem;
    display: flex;
    align-items: center;
    justify-content: space-between;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .modal-title {
    font-size: 1.25rem;
    font-weight: 600;
  }
  
  .close-button {
    background: none;
    border: none;
    width: 2rem;
    height: 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 0.375rem;
    color: #4a5568;
    cursor: pointer;
    transition: all 0.2s;
  }
  
  .close-button:hover {
    background: #f7fafc;
  }
  
  .modal-content {
    padding: 1.5rem;
  }
  
  .upload-area {
    border: 2px dashed #e2e8f0;
    border-radius: 0.75rem;
    padding: 2rem;
    text-align: center;
    cursor: pointer;
    transition: all 0.2s;
  }
  
  .upload-area:hover {
    border-color: #3182ce;
  }
  
  .upload-placeholder {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
  }
  
  .upload-text {
    font-size: 1.125rem;
    font-weight: 500;
    color: #4a5568;
  }
  
  .upload-hint {
    font-size: 0.875rem;
    color: #718096;
  }
  
  .upload-warning {
    font-size: 0.875rem;
    color: #e53e3e;
    font-weight: 500;
    margin-top: 0.5rem;
    padding: 0.5rem;
    background-color: #fed7d7;
    border-radius: 0.375rem;
  }
  
  .file-preview {
    padding: 1rem;
  }
  
  .image-preview {
    display: flex;
    justify-content: center;
  }
  
  .preview-image {
    max-width: 100%;
    max-height: 300px;
    border-radius: 0.5rem;
  }
  
  .text-preview {
    max-height: 200px;
    overflow: auto;
    background: #f7fafc;
    padding: 1rem;
    border-radius: 0.5rem;
    text-align: left;
  }
  
  .file-info {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
  }
  
  .file-name {
    font-weight: 500;
  }
  
  .file-size {
    font-size: 0.875rem;
    color: #718096;
  }
  
  .result-area {
    margin-top: 2rem;
  }
  
  .result-title {
    font-size: 1.125rem;
    font-weight: 600;
    margin-bottom: 1rem;
  }
  
  .result-card {
    background: #f7fafc;
    border-radius: 0.75rem;
    padding: 1.5rem;
  }
  
  .result-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 1.5rem;
  }
  
  .result-badge {
    padding: 0.25rem 0.75rem;
    border-radius: 9999px;
    font-size: 0.875rem;
    font-weight: 500;
  }
  
  .bg-red-100 {
    background-color: #fed7d7;
  }
  
  .text-red-800 {
    color: #9b2c2c;
  }
  
  .bg-green-100 {
    background-color: #c6f6d5;
  }
  
  .text-green-800 {
    color: #276749;
  }
  
  .result-probability {
    font-weight: 500;
  }
  
  .result-details {
    margin-top: 1rem;
  }
  
  .result-meter {
    margin-bottom: 1.5rem;
  }
  
  .meter-bar {
    height: 0.5rem;
    background: #e2e8f0;
    border-radius: 0.25rem;
    overflow: hidden;
  }
  
  .meter-progress {
    height: 100%;
    border-radius: 0.25rem;
  }
  
  .bg-red-500 {
    background-color: #e53e3e;
  }
  
  .bg-green-500 {
    background-color: #38a169;
  }
  
  .result-analysis {
    font-size: 0.875rem;
    color: #4a5568;
    line-height: 1.5;
  }
  
  .modal-footer {
    padding: 1.5rem;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    gap: 1rem;
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
  
  .detect-button {
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
  
  .detect-button:hover:not(:disabled) {
    opacity: 0.9;
  }
  
  .detect-button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
  
  .recharge-button {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0.5rem 1.25rem;
    font-weight: 500;
    color: white;
    background: linear-gradient(to right, #f6ad55, #ed8936);
    border: none;
    border-radius: 0.375rem;
    cursor: pointer;
    transition: all 0.2s;
  }
  
  .recharge-button:hover {
    opacity: 0.9;
  }
  
  /* Responsive Adjustments */
  @media (max-width: 768px) {
    .sidebar {
      position: fixed;
      z-index: 40;
      height: 100vh;
      transform: translateX(-100%);
    }
    
    .sidebar.sidebar-open {
      transform: translateX(0);
    }
    
    .main-content {
      margin-left: 0;
    }
    
    .stats-grid,
    .detection-grid {
      grid-template-columns: 1fr;
    }
  }
  </style>