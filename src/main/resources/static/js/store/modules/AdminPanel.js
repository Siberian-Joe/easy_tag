import axios from "axios";
import router from "../../router/router";

export default {
    state: {

        users: null,
        roles: null,
        typesOfRequest: null
    },
    getters: {
        getUsers(state) {
            return state.users;
        },
        getRoles(state) {
            return state.roles;
        },
        getTypesOfRequest(state) {
            return state.typesOfRequest;
        }
    },
    mutations: {
        setUsersMutation(state, users) {
            state.users = users;
        },
        setRolesMutation(state, roles) {
            state.roles = roles;
        },
        setTypesOfRequestMutation(state, typesOfRequest) {
            state.typesOfRequest = typesOfRequest;
        }
    },
    actions: {

        async updateUserRoleAction({ commit }, user) {
            await axios.put('/user/role/' + user.id + '/?id=' + user.role.id);
        },
        async deleteCompany({ commit }, user) {
            await axios.delete('/company/' + user.id);
        },
        async deleteUserAction({ commit }, id) {
            await axios.delete('/user/' + id);
        },
        async sendResponse({ state }, response) {
            await axios.post('/response/' + response.request.id + "/?type=" + response.type, {
                "description": response.description
            });
        },
        async getProfileFromServer({ commit }) {
            await axios.get("/user/").then(response => {
                if(response.data !== "") {
                    commit("setProfileMutation", response.data);
                }
                else
                    commit("setProfileMutation", null);
            });
        },
        async getCompanyFromServer({ commit }, path) {
            await axios.get("/company/" + (path !== undefined && path !== "" ? path : "619e2cf1f17838eaabb5990e")).then(response => {
                if(response.data === '')
                    router.push({ name: "pageNotFound" })
                else
                    commit("setCompanyMutation", response.data);
            });
        },
        async getUsersFromServer({ commit }) {
            await axios.get("/adminpanel/users").then(response => {
                let users = response.data;

                users.forEach(user => {
                    user.role = user.role.shift();
                });
                commit("setUsersMutation", response.data);
            });
        },
        async getRolesFromServer({ commit }) {
            await axios.get("/adminpanel/roles").then(response => {
                commit("setRolesMutation", response.data);
            });
        },
        async getRequestsFromServer({ commit }) {
            await axios.get("/adminpanel/requests").then(response => {
                commit("setUsersMutation", response.data);
            });
        },
        async getTypesOfRequestFromServer({ commit }) {
            await axios.get("/request/types").then(response => {
                commit("setTypesOfRequestMutation", response.data);
            });
        }
    }
}