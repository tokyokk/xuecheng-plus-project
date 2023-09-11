import request from "@/utils/request";
const API_PRE = "/tool";

// 分页查询生成配置列表
export function pageGenConfig(query) {
  return request({
    url: API_PRE + "/genConfig/page",
    method: "get",
    params: query,
  });
}
// 查询生成配置列表
export function listGenConfig(query) {
  return request({
    url: API_PRE + "/genConfig/list",
    method: "get",
    params: query,
  });
}
// 查询生成配置详细
export function getGenConfig(id) {
  return request({
    url: API_PRE + "/genConfig/details",
    method: "get",
    params: {
      id: id,
    },
  });
}

// 新增生成配置
export function addGenConfig(data) {
  return request({
    url: API_PRE + "/genConfig/save",
    method: "post",
    data: data,
  });
}

// 修改生成配置
export function updateGenConfig(data) {
  return request({
    url: API_PRE + "/genConfig/edit",
    method: "post",
    data: data,
  });
}

// 删除生成配置
export function delGenConfig(id) {
  return request({
    url: API_PRE + "/genConfig/delete",
    method: "post",
    params: {
      id: id,
    },
  });
}
