module.exports = {
  lintOnSave: false,
  productionSourceMap: false,
  css: {
    loaderOptions: {
      scss: {},
    },
  },
  devServer: {
    host: "0.0.0.0",
    port: 20008,
    https: false,
    hotOnly: false,
    disableHostCheck: false,
    proxy: {
      "/tetris": {
        target: "http://tetris.fingerchar.com/",
        // target: process.env.VUE_APP_API_URL,
      },
    },
  },
};
