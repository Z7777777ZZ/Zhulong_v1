import axios from 'axios';

// 创建axios实例
const instance = axios.create({
  baseURL: 'http://localhost:8081/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器
instance.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  response => {
    // 处理后端返回的不同数据结构
    const data = response.data;
    
    // 如果响应已经是标准结构 {success: true, data: {...}}，则直接返回
    if (data && (data.success !== undefined)) {
      return data;
    }
    
    // 对于其他结构，转换为标准结构
    return {
      success: true,
      data: data
    };
  },
  error => {
    if (error.response) {
      // 处理401未授权错误
      if (error.response.status === 401) {
        // 移除401未授权时自动清理token和存储的行为
        // 让各组件自己决定如何处理，避免在拦截器中自动进行状态清理或跳转
        
        // 返回特定的401错误
        return Promise.reject({
          success: false,
          message: '未授权，请重新登录',
          data: null,
          status: 401
        });
      }
      
      // 转换错误响应为标准格式
      return Promise.reject({
        success: false,
        message: error.response.data.message || '请求失败',
        data: error.response.data,
        status: error.response.status
      });
    }
    
    return Promise.reject({
      success: false,
      message: error.message || '网络错误',
      data: null
    });
  }
);

export default instance; 