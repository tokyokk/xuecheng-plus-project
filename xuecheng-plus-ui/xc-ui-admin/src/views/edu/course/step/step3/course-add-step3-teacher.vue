<template>
  <div class="step-body">
    <div class="banner">
      <el-button
        type="primary"
        size="medium"
        class="btn-add el-button"
        @click="handleAdd"
        >+添加教师</el-button
      >
    </div>

    <!-- 数据列表 -->
    <el-table
      class="dataList"
      v-loading="listLoading"
      :data="listData"
      border
      style="width: 100%"
      :header-cell-style="{ textAlign: 'center' }"
    >
      <el-table-column
        prop="teacherName"
        label="教师名称"
        align="center"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="position"
        label="教师职位"
        align="center"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="introduction"
        label="教师简介"
        width="600"
      ></el-table-column>
      <el-table-column label="教师照片" align="center" width="100">
        <template slot-scope="scope">
          <img
            :src="scope.row.photograph"
            :alt="scope.row.teacherName"
            width="90"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 对话框 -->
    <el-dialog title="课程教师" :visible.sync="isDialogVisible">
      <div class="form-dialog">
        <el-form
          ref="form"
          :model="teacherData"
          :rules="rules"
          label-width="120px"
        >
          <el-form-item label="教师姓名：" prop="teacherName">
            <el-input v-model="teacherData.teacherName"></el-input>
          </el-form-item>
          <el-form-item label="教师职位：" prop="position">
            <el-input v-model="teacherData.position"></el-input>
          </el-form-item>
          <el-form-item label="教师简介：" prop="introduction">
            <el-input
              v-model="teacherData.introduction"
              type="textarea"
              :rows="3"
            ></el-input>
          </el-form-item>
          <el-form-item label="教师照片：" prop="photograph">
            <image-upload
              v-model="teacherData.photograph"
              :limit="1"
              :param="uploadParam"
              :baseUrl="baseUrl"
              :uploadUrl="uploadFileUrl"
              returnParm1="filename"
              returnParm2="url"
              :fileType="['png', 'jpg', 'jpeg']"
            />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  deleteTeacher,
  getTeachers,
  submitTeacher,
} from "@/api/edu/content.js";
export default {
  name: "course-add-step3-teacher",
  props: {
    courseBaseId: {
      type: Number,
      default: null,
    },
  },
  data() {
    return {
      listData: [],
      listLoading: false,
      isDialogVisible: false,
      teacherData: {
        courseId: null,
        teacherName: "",
        position: "",
        introduction: "",
        photograph: "",
      },
      rules: {
        teacherName: [
          { required: true, message: "请输入姓名", trigger: "blur" },
        ],
        position: [
          {
            required: true,
            message: "请输入职位",
            trigger: "blur",
          },
        ],
      },

      uploadParam: "filedata",
      baseUrl: process.env.VUE_APP_MINIO_BASE_URL,
      uploadFileUrl: process.env.VUE_APP_MINIO_UPLOAD_URL,
    };
  },
  mounted() {
    this.getList();
  },
  computed: {
    getCourseBaseId() {
      return this.courseBaseId;
    },
  },
  watch: {
    getCourseBaseId(newValue) {
      this.teacherData.courseId = newValue;
    },
  },
  methods: {
    // 获取老师列表
    async getList() {
      this.listLoading = true;
      this.listData = await getTeachers(this.courseBaseId);
      console.log("教师数据：", this.listData);
      this.listLoading = false;
    },

    // 添加
    handleAdd() {
      this.teacherData = {
        courseId: this.courseBaseId,
        teacherName: "",
        position: "",
        introduction: "",
      };
      this.isDialogVisible = true;
    },

    // 修改
    handleEdit(data) {
      // debugger
      this.teacherData = data;
      this.isDialogVisible = true;
    },

    // 删除
    async handleDelete(data) {
      await this.$modal.showDeleteConfirm();
      await deleteTeacher(this.courseBaseId, data.id);
      this.getList();
    },

    restForm() {
      this.$refs["form"].resetFields();
    },
    handleCancel() {
      this.isDialogVisible = false;
      this.restForm();
    },
    handleSubmit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          submitTeacher(this.teacherData).then(() => {
            this.isDialogVisible = false;
            this.restForm();
            this.getList();
          });
        }
      });
    },
  },
};
</script>

<style lang="scss">
.step-body {
  .banner {
    text-align: right;
    padding-bottom: 10px;
  }
}
</style>
