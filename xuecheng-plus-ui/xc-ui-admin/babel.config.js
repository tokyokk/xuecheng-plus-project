module.exports = {
  presets: [
    // https://github.com/vuejs/vue-cli/tree/master/packages/@vue/babel-preset-app
    "@vue/cli-plugin-babel/preset",
  ],
  env: {
    development: {
      // babel-plugin-dynamic-import-node plugin only does one thing by converting all import() to require().
      // This plugin can significantly increase the speed of hot updates, when you have a large number of pages.
      plugins: ["dynamic-import-node"],
    },
  },
  plugins: [
    [
      "prismjs",
      {
        languages: ["java", "js", "sql"],
        plugins: ["line-numbers"], //配置显示行号插件
        theme: "okaidia", //主体名称
        css: true,
      },
    ],
  ],
};
