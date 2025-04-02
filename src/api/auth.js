// import axios from './axios';

// // 用户登录
// export const login = (username, password) => {
//   return axios.post('/auth/login', {
//     username,
//     password
//   });
// };

// // 用户注册
// export const register = (userData) => {
//   return axios.post('/auth/register', userData);
// };

// // 获取用户信息
// export const getUserInfo = () => {
//   return axios.get('/user/info');
// }; 

import axios from './axios';

// const api = axios.create({
//   baseURL: process.env.VUE_APP_API_URL || 'http://112.124.49.39/api',
//   withCredentials: true
// });

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

// 发送邮箱验证码
export const sendEmailVerificationCode = (email) => {
  return axios.post('/auth/verify-email', {
    email
  });
}; 