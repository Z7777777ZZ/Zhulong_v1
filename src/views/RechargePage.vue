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
          <a href="#" class="nav-item">
            <layout-dashboard-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">仪表盘</span>
          </a>
          <a href="/recharge" class="nav-item">
            <search-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">充值中心</span>
          </a>
          <a href="/history" class="nav-item">
            <history-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">历史记录</span>
          </a>
          <a href="/profile" class="nav-item">
            <user-icon class="nav-icon" />
            <span v-if="!sidebarCollapsed" class="nav-text">个人中心</span>
          </a>
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
            <h1 class="page-title">充值中心</h1>
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
        
        <!-- Recharge Content -->
        <div class="recharge-content">
          <!-- Quota Summary -->
          <div class="quota-summary">
            <div class="quota-cards">
              <div class="quota-card">
                <div class="quota-icon-container">
                  <div class="quota-icon bg-blue-500">
                    <zap-icon class="h-6 w-6" />
                  </div>
                </div>
                <div class="quota-info">
                  <p class="quota-label">总额度</p>
                  <p class="quota-value">{{ userStats.totalQuota }}</p>
                </div>
              </div>
              
              <div class="quota-card">
                <div class="quota-icon-container">
                  <div class="quota-icon bg-purple-500">
                    <check-circle-icon class="h-6 w-6" />
                  </div>
                </div>
                <div class="quota-info">
                  <p class="quota-label">已用额度</p>
                  <p class="quota-value">{{ userStats.usedQuota }}</p>
                </div>
              </div>
              
              <div class="quota-card">
                <div class="quota-icon-container">
                  <div class="quota-icon bg-green-500">
                    <refresh-cw-icon class="h-6 w-6" />
                  </div>
                </div>
                <div class="quota-info">
                  <p class="quota-label">剩余额度</p>
                  <p class="quota-value">{{ userStats.remainingQuota }}</p>
                </div>
              </div>
              
              <div class="quota-card">
                <div class="quota-icon-container">
                  <div class="quota-icon bg-orange-500">
                    <calendar-icon class="h-6 w-6" />
                  </div>
                </div>
                <div class="quota-info">
                  <p class="quota-label">每日免费</p>
                  <p class="quota-value">{{ userStats.dailyFreeQuota }}</p>
                </div>
              </div>
            </div>
            
            <div class="quota-progress-container">
              <div class="quota-progress-header">
                <h3 class="quota-progress-title">额度使用情况</h3>
                <p class="quota-progress-subtitle">剩余额度: {{ userStats.remainingQuota }} / {{ userStats.totalQuota }}</p>
              </div>
              <div class="quota-progress">
                <div class="quota-progress-bar" :style="{ width: `${(userStats.usedQuota / userStats.totalQuota) * 100}%` }"></div>
              </div>
              <div class="quota-progress-stats">
                <div class="quota-stat">
                  <div class="quota-stat-label">
                    <span class="quota-stat-dot bg-blue-500"></span>
                    <span>已使用</span>
                  </div>
                  <span class="quota-stat-value">{{ userStats.usedQuota }} ({{ usedPercentage }}%)</span>
                </div>
                <div class="quota-stat">
                  <div class="quota-stat-label">
                    <span class="quota-stat-dot bg-gray-300"></span>
                    <span>未使用</span>
                  </div>
                  <span class="quota-stat-value">{{ userStats.remainingQuota }} ({{ remainingPercentage }}%)</span>
                </div>
              </div>
            </div>
          </div>
          
          <!-- Recharge Packages -->
          <div class="packages-container">
            <h2 class="section-title">选择充值套餐</h2>
            <div class="packages-grid">
              <div 
                v-for="(pkg, index) in packages" 
                :key="index"
                class="package-card"
                :class="{ 'selected': selectedPackage === index }"
                @click="selectPackage(index)"
              >
                <div class="package-header">
                  <h3 class="package-name">{{ pkg.name }}</h3>
                  <div v-if="pkg.popular" class="popular-badge">热门</div>
                </div>
                <div class="package-price">
                  <span class="price-currency">¥</span>
                  <span class="price-amount">{{ pkg.price }}</span>
                </div>
                <div class="package-quota">
                  <zap-icon class="h-5 w-5 text-yellow-500" />
                  <span>{{ pkg.quota }} 次额度</span>
                </div>
                <div class="package-features">
                  <div v-for="(feature, i) in pkg.features" :key="i" class="package-feature">
                    <check-icon class="feature-icon" />
                    <span>{{ feature }}</span>
                  </div>
                </div>
                <div class="package-validity">有效期：{{ pkg.validity }}</div>
                <button class="package-button" @click.stop="selectPackageAndProceed(index)">
                  立即购买
                </button>
              </div>
            </div>
          </div>
          
          <!-- Recent Transactions -->
          <div class="transactions-container">
            <div class="transactions-header">
              <h2 class="section-title">充值记录</h2>
              <div class="transactions-filter">
                <label class="filter-label">过滤：</label>
                <select v-model="transactionFilter" class="filter-select">
                  <option value="all">全部</option>
                  <option value="7days">最近7天</option>
                  <option value="30days">最近30天</option>
                  <option value="90days">最近90天</option>
                </select>
              </div>
            </div>
            
            <div class="transactions-table-container">
              <table class="transactions-table">
                <thead>
                  <tr>
                    <th>订单号</th>
                    <th>日期</th>
                    <th>套餐</th>
                    <th>额度</th>
                    <th>金额</th>
                    <th>支付方式</th>
                    <th>状态</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(transaction, index) in filteredTransactions" :key="index">
                    <td class="transaction-id">{{ transaction.id }}</td>
                    <td>{{ formatDate(transaction.date) }}</td>
                    <td>{{ transaction.package }}</td>
                    <td>{{ transaction.quota }}</td>
                    <td>{{ formatCurrency(transaction.amount) }}</td>
                    <td>
                      <div class="payment-method">
                        <component :is="getPaymentIcon(transaction.paymentMethod)" class="h-4 w-4" />
                        <span>{{ transaction.paymentMethod }}</span>
                      </div>
                    </td>
                    <td>
                      <span 
                        class="status-badge" 
                        :class="getStatusClass(transaction.status)"
                      >
                        {{ transaction.status }}
                      </span>
                    </td>
                  </tr>
                  <tr v-if="filteredTransactions.length === 0">
                    <td colspan="7" class="empty-state">
                      <inbox-icon class="h-6 w-6" />
                      <p>暂无充值记录</p>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <div class="transactions-pagination">
              <button 
                class="pagination-button" 
                :disabled="currentPage === 1"
                @click="currentPage--"
              >
                <chevron-left-icon class="h-4 w-4" />
              </button>
              <span class="pagination-info">第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
              <button 
                class="pagination-button" 
                :disabled="currentPage === totalPages"
                @click="currentPage++"
              >
                <chevron-right-icon class="h-4 w-4" />
              </button>
            </div>
          </div>
        </div>
      </main>
      
      <!-- Payment Modal -->
      <div v-if="showPaymentModal" class="modal-overlay" @click="closePaymentModal">
        <div class="payment-modal" @click.stop>
          <div class="modal-header">
            <h2 class="modal-title">确认订单</h2>
            <button class="close-button" @click="closePaymentModal">
              <x-icon class="h-5 w-5" />
            </button>
          </div>
          
          <div class="modal-content">
            <div class="order-summary">
              <h3 class="summary-title">订单详情</h3>
              <div class="summary-details">
                <div class="summary-item">
                  <span class="item-label">套餐名称</span>
                  <span class="item-value">{{ selectedPackageDetails.name }}</span>
                </div>
                <div class="summary-item">
                  <span class="item-label">额度数量</span>
                  <span class="item-value">{{ selectedPackageDetails.quota }} 次</span>
                </div>
                <div class="summary-item">
                  <span class="item-label">有效期</span>
                  <span class="item-value">{{ selectedPackageDetails.validity }}</span>
                </div>
                <div class="summary-item total">
                  <span class="item-label">支付金额</span>
                  <span class="item-value">¥ {{ selectedPackageDetails.price }}</span>
                </div>
              </div>
            </div>
            
            <div class="payment-methods">
              <h3 class="methods-title">选择支付方式</h3>
              <div class="methods-list">
                <div 
                  v-for="(method, index) in paymentMethods" 
                  :key="index"
                  class="payment-method-item"
                  :class="{ 'selected': selectedPaymentMethod === index }"
                  @click="selectedPaymentMethod = index"
                >
                  <div class="method-icon" :class="method.iconBg">
                    <component :is="method.icon" class="h-5 w-5" />
                  </div>
                  <div class="method-info">
                    <p class="method-name">{{ method.name }}</p>
                    <p v-if="method.description" class="method-description">{{ method.description }}</p>
                  </div>
                  <div class="method-check">
                    <check-circle-icon class="h-5 w-5" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="modal-footer">
            <button class="cancel-button" @click="closePaymentModal">取消</button>
            <button 
              class="pay-button" 
              :disabled="isProcessingPayment"
              @click="processPayment"
            >
              <loader-icon v-if="isProcessingPayment" class="animate-spin h-5 w-5 mr-2" />
              {{ isProcessingPayment ? '处理中...' : '确认支付' }}
            </button>
          </div>
        </div>
      </div>
      
      <!-- Success Modal -->
      <div v-if="showSuccessModal" class="modal-overlay">
        <div class="success-modal">
          <div class="success-icon">
            <check-circle-icon class="h-16 w-16 text-green-500" />
          </div>
          <h2 class="success-title">支付成功！</h2>
          <p class="success-message">您已成功购买 {{ selectedPackageDetails.quota }} 次额度</p>
          <div class="success-details">
            <div class="success-item">
              <span class="success-label">订单号</span>
              <span class="success-value">{{ paymentDetails.orderId }}</span>
            </div>
            <div class="success-item">
              <span class="success-label">支付金额</span>
              <span class="success-value">¥ {{ selectedPackageDetails.price }}</span>
            </div>
            <div class="success-item">
              <span class="success-label">支付时间</span>
              <span class="success-value">{{ formatDateTime(paymentDetails.paymentTime) }}</span>
            </div>
            <div class="success-item">
              <span class="success-label">支付方式</span>
              <span class="success-value">{{ paymentMethods[selectedPaymentMethod].name }}</span>
            </div>
          </div>
          <button class="success-button" @click="closeSuccessModal">完成</button>
        </div>
      </div>
    </div>
    </template>
    
    <script setup>
    import { ref, computed, onMounted } from 'vue';
    import { useRouter } from 'vue-router';
    import { 
      ChevronLeft as ChevronLeftIcon,
      ChevronRight as ChevronRightIcon,
      LayoutDashboard as LayoutDashboardIcon,
      Search as SearchIcon,
      History as HistoryIcon,
      User as UserIcon,
      CreditCard as CreditCardIcon,
      LogOut as LogOutIcon,
      Bell as BellIcon,
      Zap as ZapIcon,
      CheckCircle as CheckCircleIcon,
      RefreshCw as RefreshCwIcon,
      Calendar as CalendarIcon,
      Check as CheckIcon,
      X as XIcon,
      Loader as LoaderIcon,
      Menu as MenuIcon,
      Wallet as WalletIcon,
      Inbox as InboxIcon
    } from 'lucide-vue-next';
    // eslint-disable-next-line no-unused-vars
    import { getAllPackages, createRechargeOrder, completeRechargeOrder, getUserTransactions } from '../api/recharge';
    import { getUserInfo } from '../api/auth';

    const router = useRouter();

    // Sidebar state
    const sidebarCollapsed = ref(false);
    const sidebarOpen = ref(false);
    
    const toggleSidebar = () => {
      sidebarCollapsed.value = !sidebarCollapsed.value;
    };
    
    const toggleMobileSidebar = () => {
      sidebarOpen.value = !sidebarOpen.value;
    };
    
    // User data and stats
    const userProfile = ref({
      username: '',
      email: '',
      avatar: ''
    });
    
    const userStats = ref({
      totalQuota: 0,
      usedQuota: 0,
      remainingQuota: 0,
      dailyFreeQuota: 10,
      usedCount: 0,
      dailyLimit: 10
    });
    
    // Packages and transactions
    const packages = ref([]);
    const transactions = ref([]);
    const selectedPackage = ref(null);
    const transactionFilter = ref('all');
    const loading = ref(true);
    // const paymentLoading = ref(false);
    const showPaymentModal = ref(false);
    const selectedPaymentMethod = ref('alipay');
    // const paymentStatus = ref('pending');
    const showSuccessModal = ref(false);
    
    // Computed properties
    const usedPercentage = computed(() => {
      if (userStats.value.totalQuota === 0) return 0;
      return Math.round((userStats.value.usedQuota / userStats.value.totalQuota) * 100);
    });
    
    const remainingPercentage = computed(() => {
      if (userStats.value.totalQuota === 0) return 0;
      return Math.round((userStats.value.remainingQuota / userStats.value.totalQuota) * 100);
    });
    
    const filteredTransactions = computed(() => {
      return transactions.value;
    });
    
    // Functions
    const handleLogout = () => {
      router.push('/');
    };
    
    const fetchUserData = async () => {
      loading.value = true;
      
      try {
        const response = await getUserInfo();
        const data = response.data;
        
        userProfile.value.username = data.username;
        userProfile.value.email = data.email;
        userProfile.value.avatar = data.avatar;
        
        userStats.value = {
          totalQuota: data.totalQuota || 0,
          remainingQuota: data.remainingQuota || 0,
          usedCount: data.usedCount || 0,
          dailyLimit: data.dailyLimit || 0
        };
      } catch (err) {
        console.error('获取用户信息失败:', err);
      } finally {
        loading.value = false;
      }
    };
    
    const fetchPackages = async () => {
      loading.value = true;
      
      try {
        const response = await getAllPackages();
        packages.value = response.data;
      } catch (error) {
        console.error('获取充值套餐失败:', error);
        error.value = '获取充值套餐失败，请稍后重试';
      } finally {
        loading.value = false;
      }
    };
    
    const fetchTransactions = async () => {
      try {
        const response = await getUserTransactions(transactionFilter.value);
        transactions.value = response.data;
      } catch (error) {
        console.error('获取交易记录失败:', error);
      }
    };
    
    // 选择充值套餐
    const selectPackage = (pkg) => {
      // 清除之前的选择
      packages.value.forEach(p => p.selected = false);
      
      // 设置当前选择
      pkg.selected = true;
      selectedPackage.value = pkg;
    };
    
    const selectPackageAndProceed = (index) => {
      selectPackage(packages.value[index]);
      showPaymentModal.value = true;
    };
    
    const closePaymentModal = () => {
      showPaymentModal.value = false;
    };
    
    // const confirmPayment = () => {
    //   // 支付确认逻辑
    //   alert(`确认支付${selectedPackage.value.price}元`);
      
    //   // 更新状态
    //   paymentStatus.value = 'completed';
    //   showSuccessModal.value = true;
    // };
    
    // 格式化交易记录的时间
    // eslint-disable-next-line no-unused-vars
    const formatTransactionTime = (timeString) => {
      const date = new Date(timeString);
      return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      });
    };
    
    // 格式化金额
    const formatCurrency = (amount) => {
      return `¥${amount.toFixed(2)}`;
    };
    
    // eslint-disable-next-line no-unused-vars
    const getPaymentIcon = (method) => {
      switch (method) {
        case '支付宝':
        case 'alipay':
          return CreditCardIcon;
        case '微信支付':
        case 'wechat':
          return WalletIcon;
        default:
          return CreditCardIcon;
      }
    };
    
    const getStatusClass = (status) => {
      switch (status) {
        case '已完成':
          return 'status-success';
        case '待支付':
          return 'status-pending';
        case '已取消':
          return 'status-failed';
        default:
          return 'status-pending';
      }
    };
    
    // eslint-disable-next-line no-unused-vars
    const handleTransactionFilterChange = (newVal) => {
      transactionFilter.value = newVal;
      fetchTransactions();
    };
    
    // 获取包的使用说明
    // eslint-disable-next-line no-unused-vars
    const getPackageDescription = (pkg) => {
      switch (pkg.type) {
        case 1:
          return `可用于检测${pkg.quota}次`;
        case 2:
          return `可用于检测${pkg.quota}次，有效期${pkg.validDays}天`;
        default:
          return `包含${pkg.quota}次检测`;
      }
    };
    
    // Initialize data
    onMounted(() => {
      fetchUserData();
      fetchPackages();
      fetchTransactions();
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
    
    /* Recharge Content */
    .recharge-content {
      padding: 2rem;
      display: flex;
      flex-direction: column;
      gap: 2rem;
    }
    
    /* Quota Summary */
    .quota-summary {
      display: grid;
      grid-template-columns: 1fr;
      gap: 1.5rem;
    }
    
    @media (min-width: 1024px) {
      .quota-summary {
        grid-template-columns: 2fr 3fr;
      }
    }
    
    .quota-cards {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 1rem;
    }
    
    .quota-card {
      background: white;
      border-radius: 0.75rem;
      padding: 1.5rem;
      display: flex;
      align-items: center;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }
    
    .quota-icon-container {
      margin-right: 1rem;
    }
    
    .quota-icon {
      width: 3rem;
      height: 3rem;
      border-radius: 0.5rem;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
    }
    
    .quota-info {
      flex: 1;
    }
    
    .quota-label {
      font-size: 0.875rem;
      color: #718096;
      margin-bottom: 0.25rem;
    }
    
    .quota-value {
      font-size: 1.5rem;
      font-weight: 700;
    }
    
    .quota-progress-container {
      background: white;
      border-radius: 0.75rem;
      padding: 1.5rem;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }
    
    .quota-progress-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.5rem;
    }
    
    .quota-progress-title {
      font-size: 1.25rem;
      font-weight: 600;
    }
    
    .quota-progress-subtitle {
      font-size: 0.875rem;
      color: #718096;
    }
    
    .quota-progress {
      height: 1rem;
      background: #edf2f7;
      border-radius: 0.5rem;
      overflow: hidden;
      margin-bottom: 1rem;
    }
    
    .quota-progress-bar {
      height: 100%;
      background: linear-gradient(90deg, #3182ce, #805ad5);
      border-radius: 0.5rem;
    }
    
    .quota-progress-stats {
      display: flex;
      justify-content: space-between;
    }
    
    .quota-stat {
      display: flex;
      flex-direction: column;
      gap: 0.25rem;
    }
    
    .quota-stat-label {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      font-size: 0.875rem;
      color: #4a5568;
    }
    
    .quota-stat-dot {
      width: 0.75rem;
      height: 0.75rem;
      border-radius: 9999px;
    }
    
    .quota-stat-value {
      font-size: 0.875rem;
      font-weight: 500;
    }
    
    /* Packages */
    .packages-container {
      background: white;
      border-radius: 0.75rem;
      padding: 1.5rem;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }
    
    .section-title {
      font-size: 1.25rem;
      font-weight: 600;
      margin-bottom: 1.5rem;
    }
    
    .packages-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 1.5rem;
    }
    
    .package-card {
      border-radius: 0.75rem;
      border: 1px solid #e2e8f0;
      padding: 1.5rem;
      display: flex;
      flex-direction: column;
      transition: all 0.3s;
      cursor: pointer;
      position: relative;
    }
    
    .package-card:hover {
      border-color: #3182ce;
      transform: translateY(-5px);
      box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
    }
    
    .package-card.selected {
      border-color: #3182ce;
      box-shadow: 0 0 0 2px rgba(49, 130, 206, 0.3), 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
    }
    
    .package-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1rem;
    }
    
    .package-name {
      font-size: 1.125rem;
      font-weight: 600;
    }
    
    .popular-badge {
      background: #fed7d7;
      color: #9b2c2c;
      font-size: 0.75rem;
      font-weight: 500;
      padding: 0.25rem 0.5rem;
      border-radius: 9999px;
    }
    
    .package-price {
      margin-bottom: 1rem;
    }
    
    .price-currency {
      font-size: 1.25rem;
      font-weight: 500;
      color: #4a5568;
    }
    
    .price-amount {
      font-size: 2.5rem;
      font-weight: 700;
      color: #2d3748;
    }
    
    .package-quota {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      padding: 0.5rem 0;
      border-top: 1px solid #e2e8f0;
      border-bottom: 1px solid #e2e8f0;
      margin-bottom: 1rem;
      font-weight: 500;
    }
    
    .package-features {
      margin-bottom: 1.5rem;
      flex: 1;
    }
    
    .package-feature {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      margin-bottom: 0.5rem;
      font-size: 0.875rem;
      color: #4a5568;
    }
    
    .feature-icon {
      color: #38a169;
      width: 1rem;
      height: 1rem;
    }
    
    .package-validity {
      font-size: 0.875rem;
      color: #718096;
      margin-bottom: 1.5rem;
    }
    
    .package-button {
      padding: 0.5rem 1.25rem;
      font-weight: 500;
      color: white;
      background: linear-gradient(to right, #3182ce, #805ad5);
      border: none;
      border-radius: 0.375rem;
      cursor: pointer;
      transition: all 0.2s;
    }
    
    .package-button:hover {
      opacity: 0.9;
    }
    
    /* Transactions */
    .transactions-container {
      background: white;
      border-radius: 0.75rem;
      padding: 1.5rem;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }
    
    .transactions-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.5rem;
    }
    
    .transactions-filter {
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }
    
    .filter-label {
      font-size: 0.875rem;
      color: #718096;
    }
    
    .filter-select {
      padding: 0.25rem 0.5rem;
      border: 1px solid #e2e8f0;
      border-radius: 0.375rem;
      font-size: 0.875rem;
      color: #4a5568;
      background: white;
    }
    
    .transactions-table-container {
      overflow-x: auto;
    }
    
    .transactions-table {
      width: 100%;
      border-collapse: collapse;
    }
    
    .transactions-table th {
      text-align: left;
      padding: 0.75rem 1rem;
      font-size: 0.75rem;
      font-weight: 500;
      color: #718096;
      text-transform: uppercase;
      border-bottom: 1px solid #e2e8f0;
    }
    
    .transactions-table td {
      padding: 0.75rem 1rem;
      font-size: 0.875rem;
      border-bottom: 1px solid #e2e8f0;
      color: #4a5568;
    }
    
    .transaction-id {
      font-family: monospace;
    }
    
    .payment-method {
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }
    
    .status-badge {
      display: inline-block;
      padding: 0.25rem 0.5rem;
      border-radius: 9999px;
      font-size: 0.75rem;
      font-weight: 500;
    }
    
    .empty-state {
      text-align: center;
      padding: 3rem 0;
      color: #718096;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 0.5rem;
    }
    
    .transactions-pagination {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 1rem;
      margin-top: 1.5rem;
    }
    
    .pagination-button {
      width: 2rem;
      height: 2rem;
      display: flex;
      align-items: center;
      justify-content: center;
      border: 1px solid #e2e8f0;
      border-radius: 0.375rem;
      background: white;
      color: #4a5568;
      cursor: pointer;
    }
    
    .pagination-button:hover:not(:disabled) {
      background: #f7fafc;
    }
    
    .pagination-button:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
    
    .pagination-info {
      font-size: 0.875rem;
      color: #718096;
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
    
    .payment-modal {
      background: white;
      border-radius: 1rem;
      width: 100%;
      max-width: 500px;
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
    
    .order-summary {
      margin-bottom: 2rem;
    }
    
    .summary-title {
      font-size: 1.125rem;
      font-weight: 600;
      margin-bottom: 1rem;
    }
    
    .summary-details {
      display: flex;
      flex-direction: column;
      gap: 0.75rem;
    }
    
    .summary-item {
      display: flex;
      justify-content: space-between;
      font-size: 0.875rem;
    }
    
    .item-label {
      color: #718096;
    }
    
    .item-value {
      font-weight: 500;
    }
    
    .summary-item.total {
      padding-top: 0.75rem;
      margin-top: 0.75rem;
      border-top: 1px solid #e2e8f0;
      font-weight: 600;
    }
    
    .payment-methods {
      margin-bottom: 1rem;
    }
    
    .methods-title {
      font-size: 1.125rem;
      font-weight: 600;
      margin-bottom: 1rem;
    }
    
    .methods-list {
      display: flex;
      flex-direction: column;
      gap: 0.75rem;
    }
    
    .payment-method-item {
      display: flex;
      align-items: center;
      gap: 1rem;
      padding: 1rem;
      border: 1px solid #e2e8f0;
      border-radius: 0.5rem;
      cursor: pointer;
      transition: all 0.2s;
    }
    
    .payment-method-item:hover {
      border-color: #cbd5e0;
    }
    
    .payment-method-item.selected {
      border-color: #3182ce;
      background: #f0f9ff;
    }
    
    .method-icon {
      width: 2.5rem;
      height: 2.5rem;
      border-radius: 0.5rem;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
    }
    
    .method-info {
      flex: 1;
    }
    
    .method-name {
      font-weight: 500;
    }
    
    .method-description {
      font-size: 0.75rem;
      color: #718096;
    }
    
    .method-check {
      color: #3182ce;
      opacity: 0;
      transition: opacity 0.2s;
    }
    
    .payment-method-item.selected .method-check {
      opacity: 1;
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
    
    .pay-button {
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
    
    .pay-button:hover:not(:disabled) {
      opacity: 0.9;
    }
    
    .pay-button:disabled {
      opacity: 0.7;
      cursor: not-allowed;
    }
    
    /* Success Modal */
    .success-modal {
      background: white;
      border-radius: 1rem;
      padding: 2rem;
      width: 100%;
      max-width: 400px;
      text-align: center;
      box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
    }
    
    .success-icon {
      margin: 0 auto 1.5rem;
    }
    
    .success-title {
      font-size: 1.5rem;
      font-weight: 700;
      margin-bottom: 0.5rem;
      color: #2d3748;
    }
    
    .success-message {
      font-size: 1rem;
      color: #718096;
      margin-bottom: 1.5rem;
    }
    
    .success-details {
      background: #f7fafc;
      border-radius: 0.5rem;
      padding: 1rem;
      display: flex;
      flex-direction: column;
      gap: 0.75rem;
      margin-bottom: 1.5rem;
      text-align: left;
    }
    
    .success-item {
      display: flex;
      justify-content: space-between;
      font-size: 0.875rem;
    }
    
    .success-label {
      color: #718096;
    }
    
    .success-value {
      font-weight: 500;
    }
    
    .success-button {
      width: 100%;
      padding: 0.75rem;
      font-weight: 500;
      color: white;
      background: linear-gradient(to right, #3182ce, #805ad5);
      border: none;
      border-radius: 0.375rem;
      cursor: pointer;
      transition: all 0.2s;
    }
    
    .success-button:hover {
      opacity: 0.9;
    }
    </style>