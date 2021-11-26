import VueRouter from "vue-router";
import UserSignIn from "./pages/UserSignIn.vue";
import UserSignUp from "./pages/UserSignUp.vue";
import NotFoundComponent from "./pages/NotFoundComponent.vue";
import CompanyMenu from "./pages/CompanyMenu.vue";
import AdminPanel from "./pages/AdminPanel.vue";
import SettingsPanel from "./pages/SettingsPanel.vue";
import EditCompany from "./pages/EditCompany.vue";

export default new VueRouter({
   routes: [
       {
           path: "/",
           component: CompanyMenu
       },
       {
           path: "/adminpanel",
           component: AdminPanel
       },
       {
           path: "/settings",
           component: SettingsPanel
       },
       {
           path: "/edit",
           component: EditCompany
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
           component: NotFoundComponent
       }
   ],
    mode: 'history'
});