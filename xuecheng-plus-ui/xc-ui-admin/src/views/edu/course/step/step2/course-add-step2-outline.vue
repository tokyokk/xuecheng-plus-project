<template>
  <div class="step-body">
    <!-- 工具栏 -->
    <!-- 大纲列表 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAddChapter"
          >添加章</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
          >展开/折叠</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <span>任务总数：{{ outlineData.length }}</span>
      </el-col>
      <right-toolbar
        :showSearchBtn="false"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>
    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="outlineData"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{
        children: 'teachPlanTreeNodes',
        hasChildren: 'hasChildren',
      }"
    >
      <el-table-column prop="pname" label="课程计划名称">
        <template slot-scope="scope">
          <span
            v-if="!scope.row.ctlEditTitle"
            @click="scope.row.ctlEditTitle = true"
          >
            {{ scope.row.pname }}
          </span>
          <el-input
            v-focus="true"
            v-else
            v-model="scope.row.pname"
            placeholder="请输入内容"
            @keyup.enter.native="scope.row.ctlEditTitle = false"
            @blur="handleEditTitleBlue(scope.row)"
            style="width: 280px; height: 25px"
          />
        </template>
      </el-table-column>
      <el-table-column prop="isPreview" label="是否免费" width="80">
        <template slot-scope="scope">
          <el-checkbox
            v-model="scope.row.isPreview"
            true-label="1"
            false-label="0"
            @change="handleChangeIsPreview(scope.row)"
          ></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column
        label="时间周期"
        align="center"
        v-if="teachmode == '200003'"
      >
        <template slot-scope="scope">
          <el-date-picker
            @change="handleChangePublishTime(scope.row)"
            v-model="scope.row.startTime"
            :disabled="!(scope.row.grade == '2' && teachmode == '200003')"
            type="datetime"
            placeholder="选择开始时间"
            default-time="12:00:00"
            :clearable="false"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker
          >~
          <el-date-picker
            @change="handleChangePublishTime(scope.row)"
            v-model="scope.row.endTime"
            :disabled="!(scope.row.grade == '2' && teachmode == '200003')"
            type="datetime"
            placeholder="选择结束时间"
            default-time="12:00:00"
            :clearable="false"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="绑定资源" align="center">
        <template slot-scope="scope">
          <el-link
            v-if="
              scope.row.grade == '2' &&
              scope.row.teachplanMedia != null &&
              scope.row.teachplanMedia.mediaFilename != null &&
              scope.row.teachplanMedia.mediaFilename != ''
            "
            icon="el-icon-delete"
            :underline="false"
            @click="handleDeleteMedia(scope.row)"
            >{{ scope.row.teachplanMedia.mediaFilename }}</el-link
          >
          <el-link
            v-else-if="
              scope.row.grade == '2' &&
              scope.row.work != null &&
              scope.row.work.workTitle != ''
            "
            icon="el-icon-delete"
            :underline="false"
            @click="handleDeleteWork(scope.row)"
            >{{ scope.row.work.workTitle }}</el-link
          >
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.grade == '1'">
            <el-button
              type="text"
              @click.stop="
                handleAddSection(scope.row.teachPlanTreeNodes, scope.row.id)
              "
              >添加小节</el-button
            >
            <el-button type="text" @click.stop="handleDeleteNode(scope.row)"
              >删除本章</el-button
            >
            <!-- 上移 -->
            <el-button type="text" @click.stop="moveUP(scope.row)"
              >上移</el-button
            >
            <!-- 下移 -->
            <el-button type="text" @click.stop="moveDown(scope.row)"
              >下移</el-button
            >
          </div>

          <div v-else-if="scope.row.grade == '2'">
            <template v-if="teachmode == '200002'">
              <!--  <span v-if="data.teachplanMedia != null "  @click="handleSelectVideo(data)">
                    {{data.teachplanMedia.mediaFilename}}
                    </span>-->
              <!-- 作业 
                  <el-button
                    v-if="data.ctlBarShow && data.teachplanMedia === null "
                    type="text"
                    @click.stop="handleSelectWork(data)"
                  >添加作业</el-button>-->

              <!-- 视频 -->
              <el-button type="text" @click.stop="handleSelectVideo(scope.row)"
                >添加视频</el-button
              >

              <!-- 文档 
                  <el-button
                    v-if="data.ctlBarShow && data.teachplanMedia === null "
                    type="text"
                    @click.stop="handleSelectDoc(data)"
                  >添加文档</el-button>-->
            </template>
            <!-- 删除 -->
            <el-button type="text" @click.stop="handleDeleteNode(scope.row)"
              >删除</el-button
            >
            <!-- 上移 -->
            <el-button type="text" @click.stop="moveUP(scope.row.id)"
              >上移</el-button
            >
            <!-- 下移 -->
            <el-button type="text" @click.stop="moveDown(scope.row.id)"
              >下移</el-button
            >
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="selectTypeTitle"
      width="500px"
      :visible.sync="mediaDialogVisible"
    >
      <div class="types">
        <el-select
          v-model="selValue"
          filterable
          remote
          reserve-keyword
          placeholder="请输入关键词"
          :remote-method="remoteQuery"
          :loading="loading"
        >
          <!-- 视频 文档 -->
          <template
            v-if="
              options.items !== undefined &&
              (selectType === 'video' || selectType === 'doc')
            "
          >
            <el-option
              v-for="item in options.items"
              :key="item.id"
              :label="item.filename"
              :value="item.id"
            ></el-option>
          </template>
          <!-- 作业 -->
          <template v-if="options.items !== undefined && selectType === 'work'">
            <el-option
              v-for="item in options.items"
              :key="item.workId"
              :label="item.title"
              :value="item.workId"
            ></el-option>
          </template>
        </el-select>
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
  deleteOutlineNode,
  getOutline,
  mediaAssociation,
  mediaUnAssociation,
  moveDownSubmit,
  moveUpSubmit,
  submitOutlineNode,
  workAssociation,
  workUnAssociation,
} from "@/api/edu/content.js";
import { getMediaPageList } from "@/api/edu/media";
export default {
  name: "course-add-step2-outline",
  props: {
    teachmode: {
      type: String,
      default: "200002",
    },
    courseBaseId: {
      type: Number,
      default: null,
    },
  },
  data() {
    return {
      // 是否展开，默认全部折叠
      isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      showSearch: false,
      outlineData: [],
      mediaDialogVisible: false,
      curSelectNode: {},
      selectType: "",
      selectTypeTitle: "",
      loading: false,
      options: {},
      selValue: null,
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
      this.outlineData.courseId = newValue;
    },
  },
  methods: {
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    // 读取大纲
    async getList() {
      this.loading = true;
      if (this.courseBaseId != 0) {
        let data = await getOutline(this.courseBaseId);
        if (data) {
          this.outlineData = data.map((v) => {
            v.ctlEditTitle = false;
            v.ctlBarShow = false;
            if (v.teachPlanTreeNodes !== null) {
              v.teachPlanTreeNodes = v.teachPlanTreeNodes.map((v) => {
                v.ctlEditTitle = false;
                v.ctlBarShow = false;
                return v;
              });
            }
            return v;
          });
        }
      }
      console.log("任务数据：", this.outlineData);
      this.loading = false;
    },

    // 点击 修改标题
    handleEditTitle(data) {
      data.ctlEditTitle = true;
    },

    // 失去焦点 修改标题
    async handleEditTitleBlue(data) {
      data.ctlEditTitle = false;
      await submitOutlineNode(data);
    },

    // 添加章
    async handleAddChapter() {
      let node = {
        courseId: this.courseBaseId,
        parentid: 0,
        grade: 1,
        pname: "新章名称 [点击修改]",
      };
      await submitOutlineNode(node);
      this.getList();
    },

    // 添加小节
    async handleAddSection(teachPlanTreeNodes, parentid) {
      let node = {
        courseId: this.courseBaseId,
        parentid: parentid,
        grade: 2,
        pname: "新小节名称 [点击修改]",
      };
      await submitOutlineNode(node);
      this.getList();
    },

    // 删除小节
    async handleDeleteNode(node) {
      await this.$modal.showDeleteConfirm();
      await deleteOutlineNode(node.courseId, node.id);
      await this.getList();
      this.$modal.msgSuccess("删除成功");
    },
    //上移
    async moveUP(node) {
      try {
        await moveUpSubmit(node.courseId, node.id);
        this.getList();
      } catch (error) {}
    },
    //下移
    async moveDown(node) {
      try {
        await moveDownSubmit(node.courseId, node.id);
        this.getList();
      } catch (error) {}
    },

    // 免费学习本章
    async handleChangeIsPreview(node) {
      // console.log(node)
      await submitOutlineNode(node);
    },

    // 修改发布时间
    async handleChangePublishTime(node) {
      // console.log(node)
      await submitOutlineNode(node);
    },

    // 选择作业
    handleSelectWork(node) {
      this.restForm();
      this.selectType = "work";
      this.selectTypeTitle = "选择作业";
      this.mediaDialogVisible = true;
      this.curSelectNode = node;
    },

    // 选择视频
    handleSelectVideo(node) {
      this.restForm();
      this.selectType = "video";
      this.selectTypeTitle = "选择视频";
      this.mediaDialogVisible = true;
      this.curSelectNode = node;
    },

    // 选择文档
    handleSelectDoc(node) {
      this.restForm();
      this.selectType = "doc";
      this.selectTypeTitle = "选择文档";
      this.mediaDialogVisible = true;
      this.curSelectNode = node;
    },
    // 收到选中数据
    async onRecvSelectedValue(ret) {
      if (ret.type === "video" || ret.type === "doc") {
        await mediaAssociation(
          ret.value.id,
          ret.value.filename,
          this.curSelectNode.id,
          this.curSelectNode.courseId
        );
      } else if (ret.type === "work") {
        await workAssociation(
          this.curSelectNode.id,
          ret.value.workId,
          ret.value.title
        );
      }
      this.getList();
    },

    // 删除 媒体
    async handleDeleteMedia(node) {
      try {
        if (
          node.teachplanMedia === undefined ||
          node.teachplanMedia.id === undefined
        ) {
          return;
        }
        await this.$modal.showDeleteConfirm();
        await mediaUnAssociation(
          node.teachplanMedia.id,
          node.teachplanMedia.mediaId,
          this.courseBaseId
        );
        this.getList();
      } catch (error) {}
    },

    // 删除 作业
    async handleDeleteWork(node) {
      try {
        if (
          node.work === undefined ||
          node.work.teachplanWorkId === undefined
        ) {
          return;
        }
        await this.$modal.showDeleteConfirm();
        await workUnAssociation(node.work.teachplanWorkId, this.courseBaseId);
        this.getList();
      } catch (error) {}
    },

    // Dialog methods
    restForm() {
      this.loading = false;
      this.options = {};
      this.selValue = null;
    },

    async remoteQuery(query) {
      this.loading = true;
      if (query !== "") {
        if (this.selectType === "video") {
          this.options = await getMediaPageList(undefined, {
            filename: query,
            type: "001002",
            auditStatus: "002003",
          });
        } else if (this.selectType === "doc") {
          this.options = await getMediaPageList(undefined, {
            filename: query,
            type: "001003",
          });
        } else if (this.selectType === "work") {
          this.options = await getWorkPageList(
            {
              title: query,
            },
            undefined
          );
        }
      } else {
        this.options = {};
      }
      console.log("文件数据：", this.options);
      this.loading = false;
    },

    handleSelect(item) {
      console.log(item);
    },
    handleCancel() {
      this.syncDialogVisible = false;
    },
    handleSubmit() {
      if (this.selectType === "video" || this.selectType === "doc") {
        this.onRecvSelectedValue({
          type: this.selectType,
          value: this.options.items.filter(
            (item) => item.id === this.selValue
          )[0],
        });
      } else if (this.selectType === "work") {
        this.onRecvSelectedValue({
          type: this.selectType,
          value: this.options.items.filter(
            (item) => item.workId === this.selValue
          )[0],
        });
      }
      this.mediaDialogVisible = false;
    },
  },
};
</script>

<style lang="scss">
.step-body {
  .types {
    text-align: center;
    height: 80px;
    .item {
      display: inline-block;
      width: 50%;
      height: 100%;
      // background-color: bisque;
      padding-top: 20px;
      .selected-item {
        margin: 0px auto;
      }
    }
    .el-select {
      width: 90%;
    }
  }
}
</style>
