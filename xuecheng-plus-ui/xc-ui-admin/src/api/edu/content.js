import request from "@/utils/request";
const API_PRE = "/content";

// 课程分类列表
export function category() {
  return request({
    url: API_PRE + "/course-category/tree-nodes",
    method: "get",
  });
}

// 课程列表
export function list(params, body) {
  return request({
    url: API_PRE + "/course/list",
    method: "post",
    params: params,
    data: body,
  });
}

// 提交审核
export function commitCourse(courseBaseId) {
  return request({
    url: API_PRE + `/courseaudit/commit/${courseBaseId}`,
    method: "post",
  });
}

// 课程发布
export function publishCourse(courseBaseId) {
  return request({
    url: API_PRE + `/coursepublish/${courseBaseId}`,
    method: "post",
  });
}

// 课程下架

export function offlineCourse(courseBaseId) {
  return request({
    url: API_PRE + `/courseoffline/${courseBaseId}`,
    method: "post",
  });
}

// 课程删除
export function removeCourse(courseBaseId) {
  return request({
    url: API_PRE + `/course/${courseBaseId}`,
    method: "delete",
  });
}

////////////////////////////////////////////////
// 第一步 基本信息
////////////////////////////////////////////////

// 读取 基本信息
export function getBaseInfo(courseBaseId) {
  return request({
    url: API_PRE + `/course/${courseBaseId}`,
    method: "get",
  });
}

// 更新 基本信息
export function submitBaseInfo(body) {
  if (body.pic) {
    body.pic = body.pic.replace(`${process.env.VUE_APP_MINIO_BASE_URL}`, "");
  }
  return request({
    url: API_PRE + `/course`,
    method: body.id !== undefined ? "put" : "post",
    data: body,
  });
}

////////////////////////////////////////////////
// 第二步 课程大纲
////////////////////////////////////////////////

// 读取完整列表
export function getOutline(courseBaseId) {
  return request({
    url: API_PRE + `/teachplan/${courseBaseId}/tree-nodes`,
    method: "get",
  });
}

// 保存节点
export function submitOutlineNode(body) {
  return request({
    url: API_PRE + `/teachplan`,
    method: "post",
    data: body,
  });
}

// 删除节点
export function deleteOutlineNode(courseId,nodeId) {
  return request({
    url: API_PRE + `/teachplan/${courseId}/${nodeId}`,
    method: "delete",
  });
}

//上移节点
export function moveUpSubmit(courseId,teachPlanId) {
  return request({
    url: API_PRE + `/teachplan/${courseId}/moveup/${teachPlanId}`,
    method: "post",
  });
}

//下移节点
export function moveDownSubmit(courseId,teachPlanId) {
  return request({
    url: API_PRE + `/teachplan/${courseId}/movedown/${teachPlanId}`,
    method: "post",
  });
}

// 媒资绑定
export function mediaAssociation(mediaId, fileName, teachplanId, courseId) {
  return request({
    url: API_PRE + `/teachplan/association/media`,
    method: "post",
    data: {
      mediaId: mediaId,
      fileName: fileName,
      teachplanId: teachplanId,
      courseId: courseId,
    },
  });
}

// // 媒资解绑
export function mediaUnAssociation(teachplanId, mediaId, courseBaseId) {
  return request({
    url:
      API_PRE +
      `/teachplan/association/media/${courseBaseId}/${teachplanId}/${mediaId}`,
    method: "delete",
  });
}

// 作业绑定
export function workAssociation(teachplanId, workId, workTitle) {
  return request({
    url: API_PRE + `/teachplan/work/association`,
    method: "post",
    data: {
      teachplanId: teachplanId,
      workId: workId,
      workTitle: workTitle,
    },
  });
}

// 作业解绑
export function workUnAssociation(teachplanWorkId, courseBaseId) {
  return request({
    url: API_PRE + `/teachplan/work/${teachplanWorkId}`,
    method: "delete",
    params: {
      courseBaseId: courseBaseId,
    },
  });
}
// ////////////////////////////////////////////////
// // 第三步 课程教师
// ////////////////////////////////////////////////

// 读取完整列表
export async function getTeachers(courseBaseId) {
  const data = await request({
    url: API_PRE + `/courseTeacher/list/${courseBaseId}`,
    method: "get",
  });
  for (let key in data) {
    if (data[key].photograph) {
      data[key].photograph =
        `${process.env.VUE_APP_MINIO_BASE_URL}` + data[key].photograph;
    }
  }
  return data;
}

// 保存教师
export function submitTeacher(body) {
  if (body.photograph) {
    body.photograph = body.photograph.replace(
      `${process.env.VUE_APP_MINIO_BASE_URL}`,
      ""
    );
  }
  return request({
    url: API_PRE + `/courseTeacher`,
    method: "post",
    data: body,
  });
}

// 删除教师
export function deleteTeacher(courseBaseId, courseTeacherId) {
  return request({
    url: API_PRE + `/courseTeacher/course/${courseBaseId}/${courseTeacherId}`,
    method: "delete",
  });
}
