<template>
  <div class="app-container">
    <!-- 步骤 -->
    <div class="steps">
      <!-- 导航 -->
      <div class="nav">
        <el-steps :active="stepsActive" simple style>
          <el-step title="基本信息">
            <template slot="icon">1</template>
          </el-step>
          <el-step title="课程大纲">
            <template slot="icon">2</template>
          </el-step>
          <el-step title="教师设置">
            <template slot="icon">3</template>
          </el-step>
        </el-steps>
      </div>

      <div class="body">
        <!-- step 1 基本信息 -->
        <CourseAddStep1BaseInfo
          v-show="stepsActive === 0"
          ref="step1"
          :courseBaseId.sync="courseBaseId"
          :teachmode="teachmode"
        />

        <!-- step 2 课程大纲 -->
        <CourseAddStep2Outline
          v-show="stepsActive === 1"
          ref="step2"
          :courseBaseId="courseBaseId"
          :teachmode="teachmode"
        />

        <!-- step 3 教师设置 -->
        <CourseAddStep3Teacher
          v-show="stepsActive === 2"
          ref="step3"
          :courseBaseId="courseBaseId"
        />
      </div>

      <!-- 按钮 上一步 下一步 完成 -->
      <div class="footer">
        <!-- 上一步 -->
        <el-button @click="handlePrev" v-if="stepsActive > 0">上一步</el-button>
        <!-- 下一步 -->
        <el-button type="primary" @click="handleNext">
          <template v-if="stepsActive == 0 || stepsActive == 1"
            >保存并进行下一步</template
          >
          <template v-else-if="stepsActive == 2">保存</template>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import CourseAddStep1BaseInfo from "./step1/course-add-step1-base.vue";
import CourseAddStep2Outline from "./step2/course-add-step2-outline.vue";
import CourseAddStep3Teacher from "./step3/course-add-step3-teacher.vue";
export default {
  name: "course-add",
  components: {
    CourseAddStep1BaseInfo,
    CourseAddStep2Outline,
    CourseAddStep3Teacher,
  },
  data() {
    return {
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,

      stepsActive: 0,
      teachmode: "200002",
      courseBaseId: null,
    };
  },
  created() {
    if (this.$route.params["teachmode"] !== undefined) {
      this.teachmode = this.$route.params.teachmode;
    }
    if (this.$route.query["courseBaseId"] !== undefined) {
      this.courseBaseId = Number(this.$route.query["courseBaseId"]);
    }
  },
  methods: {
    handlePrev() {
      this.stepsActive -= 1;
    },

    // 下一页
    async handleNext() {
      // try {
      if (this.stepsActive == 0) {
        // 保存基本信息
        await this.$refs["step1"].saveData();
         // 读取大纲
        await this.$refs['step2'].getList()
        this.stepsActive += 1;
      } else if (this.stepsActive == 1) {
          // 读取教师列表
        await this.$refs['step3'].getList()
        this.stepsActive += 1;
      } else if (this.stepsActive == 2) {
        this.$router.push({
          path: "/edu/course",
        });
      }
    },
  },
};
</script>

<style lang="scss">
.steps {
  padding: 20px;
  .nav {
    padding-bottom: 20px;
  }
  .body {
    padding: 30px 0px 50px 0px;
    // background-color: darkcyan;
  }
  .footer {
    padding: 20px 0px 20px 0px;
    border-top: 1px solid #ededed;
    text-align: center;
  }
}
</style>
