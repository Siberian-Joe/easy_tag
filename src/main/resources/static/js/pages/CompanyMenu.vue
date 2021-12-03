<template>
  <v-container class="pa-0">
    <v-row no-gutters>
      <v-col class="pa-0" cols="auto">
        <v-dialog
            v-model="dialog"
            width="490"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn fab v-bind="attrs" v-on="on">
              <v-icon>
                mdi-qrcode-scan
              </v-icon>
            </v-btn>
          </template>
          <v-card class="indent">
            <v-main>
              <v-card-text class="pa-0">
                <v-container class="pa-0">
                  <v-img class="pa-0" :src="'/img/qrcodes/' + getCompany.id + '.png'"></v-img>
                </v-container>
              </v-card-text>
            </v-main>
          </v-card>
        </v-dialog>
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
                mdi-menu
              </v-icon>
            </v-btn>
          </template>
          <router-link custom v-slot="{ href, navigate }" to="/adminpanel" v-if="getPermissions">
            <v-btn fab dark small color="orange" :href="href" @click="navigate">
              <v-icon>mdi-cogs</v-icon>
            </v-btn>
          </router-link>
          <v-btn fab dark small color="#755D9A" @click="toMyPage" v-if="getProfile.company !== null">
            <v-icon>mdi-application</v-icon>
          </v-btn>
          <router-link custom v-slot="{ href, navigate }" to="/edit" v-if="getProfile.company !== null">
            <v-btn fab dark small color="green" :href="href" @click="navigate">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </router-link>
          <router-link custom v-slot="{ href, navigate }" to="/profile">
            <v-btn fab dark small color="indigo" :href="href" @click="navigate">
              <v-icon>mdi-account-cog</v-icon>
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
      dialog: false
    }
  },
  computed: {
    ...mapGetters(["getCompany", "getProfile", "getPermissions"]),
  },
  methods: {
    ...mapActions(["getCompanyFromServer", "getProfileFromServer", "addItemAction"]),
    addItem() {
      this.addItemAction({ id: this.company.items.length, name: "Новый элемент", icon: null })
    },
    toMyPage() {
      this.$router.push({query: {company: this.getProfile.company.id}})
      this.getCompanyFromServer(this.$route.query.company);
    }
  }
}
</script>

<style scoped>
.indent {
  margin-top: 30px
}

.indent-top-bottom {
  padding: 15px 0 15px 0;
}
</style>