<template>
    <div class="page-container">
      <!-- 页面内容 -->
      <main class="main-content">
        <section class="hero-section">
          <h1 class="page-title">服务条款</h1>
          <p class="subtitle">最后更新日期：2025年3月15日</p>
        </section>
        
        <section class="terms-section">
          <div class="terms-container">
            <!-- 导航侧边栏 -->
            <div class="terms-sidebar">
              <div class="sidebar-sticky">
                <h3 class="sidebar-title">目录</h3>
                <ul class="sidebar-menu">
                  <li 
                    v-for="(section, index) in termsSections" 
                    :key="index"
                    class="sidebar-item"
                    :class="{ active: activeSection === index }"
                  >
                    <a href="#" @click.prevent="scrollToSection(index)" class="sidebar-link">
                      {{ section.title }}
                    </a>
                  </li>
                </ul>
                
                <div class="sidebar-actions">
                  <button class="action-button" @click="printTerms">
                    <printer-icon class="action-icon" />
                    打印条款
                  </button>
                  <button class="action-button" @click="downloadPdf">
                    <download-icon class="action-icon" />
                    下载PDF
                  </button>
                </div>
              </div>
            </div>
            
            <!-- 条款内容 -->
            <div class="terms-content">
              <div class="content-intro">
                <p>
                  欢迎使用AIGC Detector平台（以下简称"平台"、"我们"或"我们的"）。本服务条款（以下简称"条款"）规定了您访问和使用我们的网站、服务和应用程序的条件。请您在使用我们的服务前仔细阅读本条款。
                </p>
                <p>
                  <strong>通过访问或使用我们的服务，您同意受本条款的约束。如果您不同意本条款的任何部分，则您不得访问或使用我们的服务。</strong>
                </p>
              </div>
              
              <div 
                v-for="(section, index) in termsSections" 
                :key="index"
                :id="`section-${index}`"
                class="terms-section-content"
                ref="sectionRefs"
              >
                <h2 class="section-title">{{ section.title }}</h2>
                <div class="section-body" v-html="section.content"></div>
              </div>
              
              <div class="terms-footer">
                <p>
                  如果您对本服务条款有任何疑问，请通过以下方式联系我们：
                </p>
                <p>
                  <strong>AIGC Detector</strong><br>
                  电子邮件：zhulong@aigcdetector.com<br>
                  地址：浙江省烛龙科技人工智能有限公司<br>
                  电话：+86 123 4567 8910
                </p>
              </div>
            </div>
          </div>
        </section>
        
        <section class="cta-section">
          <h2 class="cta-title">有任何疑问？</h2>
          <p class="cta-description">如果您对我们的服务条款有任何疑问，请随时联系我们的客服团队</p>
          <div class="cta-buttons">
            <a href="/contact" class="cta-button primary">
              <message-circle-icon class="button-icon" />
              联系我们
            </a>
            <a href="/help" class="cta-button secondary">
              <help-circle-icon class="button-icon" />
              帮助中心
            </a>
          </div>
        </section>
      </main>
      
      <!-- 页脚 -->
      <footer class="footer">
        <div class="footer-content">
          <div class="footer-logo">
            <img src="/placeholder.svg?height=40&width=40" alt="Logo" class="logo-img" />
            <span class="logo-text">AIGC Detector</span>
          </div>
          
          <div class="footer-links">
            <a href="/home" class="footer-link">首页</a>
            <!--<a href="#" class="footer-link">产品</a>
            <a href="#" class="footer-link">定价</a>
            <a href="#" class="footer-link">文档</a>
            <a href="#" class="footer-link">博客</a>-->
            <a href="/about" class="footer-link">关于我们</a>
          </div>
          <!--<div class="legal-links">
            <a href="/terms" class="legal-link active">服务条款</a>
            <a href="/privacy" class="legal-link">隐私政策</a>
            <a href="/cookies" class="legal-link">Cookie政策</a>
          </div>-->
          
          
          <p class="copyright">© 2025 AIGC Detector. 保留所有权利。</p>
        </div>
      </footer>
      
      <!-- 返回顶部按钮 -->
      <button 
        v-show="showBackToTop" 
        @click="scrollToTop" 
        class="back-to-top"
        aria-label="返回顶部"
      >
        <chevron-up-icon class="back-to-top-icon" />
      </button>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, onUnmounted } from 'vue';
  import { 
    Printer as PrinterIcon,
    Download as DownloadIcon,
    MessageCircle as MessageCircleIcon,
    HelpCircle as HelpCircleIcon,
    ChevronUp as ChevronUpIcon
  } from 'lucide-vue-next';
  
  // 条款内容
  const termsSections = [
    {
      title: '1. 条款接受',
      content: `
        <p>1.1 通过访问或使用AIGC Detector平台，您确认您已阅读、理解并同意受本服务条款的约束。如果您不同意本条款的任何部分，请勿使用我们的服务。</p>
        <p>1.2 您必须年满18岁或在您所在司法管辖区内具有法定成年人资格才能使用我们的服务。如果您未满18岁或不具有法定成年人资格，您必须在父母或法定监护人的监督下使用我们的服务。</p>
        <p>1.3 我们保留随时修改、更改或更新本条款的权利。任何修改将在我们的网站上发布后立即生效。您继续使用我们的服务将被视为接受修改后的条款。</p>
      `
    },
    {
      title: '2. 服务描述',
      content: `
        <p>2.1 AIGC Detector平台提供AI生成内容检测服务，包括但不限于图片、文本、视频和音频内容的AI生成可能性分析。</p>
        <p>2.2 我们的服务可能会不时更新或修改，我们保留随时更改、暂停或终止服务的任何方面的权利，恕不另行通知。</p>
        <p>2.3 我们不保证服务将始终可用或不间断。服务可能会因维护、更新或其他因素而暂时不可用。</p>
        <p>2.4 某些功能和服务可能需要付费订阅。付费服务的详细信息将在我们的网站上明确标明。</p>
      `
    },
    {
      title: '3. 用户账户',
      content: `
        <p>3.1 为了使用我们的某些服务，您可能需要创建一个账户。您同意提供准确、完整和最新的信息，并同意及时更新您的信息。</p>
        <p>3.2 您负责维护您账户的保密性，并对您账户下发生的所有活动负责。如果您发现任何未经授权使用您账户的情况，请立即通知我们。</p>
        <p>3.3 我们保留拒绝服务、终止账户、删除或编辑内容或取消订单的权利，恕不另行通知。</p>
        <p>3.4 您可以随时按照我们网站上提供的说明删除您的账户。账户删除后，您的个人数据将按照我们的隐私政策处理。</p>
      `
    },
    {
      title: '4. 用户义务',
      content: `
        <p>4.1 您同意不会：</p>
        <ul>
          <li>违反任何适用的法律、法规或本条款；</li>
          <li>侵犯他人的知识产权或其他权利；</li>
          <li>上传、发布或传输任何包含病毒、蠕虫、特洛伊木马或其他有害代码的内容；</li>
          <li>干扰或破坏服务或连接到服务的服务器和网络；</li>
          <li>尝试未经授权访问我们的系统或其他用户的账户；</li>
          <li>使用自动化程序、机器人或其他方式大量访问我们的服务；</li>
          <li>出于任何非法、欺诈或未经授权的目的使用我们的服务。</li>
        </ul>
        <p>4.2 您理解并同意，您对您上传、提交或通过我们的服务处理的所有内容负责。</p>
      `
    },
    {
      title: '5. 知识产权',
      content: `
        <p>5.1 我们的服务和内容（包括但不限于文本、图形、徽标、图标、图像、音频剪辑、下载、数据编译和软件）是AIGC Detector或其许可方的财产，受版权、商标和其他知识产权法律的保护。</p>
        <p>5.2 我们授予您有限的、非排他性的、不可转让的许可，以访问和使用我们的服务用于个人或内部业务目的。</p>
        <p>5.3 未经我们明确书面许可，您不得：</p>
        <ul>
          <li>修改、复制、分发、传输、展示、执行、复制、发布、许可、创建衍生作品、转让或销售我们服务中的任何信息、软件、产品或服务；</li>
          <li>使用我们的商标、徽标或其他专有信息；</li>
          <li>对我们的软件进行反向工程、反编译或反汇编。</li>
        </ul>
      `
    },
    {
      title: '6. 隐私政策',
      content: `
        <p>6.1 我们重视您的隐私。我们的<a href="/privacy" class="text-link">隐私政策</a>描述了我们如何收集、使用、存储和保护您的个人信息。通过使用我们的服务，您同意我们按照隐私政策处理您的个人信息。</p>
        <p>6.2 我们将采取合理的安全措施保护您的个人信息，但不能保证绝对的安全性。您理解并接受使用互联网传输信息的固有风险。</p>
      `
    },
    {
      title: '7. 付款和订阅',
      content: `
        <p>7.1 某些服务可能需要付费订阅。所有价格均以人民币计价，除非另有说明。</p>
        <p>7.2 您同意支付与您选择的订阅计划相关的所有费用。付款将按照您选择的订阅周期（月度、年度等）自动续费，直到您取消订阅。</p>
        <p>7.3 您可以随时在账户设置中取消订阅。取消后，您仍然可以使用当前订阅周期内的服务，直到订阅期结束。</p>
        <p>7.4 我们保留随时更改价格的权利。价格变更将在下一个计费周期生效，并会提前通知您。</p>
        <p>7.5 除非适用法律要求，否则所有付款均不可退款。</p>
      `
    },
    {
      title: '8. 责任限制',
      content: `
        <p>8.1 在法律允许的最大范围内，AIGC Detector及其董事、员工、合作伙伴和代理人不对因使用或无法使用我们的服务而导致的任何直接、间接、附带、特殊、后果性或惩罚性损害负责，包括但不限于利润损失、商誉损失、数据损失或其他无形损失。</p>
        <p>8.2 我们的服务按"现状"和"可用"基础提供，没有任何明示或暗示的保证，包括但不限于适销性、特定用途适用性和非侵权的暗示保证。</p>
        <p>8.3 我们不保证检测结果的准确性。我们的AI检测服务提供的是基于概率的分析，可能存在误报或漏报的情况。</p>
        <p>8.4 在任何情况下，我们对您的总责任不超过您在过去12个月内为我们的服务支付的金额。</p>
      `
    },
    {
      title: '9. 终止',
      content: `
        <p>9.1 我们可以自行决定，出于任何原因或无任何原因，暂停或终止您对我们服务的访问，恕不另行通知。</p>
        <p>9.2 如果您违反本条款的任何规定，我们有权立即终止您使用我们服务的权利。</p>
        <p>9.3 终止后，您访问我们服务的权利将立即停止。本条款中在其性质上应当继续有效的条款将在终止后继续有效。</p>
      `
    },
    {
      title: '10. 条款变更',
      content: `
        <p>10.1 我们保留随时修改本条款的权利。修改后的条款将在我们的网站上发布，并在发布后立即生效。</p>
        <p>10.2 您继续使用我们的服务将被视为接受修改后的条款。如果您不同意修改后的条款，您应停止使用我们的服务。</p>
        <p>10.3 我们建议您定期查看本条款，以了解任何变更。</p>
      `
    },
    {
      title: '11. 适用法律',
      content: `
        <p>11.1 本条款受中华人民共和国法律管辖，不考虑法律冲突原则。</p>
        <p>11.2 与本条款相关的任何争议应提交至北京市有管辖权的法院解决。</p>
        <p>11.3 如果本条款的任何条款被认定为无效或不可执行，其余条款仍将继续有效。</p>
      `
    },
    {
      title: '12. 联系我们',
      content: `
        <p>如果您对本服务条款有任何疑问或建议，请通过以下方式联系我们：</p>
        <p>
          <strong>AIGC Detector</strong><br>
          电子邮件：zhulong@aigcdetector.com<br>
          地址：浙江省烛龙科技人工智能有限公司<br>
          电话：+86 123 4567 8910
        </p>
      `
    }
  ];
  
  // 当前活跃的部分
  const activeSection = ref(0);
  const sectionRefs = ref([]);
  const showBackToTop = ref(false);
  
  // 滚动到指定部分
  const scrollToSection = (index) => {
    activeSection.value = index;
    const element = sectionRefs.value[index];
    if (element) {
      const yOffset = -100; // 考虑固定头部的高度
      const y = element.getBoundingClientRect().top + window.pageYOffset + yOffset;
      window.scrollTo({ top: y, behavior: 'smooth' });
    }
  };
  
  // 滚动到顶部
  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };
  
  // 打印条款
  const printTerms = () => {
    window.print();
  };
  
  // 下载PDF（模拟）
  const downloadPdf = () => {
    alert('咱不支持该功能');
    // 实际实现中，这里会调用API生成PDF并提供下载
  };
  
  // 监听滚动事件，更新当前活跃部分
  const handleScroll = () => {
    // 显示/隐藏返回顶部按钮
    showBackToTop.value = window.scrollY > 300;
    
    // 更新当前活跃部分
    if (sectionRefs.value.length > 0) {
      const scrollPosition = window.scrollY + 150; // 添加偏移量以提高准确性
      
      for (let i = sectionRefs.value.length - 1; i >= 0; i--) {
        const element = sectionRefs.value[i];
        if (element) {
          const offsetTop = element.offsetTop;
          
          if (scrollPosition >= offsetTop) {
            activeSection.value = i;
            break;
          }
        }
      }
    }
  };
  
  // 生命周期钩子
  onMounted(() => {
    window.addEventListener('scroll', handleScroll);
    handleScroll(); // 初始检查
  });
  
  onUnmounted(() => {
    window.removeEventListener('scroll', handleScroll);
  });
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
    font-size: 1.125rem;
    color: #718096;
  }
  
  /* 条款部分 */
  .terms-section {
    margin-bottom: 4rem;
  }
  
  .terms-container {
    display: grid;
    grid-template-columns: 1fr;
    gap: 2rem;
    max-width: 1200px;
    margin: 0 auto;
  }
  
  @media (min-width: 1024px) {
    .terms-container {
      grid-template-columns: 280px 1fr;
    }
  }
  
  /* 侧边栏 */
  .terms-sidebar {
    position: relative;
  }
  
  .sidebar-sticky {
    position: sticky;
    top: 2rem;
    background: white;
    border-radius: 0.75rem;
    padding: 1.5rem;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
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
    margin: 0 0 1.5rem;
  }
  
  .sidebar-item {
    margin-bottom: 0.5rem;
  }
  
  .sidebar-link {
    display: block;
    padding: 0.5rem 0.75rem;
    border-radius: 0.375rem;
    color: #4a5568;
    text-decoration: none;
    font-size: 0.875rem;
    transition: all 0.2s;
  }
  
  .sidebar-link:hover {
    background: #edf2f7;
    color: #3182ce;
  }
  
  .sidebar-item.active .sidebar-link {
    background: #ebf8ff;
    color: #3182ce;
    font-weight: 500;
  }
  
  .sidebar-actions {
    display: flex;
    flex-direction: column;
    gap: 0.75rem;
  }
  
  .action-button {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.75rem 1rem;
    background: white;
    border: 1px solid #e2e8f0;
    border-radius: 0.375rem;
    color: #4a5568;
    font-size: 0.875rem;
    cursor: pointer;
    transition: all 0.2s;
  }
  
  .action-button:hover {
    background: #f7fafc;
    border-color: #cbd5e0;
  }
  
  .action-icon {
    width: 1rem;
    height: 1rem;
  }
  
  /* 条款内容 */
  .terms-content {
    background: white;
    border-radius: 0.75rem;
    padding: 2rem;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .content-intro {
    margin-bottom: 2rem;
    padding-bottom: 1.5rem;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .content-intro p {
    margin-bottom: 1rem;
    line-height: 1.7;
    color: #4a5568;
  }
  
  .terms-section-content {
    margin-bottom: 2.5rem;
    scroll-margin-top: 2rem;
  }
  
  .section-title {
    font-size: 1.5rem;
    font-weight: 700;
    margin-bottom: 1.5rem;
    color: #2d3748;
  }
  
  .section-body {
    color: #4a5568;
  }
  
  .section-body p {
    margin-bottom: 1rem;
    line-height: 1.7;
  }
  
  .section-body ul {
    margin-bottom: 1rem;
    padding-left: 1.5rem;
  }
  
  .section-body li {
    margin-bottom: 0.5rem;
    line-height: 1.7;
  }
  
  .text-link {
    color: #3182ce;
    text-decoration: none;
  }
  
  .text-link:hover {
    text-decoration: underline;
  }
  
  .terms-footer {
    margin-top: 3rem;
    padding-top: 1.5rem;
    border-top: 1px solid #e2e8f0;
  }
  
  .terms-footer p {
    margin-bottom: 1rem;
    line-height: 1.7;
    color: #4a5568;
  }
  
  /* CTA部分 */
  .cta-section {
    background: linear-gradient(to right, #3182ce, #805ad5);
    border-radius: 0.75rem;
    padding: 3rem 2rem;
    text-align: center;
    color: white;
    margin-bottom: 4rem;
    max-width: 1200px;
    margin-left: auto;
    margin-right: auto;
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
    max-width: 1200px;
    margin: 0 auto;
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
  
  .legal-links {
    display: flex;
    gap: 1.5rem;
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .legal-link {
    color: #718096;
    text-decoration: none;
    font-size: 0.875rem;
    transition: color 0.2s;
  }
  
  .legal-link:hover {
    color: #3182ce;
  }
  
  .legal-link.active {
    color: #3182ce;
    font-weight: 500;
  }
  
  .copyright {
    color: #718096;
    font-size: 0.875rem;
  }
  
  /* 返回顶部按钮 */
  .back-to-top {
    position: fixed;
    bottom: 2rem;
    right: 2rem;
    width: 3rem;
    height: 3rem;
    background: white;
    border: 1px solid #e2e8f0;
    border-radius: 9999px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
    transition: all 0.2s;
    z-index: 10;
  }
  
  .back-to-top:hover {
    transform: translateY(-3px);
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  }
  
  .back-to-top-icon {
    width: 1.25rem;
    height: 1.25rem;
    color: #3182ce;
  }
  
  /* 打印样式 */
  @media print {
    .terms-sidebar,
    .cta-section,
    .back-to-top,
    .footer {
      display: none;
    }
    
    .page-container {
      background-color: white;
    }
    
    .main-content {
      padding: 0;
    }
    
    .terms-container {
      display: block;
    }
    
    .terms-content {
      box-shadow: none;
      padding: 0;
    }
    
    .section-title {
      break-after: avoid;
    }
    
    .section-body {
      page-break-inside: avoid;
    }
  }
  </style>