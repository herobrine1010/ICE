// vue.config.js
module.exports = {
  publicPath: '',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8021',
        changeOrigin: true, // needed for virtual hosted sites
        pathRewrite: {
          '/api': '' // rewrite path
        }
      }
    }
  }
}
