import axios from './axios';

// 获取所有历史记录
export const getUserDetectionRecords = () => {
  return axios.get('/history');
};

// 根据类型获取历史记录
export const getDetectionRecordsByType = (typeId) => {
  return axios.get(`/history/type/${typeId}`);
};

// 根据时间范围获取历史记录
export const getDetectionRecordsByTimeRange = (timeRange) => {
  return axios.get('/history/time', { params: { timeRange } });
};

// 根据检测结果获取历史记录
export const getDetectionRecordsByResult = (result) => {
  return axios.get(`/history/result/${result}`);
};

// 获取历史记录详情
export const getDetectionRecordDetail = (id) => {
  return axios.get(`/history/${id}`);
};

// 删除历史记录
export const deleteDetectionRecord = (id) => {
  return axios.delete(`/history/${id}`);
}; 