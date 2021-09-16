import Vue from 'vue';
import App from 'pages/App.vue';

new Vue({
    el:'#app',
    render: a => a(App)
});

// var app = new Vue({
//     el: '#app',
//     template: ,
//     data: {
//         message: 'Привет, Vue!',
//         profile: frontendData.profile
//     }
// });