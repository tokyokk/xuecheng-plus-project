<template>
  <pre :class="'language-' + getType">
    <code  :class="'language-'+ getType" v-html="showCode"></code>
 </pre>
</template>

<script>
import Prism from "prismjs";

export default {
  name: "previewCode",
  props: {
    code: {
      type: String,
      default: "",
    },
    type: {
      type: String,
      default: "html",
    },
  },
  data() {
    return {};
  },
  created() {},
  computed: {
    showCode() {
      return Prism.highlight(
        this.code,
        Prism.languages[this.getType],
        this.getType
      );
    },
    getType() {
      return this.type == "vue" ? "js" : this.type;
    },
  },
  mounted() {
    Prism.highlightAll();
  },
  methods: {},
};
</script>
