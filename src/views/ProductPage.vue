<template>
    <div class="page-container">
      <!-- 使用NavBar组件 -->
      <NavBar />
      
      <!-- 页面内容 -->
      <main class="main-content">
        <section class="hero-section">
          <h1 class="page-title">产品介绍</h1>
          <p class="subtitle">了解我们的AI生成内容检测技术</p>
        </section>
        
        <section class="content-section">
          <div class="feature-grid">
            <div class="feature-card">
              <div class="feature-icon bg-blue-500">
                <image-icon class="icon" />
              </div>
              <h3 class="feature-title">图片检测</h3>
              <p class="feature-description">
                我们的图片检测技术能够精确识别AI生成的图像，检测特征包括不自然的纹理、不一致的光影效果和过于完美的细节等。
              </p>
            </div>
            
            <div class="feature-card">
              <div class="feature-icon bg-purple-500">
                <file-text-icon class="icon" />
              </div>
              <h3 class="feature-title">文本检测</h3>
              <p class="feature-description">
                文本检测系统能够分析文本结构、表达方式和语言特征，有效区分AI生成的内容和人工创作的内容。
              </p>
            </div>
            
            <div class="feature-card">
              <div class="feature-icon bg-orange-500">
                <video-icon class="icon" />
              </div>
              <h3 class="feature-title">视频检测</h3>
              <p class="feature-description">
                视频检测功能可以识别AI生成视频中的不自然动作过渡、面部表情不协调等特征，提供全面的分析报告。
              </p>
            </div>
            
            <div class="feature-card">
              <div class="feature-icon bg-green-500">
                <music-icon class="icon" />
              </div>
              <h3 class="feature-title">音频检测</h3>
              <p class="feature-description">
                音频检测系统通过分析声音特征、语调变化和情感表达，准确判断音频内容是否由AI生成。
              </p>
            </div>
          </div>
        </section>
        
        <section class="content-section">
          <h2 class="section-title">技术优势</h2>
          <div class="advantage-list">
            <div class="advantage-item">
              <check-circle-icon class="advantage-icon" />
              <div class="advantage-content">
                <h4 class="advantage-title">高精度检测</h4>
                <p class="advantage-description">基于深度学习的检测算法，准确率高达95%以上</p>
              </div>
            </div>
            
            <div class="advantage-item">
              <check-circle-icon class="advantage-icon" />
              <div class="advantage-content">
                <h4 class="advantage-title">多模态支持</h4>
                <p class="advantage-description">支持图片、文本、视频和音频等多种内容格式的检测</p>
              </div>
            </div>
            
            <div class="advantage-item">
              <check-circle-icon class="advantage-icon" />
              <div class="advantage-content">
                <h4 class="advantage-title">实时分析</h4>
                <p class="advantage-description">快速处理检测请求，提供即时的分析结果</p>
              </div>
            </div>
            
            <div class="advantage-item">
              <check-circle-icon class="advantage-icon" />
              <div class="advantage-content">
                <h4 class="advantage-title">详细报告</h4>
                <p class="advantage-description">生成全面的检测报告，包含详细的分析和建议</p>
              </div>
            </div>
          </div>
        </section>
        
        <section class="cta-section">
          <h2 class="cta-title">立即体验我们的检测服务</h2>
          <p class="cta-description">注册账号，获得免费检测次数，体验AI内容检测的强大功能</p>
          <router-link to="/register" class="cta-button">
            免费注册
          </router-link>
        </section>
      </main>
      
      <!-- 页脚 -->
      <footer class="footer">
        <p class="copyright">© 2025 AIGC Detector. 保留所有权利。</p>
      </footer>
    </div>
  </template>
  
  <script setup>
  // import { ref, computed, onMounted } from 'vue';
  // import { useRouter } from 'vue-router';
  import { 
    Image as ImageIcon,
    FileText as FileTextIcon,
    Video as VideoIcon,
    Music as MusicIcon,
    CheckCircle as CheckCircleIcon
    // Menu as MenuIcon
  } from 'lucide-vue-next';
  // import auth from '../store/auth';
  import NavBar from '../components/NavBar.vue';
  
  // const router = useRouter();
  // const mobileMenuOpen = ref(false);
  
  // const isLoggedIn = computed(() => auth.state.isLoggedIn);
  // const user = computed(() => auth.state.user || {});
  
  // const toggleMobileMenu = () => {
  //   mobileMenuOpen.value = !mobileMenuOpen.value;
  // };
  
  // const handleLogout = () => {
  //   auth.logout();
  //   router.push('/');
  // };
  
  // onMounted(() => {
  //   // 初始化代码
  // });
  </script>
  
  <style scoped>
  .page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }
  
  .header {
    background-color: white;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    position: sticky;
    top: 0;
    z-index: 10;
  }
  
  .logo-container {
    display: flex;
    align-items: center;
  }
  
  .mobile-menu {
    background-color: white;
    border-top: 1px solid #e2e8f0;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  }
  
  .nav-link {
    color: #4a5568;
    text-decoration: none;
    font-weight: 500;
    padding: 0.5rem 0;
    position: relative;
    transition: color 0.2s;
  }
  
  .nav-link:hover, .nav-link.active {
    color: #3182ce;
  }
  
  .nav-link.active::after {
    content: '';
    position: absolute;
    bottom: -2px;
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
    padding: 0.5rem 1rem;
    font-weight: 500;
    color: #3182ce;
    text-decoration: none;
    border: 1px solid #3182ce;
    border-radius: 0.375rem;
    transition: all 0.2s;
  }
  
  .login-btn:hover {
    background-color: #ebf8ff;
  }
  
  .register-btn {
    padding: 0.5rem 1rem;
    font-weight: 500;
    color: white;
    background: linear-gradient(to right, #3182ce, #805ad5);
    border: none;
    border-radius: 0.375rem;
    text-decoration: none;
    transition: opacity 0.2s;
  }
  
  .register-btn:hover {
    opacity: 0.9;
  }
  
  .logout-btn {
    padding: 0.25rem 0.5rem;
    font-size: 0.875rem;
    color: #e53e3e;
    background: none;
    border: 1px solid #e53e3e;
    border-radius: 0.25rem;
    cursor: pointer;
    transition: all 0.2s;
  }
  
  .logout-btn:hover {
    background-color: #fff5f5;
  }
  
  /* Main Content Styles */
  .main-content {
    flex: 1;
    padding: 2rem;
  }
  
  .hero-section {
    text-align: center;
    margin-bottom: 3rem;
  }
  
  .page-title {
    font-size: 2.5rem;
    font-weight: 800;
    margin-bottom: 1rem;
    background: linear-gradient(to right, #3182ce, #805ad5);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }
  
  .subtitle {
    font-size: 1.25rem;
    color: #718096;
  }
  
  .content-section {
    margin-bottom: 4rem;
  }
  
  .section-title {
    font-size: 1.875rem;
    font-weight: 700;
    margin-bottom: 2rem;
    text-align: center;
  }
  
  .feature-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 2rem;
  }
  
  @media (min-width: 1024px) {
    .feature-grid {
      grid-template-columns: repeat(4, 1fr);
    }
  }
  
  .feature-card {
    padding: 2rem;
    background: white;
    border-radius: 0.75rem;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    text-align: center;
    transition: transform 0.2s, box-shadow 0.2s;
  }
  
  .feature-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  }
  
  .feature-icon {
    width: 4rem;
    height: 4rem;
    border-radius: 9999px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 1.5rem;
  }
  
  .icon {
    width: 2rem;
    height: 2rem;
    color: white;
  }
  
  .feature-title {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 1rem;
  }
  
  .feature-description {
    color: #718096;
    line-height: 1.5;
  }
  
  .advantage-list {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 2rem;
  }
  
  .advantage-item {
    display: flex;
    align-items: flex-start;
    background: white;
    padding: 1.5rem;
    border-radius: 0.75rem;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .advantage-icon {
    color: #38a169;
    width: 1.5rem;
    height: 1.5rem;
    flex-shrink: 0;
    margin-right: 1rem;
    margin-top: 0.25rem;
  }
  
  .advantage-title {
    font-size: 1.125rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
  }
  
  .advantage-description {
    color: #718096;
    line-height: 1.5;
  }
  
  .cta-section {
    text-align: center;
    background: linear-gradient(135deg, #3182ce 0%, #805ad5 100%);
    padding: 3rem 2rem;
    border-radius: 0.75rem;
    color: white;
  }
  
  .cta-title {
    font-size: 1.875rem;
    font-weight: 700;
    margin-bottom: 1rem;
  }
  
  .cta-description {
    max-width: 36rem;
    margin: 0 auto 2rem;
    color: rgba(255, 255, 255, 0.9);
  }
  
  .cta-button {
    display: inline-block;
    padding: 0.75rem 1.5rem;
    background: white;
    color: #3182ce;
    font-weight: 600;
    border-radius: 0.375rem;
    text-decoration: none;
    transition: transform 0.2s;
  }
  
  .cta-button:hover {
    transform: translateY(-2px);
  }
  
  .footer {
    background-color: #f7fafc;
    padding: 2rem;
    text-align: center;
    border-top: 1px solid #e2e8f0;
  }
  
  .copyright {
    color: #718096;
  }
  
  @media (max-width: 640px) {
    .main-content {
      padding: 1.5rem;
    }
    
    .feature-grid, .advantage-list {
      grid-template-columns: 1fr;
    }
  }
  </style>