<template>
  <div class="app-container">
    <!-- 使用NavBar组件 -->
    <NavBar />

    <!-- Hero Section -->
    <section class="hero-section">
      <div class="container mx-auto px-4 py-20 flex flex-col md:flex-row items-center">
        <div class="md:w-1/2 mb-10 md:mb-0">
          <h1 class="text-4xl md:text-5xl font-bold mb-6 leading-tight">
            <span class="bg-gradient-to-r from-blue-500 to-purple-600 bg-clip-text text-transparent">AI生成内容检测</span>
            <br />智能、精准、高效
          </h1>
          <p class="text-lg mb-8 text-gray-600 max-w-lg">
            我们的平台能够精准检测图片、文本、视频和音频是否由AI生成，为您的内容审核提供强大支持。
          </p>
          <div class="flex flex-col sm:flex-row gap-4">
            <button @click="goToWorkspace" class="try-now-btn">
              <zap-icon class="h-5 w-5 mr-2" />
              立即试用
            </button>
            <router-link to="/product" class="learn-more-btn">
              <info-icon class="h-5 w-5 mr-2" />
              了解更多
            </router-link>
          </div>
        </div>
        <div class="md:w-1/2 relative">
          <div class="hero-image-container">
            <img src="/Home_pic1.jpg?height=400&width=500" alt="AI Detection" class="hero-image" />
            <div class="floating-badge top-10 left-0">
              <image-icon class="h-5 w-5 mr-2" />
              图片检测
            </div>
            <div class="floating-badge top-20 right-0">
              <file-text-icon class="h-5 w-5 mr-2" />
              文本检测
            </div>
            <div class="floating-badge bottom-20 left-10">
              <video-icon class="h-5 w-5 mr-2" />
              视频检测
            </div>
            <div class="floating-badge bottom-10 right-10">
              <music-icon class="h-5 w-5 mr-2" />
              音频检测
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section class="features-section">
      <div class="container mx-auto px-4 py-16">
        <h2 class="section-title">核心功能</h2>
        <p class="section-subtitle">全方位检测AI生成内容，为您的内容审核保驾护航</p>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mt-12">
          <div class="feature-card" v-for="(feature, index) in features" :key="index">
            <div class="feature-icon-container">
              <component :is="feature.icon" class="h-6 w-6" />
            </div>
            <h3 class="feature-title">{{ feature.title }}</h3>
            <p class="feature-description">{{ feature.description }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- How It Works -->
    <section class="how-it-works-section">
      <div class="container mx-auto px-4 py-16">
        <h2 class="section-title">使用流程</h2>
        <p class="section-subtitle">简单三步，轻松检测AI生成内容</p>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mt-12">
          <div class="step-card" v-for="(step, index) in steps" :key="index">
            <div class="step-number">{{ index + 1 }}</div>
            <h3 class="step-title">{{ step.title }}</h3>
            <p class="step-description">{{ step.description }}</p>
            <img :src="step.image" :alt="step.title" class="step-image" />
          </div>
        </div>
      </div>
    </section>

    <!-- Stats Section -->
    <section class="stats-section">
      <div class="container mx-auto px-4 py-16">
        <div class="stats-grid">
          <div class="stat-card" v-for="(stat, index) in stats" :key="index">
            <div class="stat-value">{{ stat.value }}</div>
            <div class="stat-label">{{ stat.label }}</div>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="cta-section">
      <div class="container mx-auto px-4 py-16 text-center">
        <h2 class="text-3xl md:text-4xl font-bold mb-6 text-white">准备好开始使用了吗？</h2>
        <p class="text-lg mb-8 text-white/80 max-w-2xl mx-auto">
          注册账号，立即体验我们强大的AI生成内容检测功能，每天免费10次检测额度。
        </p>
        <router-link to="/register" class="cta-button">
          <user-plus-icon class="h-5 w-5 mr-2" />
          免费注册
        </router-link>
      </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
      <div class="container mx-auto px-4 py-12">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <div class="flex items-center mb-6">
              <!---->
              <img src="/logo1_transparent.svg?height=40&width=40" alt="Logo" class="h-10 w-10" />
              <span class="ml-2 text-xl font-bold text-white">AIGC Detector</span>
            </div>
            <p class="text-gray-400 mb-6">
              专业的AI生成内容检测平台，为您提供精准、高效的检测服务。
            </p>
            <!--<div class="flex space-x-4">
              <a href="#" class="text-gray-400 hover:text-white">
                <twitter-icon class="h-5 w-5" />
              </a>
              <a href="#" class="text-gray-400 hover:text-white">
                <github-icon class="h-5 w-5" />
              </a>
              <a href="#" class="text-gray-400 hover:text-white">
                <linkedin-icon class="h-5 w-5" />
              </a>
            </div>-->
            
          </div>
          
          <div>
            <h3 class="text-lg font-semibold text-white mb-6">产品</h3>
            <ul class="space-y-4">
              <li><a href="/product" class="text-gray-400 hover:text-white" >图片检测</a></li>
              <li><a href="/product" class="text-gray-400 hover:text-white" >文本检测</a></li>
              <li><a href="/product" class="text-gray-400 hover:text-white" >视频检测</a></li>
              <li><a href="/product" class="text-gray-400 hover:text-white" >音频检测</a></li>
            </ul>
          </div>
          
          <div>
            <h3 class="text-lg font-semibold text-white mb-6">关于</h3>
            <ul class="space-y-4">
              <li><a href="/about" class="text-gray-400 hover:text-white">关于我们</a></li>
              <li><a href="/contact" class="text-gray-400 hover:text-white">联系我们</a></li>
              <!--<li><a href="#" class="text-gray-400 hover:text-white">隐私政策</a></li>-->
              <li><a href="/service" class="text-gray-400 hover:text-white">服务条款</a></li>
            </ul>
          </div>
          
          <div>
            <h3 class="text-lg font-semibold text-white mb-6">联系我们</h3>
            <ul class="space-y-4">
              <li class="flex items-center text-gray-400">
                <mail-icon class="h-5 w-5 mr-2" />
                zhulong@aigcdetector.com
              </li>
              <li class="flex items-center text-gray-400">
                <phone-icon class="h-5 w-5 mr-2" />
                +86 123 4567 8910
              </li>
              <li class="flex items-center text-gray-400">
                <map-pin-icon class="h-5 w-5 mr-2" />
                浙江省烛龙人工智能有限公司
              </li>
            </ul>
          </div>
        </div>
        
        <div class="border-t border-gray-800 mt-12 pt-8 text-center text-gray-400">
          <p>© 2025 AIGC Detector. 保留所有权利。</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { 
  // Menu as MenuIcon, 
  Zap as ZapIcon, 
  Info as InfoIcon,
  Image as ImageIcon,
  FileText as FileTextIcon,
  Video as VideoIcon,
  Music as MusicIcon,
  UserPlus as UserPlusIcon,
  // Twitter as TwitterIcon,
  // Github as GithubIcon,
  // Linkedin as LinkedinIcon,
  Mail as MailIcon,
  Phone as PhoneIcon,
  MapPin as MapPinIcon
} from 'lucide-vue-next';
import NavBar from '../components/NavBar.vue';

// 注入auth服务
const auth = inject('auth');
const router = useRouter();

// 计算属性：用户是否已登录
const isLoggedIn = computed(() => auth.state.isLoggedIn);
// 计算属性：当前用户信息
// const user = computed(() => auth.state.user);

// 在组件挂载时检查认证状态
onMounted(() => {
  auth.checkAuth();
});

// 处理登出
// const handleLogout = () => {
//   auth.logout();
// };

// 处理工作台跳转
const goToWorkspace = () => {
  if (isLoggedIn.value) {
    router.push('/workspace');
  } else {
    // 保存重定向路径
    localStorage.setItem('redirectPath', '/workspace');
    router.push('/login');
  }
};


// const mobileMenuOpen = ref(false);

// // const toggleMobileMenu = () => {
// //   mobileMenuOpen.value = !mobileMenuOpen.value;
// // };

const features = [
  {
    icon: ImageIcon,
    title: '图片检测',
    description: '精准识别AI生成的图片，支持多种图片格式，检测准确率高达95%以上。'
  },
  {
    icon: FileTextIcon,
    title: '文本检测',
    description: '快速分析文本内容，识别是否由AI生成，支持多语言检测。'
  },
  {
    icon: VideoIcon,
    title: '视频检测',
    description: '对视频内容进行深度分析，检测是否含有AI生成的画面或音频。'
  },
  {
    icon: MusicIcon,
    title: '音频检测',
    description: '识别语音、音乐等音频内容是否由AI生成，支持多种音频格式。'
  }
];

const steps = [
  {
    title: '注册账号',
    description: '简单注册账号，每天免费获得10次检测机会。',
    image: '/Home_pic4.jpg?height=150&width=200'
  },
  {
    title: '上传内容',
    description: '上传需要检测的图片、文本、视频或音频文件。',
    image: '/Home_pic2.jpg?height=150&width=200'
  },
  {
    title: '获取结果',
    description: '系统快速分析并给出详细的检测结果和AI生成概率。',
    image: '/Home_pic3.jpg?height=150&width=200'
  }
];

const stats = [
  { value: '99.8%', label: '检测准确率' },
  { value: '500万+', label: '检测次数' },
  { value: '10万+', label: '注册用户' },
  { value: '3秒', label: '平均响应时间' }
];
</script>

<style scoped>
/* Base Styles */
.app-container {
  font-family: 'Inter', sans-serif;
  color: #1a202c;
  background-color: #f7f9fc;
}

/* Hero Section */
.hero-section {
  background: linear-gradient(135deg, #f6f9fc 0%, #edf2f7 100%);
  overflow: hidden;
}

.hero-image-container {
  position: relative;
  animation: float 6s ease-in-out infinite;
}

.hero-image {
  border-radius: 1rem;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.floating-badge {
  position: absolute;
  display: flex;
  align-items: center;
  padding: 0.5rem 1rem;
  background-color: white;
  border-radius: 9999px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  font-weight: 500;
  font-size: 0.875rem;
  animation: pulse 3s infinite;
}

.try-now-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.75rem 1.5rem;
  font-weight: 600;
  color: white;
  background: linear-gradient(to right, #3182ce, #805ad5);
  border-radius: 0.5rem;
  transition: all 0.3s;
  box-shadow: 0 4px 6px -1px rgba(49, 130, 206, 0.4);
}

.try-now-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 15px -3px rgba(49, 130, 206, 0.4);
}

.learn-more-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.75rem 1.5rem;
  font-weight: 600;
  color: #4a5568;
  background-color: white;
  border: 1px solid #e2e8f0;
  border-radius: 0.5rem;
  transition: all 0.3s;
}

.learn-more-btn:hover {
  background-color: #f7fafc;
}

/* Features Section */
.features-section {
  background-color: white;
}

.section-title {
  text-align: center;
  font-size: 2.25rem;
  font-weight: 700;
  margin-bottom: 1rem;
  background: linear-gradient(to right, #3182ce, #805ad5);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

.section-subtitle {
  text-align: center;
  font-size: 1.125rem;
  color: #718096;
  max-width: 36rem;
  margin: 0 auto;
}

.feature-card {
  background-color: white;
  border-radius: 1rem;
  padding: 2rem;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s, box-shadow 0.3s;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.feature-icon-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 3rem;
  height: 3rem;
  border-radius: 0.75rem;
  background: linear-gradient(135deg, #3182ce 0%, #805ad5 100%);
  color: white;
  margin-bottom: 1.5rem;
}

.feature-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 0.75rem;
}

.feature-description {
  color: #718096;
  line-height: 1.625;
}

/* How It Works Section */
.how-it-works-section {
  background-color: #f7f9fc;
}

.step-card {
  background-color: white;
  border-radius: 1rem;
  padding: 2rem;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
  text-align: center;
  position: relative;
}

.step-number {
  position: absolute;
  top: -1.25rem;
  left: 50%;
  transform: translateX(-50%);
  width: 2.5rem;
  height: 2.5rem;
  background: linear-gradient(135deg, #3182ce 0%, #805ad5 100%);
  color: white;
  border-radius: 9999px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 1.25rem;
}

.step-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-top: 1rem;
  margin-bottom: 0.75rem;
}

.step-description {
  color: #718096;
  margin-bottom: 1.5rem;
}

.step-image {
  border-radius: 0.5rem;
  margin: 0 auto;
}

/* Stats Section */
.stats-section {
  background: linear-gradient(135deg, #3182ce 0%, #805ad5 100%);
  color: white;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 2rem;
}

.stat-card {
  text-align: center;
}

.stat-value {
  font-size: 3rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.stat-label {
  font-size: 1.125rem;
  opacity: 0.9;
}

/* CTA Section */
.cta-section {
  background: linear-gradient(135deg, #2c5282 0%, #6b46c1 100%);
  color: white;
}

.cta-button {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.75rem 2rem;
  font-weight: 600;
  color: #2d3748;
  background-color: white;
  border-radius: 0.5rem;
  transition: all 0.3s;
  margin: 0 auto;
}

.cta-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 15px -3px rgba(255, 255, 255, 0.2);
}

/* Footer */
.footer {
  background-color: #1a202c;
  color: white;
}

/* Animations */
@keyframes float {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
  100% {
    transform: translateY(0px);
  }
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.05);
    opacity: 0.9;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}
</style>