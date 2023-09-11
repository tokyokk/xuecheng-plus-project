<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        v-model:showSearch="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="genDataSourceList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="编码" align="center" prop="code">
      </el-table-column>
      <el-table-column label="名称" align="center" prop="connName">
      </el-table-column>
      <el-table-column label="链接" align="center" prop="connUrl">
      </el-table-column>
      <el-table-column label="驱动类型" align="center" prop="dbType">
      </el-table-column>
      <el-table-column label="密码" align="center" prop="password">
      </el-table-column>
      <el-table-column label="用户名称" align="center" prop="userName">
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button type="text" icon="Delete" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改数据源信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form
        ref="genDataSourceRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="名称" prop="connName">
              <el-input placeholder="请输入名称" v-model="form.connName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="编码" prop="code">
              <el-input placeholder="请输入编码" v-model="form.code" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="驱动类型" prop="dbType">
              <el-input placeholder="请输入驱动类型" v-model="form.dbType" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="链接" prop="connUrl">
              <el-input placeholder="请输入链接" v-model="form.connUrl" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账号" prop="userName">
              <el-input placeholder="请输入账号" v-model="form.userName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input placeholder="请输入密码" v-model="form.password" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  pageGenDataSource,
  getGenDataSource,
  delGenDataSource,
  addGenDataSource,
  updateGenDataSource,
} from "@/api/tool/genDataSource";
export default {
  name: "dataSource",

  data() {
    return {
      form: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      rules: {},
      genDataSourceList: [],
      open: false,
      loading: false,
      showSearch: true,
      ids: [],

      single: true,
      multiple: true,
      total: 0,
      title: "",
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询表集合 */
    getList() {
      this.loading = true;
      pageGenDataSource(this.queryParams).then(
        (response) => {
          this.genDataSourceList = response.data;
          this.total = response.totalCount;
          this.loading = false;
        }
      );
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: -1,
        code: null,
        connName: null,
        connUrl: null,
        dbType: null,
        password: null,
        userName: null,
        createTime: null,
      };
      this.resetForm("genDataSourceRef");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据源信息";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      const dataId = row.id || this.ids;
      getGenDataSource(Number(dataId)).then((response) => {
        if (response.success) {
          this.form = response.data;
          this.open = true;
          this.title = "修改数据源信息";
        } else {
          this.$modal.msgError("获取数据源信息失败");
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["genDataSourceRef"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGenDataSource(this.form).then(() => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGenDataSource(this.form).then(() => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const dataId = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除表编号为"' + dataId + '"的数据项？')
        .then(function () {
          return delGenDataSource(dataIds.toString());
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
  },
};
</script>
