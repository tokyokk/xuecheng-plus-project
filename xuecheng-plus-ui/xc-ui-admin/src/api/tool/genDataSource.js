
import request from "@/utils/request";
const API_PRE = "/tool";

// 分页查询数据源信息列表
export function pageGenDataSource(query) {
  return request({
    url: API_PRE+'/genDataSource/page',
    method: 'get',
    params: query
  })
}
// 查询数据源信息列表
export function listGenDataSource(query) {
  return request({
    url: API_PRE+'/genDataSource/list',
    method: 'get',
    params: query
  })
}
// 查询数据源信息详细
export function getGenDataSource(dataId) {
  return request({
    url: API_PRE+'/genDataSource/details',
    method: 'get',
    params:{
      id:  dataId
    }
  })
}

// 新增数据源信息
export function addGenDataSource(data) {
  return request({
    url: API_PRE+'/genDataSource/save',
    method: 'post',
    data: data
  })
}

// 修改数据源信息
export function updateGenDataSource(data) {
  return request({
    url: API_PRE+'/genDataSource/edit',
    method: 'post',
    data: data
  })
}

// 删除数据源信息
export function delGenDataSource(dataId) {
  return request({
    url: API_PRE+'/genDataSource/delete',
    method: 'post',
    params:{
      id: dataId
    }
  })
}