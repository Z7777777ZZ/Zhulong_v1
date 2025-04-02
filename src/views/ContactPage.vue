<template>
    <div class="page-container">
      <!-- 页面内容 -->
      <main class="main-content">
        <section class="hero-section">
          <h1 class="page-title">联系我们</h1>
          <p class="subtitle">我们随时为您提供支持和帮助</p>
        </section>
        
        <section class="contact-section">
          <div class="contact-container">
            <!-- 联系信息卡片 -->
            <div class="info-cards">
              <div class="info-card">
                <div class="card-icon">
                  <mail-icon class="icon" />
                </div>
                <h3 class="card-title">电子邮件</h3>
                <p class="card-text">zhulong@aigcdetector.com</p>
                <p class="card-description">工作日24小时内回复</p>
                <a href="mailto:support@aigcdetector.com" class="card-link">发送邮件</a>
              </div>
              
              <div class="info-card">
                <div class="card-icon">
                  <phone-icon class="icon" />
                </div>
                <h3 class="card-title">客服热线</h3>
                <p class="card-text">400-123-4567</p>
                <p class="card-description">周一至周五 9:00-18:00</p>
                <button class="card-link" @click="tempPrompt">立即拨打</button>
              </div>
              
              <div class="info-card">
                <div class="card-icon">
                  <message-circle-icon class="icon" />
                </div>
                <h3 class="card-title">在线客服</h3>
                <p class="card-text">实时聊天支持</p>
                <p class="card-description">工作日 9:00-22:00</p>
                <button class="card-link" @click="tempPrompt">开始聊天</button>
              </div>
            </div>
            
            <!-- 联系表单 -->
            <div class="contact-form-container">
              <div class="form-header">
                <h2 class="form-title">发送消息</h2>
                <p class="form-description">填写下面的表单，我们会尽快回复您</p>
              </div>
              
              <form class="contact-form" @submit.prevent="submitForm">
                <div class="form-group">
                  <label for="name" class="form-label">您的姓名</label>
                  <input 
                    type="text" 
                    id="name" 
                    v-model="formData.name" 
                    class="form-input" 
                    placeholder="请输入您的姓名"
                    required
                  />
                </div>
                
                <div class="form-group">
                  <label for="email" class="form-label">电子邮箱</label>
                  <input 
                    type="email" 
                    id="email" 
                    v-model="formData.email" 
                    class="form-input" 
                    placeholder="请输入您的电子邮箱"
                    required
                  />
                </div>
                
                <div class="form-group">
                  <label for="subject" class="form-label">主题</label>
                  <select 
                    id="subject" 
                    v-model="formData.subject" 
                    class="form-select"
                    required
                  >
                    <option value="" disabled selected>请选择主题</option>
                    <option value="general">一般咨询</option>
                    <option value="technical">技术支持</option>
                    <option value="billing">账单问题</option>
                    <option value="partnership">商务合作</option>
                    <option value="feedback">产品反馈</option>
                  </select>
                </div>
                
                <div class="form-group">
                  <label for="message" class="form-label">消息内容</label>
                  <textarea 
                    id="message" 
                    v-model="formData.message" 
                    class="form-textarea" 
                    placeholder="请详细描述您的问题或需求"
                    rows="5"
                    required
                  ></textarea>
                </div>
                
                <div class="form-group form-checkbox">
                  <input 
                    type="checkbox" 
                    id="agreement" 
                    v-model="formData.agreement" 
                    required
                  />
                  <label for="agreement" class="checkbox-label">
                    我同意根据<a href="#" class="text-link">隐私政策</a>处理我的个人数据
                  </label>
                </div>
                
                <button type="submit" class="submit-button" :disabled="isSubmitting">
                  <loader-icon v-if="isSubmitting" class="animate-spin mr-2" />
                  {{ isSubmitting ? '提交中...' : '发送消息' }}
                </button>
              </form>
            </div>
          </div>
        </section>
        
        <!-- 办公地点 -->
        <section class="locations-section">
          <h2 class="section-title">我们的办公地点</h2>
          
          <div class="locations-container">
            <div class="location-card">
              <div class="location-image">
                <img src="/placeholder.svg?height=200&width=350" alt="北京办公室" class="location-img" />
              </div>
              <div class="location-info">
                <h3 class="location-title">杭州总部</h3>
                <p class="location-address">
                  <map-pin-icon class="location-icon" />
                  浙江省杭州市浙江大学创业园
                </p>
                <p class="location-hours">
                  <clock-icon class="location-icon" />
                  周一至周五 9:00-18:00
                </p>
              </div>
            </div>
            
            <div class="location-card">
              <div class="location-image">
                <img src="/placeholder.svg?height=200&width=350" alt="上海办公室" class="location-img" />
              </div>
              <div class="location-info">
                <h3 class="location-title">杭州分部</h3>
                <p class="location-address">
                  <map-pin-icon class="location-icon" />
                  浙江省杭州市浙江大学紫金港校区元空间
                </p>
                <p class="location-hours">
                  <clock-icon class="location-icon" />
                  周一至周五 9:00-18:00
                </p>
              </div>
            </div>
          </div>
        </section>
        
        <!-- 常见问题 -->
        <section class="faq-section">
          <div class="faq-container">
            <div class="faq-header">
              <h2 class="faq-title">常见问题</h2>
              <p class="faq-description">在联系我们之前，您可以查看以下常见问题</p>
            </div>
            
            <div class="faq-list">
              <div class="faq-item" :class="{ 'active': expandedFaq === 0 }" @click="toggleFaq(0)">
                <div class="faq-question">
                  <span>如何获取技术支持？</span>
                  <chevron-down-icon class="faq-icon" :class="{ 'rotate': expandedFaq === 0 }" />
                </div>
                <div class="faq-answer" :class="{ 'expanded': expandedFaq === 0 }">
                  <p>您可以通过多种方式获取技术支持：填写本页的联系表单、发送邮件至support@aigcdetector.com、拨打客服热线400-123-4567，或在工作时间使用网站右下角的在线聊天功能。</p>
                </div>
              </div>
              
              <div class="faq-item" :class="{ 'active': expandedFaq === 1 }" @click="toggleFaq(1)">
                <div class="faq-question">
                  <span>响应时间是多久？</span>
                  <chevron-down-icon class="faq-icon" :class="{ 'rotate': expandedFaq === 1 }" />
                </div>
                <div class="faq-answer" :class="{ 'expanded': expandedFaq === 1 }">
                  <p>对于一般咨询，我们会在24小时内回复您的邮件或表单提交。对于紧急技术问题，建议使用在线聊天或电话联系，以获得更快的响应。</p>
                </div>
              </div>
              
              <div class="faq-item" :class="{ 'active': expandedFaq === 2 }" @click="toggleFaq(2)">
                <div class="faq-question">
                  <span>如何申请商务合作？</span>
                  <chevron-down-icon class="faq-icon" :class="{ 'rotate': expandedFaq === 2 }" />
                </div>
                <div class="faq-answer" :class="{ 'expanded': expandedFaq === 2 }">
                  <p>如果您希望与我们建立商务合作，请在联系表单中选择"商务合作"主题，并详细描述您的合作意向。我们的商务团队会在3个工作日内与您联系。</p>
                </div>
              </div>
            </div>
          </div>
        </section>
        
        <!-- 社交媒体 -->
        <section class="social-section">
          <h2 class="social-title">关注我们</h2>
          <p class="social-description">在社交媒体上关注我们，获取最新动态和技术资讯</p>
          
          <div class="social-links">
            <a href="#" class="social-link">
              <div class="social-icon">
                <twitter-icon />
              </div>
              <span class="social-name">Twitter</span>
            </a>
            
            <a href="#" class="social-link">
              <div class="social-icon">
                <linkedin-icon />
              </div>
              <span class="social-name">LinkedIn</span>
            </a>
            
            <a href="#" class="social-link">
              <div class="social-icon">
                <github-icon />
              </div>
              <span class="social-name">GitHub</span>
            </a>
            
            <a href="#" class="social-link">
              <div class="social-icon">
                <youtube-icon />
              </div>
              <span class="social-name">YouTube</span>
            </a>
          </div>
        </section>
      </main>
      
      <!-- 页脚 -->
      <footer class="footer">
        <div class="footer-content">
          <div class="footer-logo">
            <img src="/logo.jpg?height=40&width=40" alt="Logo" class="logo-img" />
            <span class="logo-text">AIGC Detector</span>
          </div>
          
          <div class="footer-links">
            <a href="/home" class="footer-link">首页</a>
            <!-- <a href="#" class="footer-link">产品</a>
            <a href="#" class="footer-link">定价</a>
            <a href="#" class="footer-link">文档</a>
            <a href="#" class="footer-link">博客</a>-->
           
            <a href="/about" class="footer-link">关于我们</a>
          </div>
          
          <p class="copyright">© 2025 AIGC Detector. 保留所有权利。</p>
        </div>
      </footer>
      
      <!-- 成功提交消息 -->
      <div v-if="showSuccessMessage" class="success-message">
        <div class="success-content">
          <check-circle-icon class="success-icon" />
          <h3 class="success-title">消息已发送！</h3>
          <p class="success-text">感谢您的留言，我们会尽快回复您。</p>
          <button class="success-button" @click="showSuccessMessage = false">关闭</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { 
    Mail as MailIcon,
    Phone as PhoneIcon,
    MessageCircle as MessageCircleIcon,
    MapPin as MapPinIcon,
    Clock as ClockIcon,
    ChevronDown as ChevronDownIcon,
    Loader as LoaderIcon,
    CheckCircle as CheckCircleIcon,
    Twitter as TwitterIcon,
    Linkedin as LinkedinIcon,
    Github as GithubIcon,
    Youtube as YoutubeIcon
  } from 'lucide-vue-next';
  
  // 表单数据
  const formData = ref({
    name: '',
    email: '',
    subject: '',
    message: '',
    agreement: false
  });
  
  // 表单状态
  const isSubmitting = ref(false);
  const showSuccessMessage = ref(false);
  
  // FAQ状态
  const expandedFaq = ref(null);
  
  // 切换FAQ展开状态
  const toggleFaq = (index) => {
    if (expandedFaq.value === index) {
      expandedFaq.value = null;
    } else {
      expandedFaq.value = index;
    }
  };

const tempPrompt = () => {
  alert('暂不支持该功能');
}
  
  // 提交表单
  const submitForm = async () => {
    isSubmitting.value = true;
    
    try {
      // 模拟API请求
      await new Promise(resolve => setTimeout(resolve, 1500));
      
      // 重置表单
      formData.value = {
        name: '',
        email: '',
        subject: '',
        message: '',
        agreement: false
      };
      
      // 显示成功消息
      showSuccessMessage.value = true;
    } catch (error) {
      console.error('提交表单失败:', error);
    } finally {
      isSubmitting.value = false;
    }
  };
  </script>
  
  <style scoped>
  .page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f7f9fc;
  }
  
  .main-content {
    flex: 1;
    padding: 2rem;
  }
  
  /* 页面标题 */
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
  
  /* 联系信息部分 */
  .contact-section {
    margin-bottom: 4rem;
  }
  
  .contact-container {
    display: grid;
    grid-template-columns: 1fr;
    gap: 2rem;
  }
  
  @media (min-width: 1024px) {
    .contact-container {
      grid-template-columns: 1fr 1.5fr;
    }
  }
  
  /* 信息卡片 */
  .info-cards {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 1.5rem;
  }
  
  .info-card {
    background: white;
    border-radius: 0.75rem;
    padding: 2rem;
    text-align: center;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
  }
  
  .info-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
  }
  
  .card-icon {
    width: 3.5rem;
    height: 3.5rem;
    background: linear-gradient(to right, #3182ce, #805ad5);
    border-radius: 9999px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 1.5rem;
  }
  
  .icon {
    width: 1.5rem;
    height: 1.5rem;
    color: white;
  }
  
  .card-title {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 0.75rem;
    color: #2d3748;
  }
  
  .card-text {
    font-size: 1.125rem;
    font-weight: 500;
    margin-bottom: 0.5rem;
    color: #4a5568;
  }
  
  .card-description {
    font-size: 0.875rem;
    color: #718096;
    margin-bottom: 1.5rem;
  }
  
  .card-link {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 0.5rem 1.25rem;
    background: transparent;
    color: #3182ce;
    border: 1px solid #3182ce;
    border-radius: 0.375rem;
    font-weight: 500;
    text-decoration: none;
    transition: all 0.2s;
    cursor: pointer;
  }
  
  .card-link:hover {
    background: #3182ce;
    color: white;
  }
  
  /* 联系表单 */
  .contact-form-container {
    background: white;
    border-radius: 0.75rem;
    padding: 2rem;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .form-header {
    margin-bottom: 2rem;
  }
  
  .form-title {
    font-size: 1.5rem;
    font-weight: 700;
    margin-bottom: 0.5rem;
    color: #2d3748;
  }
  
  .form-description {
    font-size: 1rem;
    color: #718096;
  }
  
  .contact-form {
    display: grid;
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
  
  .form-input,
  .form-select,
  .form-textarea {
    padding: 0.75rem 1rem;
    border: 1px solid #e2e8f0;
    border-radius: 0.375rem;
    font-size: 1rem;
    transition: border-color 0.2s;
  }
  
  .form-input:focus,
  .form-select:focus,
  .form-textarea:focus {
    outline: none;
    border-color: #3182ce;
    box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
  }
  
  .form-checkbox {
    flex-direction: row;
    align-items: center;
    gap: 0.75rem;
  }
  
  .checkbox-label {
    font-size: 0.875rem;
    color: #4a5568;
  }
  
  .text-link {
    color: #3182ce;
    text-decoration: none;
  }
  
  .text-link:hover {
    text-decoration: underline;
  }
  
  .submit-button {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 0.75rem 1.5rem;
    background: linear-gradient(to right, #3182ce, #805ad5);
    color: white;
    border: none;
    border-radius: 0.375rem;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: opacity 0.2s;
  }
  
  .submit-button:hover {
    opacity: 0.9;
  }
  
  .submit-button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
  
  /* 办公地点 */
  .locations-section {
    margin-bottom: 4rem;
  }
  
  .section-title {
    font-size: 1.875rem;
    font-weight: 700;
    margin-bottom: 2rem;
    text-align: center;
    color: #2d3748;
  }
  
  .locations-container {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 2rem;
  }
  
  .location-card {
    background: white;
    border-radius: 0.75rem;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
  }
  
  .location-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
  }
  
  .location-image {
    height: 200px;
  }
  
  .location-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .location-info {
    padding: 1.5rem;
  }
  
  .location-title {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 1rem;
    color: #2d3748;
  }
  
  .location-address,
  .location-hours {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    margin-bottom: 0.5rem;
    font-size: 0.875rem;
    color: #4a5568;
  }
  
  .location-icon {
    width: 1rem;
    height: 1rem;
    color: #3182ce;
    flex-shrink: 0;
  }
  
  /* FAQ部分 */
  .faq-section {
    margin-bottom: 4rem;
  }
  
  .faq-container {
    max-width: 800px;
    margin: 0 auto;
  }
  
  .faq-header {
    text-align: center;
    margin-bottom: 2rem;
  }
  
  .faq-title {
    font-size: 1.875rem;
    font-weight: 700;
    margin-bottom: 0.5rem;
    color: #2d3748;
  }
  
  .faq-description {
    font-size: 1rem;
    color: #718096;
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
    background: white;
  }
  
  .faq-question {
    padding: 1.25rem;
    display: flex;
    align-items: center;
    justify-content: space-between;
    cursor: pointer;
    font-weight: 600;
    transition: all 0.2s;
  }
  
  .faq-question:hover {
    background: #f7fafc;
  }
  
  .faq-item.active .faq-question {
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
  }
  
  .faq-answer p {
    line-height: 1.7;
    color: #4a5568;
  }
  
  /* 社交媒体 */
  .social-section {
    text-align: center;
    margin-bottom: 4rem;
  }
  
  .social-title {
    font-size: 1.875rem;
    font-weight: 700;
    margin-bottom: 0.5rem;
    color: #2d3748;
  }
  
  .social-description {
    font-size: 1rem;
    color: #718096;
    margin-bottom: 2rem;
  }
  
  .social-links {
    display: flex;
    justify-content: center;
    gap: 1.5rem;
    flex-wrap: wrap;
  }
  
  .social-link {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
    text-decoration: none;
    transition: transform 0.3s ease;
  }
  
  .social-link:hover {
    transform: translateY(-5px);
  }
  
  .social-icon {
    width: 3rem;
    height: 3rem;
    background: white;
    border-radius: 9999px;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    color: #3182ce;
  }
  
  .social-name {
    font-size: 0.875rem;
    font-weight: 500;
    color: #4a5568;
  }
  
  /* 页脚 */
  .footer {
    background: white;
    padding: 2rem;
    border-top: 1px solid #e2e8f0;
  }
  
  .footer-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1.5rem;
  }
  
  .footer-logo {
    display: flex;
    align-items: center;
    gap: 0.75rem;
  }
  
  .logo-img {
    width: 2.5rem;
    height: 2.5rem;
  }
  
  .logo-text {
    font-size: 1.25rem;
    font-weight: 700;
    color: #2d3748;
  }
  
  .footer-links {
    display: flex;
    gap: 1.5rem;
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .footer-link {
    color: #4a5568;
    text-decoration: none;
    font-size: 0.875rem;
    transition: color 0.2s;
  }
  
  .footer-link:hover {
    color: #3182ce;
  }
  
  .copyright {
    color: #718096;
    font-size: 0.875rem;
  }
  
  /* 成功消息 */
  .success-message {
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
  
  .success-content {
    background: white;
    border-radius: 0.75rem;
    padding: 2rem;
    text-align: center;
    max-width: 400px;
    width: 90%;
    box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
  }
  
  .success-icon {
    width: 4rem;
    height: 4rem;
    color: #38a169;
    margin: 0 auto 1.5rem;
  }
  
  .success-title {
    font-size: 1.5rem;
    font-weight: 700;
    margin-bottom: 0.75rem;
    color: #2d3748;
  }
  
  .success-text {
    font-size: 1rem;
    color: #718096;
    margin-bottom: 1.5rem;
  }
  
  .success-button {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 0.75rem 2rem;
    background: linear-gradient(to right, #3182ce, #805ad5);
    color: white;
    border: none;
    border-radius: 0.375rem;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: opacity 0.2s;
  }
  
  .success-button:hover {
    opacity: 0.9;
  }
  
  /* 响应式调整 */
  @media (max-width: 768px) {
    .social-links {
      gap: 1rem;
    }
    
    .footer-links {
      gap: 1rem;
    }
  }
  </style>