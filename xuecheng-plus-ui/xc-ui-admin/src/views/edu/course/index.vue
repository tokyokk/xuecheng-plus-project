<template>
  <div class="app-container">
    <el-form
      :model="query"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="课程名称" prop="courseName">
        <el-input
          v-model="query.courseName"
          placeholder="请输入课程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select
          v-model="query.publishStatus"
          placeholder="审核状态"
          clearable
        >
          <el-option
            v-for="dict in dict.type.C202"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="状态" prop="publishStatus">
        <el-select
          v-model="query.publishStatus"
          placeholder="发布状态"
          clearable
        >
          <el-option
            v-for="dict in dict.type.C203"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="open = true"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleEdit(selection[0])"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="single"
          @click="handleDelete(selection[0])"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <!-- 数据列表 -->
    <el-table
      v-loading="loading"
      :data="list"
      border
      style="width: 100%"
      :header-cell-style="{ textAlign: 'center' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        prop="id"
        label="编号"
        width="75"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="name"
        label="课程名称"
        width="150"
        align="center"
      ></el-table-column>
      <el-table-column label="课程等级" align="center" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.C204" :value="scope.row.grade" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" width="100">
        <template slot-scope="scope">
          <div>
            <dict-tag
              :options="dict.type.C202"
              :value="scope.row.auditStatus"
            />
            <el-button
              v-if="scope.row.auditStatus == '202001'"
              type="text"
              size="mini"
              @click="showMessageBox(scope.row.auditMind, '审核未通过')"
              >查看审核意见</el-button
            >
            <!-- <el-button
              v-else-if="scope.row.auditStatus == '202005'"
              type="text"
              size="mini"
              @click="handleViewDetail(scope.row)"
              >查看课程详情</el-button> -->
          </div>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.C200" :value="scope.row.teachmode" />
        </template>
      </el-table-column>
      <el-table-column label="发布状态" align="center" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.C203" :value="scope.row.status" />
        </template>
      </el-table-column>
      <!-- TODO: 增加友好提示 -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="mini"
            :disabled="scope.row.status == '203002'"
            @click="handleEdit(scope.row)"
            >编辑</el-button
          >
          <el-button
            type="text"
            size="mini"
            :disabled="scope.row.auditStatus != '202001'"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
          <el-button type="text" size="mini" @click="handlePreview(scope.row)"
            >预览</el-button
          >
          <el-button
            type="text"
            size="mini"
            :disabled="scope.row.auditStatus != '202002'"
            @click="handleCommit(scope.row)"
            >提交审核</el-button
          >
          <el-button
            type="text"
            size="mini"
            :disabled="
              !(
                scope.row.status == '203001' &&
                scope.row.auditStatus == '202004'
              )
            "
            @click="handlePublish(scope.row)"
            >发布</el-button
          >
          <el-button
            type="text"
            size="mini"
            :disabled="scope.row.status != '203002'"
            @click="handleOffline(scope.row)"
            >下架</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNo"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <el-dialog
      title="选择课程形式"
      width="500px"
      :visible.sync="open"
      append-to-body
    >
      <div class="types">
        <div class="item" v-for="(item, index) in dict.type.C200" :key="index">
          <div class="selected-item">
            <div
              class="clouse-type"
              :class="{ selected: addtype == item.value }"
              @click="addtype = item.value"
            >
              <div class="icon">
                <img
                  src="@/assets/img/icon_zhiboke@2x.png"
                  v-if="item.value == '200003'"
                />
                <img
                  src="@/assets/img/icon_luboke@2x.png"
                  v-if="item.value == '200002'"
                />
              </div>
              <div class="title">{{ item.label }}</div>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer">
        <el-button type="primary" @click="handleNext">下一步</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  list,
  commitCourse,
  publishCourse,
  offlineCourse,
} from "@/api/edu/content.js";

export default {
  name: "course",
  dicts: ["C200", "C201", "C202", "C203", "C204"],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      selection: [],
      // 总条数
      total: 0,
      // 参数表格数据
      list: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
      },
      //查询参数
      query: {
        courseName: "",
        auditStatus: undefined,
        publishStatus: undefined,
      },
      open: false,
      addtype: "200002",

      auditForm: {
        courseId: null,
        name: "",
        auditStatus: null,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      list(this.queryParams, this.query).then((res) => {
        this.list = res.items;
        this.total = res.counts;
        this.loading = false;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selection = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    showMessageBox(auditMind, msg) {
      this.$modal.alert(auditMind, msg);
    },

    handleViewDetail(row) {
      if (row.coursePubId) {
        window.open(
          `${process.env.VUE_APP_SERVER_QINIU_URL}/course_pub/${row.coursePubId}.html`,
          "_blank"
        );
      }
    },

    handleEdit(row) {
      let teachmode = row.teachmode;
      let params = { courseBaseId: row.id };
      this.$tab.openPage(
        "课程编辑",
        "/edu/course-add/index/" + teachmode,
        params
      );
    },
    async handleDelete(row) {
      try {
        await this.showDeleteConfirm();
        if (row.courseBaseId) {
          await removeCourse(row.courseBaseId);
          await this.getList();
        }
      } catch (error) {}
    },
    handlePreview(row) {
      if (row.id) {
        window.open(
          `${process.env.VUE_APP_SERVER_API_URL}/content/coursepreview/${row.id}`,
          "_blank"
        );
      }
    },
    handleCommit(row) {
      this.$modal
        .confirm("请确认课程信息编辑完成，是否提交平台审核?")
        .then(() => {
          if (row.id) {
            commitCourse(row.id).then(() => {
              this.$modal.msgSuccess("提交成功");
              //提交成功后刷新列表
              this.getList();
            });
          }
        });
    },
    handlePublish(row) {
      this.$modal
        .confirm("课程发布后将在网站公开，是否继续发布课程?")
        .then(() => {
          if (row.id) {
            publishCourse(row.id).then(() => {
              this.$modal.msgSuccess("操作成功，请稍后在课程搜索中搜索课程");
            });
            this.getList();
          }
        });
    },
    handleOffline(row) {
      this.$modal
        .confirm(
          "课程下架后无法在网站查询，但不影响现有学生学习，是否继续下架课程?"
        )
        .then(() => {
          if (row.id) {
            offlineCourse(row.id).then(() => {
              this.$message.success(
                "操作成功，稍后在课程搜索中将无法查询到课程。"
              );
            });
            this.getList();
          }
        });
    },
    handleNext() {
      this.open = false;
      let teachmode = this.addtype;
      let params = {};
      this.$tab.openPage(
        "课程添加",
        "/edu/course-add/index/" + teachmode,
        params
      );
    },
  },
};
</script>

<style lang="scss" scoped>
.types {
  height: 220px;
  .item {
    display: inline-block;
    width: 50%;
    height: 100%;
    // background-color: bisque;
    padding-top: 20px;
    .selected-item {
      margin: 0px auto;
      .clouse-type {
        cursor: pointer;
        width: 152px;
        height: 174px;
        border-radius: 6px;
        text-align: center;
        padding-top: 20px;
        // 未选中
        background: rgba(249, 249, 249, 1);
        border: 0px;

        .icon {
          margin: 0px auto;
          width: 80px;
          height: 80px;
          border: 1px dotted #e4e7ed;
          img {
            height: 80px;
            width: 80px;
          }
        }
        .title {
          padding-top: 20px;
          color: #666666;
        }
        &.selected {
          background: rgba(249, 253, 255, 1);
          border: 1px solid rgba(0, 164, 255, 1);
        }
      }
    }
  }
}
</style>
