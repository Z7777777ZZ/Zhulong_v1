/**
 * 格式化日期显示
 * @param {string|Date} dateString - 日期字符串或Date对象
 * @param {boolean} includeTime - 是否包含时间
 * @returns {string} 格式化后的日期字符串
 */
export const formatDate = (dateString, includeTime = false) => {
  if (!dateString) return '';
  
  const date = typeof dateString === 'string' ? new Date(dateString) : dateString;
  
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  };
  
  if (includeTime) {
    options.hour = '2-digit';
    options.minute = '2-digit';
  }
  
  return date.toLocaleString('zh-CN', options);
}; 