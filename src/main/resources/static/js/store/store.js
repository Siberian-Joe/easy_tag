import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import router from "../router";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        company: { },
        profile: { },
        users: null,
        roles: null
    },
    getters: {
        getCompany(state) {
            return state.company;
        },
        getProfile(state) {
            return state.profile;
        },
        getUsers(state) {
            return state.users;
        },
        getRoles(state) {
            return state.roles;
        },
        getPermissions(state) {
            return state.profile.role !== undefined ? state.profile.role.shift().role === "ADMIN" : false;
        }
    },
    mutations: {
        setCompanyMutation(state, company) {
            let newItems = [];
            for(let i = 0; i < company.items.length; i++)
                for(let k = 0; k < company.items.length; k++)
                    if(company.items[k].id === i.toString())
                        newItems[i] = company.items[k];

            company.items = newItems;
            document.title = company.name;
            state.company = company;
        },
        setUserCompany(state, company) {
            state.profile.company = company;
        },
        setProfileMutation(state, profile) {
            state.profile = profile;
        },
        setCompanyNameMutation(state, name) {
            state.company.name = name;
        },
        setFullNameMutation(state, fullName) {
            state.profile.fullName = fullName;
        },
        setEmailMutation(state, email) {
            state.profile.email = email;
        },
        addItemMutation(state, item) {
            state.company.items = [
                ...state.company.items,
                item
            ]
        },
        deleteItemMutation(state, item) {
            const deletionIndex = state.company.items.findIndex(oldItem => oldItem.id === item.id)

            for(let i = state.company.items.length - 1; i > state.company.items.indexOf(item); i--)
                state.company.items[i].id = (i - 1).toString();

            if(deletionIndex >  -1) {
                state.company.items = [
                    ...state.company.items.slice(0, deletionIndex),
                    ...state.company.items.slice(deletionIndex + 1)
                ]
            }
        },
        setUsersMutation(state, users) {
            users.forEach(user => {
                user.role = user.role.role;
            });

            state.users = users;
        },
        setRolesMutation(state, roles) {
            state.roles = roles;
        },
        updateRequest(state, request) {
            state.profile.request = request;
        }
    },
    actions: {
        async setCompanyNameAction({ commit, state }, name) {

            await axios.put('/company/' + state.company.id, {
                id: state.company.id,
                name: name,
                items: state.company.items
            });
            commit( "setCompanyNameMutation", name);
            document.title = name;
        },
        async updateFullNameAction({ commit, state }, fullName) {
            await axios.put('/user/' + state.profile.id, {
                id: state.profile.id,
                fullName: fullName,
                email: state.profile.email,
                company: state.profile.company
            });
            commit( "setFullNameMutation", fullName);
        },
        async updateEmailAction({commit, state}, email) {
            await axios.put('/user/' + state.profile.id, {
                id: state.profile.id,
                fullName: state.profile.fullName,
                email: email,
                company: state.profile.company
            });
            commit( "setEmailMutation", email);
        },
        async updateUserRoleAction({ commit }, user) {
            await axios.put('/user/role/' + user.id + '/?id=' + user.role.id);
        },
        async updateUserCompany({ commit, state }, body) {
            let company;
            await axios.post('/company/create/' + body.user.id, {
                id: body.user.company.id,
                name: body.user.company.name,
                items: body.user.company.items
            }).then(response => {
                company = response.data;
            });
            if(body.user.id === state.profile.id)
                commit( "setUserCompany", body.user.company);
            await axios.get('/genrateQRCode/' + company.id + "/?path=" + body.path + "/?company=");
        },
        async deleteCompany({ commit }, user) {
            await axios.delete('/company/' + user.id);
        },
        async deleteUserAction({ commit }, id) {
            await axios.delete('/user/' + id);
        },
        async addItemAction({ commit, state }, item) {
            commit( "addItemMutation", item);
            await axios.put('/company/' + state.company.id, {
                id: state.company.id,
                name: state.company.name,
                items: state.company.items
            });
        },
        async updateItemAction({ state }) {
            await axios.put('/company/' + state.company.id, {
                id: state.company.id,
                name: state.company.name,
                items: state.company.items
            });
        },
        async sendResponse({ state }, response) {
            await axios.post('/response/' + response.request.id + "/?type=" + response.type, {
                "description": response.description
            });
        },
        async deleteItemAction({ commit, state }, item) {
            commit( "deleteItemMutation", item);
            await axios.put('/company/' + state.company.id, {
                id: state.company.id,
                name: state.company.name,
                items: state.company.items
            });
        },
        async postRequest({ commit, state }, request) {
            await axios.post('/request/' + state.profile.id + '/?type=' + request.type, {
                description: request.description
            }).then(response => {
                commit("updateRequest", response.data);
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
            await axios.get("/company/" + (path !== undefined && path !== "" ? path.toString() : "619e2cf1f17838eaabb5990e")).then(response => {
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
                const requests = response.data;
                requests.forEach(request => {
                    if(request.request.type.type === "CREATE")
                        request.request.type = "Создание компании";
                    else
                        request.request.type = "Вопрос";
                });
                commit("setUsersMutation", requests);
            });
        },
    }
})