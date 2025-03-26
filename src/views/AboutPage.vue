<template>
    <div class="page-container">
      <!-- 使用NavBar组件 -->
      <NavBar />
      
      <!-- 页面内容 -->
      <main class="main-content">
        <section class="hero-section">
          <h1 class="page-title">关于我们</h1>
          <p class="subtitle">致力于打造最先进的AI生成内容检测平台</p>
        </section>
        
        <section class="content-section">
          <div class="about-grid">
            <div class="about-image">
              <img src="/placeholder.svg?height=400&width=600" alt="团队照片" class="team-image" />
            </div>
            
            <div class="about-content">
              <h2 class="section-title">我们的使命</h2>
              <p class="about-text">
                随着人工智能技术的快速发展，AI生成内容（AIGC）已经变得越来越难以与人类创作的内容区分。这给内容创作、版权保护和信息真实性带来了新的挑战。
              </p>
              <p class="about-text">
                我们的使命是开发先进的AI检测技术，帮助用户准确识别AI生成的内容，保护原创作品的价值，维护信息生态的健康发展。
              </p>
              <p class="about-text">
                我们相信，在享受AI技术带来便利的同时，也需要有效的工具来区分AI与人类创作，这对于保持创意产业的可持续发展至关重要。
              </p>
            </div>
          </div>
        </section>
        
        <section class="content-section">
          <h2 class="section-title">我们的团队</h2>
          <p class="team-intro">
            我们的团队由AI研究、计算机视觉、自然语言处理和音频分析领域的专家组成，拥有丰富的行业经验和学术背景。
          </p>
          
          <div class="team-grid">
            <div class="team-card">
              <div class="member-avatar">
                <img src="/placeholder.svg?height=120&width=120" alt="团队成员" class="avatar-image" />
              </div>
              <h3 class="member-name">张博士</h3>
              <p class="member-title">首席技术官</p>
              <p class="member-bio">
                人工智能领域资深专家，拥有10年以上深度学习研究经验，曾在多家知名科技公司担任技术领导职位。
              </p>
            </div>
            
            <div class="team-card">
              <div class="member-avatar">
                <img src="/placeholder.svg?height=120&width=120" alt="团队成员" class="avatar-image" />
              </div>
              <h3 class="member-name">李教授</h3>
              <p class="member-title">首席科学家</p>
              <p class="member-bio">
                计算机视觉专家，在图像分析和识别领域发表多篇高影响力论文，曾主导多个大型AI项目的研发工作。
              </p>
            </div>
            
            <div class="team-card">
              <div class="member-avatar">
                <img src="/placeholder.svg?height=120&width=120" alt="团队成员" class="avatar-image" />
              </div>
              <h3 class="member-name">王工程师</h3>
              <p class="member-title">自然语言处理负责人</p>
              <p class="member-bio">
                语言模型专家，专注于文本生成和分析技术，拥有丰富的大型语言模型开发和应用经验。
              </p>
            </div>
            
            <div class="team-card">
              <div class="member-avatar">
                <img src="/placeholder.svg?height=120&width=120" alt="团队成员" class="avatar-image" />
              </div>
              <h3 class="member-name">陈总监</h3>
              <p class="member-title">产品总监</p>
              <p class="member-bio">
                拥有丰富的AI产品设计和管理经验，致力于打造用户友好、功能强大的AI检测工具。
              </p>
            </div>
          </div>
        </section>
        
        <section class="content-section">
          <h2 class="section-title">我们的价值观</h2>
          <div class="values-grid">
            <div class="value-card">
              <shield-icon class="value-icon" />
              <h3 class="value-title">诚信可靠</h3>
              <p class="value-description">
                我们坚持以诚信为本，提供准确可靠的检测结果，赢得用户的信任和尊重。
              </p>
            </div>
            
            <div class="value-card">
              <lightbulb-icon class="value-icon" />
              <h3 class="value-title">创新驱动</h3>
              <p class="value-description">
                我们不断探索AI检测技术的前沿，持续创新，提升检测的准确性和效率。
              </p>
            </div>
            
            <div class="value-card">
              <users-icon class="value-icon" />
              <h3 class="value-title">用户至上</h3>
              <p class="value-description">
                我们以用户需求为中心，不断优化产品体验，提供简单易用的检测服务。
              </p>
            </div>
            
            <div class="value-card">
              <globe-icon class="value-icon" />
              <h3 class="value-title">社会责任</h3>
              <p class="value-description">
                我们致力于维护健康的信息环境，促进AI技术的负责任发展和应用。
              </p>
            </div>
          </div>
        </section>
        
        <section class="cta-section">
          <h2 class="cta-title">加入我们，共同探索AI的未来</h2>
          <p class="cta-description">我们始终欢迎志同道合的伙伴加入，一起推动AI检测技术的发展</p>
          <a href="/contact" class="cta-button">
            联系我们
          </a>
        </section>
      </main>
      
      <!-- 页脚 -->
      <footer class="footer">
        <p class="copyright">© 2025 AIGC Detector. 保留所有权利。</p>
      </footer>
    </div>
  </template>
  
  <script setup>
  import { 
    Shield as ShieldIcon,
    Lightbulb as LightbulbIcon,
    Users as UsersIcon,
    Globe as GlobeIcon
  } from 'lucide-vue-next';
  import NavBar from '../components/NavBar.vue';
  // import { ref, computed, onMounted } from 'vue';
  // import { useRouter } from 'vue-router';
  // import auth from '../store/auth';
  
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
    font-weight: 700;
    margin-bottom: 1rem;
    background: linear-gradient(to right, #3182ce, #805ad5);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }
  
  .subtitle {
    font-size: 1.25rem;
    color: #4a5568;
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
  
  .about-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 3rem;
    align-items: center;
  }
  
  .team-image {
    width: 100%;
    border-radius: 0.75rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  }
  
  .about-content .section-title {
    text-align: left;
    margin-bottom: 1.5rem;
  }
  
  .about-text {
    margin-bottom: 1.5rem;
    line-height: 1.7;
    color: #4a5568;
  }
  
  .team-intro {
    text-align: center;
    max-width: 800px;
    margin: 0 auto 3rem;
    color: #4a5568;
    font-size: 1.125rem;
  }
  
  .team-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 2rem;
  }
  
  .team-card {
    background: white;
    border-radius: 0.75rem;
    padding: 2rem;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    text-align: center;
  }
  
  .member-avatar {
    width: 120px;
    height: 120px;
    border-radius: 9999px;
    overflow: hidden;
    margin: 0 auto 1.5rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  }
  
  .avatar-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .member-name {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
  }
  
  .member-title {
    color: #3182ce;
    font-weight: 500;
    margin-bottom: 1rem;
  }
  
  .member-bio {
    color: #4a5568;
    font-size: 0.875rem;
    line-height: 1.6;
  }
  
  .values-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 2rem;
  }
  
  .value-card {
    background: white;
    border-radius: 0.75rem;
    padding: 2rem;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    text-align: center;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .value-icon {
    width: 3rem;
    height: 3rem;
    color: #3182ce;
    margin-bottom: 1.5rem;
  }
  
  .value-title {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 1rem;
  }
  
  .value-description {
    color: #4a5568;
    line-height: 1.6;
  }
  
  .cta-section {
    background: linear-gradient(to right, #3182ce, #805ad5);
    border-radius: 0.75rem;
    padding: 3rem 2rem;
    text-align: center;
    color: white;
  }
  
  .cta-title {
    font-size: 1.875rem;
    font-weight: 700;
    margin-bottom: 1rem;
  }
  
  .cta-description {
    font-size: 1.125rem;
    margin-bottom: 2rem;
    opacity: 0.9;
  }
  
  .cta-button {
    display: inline-block;
    padding: 0.75rem 2rem;
    background: white;
    color: #3182ce;
    font-weight: 600;
    border-radius: 0.375rem;
    text-decoration: none;
    transition: all 0.2s;
  }
  
  .cta-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  }
  
  .footer {
    background: white;
    padding: 2rem;
    text-align: center;
    border-top: 1px solid #e2e8f0;
  }
  
  .copyright {
    color: #718096;
    font-size: 0.875rem;
  }
  
  @media (max-width: 768px) {
    .about-grid {
      grid-template-columns: 1fr;
    }
    
    .about-image {
      order: 2;
    }
    
    .about-content {
      order: 1;
    }
    
    .team-grid, .values-grid {
      grid-template-columns: 1fr;
    }
  }
  </style>