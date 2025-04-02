import axios from './axios';

// 获取所有充值套餐
export const getAllPackages = () => {
  console.log('调用获取所有充值套餐API');
  return axios.get('/recharge/packages')
    .catch(error => {
      console.error('获取充值套餐失败:', error);
      throw error;
    });
};

// 获取套餐详情
export const getPackageById = (id) => {
  console.log(`调用获取套餐详情API, id: ${id}`);
  return axios.get(`/recharge/packages/${id}`)
    .catch(error => {
      console.error(`获取套餐详情失败, id: ${id}:`, error);
      throw error;
    });
};

// 创建充值订单
export const createRechargeOrder = (packageId, paymentMethod) => {
  console.log(`调用创建充值订单API, packageId: ${packageId}, paymentMethod: ${paymentMethod}`);
  return axios.post('/recharge/order', null, {
    params: { packageId, paymentMethod }
  })
    .catch(error => {
      console.error('创建充值订单失败:', error);
      throw error;
    });
};

// 完成充值订单
export const completeRechargeOrder = (orderId) => {
  console.log(`调用完成充值订单API, orderId: ${orderId}`);
  return axios.post(`/recharge/complete/${orderId}`)
    .catch(error => {
      console.error(`完成充值订单失败, orderId: ${orderId}:`, error);
      throw error;
    });
};

// 获取用户交易记录
export const getUserTransactions = (filter) => {
  console.log(`调用获取用户交易记录API, filter: ${filter}`);
  return axios.get('/recharge/transactions', {
    params: { filter }
  })
    .catch(error => {
      console.error('获取用户交易记录失败:', error);
      throw error;
    });
}; 