import Vue from 'vue'
import App from 'pages/App.vue'
import vuetify from "../plugins/vuetify";
import '@babel/polyfill'
import store from "./store/store";
import VueRouter from "vue-router";
import router from "./router";

Vue.use(VueRouter);

new Vue({
    el: '#app',
    vuetify,
    store,
    router,
    render: a => a(App)
})