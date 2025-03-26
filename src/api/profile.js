import axios from './axios';

// 获取用户个人资料
export const getUserProfile = async () => {
  try {
    const response = await axios.get('/profile');
    if (response.data) {
      return {
        success: true,
        data: response.data.data || response.data
      };
    }
    return {
      success: false,
      message: '获取个人资料失败'
    };
  } catch (error) {
    console.error('获取个人资料错误:', error);
    return {
      success: false,
      message: error.response?.data?.message || '获取个人资料失败'
    };
  }
};

// 更新用户个人资料
export const updateUserProfile = async (userData) => {
  try {
    const response = await axios.put('/profile', null, {
      params: userData
    });
    if (response.data) {
      return response.data;
    }
    throw new Error('更新个人资料失败');
  } catch (error) {
    console.error('更新个人资料错误:', error);
    throw error.response?.data?.message 
      ? new Error(error.response.data.message) 
      : new Error('更新个人资料失败，请稍后重试');
  }
};

// 更新用户密码
export const updateUserPassword = async (passwords) => {
  try {
    const response = await axios.put('/profile/password', null, {
      params: passwords
    });
    if (response.data) {
      return response.data;
    }
    throw new Error('更新密码失败');
  } catch (error) {
    console.error('更新密码错误:', error);
    throw error.response?.data?.message 
      ? new Error(error.response.data.message) 
      : new Error('更新密码失败，请稍后重试');
  }
}; 