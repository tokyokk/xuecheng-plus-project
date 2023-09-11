<template>
  <div class="step-body">
    <el-form
      ref="form"
      v-if="baseInfoData"
      :model="baseInfoData"
      :rules="rules"
      label-width="120px"
    >
      <el-form-item label="课程名称：" prop="name">
        <el-input v-model="baseInfoData.name"></el-input>
      </el-form-item>
      <el-form-item label="课程标签：" prop="tags">
        <el-input v-model="baseInfoData.tags"></el-input>
      </el-form-item>
      <el-form-item label="课程分类：" prop="uiCategoryTreeSelected">
        <el-cascader
          v-if="categoryTreeData.length > 0"
          v-model="baseInfoData.uiCategoryTreeSelected"
          :options="categoryTreeData"
          :props="defaultProps"
          @change="handleCategoryTreeChange"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="课程等级：" prop="grade">
        <el-radio-group v-model="baseInfoData.grade">
          <el-radio
            v-for="dict in dict.type.C204"
            :key="dict.value"
            :label="dict.value"
            >{{ dict.label }}</el-radio
          >
        </el-radio-group>
      </el-form-item>
      <el-form-item label="课程简介：">
        <el-input
          v-model="baseInfoData.description"
          type="textarea"
          :rows="5"
        ></el-input>
      </el-form-item>
      <!--<el-form-item label="课程目标：">
        <el-input v-model="baseInfoData.objectives" type="textarea" :rows="5"></el-input>
      </el-form-item>-->
      <el-form-item label="适用人群：" prop="users">
        <el-input
          v-model="baseInfoData.users"
          type="textarea"
          :rows="5"
        ></el-input>
      </el-form-item>
      <el-form-item label="课程封面：" prop="pic">
        <image-upload
          v-model="baseInfoData.pic"
          :limit="1"
          :param="uploadParam"
          :baseUrl="baseUrl"
          :uploadUrl="uploadFileUrl"
          returnParm1="filename"
          returnParm2="url"
          :fileType="['png', 'jpg', 'jpeg']"
        />
      </el-form-item>
      <el-form-item label="课程类型：" prop="charge">
        <el-radio-group v-model="baseInfoData.charge">
          <el-radio
            v-for="dict in dict.type.C201"
            :key="dict.value"
            :label="dict.value"
            >{{ dict.label }}</el-radio
          >
        </el-radio-group>
      </el-form-item>
      <el-form-item label="原价：" prop="originalPrice">
        <el-input
          v-model="baseInfoData.originalPrice"
          style="width: 150px"
        ></el-input>
        <!-- <span>&nbsp;元</span> -->
      </el-form-item>
      <el-form-item label="现价：" prop="price">
        <el-input v-model="baseInfoData.price" style="width: 150px"></el-input>
      </el-form-item>
      <el-form-item label="咨询QQ: " prop="qq">
        <el-input v-model="baseInfoData.qq" style="width: 150px"></el-input>
      </el-form-item>
      <el-form-item label="微信号：" prop="wechat">
        <el-input v-model="baseInfoData.wechat" style="width: 150px"></el-input>
      </el-form-item>
      <el-form-item label="电话：" prop="phone">
        <el-input v-model="baseInfoData.phone" style="width: 150px"></el-input>
      </el-form-item>
      <el-form-item label="有效期(天)：" prop="validDays">
        <el-input
          v-model="baseInfoData.validDays"
          style="width: 150px"
        ></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { category, getBaseInfo, submitBaseInfo } from "@/api/edu/content.js";
export default {
  name: "course-add-step1-base",
  dicts: ["C201", "C204"],
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
      categoryTreeData: [],
      uploadParam: "filedata",
      baseUrl: process.env.VUE_APP_MINIO_BASE_URL,
      uploadFileUrl: process.env.VUE_APP_MINIO_UPLOAD_URL,
      baseInfoData: {
        charge: "201000",
        price: 0,
        qq: "",
        wechat: "",
        phone: "",
        validDays: 365,
        mt: "",
        st: "",
        name: "",
        pic: "",
        teachmode: this.teachmode,
        users: "",

        tags: "",
        grade: "204001",
        objectives: "",

        uiCategoryTreeSelected: [],
      },
      defaultProps: {
        children: "childrenTreeNodes",
        value: "id",
        label: "label",
      },
      rules: {
        name: [{ required: true, message: "请输入课程名称", trigger: "blur" }],
        uiCategoryTreeSelected: [
          {
            required: true,
            message: "请选择课程分类",
            trigger: "change",
          },
        ],
        charge: [
          { required: true, message: "请输入收费规则", trigger: "blur" },
        ],
        grade: [{ required: true, message: "请输入课程等级", trigger: "blur" }],
        users: [{ required: true, message: "请输入适用人群", trigger: "blur" }],
        // price: [
        //   {
        //     required: true,
        //     message: '请正确输入课程价格',
        //     trigger: 'change'
        //   }
        // ]
      },
    };
  },
  created() {
    this.getCategory();
  },
  mounted() {
    this.getData();
  },
  methods: {
    getCategory() {
      category().then((res) => {
        this.categoryTreeData = res;
      });
    },
    handleCategoryTreeChange(data) {
      this.baseInfoData.mt = data[0];
      this.baseInfoData.st = data[1];
    },
    async getData() {
      console.log('课程id',this.courseBaseId);
      if (this.courseBaseId) {
        let data = await getBaseInfo(this.courseBaseId);
        data.uiCategoryTreeSelected = [data.mt, data.st];
        this.baseInfoData = data;
        console.log("地址",this.baseInfoData.pic);
        this.baseInfoData.pic =
          `${process.env.VUE_APP_MINIO_BASE_URL}` + this.baseInfoData.pic;
      }
    },
    saveData: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          submitBaseInfo(this.baseInfoData).then((res) => {
            if (res.id !== undefined) {
              this.courseBaseId = res.id;
            }
          });
        }
      });
    },
  },
};
</script>

<style lang="scss">
.step-body {

}
</style>
