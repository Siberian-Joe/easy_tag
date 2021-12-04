<template>
  <v-container class="pa-0">
    <v-row no-gutters>
      <v-spacer/>
      <v-col class="pa-0" cols="auto">
        <router-link custom v-slot="{ href, navigate }" to="/">
          <v-btn fab :href="href" @click="navigate">
            <v-icon>
              mdi-arrow-left
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
import CompanyTitle from "../components/CompanyTitle.vue";
import CompanyInformationList from "../components/CompanyInformationList.vue";

export default {
  components: {
    CompanyTitle,
    CompanyInformationList
  },
  data() {
    return {
      editMode: true
    }
  },
  async mounted() {
    await this.getProfileFromServer();
    if(this.getProfile.company !== null && this.getCompany !== undefined)
      await this.getCompanyFromServer(this.getProfile.company.id);
    else {
      await this.getCompanyFromServer();
      await this.$router.push({name: "company"});
    }
  },
  computed: {
    ...mapGetters(["getCompany", "getProfile"])
  },
  methods: {
    ...mapActions(["getCompanyFromServer", "addItemAction", "getProfileFromServer"]),
    addItem() {
      this.addItemAction({ id: this.getCompany.items.length, name: "Новый элемент", icon: null })
    }
  }
}
</script>

<style scoped>
.indent {
  margin-top: 30px
}
</style>