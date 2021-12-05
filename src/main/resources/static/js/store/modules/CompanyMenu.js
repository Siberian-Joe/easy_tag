import axios from "axios";

export default {
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
        setCompanyLogoMutation(state, logo) {
            state.company.logo = logo;
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
        async setCompanyLogo({ commit, state }, file) {
            let formData = new FormData();
            formData.append("file", file);
            await axios.post('/company/logo/' + state.company.id, formData).then(response => {
                commit("setCompanyLogoMutation", response.data.logo);
            });
        },
        async updateFullNameAction({ commit, state }, fullName) {
            await axios.put('/user/' + state.profile.id, {
                id: state.profile.id,
                fullName: fullName,
                email: state.profile.email,
            });
            commit( "setFullNameMutation", fullName);
        },
        async updateEmailAction({commit, state}, email) {
            await axios.put('/user/' + state.profile.id, {
                id: state.profile.id,
                fullName: state.profile.fullName,
                email: email
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
        async postRequest({ commit, state }, request) {
            await axios.post('/request/' + state.profile.id + '/?type=' + request.type, {
                description: request.description
            }).then(response => {
                commit("updateRequest", response.data);
            });
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
            if(company !== '')
                await axios.get('/company/genrateqrcode/' + company.id + "/?path=" + body.path + "/?company=");
        }
    }
}