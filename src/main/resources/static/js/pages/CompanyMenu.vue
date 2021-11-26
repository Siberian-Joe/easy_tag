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
        <v-speed-dial v-model="fab" direction="left" v-if="getProfile !== null">
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
          <router-link custom v-slot="{ href, navigate }" to="/edit">
            <v-btn fab dark small color="green" :href="href" @click="navigate" v-if="getProfile.company !== null">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </router-link>
          <router-link custom v-slot="{ href, navigate }" to="/settings">
            <v-btn fab dark small color="indigo" :href="href" @click="navigate">
              <v-icon>mdi-cogs</v-icon>
            </v-btn>
          </router-link>
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
      <v-col class="indent pa-0">
        <company-title :name="getCompany.name" :logo="getCompany.logo" :editMode="editMode"/>
      </v-col>
      <v-col class="pa-0" align="end" v-if="editMode">
        <v-btn small icon @click="addItem"><v-icon>mdi-plus</v-icon></v-btn>
      </v-col>
      <v-col class="pa-0">
        <company-information-list :items="getCompany.items" :editMode="editMode"/>
      </v-col>
    </v-container>
  </v-container>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import CompanyTitle from '../components/CompanyTitle.vue';
import CompanyInformationList from "../components/CompanyInformationList.vue";

export default {
  components: {
    CompanyTitle,
    CompanyInformationList
  },
  data() {
    return {
      editMode: false,
      fab: false,
    }
  },
  mounted() {
    this.getProfileFromServer();
    this.getCompanyFromServer(this.$route.query.company);
  },
  computed: {
    ...mapGetters(["getCompany", "getProfile"])
  },
  methods: {
    ...mapActions(["getCompanyFromServer", "getProfileFromServer", "addItemAction"]),
    addItem() {
      this.addItemAction({ id: this.company.items.length, name: "Новый элемент", icon: null })
    }
  }
}
</script>

<style scoped>
.indent {
  margin-top: 30px
}
</style>