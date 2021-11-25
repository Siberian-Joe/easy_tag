import VueRouter from "vue-router";
import UserSignIn from "./components/UserSignIn.vue";
import UserSignUp from "./components/UserSignUp.vue";
import CompanyNotFound from "./components/CompanyNotFound.vue";
import CompanyMenu from "./components/CompanyMenu.vue";

export default new VueRouter({
   routes: [
       {
           path: "/",
           component: CompanyMenu
       },
       {
           path: "/login",
           component: UserSignIn
       },
       {
           path: "/registration",
           component: UserSignUp
       },
       {
           path: "*",
           name: "pageNotFound",
           component: CompanyNotFound
       }
   ],
    mode: 'history'
});