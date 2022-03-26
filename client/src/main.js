import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import VueRouter from 'vue-router'
import axios from 'axios';
import router from './router';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.prototype.axios = axios;
Vue.config.productionTip = false
axios.defaults.withCredentials=true
new Vue({
  router:router,
  render: h => h(App),
  data() {
    return {
      usershuju:''  //全局变量用来存储登录的用户信息
    }
  },
  beforeCreate(){
    Vue.prototype.$bus = this
  }
}).$mount('#app')
