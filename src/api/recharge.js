import axios from './axios';

// 获取所有充值套餐
export const getAllPackages = () => {
  return axios.get('/recharge/packages');
};

// 获取套餐详情
export const getPackageById = (id) => {
  return axios.get(`/recharge/packages/${id}`);
};

// 创建充值订单
export const createRechargeOrder = (packageId, paymentMethod) => {
  return axios.post('/recharge/order', null, {
    params: { packageId, paymentMethod }
  });
};

// 完成充值订单
export const completeRechargeOrder = (orderId) => {
  return axios.post(`/recharge/complete/${orderId}`);
};

// 获取用户交易记录
export const getUserTransactions = (filter) => {
  return axios.get('/recharge/transactions', {
    params: { filter }
  });
}; 