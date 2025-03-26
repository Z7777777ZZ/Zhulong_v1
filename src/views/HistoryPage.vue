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
        <router-link to="/history" class="nav-item active">
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
            <div class="usage-progress" :style="{ width: `${(userStats.usedCount / userStats.dailyLimit) * 100}%` }"></div>
          </div>
          <p class="text-xs text-gray-400 mt-1">{{ userStats.usedCount }}/{{ userStats.dailyLimit }} 次</p>
        </div>
        
        <div class="user-profile">
          <div class="avatar">
            <img src="/placeholder.svg?height=40&width=40" alt="User" class="h-8 w-8 rounded-full" />
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
          <h1 class="page-title">历史记录</h1>
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
      
      <!-- History Content -->
      <div class="history-content">
        <div class="filters">
          <div class="filter-group">
            <label class="filter-label">检测类型</label>
            <div class="filter-buttons">
              <button 
                v-for="type in detectionTypes" 
                :key="type.id"
                class="filter-button"
                :class="{ active: selectedType === type.id }"
                @click="setTypeFilter(type.id)"
              >
                <component :is="type.icon" class="filter-icon" />
                {{ type.label }}
              </button>
            </div>
          </div>
          
          <div class="filter-group">
            <label class="filter-label">时间范围</label>
            <select v-model="timeRange" class="time-select">
              <option value="all">全部时间</option>
              <option value="today">今天</option>
              <option value="week">本周</option>
              <option value="month">本月</option>
            </select>
          </div>
          
          <div class="filter-group">
            <label class="filter-label">检测结果</label>
            <select v-model="resultFilter" class="result-select">
              <option value="all">全部结果</option>
              <option value="ai">AI生成</option>
              <option value="human">人工创作</option>
            </select>
          </div>
        </div>
        
        <div class="history-table-container">
          <table class="history-table">
            <thead>
              <tr>
                <th>文件名</th>
                <th>检测类型</th>
                <th>检测时间</th>
                <th>检测结果</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(record, index) in filteredRecords" :key="index">
                <td class="file-cell">
                  <div class="file-info">
                    <div class="file-icon" :class="getTypeColor(record.type)">
                      <component :is="getTypeIcon(record.type)" class="h-4 w-4 text-white" />
                    </div>
                    <span class="file-name">{{ record.fileName }}</span>
                  </div>
                </td>
                <td>{{ getTypeName(record.type) }}</td>
                <td>{{ record.time }}</td>
                <td>
                  <span 
                    class="result-badge"
                    :class="record.result.includes('AI') ? 'ai-badge' : 'human-badge'"
                  >
                    {{ record.result }}
                  </span>
                </td>
                <td>
                  <div class="action-buttons">
                    <button class="action-button" @click="viewDetails(record)">
                      <eye-icon class="h-4 w-4" />
                    </button>
                    <button class="action-button" @click="downloadResult(record)">
                      <download-icon class="h-4 w-4" />
                    </button>
                    <button class="action-button" @click="deleteRecord(record)">
                      <trash-icon class="h-4 w-4" />
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <div class="pagination">
          <button 
            class="pagination-button" 
            :disabled="currentPage === 1"
            @click="prevPage"
          >
            <chevron-left-icon class="h-4 w-4" />
          </button>
          
          <div class="pagination-pages">
            <button 
              v-for="page in displayedPages" 
              :key="page"
              class="page-button"
              :class="{ active: currentPage === page }"
              @click="goToPage(page)"
            >
              {{ page }}
            </button>
          </div>
          
          <button 
            class="pagination-button" 
            :disabled="currentPage === totalPages"
            @click="nextPage"
          >
            <chevron-right-icon class="h-4 w-4" />
          </button>
        </div>
      </div>
    </main>
    
    <!-- Details Modal -->
    <div v-if="showDetailsModal" class="modal-overlay" @click="closeDetailsModal">
      <div class="details-modal" @click.stop>
        <div class="modal-header">
          <h2 class="modal-title">检测详情</h2>
          <button class="close-button" @click="closeDetailsModal">
            <x-icon class="h-5 w-5" />
          </button>
        </div>
        
        <div class="modal-content">
          <div class="details-grid">
            <div class="detail-item">
              <span class="detail-label">文件名</span>
              <span class="detail-value">{{ selectedRecord.fileName }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">检测类型</span>
              <span class="detail-value">{{ getTypeName(selectedRecord.type) }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">检测时间</span>
              <span class="detail-value">{{ selectedRecord.time }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">检测结果</span>
              <span 
                class="result-badge"
                :class="selectedRecord.result && selectedRecord.result.includes('AI') ? 'ai-badge' : 'human-badge'"
              >
                {{ selectedRecord.result }}
              </span>
            </div>
          </div>
          
          <div class="result-details">
            <h3 class="result-title">检测分析</h3>
            <div class="result-card">
              <div class="result-header">
                <div 
                  class="result-badge"
                  :class="selectedRecord.result && selectedRecord.result.includes('AI') ? 'ai-badge' : 'human-badge'"
                >
                  {{ selectedRecord.result && selectedRecord.result.includes('AI') ? 'AI生成' : '人工创作' }}
                </div>
                <p class="result-probability">AI生成概率: {{ selectedRecord.probability }}%</p>
              </div>
              <div class="result-meter">
                <div class="meter-bar">
                  <div 
                    class="meter-progress" 
                    :style="{ width: `${selectedRecord.probability}%` }"
                    :class="selectedRecord.result && selectedRecord.result.includes('AI') ? 'ai-progress' : 'human-progress'"
                  ></div>
                </div>
              </div>
              <div class="result-analysis">
                <p>{{ selectedRecord.analysis }}</p>
              </div>
            </div>
          </div>
          
          <div class="file-preview">
            <h3 class="preview-title">文件预览</h3>
            <div class="preview-container">
              <img 
                v-if="selectedRecord.type === 'image'" 
                :src="selectedRecord.fileUrl" 
                alt="Preview" 
                class="preview-image" 
              />
              <div v-else-if="selectedRecord.type === 'text'" class="text-preview">
                {{ selectedRecord.content }}
              </div>
              <div v-else class="file-placeholder">
                <component :is="getTypeIcon(selectedRecord.type)" class="h-12 w-12 text-gray-400" />
                <p class="placeholder-text">{{ getTypeName(selectedRecord.type) }}文件预览不可用</p>
              </div>
            </div>
          </div>
        </div>
        
        <div class="modal-footer">
          <button class="secondary-button" @click="closeDetailsModal">关闭</button>
          <button class="primary-button" @click="downloadResult(selectedRecord)">
            <download-icon class="h-4 w-4 mr-2" />
            下载报告
          </button>
        </div>
      </div>
    </div>
    
    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="modal-overlay" @click="closeDeleteModal">
      <div class="delete-modal" @click.stop>
        <div class="modal-header">
          <h2 class="modal-title">确认删除</h2>
          <button class="close-button" @click="closeDeleteModal">
            <x-icon class="h-5 w-5" />
          </button>
        </div>
        
        <div class="modal-content">
          <p class="delete-message">
            您确定要删除 <span class="font-semibold">{{ recordToDelete.fileName }}</span> 的检测记录吗？
            此操作无法撤销。
          </p>
        </div>
        
        <div class="modal-footer">
          <button class="secondary-button" @click="closeDeleteModal">取消</button>
          <button class="danger-button" @click="confirmDelete">
            <trash-icon class="h-4 w-4 mr-2" />
            确认删除
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import auth from '../store/auth';

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
  Download as DownloadIcon,
  Trash as TrashIcon,
  FileText as FileTextIcon,
  Image as ImageIcon,
  Video as VideoIcon,
  Music as MusicIcon,
  File as FileIcon,
  Eye as EyeIcon,
  X as XIcon
} from 'lucide-vue-next';

import { getUserDetectionRecords, getDetectionRecordsByType, getDetectionRecordsByTimeRange, getDetectionRecordsByResult, getDetectionRecordDetail, deleteDetectionRecord } from '../api/history';
import { getUserInfo } from '../api/auth';

const router = useRouter();

// Sidebar state
const sidebarCollapsed = ref(false);
const sidebarOpen = ref(false);

// User data and stats
const userProfile = ref({
  username: '',
  email: ''
});

const userStats = ref({
  usedCount: 0,
  dailyLimit: 10
});

// Detection types
const detectionTypes = [
  { id: 'all', label: '全部类型', icon: FileTextIcon },
  { id: 1, label: '文本检测', icon: FileTextIcon },
  { id: 2, label: '图像检测', icon: ImageIcon },
  { id: 3, label: '视频检测', icon: VideoIcon },
  { id: 4, label: '音频检测', icon: MusicIcon }
];

// Filter states
const selectedType = ref('all');
const timeRange = ref('all');
const resultFilter = ref('all');

// Records and UI state
const records = ref([]);
const loading = ref(true);
const error = ref(null);

// Modal states
const showDetailsModal = ref(false);
const showDeleteModal = ref(false);
const selectedRecord = ref({});
const recordToDelete = ref({});

// Pagination
const currentPage = ref(1);
const recordsPerPage = ref(5);

// Toggle functions
const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value;
};

const toggleMobileSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
};

const handleLogout = () => {
  router.push('/');
};

// eslint-disable-next-line no-unused-vars
const fetchUserInfo = async () => {
  try {
    const response = await getUserInfo();
    if (response.success) {
      const data = response.data;
      
      userProfile.value = {
        username: data.username,
        email: data.email
      };
      
      userStats.value = {
        usedCount: data.usedQuota || 0,
        dailyLimit: data.dailyFreeQuota || 10
      };
    } else {
      console.error('获取用户信息失败:', response.message);
    }
  } catch (error) {
    console.error('获取用户信息失败:', error);
  }
};

const fetchRecords = async () => {
  loading.value = true;
  error.value = null;
  
  try {
    let response;
    
    if (selectedType.value !== 'all') {
      response = await getDetectionRecordsByType(selectedType.value);
    } else if (timeRange.value !== 'all') {
      response = await getDetectionRecordsByTimeRange(timeRange.value);
    } else if (resultFilter.value !== 'all') {
      response = await getDetectionRecordsByResult(resultFilter.value);
    } else {
      response = await getUserDetectionRecords();
    }
    
    if (response.success) {
      records.value = response.data;
    } else {
      console.error('获取历史记录失败:', response.message);
      error.value = response.message || '获取历史记录失败，请稍后重试';
      records.value = [];
    }
  } catch (err) {
    console.error('获取历史记录失败:', err);
    error.value = err.message || '获取历史记录失败，请稍后重试';
    records.value = [];
  } finally {
    loading.value = false;
  }
};

const setTypeFilter = (typeId) => {
  selectedType.value = typeId;
  currentPage.value = 1;
  fetchRecords();
};

const filteredRecords = computed(() => {
  return records.value;
});

const totalPages = computed(() => {
  return Math.ceil(filteredRecords.value.length / recordsPerPage.value);
});

const displayedPages = computed(() => {
  const pages = [];
  let startPage = Math.max(1, currentPage.value - 2);
  let endPage = Math.min(totalPages.value, startPage + 4);
  
  if (endPage - startPage < 4) {
    startPage = Math.max(1, endPage - 4);
  }
  
  for (let i = startPage; i <= endPage; i++) {
    pages.push(i);
  }
  
  return pages;
});

const goToPage = (page) => {
  currentPage.value = page;
};

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

const viewDetails = async (record) => {
  try {
    // 如果已经有详细信息，就直接显示
    if (record.probability !== undefined) {
      selectedRecord.value = record;
      showDetailsModal.value = true;
      return;
    }
    
    // 否则从后端获取详细信息
    const response = await getDetectionRecordDetail(record.id);
    selectedRecord.value = response.data;
    showDetailsModal.value = true;
  } catch (error) {
    console.error('获取检测详情失败:', error);
    alert('获取检测详情失败，请稍后重试');
  }
};

const closeDetailsModal = () => {
  showDetailsModal.value = false;
};

const closeDeleteModal = () => {
  showDeleteModal.value = false;
};

const confirmDelete = () => {
  // Here you would make an API call to delete the record
  records.value = records.value.filter(record => record.id !== recordToDelete.value.id);
  closeDeleteModal();
};

const downloadResult = (record) => {
  // 实现下载功能
  console.log('下载结果:', record.id);
  alert('下载功能将在后续版本实现');
};

const deleteRecord = async (record) => {
  if (!confirm('确定要删除此记录吗？')) {
    return;
  }
  
  try {
    await deleteDetectionRecord(record.id);
    fetchRecords(); // 重新获取数据
    alert('记录已删除');
  } catch (error) {
    console.error('删除记录失败:', error);
    alert('删除记录失败，请稍后重试');
  }
};

const getTypeIcon = (typeId) => {
  switch (typeId) {
    case 1: return FileTextIcon;
    case 2: return ImageIcon;
    case 3: return VideoIcon;
    case 4: return MusicIcon;
    default: return FileIcon;
  }
};

const getTypeColor = (typeId) => {
  switch (typeId) {
    case 1: return 'bg-blue-500';
    case 2: return 'bg-purple-500';
    case 3: return 'bg-orange-500';
    case 4: return 'bg-green-500';
    default: return 'bg-gray-500';
  }
};

const getTypeName = (typeId) => {
  const type = detectionTypes.find(t => t.id === typeId);
  return type ? type.label : '未知类型';
};

onMounted(async () => {
  try {
    // 检查登录状态
    if (!auth.state.isLoggedIn) {
      console.log('用户未登录，跳转到登录页');
      localStorage.setItem('redirectPath', '/history');
      router.push('/login');
      return;
    }
    
    // 已登录，先获取用户信息
    const userData = await auth.fetchUserInfo();
    if (!userData) {
      console.log('获取用户信息失败，可能是token失效');
      localStorage.setItem('redirectPath', '/history');
      router.push('/login');
      return;
    }
    
    // 获取历史记录
    await fetchRecords();
  } catch (error) {
    console.error('历史记录页面初始化错误:', error);
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

/* History Content */
.history-content {
  padding: 2rem;
}

/* Filters */
.filters {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  margin-bottom: 2rem;
  background: white;
  padding: 1.5rem;
  border-radius: 0.75rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.filter-label {
  font-size: 0.875rem;
  font-weight: 500;
  color: #4a5568;
}

.filter-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.filter-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  font-size: 0.875rem;
  font-weight: 500;
  color: #4a5568;
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 0.375rem;
  cursor: pointer;
  transition: all 0.2s;
}

.filter-button:hover {
  background: #f7fafc;
}

.filter-button.active {
  color: white;
  background: linear-gradient(to right, #3182ce, #805ad5);
  border-color: transparent;
}

.filter-icon {
  width: 1rem;
  height: 1rem;
}

.time-select,
.result-select {
  padding: 0.5rem;
  border: 1px solid #e2e8f0;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  min-width: 150px;
}

/* Table */
.history-table-container {
  background: white;
  border-radius: 0.75rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  margin-bottom: 1.5rem;
}

.history-table {
  width: 100%;
  border-collapse: collapse;
}

.history-table th {
  background: #f7fafc;
  padding: 1rem;
  text-align: left;
  font-weight: 600;
  font-size: 0.875rem;
  color: #4a5568;
  border-bottom: 1px solid #e2e8f0;
}

.history-table td {
  padding: 1rem;
  border-bottom: 1px solid #e2e8f0;
  font-size: 0.875rem;
}

.history-table tr:last-child td {
  border-bottom: none;
}

.file-cell {
  min-width: 200px;
}

.file-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.file-icon {
  width: 2rem;
  height: 2rem;
  border-radius: 0.375rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.file-name {
  font-weight: 500;
}

.result-badge {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  border-radius: 9999px;
  font-size: 0.75rem;
  font-weight: 500;
}

.ai-badge {
  background-color: #fed7d7;
  color: #9b2c2c;
}

.human-badge {
  background-color: #c6f6d5;
  color: #276749;
}

.action-buttons {
  display: flex;
  gap: 0.5rem;
}

.action-button {
  width: 2rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #e2e8f0;
  border-radius: 0.375rem;
  color: #4a5568;
  background: white;
  cursor: pointer;
  transition: all 0.2s;
}

.action-button:hover {
  background: #f7fafc;
}

/* Pagination */
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.pagination-button {
  width: 2.5rem;
  height: 2.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #e2e8f0;
  border-radius: 0.375rem;
  background: white;
  color: #4a5568;
  cursor: pointer;
  transition: all 0.2s;
}

.pagination-button:hover:not(:disabled) {
  background: #f7fafc;
}

.pagination-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.pagination-pages {
  display: flex;
  gap: 0.25rem;
}

.page-button {
  width: 2.5rem;
  height: 2.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #e2e8f0;
  border-radius: 0.375rem;
  background: white;
  color: #4a5568;
  font-size: 0.875rem;
  cursor: pointer;
  transition: all 0.2s;
}

.page-button:hover {
  background: #f7fafc;
}

.page-button.active {
  color: white;
  background: linear-gradient(to right, #3182ce, #805ad5);
  border-color: transparent;
}

/* Modal */
.modal-overlay {
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

.details-modal {
  background: white;
  border-radius: 1rem;
  width: 100%;
  max-width: 700px;
  max-height: 90vh;
  overflow: auto;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.delete-modal {
  background: white;
  border-radius: 1rem;
  width: 100%;
  max-width: 450px;
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

.details-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.detail-item {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.detail-label {
  font-size: 0.875rem;
  color: #718096;
}

.detail-value {
  font-weight: 500;
}

.result-details {
  margin-bottom: 2rem;
}

.result-title,
.preview-title {
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

.result-probability {
  font-weight: 500;
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

.ai-progress {
  background-color: #e53e3e;
}

.human-progress {
  background-color: #38a169;
}

.result-analysis {
  font-size: 0.875rem;
  color: #4a5568;
  line-height: 1.5;
}

.file-preview {
  margin-bottom: 1rem;
}

.preview-container {
  background: #f7fafc;
  border-radius: 0.75rem;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 200px;
}

.preview-image {
  max-width: 100%;
  max-height: 300px;
  border-radius: 0.5rem;
}

.text-preview {
  width: 100%;
  max-height: 200px;
  overflow: auto;
  background: white;
  padding: 1rem;
  border-radius: 0.5rem;
  border: 1px solid #e2e8f0;
  font-size: 0.875rem;
  line-height: 1.5;
}

.file-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.placeholder-text {
  color: #718096;
  font-size: 0.875rem;
}

.delete-message {
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

.secondary-button {
  padding: 0.5rem 1.25rem;
  font-weight: 500;
  color: #4a5568;
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 0.375rem;
  cursor: pointer;
  transition: all 0.2s;
}

.secondary-button:hover {
  background: #f7fafc;
}

.primary-button {
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

.primary-button:hover {
  opacity: 0.9;
}

.danger-button {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.5rem 1.25rem;
  font-weight: 500;
  color: white;
  background: #e53e3e;
  border: none;
  border-radius: 0.375rem;
  cursor: pointer;
  transition: all 0.2s;
}

.danger-button:hover {
  background: #c53030;
}
</style>