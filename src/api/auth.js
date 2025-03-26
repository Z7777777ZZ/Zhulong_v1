import axios from './axios';

// 用户登录
export const login = (username, password) => {
  return axios.post('/auth/login', {
    username,
    password
  });
};

// 用户注册
export const register = (userData) => {
  return axios.post('/auth/register', userData);
};

// 获取用户信息
export const getUserInfo = () => {
  return axios.get('/user/info');
}; 