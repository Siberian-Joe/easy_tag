import Vue from 'vue'
import Vuex from 'vuex'
import CompanyMenu from "./modules/CompanyMenu";
import AdminPanel from "./modules/AdminPanel";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        CompanyMenu,
        AdminPanel
    }
})