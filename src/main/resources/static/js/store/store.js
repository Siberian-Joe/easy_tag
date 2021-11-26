import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import router from "../router";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        company: { },
        profile: { }
    },
    getters: {
        getCompany(state) {
            return state.company;
        },
        getProfile(state) {
            return state.profile;
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
        async deleteItemAction({ commit, state }, item) {
            commit( "deleteItemMutation", item);
            await axios.put('/company/' + state.company.id, {
                id: state.company.id,
                name: state.company.name,
                items: state.company.items
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
        }
    }
})