import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import moment from 'moment'

Vue.config.productionTip = false
//让前端能够带上 cookie
axios.defaults.withCredentials = true
//Vue.prototype.$moment = moment

// 时间戳过滤器

Vue.filter("dateF",(input, formatString = "YYYY-MM-DD") =>{
  // moment(input) 把时间字符串转成时间对象
  // format(formatString) 把时间对象，按照指定格式，格式化成符合条件的字符串
  return moment(input).format(formatString)
})



new Vue({
  router,
  store,
  render: h => h(App),
  components: { App },
  template: '<App/>'
}).$mount('#app')

