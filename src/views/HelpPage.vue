<template>
  <div class="page-container">
    <!-- 使用NavBar组件 -->
    <NavBar />
    
    <!-- 页面内容 -->
    <main class="main-content">
      <section class="hero-section">
        <h1 class="page-title">帮助中心</h1>
        <p class="subtitle">获取使用指南和常见问题解答</p>
      </section>
      
      <section class="content-section">
        <div class="help-container">
          <div class="help-sidebar">
            <h3 class="sidebar-title">快速导航</h3>
            <ul class="sidebar-menu">
              <li 
                v-for="(section, index) in helpSections" 
                :key="index"
                class="sidebar-item"
                :class="{ active: activeSection === index }"
                @click="activeSection = index"
              >
                {{ section.title }}
              </li>
            </ul>
            
            <div class="contact-box">
              <h4 class="contact-title">需要更多帮助？</h4>
              <p class="contact-text">我们的客服团队随时为您提供支持</p>
              <a href="mailto:support@aigcdetector.com" class="contact-button">
                <mail-icon class="contact-icon" />
                联系客服
              </a>
            </div>
          </div>
          
          <div class="help-content">
            <div class="section-container">
              <h2 class="section-title">{{ helpSections[activeSection].title }}</h2>
              
              <div class="faq-list">
                <div 
                  v-for="(faq, index) in helpSections[activeSection].faqs" 
                  :key="index"
                  class="faq-item"
                >
                  <div 
                    class="faq-question"
                    :class="{ 'active': expandedFaqs[activeSection] === index }"
                    @click="toggleFaq(index)"
                  >
                    <span>{{ faq.question }}</span>
                    <chevron-down-icon 
                      class="faq-icon"
                      :class="{ 'rotate': expandedFaqs[activeSection] === index }"
                    />
                  </div>
                  <div 
                    class="faq-answer"
                    :class="{ 'expanded': expandedFaqs[activeSection] === index }"
                  >
                    <p>{{ faq.answer }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      
      <section class="video-tutorials">
        <h2 class="section-title">视频教程</h2>
        <div class="tutorials-grid">
          <div class="tutorial-card">
            <div class="tutorial-thumbnail">
              <img src="/placeholder.svg?height=180&width=320" alt="教程缩略图" class="thumbnail-image" />
              <div class="play-button">
                <play-icon class="play-icon" />
              </div>
            </div>
            <h3 class="tutorial-title">如何使用图片检测功能(待更新)</h3>
            <p class="tutorial-duration">3分钟</p>
          </div>
          
          <div class="tutorial-card">
            <div class="tutorial-thumbnail">
              <img src="/placeholder.svg?height=180&width=320" alt="教程缩略图" class="thumbnail-image" />
              <div class="play-button">
                <play-icon class="play-icon" />
              </div>
            </div>
            <h3 class="tutorial-title">文本检测详细指南(待更新)</h3>
            <p class="tutorial-duration">5分钟</p>
          </div>
          
          <div class="tutorial-card">
            <div class="tutorial-thumbnail">
              <img src="/placeholder.svg?height=180&width=320" alt="教程缩略图" class="thumbnail-image" />
              <div class="play-button">
                <play-icon class="play-icon" />
              </div>
            </div>
            <h3 class="tutorial-title">如何理解检测报告(待更新)</h3>
            <p class="tutorial-duration">4分钟</p>
          </div>
          
          <div class="tutorial-card">
            <div class="tutorial-thumbnail">
              <img src="/placeholder.svg?height=180&width=320" alt="教程缩略图" class="thumbnail-image" />
              <div class="play-button">
                <play-icon class="play-icon" />
              </div>
            </div>
            <h3 class="tutorial-title">账户管理与设置(待更新)</h3>
            <p class="tutorial-duration">2分钟</p>
          </div>
        </div>
      </section>
      
      <section class="cta-section">
        <h2 class="cta-title">还有其他问题？</h2>
        <p class="cta-description">浏览我们的完整文档或直接联系客服团队获取帮助</p>
        <div class="cta-buttons">
          <a href="#" class="cta-button primary">
            <book-open-icon class="button-icon" />
            查看文档
          </a>
          <a href="/contact" class="cta-button secondary">
            <message-circle-icon class="button-icon" />
            联系客服
          </a>
        </div>
      </section>
    </main>
    
    <!-- 页脚 -->
    <footer class="footer">
      <p class="copyright">© 2025 AIGC Detector. 保留所有权利。</p>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { 
  ChevronDown as ChevronDownIcon,
  Mail as MailIcon,
  Play as PlayIcon,
  BookOpen as BookOpenIcon,
  MessageCircle as MessageCircleIcon
} from 'lucide-vue-next';
import NavBar from '../components/NavBar.vue';
// 帮助中心内容
const helpSections = [
  {
    title: '基本使用指南',
    faqs: [
      {
        question: '如何开始使用AIGC检测平台？',
        answer: '首先，您需要注册一个账户并登录。然后，您可以在工作区选择需要的检测类型（图片、文本、视频或音频），上传您要检测的内容，点击"开始检测"按钮即可获得检测结果。'
      },
      {
        question: '支持哪些文件格式？',
        answer: '我们支持多种文件格式：图片检测支持JPG、PNG、WEBP等格式；文本检测支持TXT、DOC、PDF等格式；视频检测支持MP4、AVI、MOV等格式；音频检测支持MP3、WAV、FLAC等格式。'
      },
      {
        question: '检测结果的准确率如何？',
        answer: '我们的检测系统基于最先进的深度学习算法，对于大多数内容，准确率可达95%以上。但请注意，随着AI技术的不断发展，检测结果可能会有一定误差，我们也在持续优化算法提高准确率。'
      },
      {
        question: '如何理解检测报告中的数据？',
        answer: '检测报告中的百分比表示内容由AI生成的概率。例如，"AI生成 (92%)"表示系统认为该内容有92%的可能性是由AI生成的。报告还包含详细分析，解释系统做出判断的依据。'
      }
    ]
  },
  {
    title: '账户与订阅',
    faqs: [
      {
        question: '免费账户有什么限制？',
        answer: '免费账户每天可以进行5次检测，支持所有检测类型，但不提供详细的分析报告和历史记录保存功能。'
      },
      {
        question: '如何升级到专业版？',
        answer: '登录您的账户后，点击"设置"中的"订阅管理"，选择适合您的订阅计划并完成支付即可升级到专业版。'
      },
      {
        question: '专业版有哪些额外功能？',
        answer: '专业版提供无限次数的检测、详细的分析报告、历史记录永久保存、批量检测功能，以及优先客服支持。'
      },
      {
        question: '如何取消订阅？',
        answer: '您可以在"设置"中的"订阅管理"页面取消自动续费。取消后，您仍然可以使用当前订阅周期内的服务，直到订阅期结束。'
      }
    ]
  },
  {
    title: '技术问题',
    faqs: [
      {
        question: '上传文件时遇到错误怎么办？',
        answer: '请确保您的文件格式正确且文件大小不超过限制（图片和文本：10MB，视频：100MB，音频：50MB）。如果问题仍然存在，请尝试使用不同的浏览器或联系客服支持。'
      },
      {
        question: '为什么检测过程需要较长时间？',
        answer: '检测时间取决于文件大小和类型。通常，图片和文本检测只需几秒钟，而视频和音频可能需要更长时间。如果检测时间超过5分钟，可能是系统繁忙或网络问题，请稍后重试。'
      },
      {
        question: '检测结果可以导出吗？',
        answer: '是的，您可以将检测结果导出为PDF或CSV格式。在检测结果页面，点击"下载报告"按钮选择您需要的格式即可。'
      },
      {
        question: '系统支持哪些语言的文本检测？',
        answer: '目前，我们支持中文、英文、日文、韩文、法文、德文、西班牙文和俄文的文本检测。我们计划在未来增加更多语言支持。'
      }
    ]
  },
  {
    title: '隐私与安全',
    faqs: [
      {
        question: '我上传的内容会被保存吗？',
        answer: '默认情况下，我们会临时保存您上传的内容以进行检测，检测完成后会在24小时内自动删除。如果您是专业版用户，可以选择永久保存检测记录和内容。'
      },
      {
        question: '我的检测数据安全吗？',
        answer: '是的，我们采用行业标准的加密技术保护您的数据。所有数据传输都通过HTTPS加密，存储的数据也经过加密处理。我们不会与第三方共享您的个人数据和检测内容。'
      },
      {
        question: '如何删除我的账户和所有数据？',
        answer: '您可以在"设置"中的"账户管理"页面申请删除账户。账户删除后，所有与您相关的数据将在30天内从我们的系统中永久删除。'
      },
      {
        question: '你们是否使用我的数据来训练AI模型？',
        answer: '未经您的明确许可，我们不会使用您的数据来训练AI模型。如果您选择参与我们的"改进计划"，您的匿名数据可能会用于改进检测算法，但您可以随时在设置中退出该计划。'
      }
    ]
  }
];

// 当前活跃的部分
const activeSection = ref(0);

// 展开的FAQ
const expandedFaqs = ref({
  0: null,
  1: null,
  2: null,
  3: null
});

// 切换FAQ展开状态
const toggleFaq = (index) => {
  if (expandedFaqs.value[activeSection.value] === index) {
    expandedFaqs.value[activeSection.value] = null;
  } else {
    expandedFaqs.value[activeSection.value] = index;
  }
};
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

.help-container {
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 2rem;
  background: white;
  border-radius: 0.75rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.help-sidebar {
  background: #f7fafc;
  padding: 2rem;
  border-right: 1px solid #e2e8f0;
}

.sidebar-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  color: #2d3748;
}

.sidebar-menu {
  list-style: none;
  padding: 0;
  margin: 0 0 2rem;
}

.sidebar-item {
  padding: 0.75rem 1rem;
  border-radius: 0.375rem;
  cursor: pointer;
  transition: all 0.2s;
  margin-bottom: 0.5rem;
  font-weight: 500;
  display: flex;
  align-items: center;
}

.sidebar-item:hover {
  background: #edf2f7;
}

.sidebar-item.active {
  background: #ebf8ff;
  color: #3182ce;
  font-weight: 600;
}

.contact-box {
  background: linear-gradient(to right, #3182ce, #805ad5);
  color: white;
  padding: 1.5rem;
  border-radius: 0.75rem;
}

.contact-title {
  font-size: 1.125rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.contact-text {
  font-size: 0.875rem;
  margin-bottom: 1.5rem;
  opacity: 0.9;
}

.contact-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  background: white;
  color: #3182ce;
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  font-weight: 500;
  text-decoration: none;
  transition: all 0.2s;
  line-height: 1.5;
  height: 2.5rem;
}

.contact-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

.contact-icon {
  width: 1rem;
  height: 1rem;
  flex-shrink: 0;
}

.help-content {
  padding: 2rem;
}

.section-container {
  max-width: 800px;
}

.section-title {
  font-size: 1.875rem;
  font-weight: 700;
  margin-bottom: 2rem;
  color: #2d3748;
}

.faq-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.faq-item {
  border: 1px solid #e2e8f0;
  border-radius: 0.5rem;
  overflow: hidden;
}

.faq-question {
  padding: 1.25rem;
  background: #f7fafc;
  display: flex;
  align-items: center;
  justify-content: space-between;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.2s;
}

.faq-question:hover {
  background: #edf2f7;
}

.faq-question.active {
  background: #ebf8ff;
  color: #3182ce;
}

.faq-icon {
  width: 1.25rem;
  height: 1.25rem;
  transition: transform 0.3s ease;
  flex-shrink: 0;
}

.faq-icon.rotate {
  transform: rotate(180deg);
}

.faq-answer {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.3s ease, padding 0.3s ease;
}

.faq-answer.expanded {
  max-height: 500px;
  padding: 1.25rem;
  background: white;
}

.faq-answer p {
  line-height: 1.7;
  color: #4a5568;
}

.video-tutorials {
  margin-bottom: 4rem;
}

.tutorials-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
}

.tutorial-card {
  background: white;
  border-radius: 0.75rem;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
}

.tutorial-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
}

.tutorial-thumbnail {
  position: relative;
  height: 180px;
}

.thumbnail-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.play-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 3rem;
  height: 3rem;
  background: rgba(0, 0, 0, 0.5);
  border-radius: 9999px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.2s;
}

.tutorial-card:hover .play-button {
  background: rgba(49, 130, 206, 0.8);
}

.play-icon {
  width: 1.5rem;
  height: 1.5rem;
  color: white;
}

.tutorial-title {
  padding: 1.25rem 1.25rem 0.5rem;
  font-size: 1.125rem;
  font-weight: 600;
}

.tutorial-duration {
  padding: 0 1.25rem 1.25rem;
  color: #718096;
  font-size: 0.875rem;
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

.cta-buttons {
  display: flex;
  justify-content: center;
  gap: 1.5rem;
}

.cta-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.75rem 2rem;
  border-radius: 0.375rem;
  font-weight: 600;
  text-decoration: none;
  transition: all 0.2s;
  line-height: 1.5;
  height: 2.75rem;
}

.cta-button.primary {
  background: white;
  color: #3182ce;
}

.cta-button.secondary {
  background: rgba(255, 255, 255, 0.2);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.4);
}

.cta-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

.button-icon {
  width: 1.25rem;
  height: 1.25rem;
  flex-shrink: 0;
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
  .help-container {
    grid-template-columns: 1fr;
  }
  
  .help-sidebar {
    border-right: none;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .cta-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .tutorials-grid {
    grid-template-columns: 1fr;
  }
}
</style>