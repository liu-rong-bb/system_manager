import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {

    adminMenu: [],

    logout (state) {
      state.user = []
      window.localStorage.removeItem('user')
    },

  },

  mutations: {
    initAdminMenu (state, menus) {
      state.adminMenus = menus
    }
  },
  actions: {
  },
  modules: {
  }
})
