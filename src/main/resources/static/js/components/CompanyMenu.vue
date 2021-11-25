<template>
  <v-container class="pa-0">
    <v-row no-gutters>
      <v-col class="pa-0" cols="auto">
        <v-btn fab>
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
          <v-btn fab dark small color="green" @click="changeEditMode">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn fab dark small color="indigo">
            <v-icon>mdi-cogs</v-icon>
          </v-btn>
          <v-btn fab dark small color="red" href="/logout">
            <v-icon>mdi-exit-to-app</v-icon>
          </v-btn>
        </v-speed-dial>
        <router-link v-else custom v-slot="{ href, navigate }" to="/login">
          <v-btn fab :href="href" @click="navigate">
            <v-icon>
              mdi-account-circle
            </v-icon>
          </v-btn>
        </router-link>

      </v-col>
    </v-row>

    <v-container class="pa-0">
      <v-col class="pa-0">
        <company-title :name="company.name" :logo="company.logo" :editMode="editMode"/>
      </v-col>
      <v-col class="pa-0" align="end" v-if="editMode">
        <v-btn small icon @click="addItem"><v-icon>mdi-plus</v-icon></v-btn>
      </v-col>
      <v-col class="pa-0">
        <company-information-list :items="company.items" :editMode="editMode" :saveItem="saveItem" :deleteItem="deleteItem"/>
      </v-col>
    </v-container>
  </v-container>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex';
import CompanyTitle from './CompanyTitle.vue';
import CompanyInformationList from "./CompanyInformationList.vue";
import UserAuthorization from "./UserAuthorization.vue";
import axios from "axios";

export default {
  components: {
    CompanyTitle,
    CompanyInformationList,
    UserAuthorization
  },
  data() {
    return {
      editMode: false,
      fab: false,
    }
  },
  mounted() {
    axios.get("/company/" + (this.$route.query.company !== undefined ? this.$route.query.company : "619e2cf1f17838eaabb5990e")).then(response => {
      let company = { };
      if(response.data === '')
        this.$router.push({ name: "pageNotFound" })
      else {
        company = response.data
        this.setCompanyMutation(company)
        document.title = this.company.name;
        this.company.items = this.makeQueue;
      }
    });
  },
  computed: {
    ...mapState(["company", "profile"]),
    makeQueue() {
      let newItems = [];
      for(let i = 0; i < this.company.items.length; i++)
        for(let k = 0; k < this.company.items.length; k++)
          if(this.company.items[k].id === i.toString())
            newItems[i] = this.company.items[k];
      return newItems;
    }
  },
  methods: {
    ...mapMutations(["setCompanyMutation"]),
    ...mapActions(["addItemAction"]),
    changeEditMode() {
      this.editMode = !this.editMode;
    },
    addItem() {
      this.addItemAction({ id: this.company.items.length, name: "Новый элемент", icon: null })
      // this.items.push({id: this.items.length, name: "Новый элемент", icon: null})
      // axios.put('/company/items/' + this.company.id, {
      //   id: this.company.id,
      //   name: this.company. name,
      //   items: this.items
      // });
    },
    saveItem() {
      axios.put('/company/items/' + this.company.id, {
        id: this.company.id,
        name: this.company. name,
        items: this.items
      });
    },
    deleteItem(item) {
      // this.deleteItemInQueue(this.items, item)
      // axios.put('/company/items/' + this.company.id, {
      //   id: this.company.id,
      //   name: this.company. name,
      //   items: this.items
      // });
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




</style>