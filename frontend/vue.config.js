'use strict'
const path = require('path')
const defaultSettings = require('./src/settings.js')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const name = defaultSettings.title || 'vue Element Admin' // page title

// If your port is set to 80,
// use administrator privileges to execute the command line.
// For example, Mac: sudo npm run
// You can change the port by the following method:
// port = 9527 npm run dev OR npm run dev --port = 9527
//  配置Vue项目中的API请求地址的, 通常用于区分开发环境和生产环境的API端点
const port = process.env.port || process.env.npm_config_port || 9527 // dev port
const gatewayUrl = 'http://localhost:8082/api'
const devUrlMap = {
  'user-api' : 'http://localhost:8090/v1'
}
const prodUrlMap = {
  'user-api': gatewayUrl + '/user/v1'
}
const devMode = process.env.NODE_ENV !== 'production'

const urlMap = devMode ? devUrlMap : prodUrlMap
const proxyTable = {}
// changeOrigin: true, 设置changeOrigin为true，这通常用于避免Host header的问题，允许服务器端重定向。
// 设置secure为false，这通常用于允许请求非HTTPS的目标URL。
// pathRewrite: { ['^/' + key]: '/' } 定义了一个路径重写规则，它将代理路径中匹配'^/' + key的部分重写为'/'。这是一种正则表达式重写，^表示字符串的开始，/key是动态插入的键名。
for(const key in urlMap) {
  proxyTable['/' + key] = {
    target: urlMap[key], // 这里要设置的就是后端的地址了，如果在其它地方要访问后端接口 http://backend.example.com/api/user，可以直接写/api/user进行请求
    changeOrigin: true, // 必须为true，表示跨越
    secure: false,  // 如果是https接口，需要配置整个参数为true
    pathRewrite: {
      ['^/' + key]: '/' // 将请求中的 '/key' 替换为'/'
    }
  }
}


// All configuration item explanations can be find in https://cli.vuejs.org/config/
module.exports = {
  /**
   * You will need to set publicPath if you plan to deploy your site under a sub path,
   * for example GitHub Pages. If you plan to deploy your site to https://foo.github.io/bar/,
   * then publicPath should be set to "/bar/".
   * In most cases please use '/' !!!
   * Detail: https://cli.vuejs.org/config/#publicpath
   */
  publicPath: '/', // 如果改为 '/my-app/',则项目会部署在 http://localhost:port/my-app/
  outputDir: 'dist', // 将构建好的文件输出到哪里（或者说将编译的文件，打包目录），当运行 vue-cli-service build 时生成的生产环境构建文件的目录。注意目标目录在构建之前会被清除 (构建时传入 --no clean 可以关闭该行为）
  assetsDir: 'static', // 放置生成的静态资源 (js、css、img、fonts) 的目录。
  lintOnSave: false, // 是否在保存的时候使用 `eslint-loader` 进行检查。 有效的值：`ture` | `false` | `"error"`  当设置为 `"error"` 时，检查出的错误会触发编译失败。
  productionSourceMap: false, // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
  devServer: { // 如果前端应用和后端api服务器没有运行在一个主机上，需要在开发环境下将 API 请求代理到 API 服务器
    port: port,
    open: false, // 修改成false,为了编译启动项目时不会打开两个网页
    overlay: {
      warnings: false,
      errors: true
    },
    proxy:proxyTable
    // before: require('./mock/mock-server.js') // 引入mock.js用前端产生假数据进行前后交互，使用真实的后端接口的时候，需要把这行注释掉
  },
  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  },
  chainWebpack(config) {
    // it can improve the speed of the first screen, it is recommended to turn on preload
    // it can improve the speed of the first screen, it is recommended to turn on preload
    config.plugin('preload').tap(() => [
      {
        rel: 'preload',
        // to ignore runtime.js
        // https://github.com/vuejs/vue-cli/blob/dev/packages/@vue/cli-service/lib/config/app.js#L171
        fileBlacklist: [/\.map$/, /hot-update\.js$/, /runtime\..*\.js$/],
        include: 'initial'
      }
    ])

    // when there are many pages, it will cause too many meaningless requests
    config.plugins.delete('prefetch')

    // set svg-sprite-loader
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()

    config
      .when(process.env.NODE_ENV !== 'development',
        config => {
          config
            .plugin('ScriptExtHtmlWebpackPlugin')
            .after('html')
            .use('script-ext-html-webpack-plugin', [{
            // `runtime` must same as runtimeChunk name. default is `runtime`
              inline: /runtime\..*\.js$/
            }])
            .end()
          config
            .optimization.splitChunks({
              chunks: 'all',
              cacheGroups: {
                libs: {
                  name: 'chunk-libs',
                  test: /[\\/]node_modules[\\/]/,
                  priority: 10,
                  chunks: 'initial' // only package third parties that are initially dependent
                },
                elementUI: {
                  name: 'chunk-elementUI', // split elementUI into a single package
                  priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
                  test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
                },
                commons: {
                  name: 'chunk-commons',
                  test: resolve('src/components'), // can customize your rules
                  minChunks: 3, //  minimum common number
                  priority: 5,
                  reuseExistingChunk: true
                }
              }
            })
          // https:// webpack.js.org/configuration/optimization/#optimizationruntimechunk
          config.optimization.runtimeChunk('single')
        }
      )
  }
}
