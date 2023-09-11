import request from "@/utils/request";
const API_PRE = "/media";
// 机构查询媒资信息
export function getMediaPageList(params, body) {
  return request({
    url: API_PRE + "/files",
    method: "post",
    params: params,
    data: body,
  });
}

// 获取视频上传凭证
export function getVodToken(body) {
  return request({
    url: API_PRE + "/media/vod-token",
    method: "post",
    data: body,
  });
}

// 保存媒资信息
export function saveMedia(body) {
  return request({
    url: API_PRE + "/media",
    method: "post",
    data: body,
  });
}

// 预览点播视频
export function previewMedia(mediaId) {
  return request({
    url: API_PRE + `/preview/${mediaId}`,
    method: "get",
  });
}

// 删除媒资信息
export function deleteMedia(id) {
  return request({
    url: API_PRE + `/${id}`,
    method: "delete",
  });
}
