import Vue from 'vue'
import App from 'pages/App.vue'
import vuetify from "../plugins/vuetify";

new Vue({
    el: '#app',
    vuetify,
    render: a => a(App)
})