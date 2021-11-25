import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        company: { },
        profile: null
    },
    // getters: {
    //     makeQueue: state =>  {
    //         let newItems = [];
    //         for(let i = 0; i < state.company.items.length; i++)
    //             for(let k = 0; k < state.company.items.length; k++)
    //                 if(state.company.items[k].id === i.toString())
    //                     newItems[i] = state.company.items[k];
    //         return newItems;
    //     }
    // },
    mutations: {
        setCompanyMutation(state, company) {
            state.company = company;
        },
        setProfileMutation(state, profile) {
            state.profile = profile;
        },
        addItemMutation(state, item) {
            state.company.items = [
                ...state.company.items,
                item
            ]
        },
        // updateItemMutation(state, item) {
        //     const updateIndex = state.company.items.findIndex(oldItem => oldItem.id === item.id)
        //         state.company.items = [
        //             ...state.company.items.slice(0, updateIndex),
        //             item,
        //             ...state.company.items.slice(updateIndex + 1)
        //         ]
        // },
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
        addCompanyAction({ commit }, company) {

        },
        addProfileAction({ commit }, Profile) {

        },
        async addItemAction({ commit, state }, item) {
            commit( "addItemMutation", item)
            await axios.put('/company/items/' + state.company.id, {
                id: state.company.id,
                name: state.company.name,
                items: state.company.items
            });
        },
        async updateItemAction({ state }) {
            await axios.put('/company/items/' + state.company.id, {
                id: state.company.id,
                name: state.company.name,
                items: state.company.items
            });
        },
        async deleteItemAction({ commit, state }, item) {
            commit( "deleteItemMutation", item)
            console.log(state.company.items)
            await axios.put('/company/items/' + state.company.id, {
                id: state.company.id,
                name: state.company.name,
                items: state.company.items
            });
        }
    }
})