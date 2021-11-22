<template>
  <v-card class="menu align-self-center rounded-xl" elevation="15">
    <v-container class="in-menu">
      <v-row dense>
        <v-col class="pa-0" cols="auto" v-if="!isAuthorization">
          <v-btn small height="50">
            <v-icon>
              mdi-qrcode-scan
            </v-icon>
          </v-btn>
        </v-col>
        <v-spacer/>
        <v-col class="pa-0" cols="auto">
          <v-speed-dial v-model="fab" direction="left" v-if="profile != null">
            <template v-slot:activator>
              <v-btn v-model="fab" fab>
                <v-icon v-if="fab">
                  mdi-close
                </v-icon>
                <v-icon v-else>
                  mdi-account-circle
                </v-icon>
              </v-btn>
            </template>
            <v-btn fab dark small color="green" @click="editMode = !editMode">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn fab dark small color="indigo" @click="isAdminPanel = !isAdminPanel">
              <v-icon>mdi-cogs</v-icon>
            </v-btn>
            <v-btn fab dark small color="red">
              <v-icon>mdi-exit-to-app</v-icon>
            </v-btn>
          </v-speed-dial>
          <v-btn fab v-else @click="changeIsAuthorization">
            <v-icon v-if="!isAuthorization">
              mdi-account-circle
            </v-icon>
            <v-icon v-else>
              mdi-close
            </v-icon>
          </v-btn>
        </v-col>
      </v-row>

      <v-container class="pa-0" v-if="!isAuthorization">
        <v-col class="pa-0">
          <company-title :name="company.name" :logo="company.logo" :editMode="editMode"/>
        </v-col>
        <v-col class="pa-0" align="end" v-if="editMode">
          <v-btn small icon @click="addItem"><v-icon>mdi-plus</v-icon></v-btn>
        </v-col>
        <v-col class="pa-0">
          <company-information-list :items="items" :editMode="editMode" :saveItem="saveItem" :deleteItem="deleteItem"/>
        </v-col>
      </v-container>
      <v-container class="pa-0" v-else>
        <user-authorization :profile = "profile" :set-user="setUser" :change-is-authorization="changeIsAuthorization"/>
      </v-container>
    </v-container>

  </v-card>
</template>

<script>
import CompanyTitle from './CompanyTitle.vue';
import CompanyInformationList from "./CompanyInformationList.vue";
import UserAuthorization from "./UserAuthorization.vue";
import axios from "axios";

export default {
  props: ["company", "profile"],
  components: {
    CompanyTitle,
    CompanyInformationList,
    UserAuthorization
  },
  data() {
    return {
      editMode: false,
      fab: false,
      items: this.makeQueue(this.company.items),
      isAuthorization: false
    }
  },
  methods: {
    addItem() {
      this.items.push({id: this.items.length, name: "Новый элемент", icon: null})
      axios.put('/company/items/' + this.company.id, {
        id: this.company.id,
        name: this.company. name,
        items: this.items
      });
    },
    saveItem() {
      axios.put('/company/items/' + this.company.id, {
        id: this.company.id,
        name: this.company. name,
        items: this.items
      });
    },
    deleteItem(item) {
      this.deleteItemInQueue(this.items, item)
      axios.put('/company/items/' + this.company.id, {
        id: this.company.id,
        name: this.company. name,
        items: this.items
      });
    },
    makeQueue(items) {
      let newItems = [];
      for(let i = 0; i < items.length; i++)
        for(let k = 0; k < items.length; k++)
          if(items[k].id === i.toString())
            newItems[i] = items[k];
      return newItems;
    },
    deleteItemInQueue(items, item) {
      for(let i = items.length - 1; i > items.indexOf(item); i--)
        items[i].id = (i - 1).toString();
      items.splice(items.indexOf(item), 1);
    },
    changeIsAuthorization() {
      this.isAuthorization = !this.isAuthorization
    },
    setUser(body) {
      this.user = body
    }
  }
}
</script>

<style scoped>

.menu {
  margin: 20px;

  width: 460px;

  background: #FFFFFF;
  box-shadow: 0 0 10px 10px rgba(0, 0, 0, 0.8);
}

.in-menu {
  padding: 30px;
  /*height: 867px;*/
}
</style>