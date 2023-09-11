export default {
  bind(el, binding, vnode) {
    var defClass = "el-input",
      defTag = "input";
    var value = binding.value || true;
    if (typeof value === "boolean")
      //页面中只有一个组件用到v-focus指令 v-focus='true'
      value = { cls: defClass, tag: defTag, foc: value };
    //页面有多个组件用到v-focus指令
    else
      value = {
        cls: value.cls || defClass,
        tag: value.tag || defTag,
        foc: value.foc || false,
      };
    if (el.classList.contains(value.cls) && value.foc)
      //当页面中包含defclass，且foc属性为true时
      el.getElementsByTagName(value.tag)[0].focus(); //设置tag的第一个元素聚焦
  },
};
