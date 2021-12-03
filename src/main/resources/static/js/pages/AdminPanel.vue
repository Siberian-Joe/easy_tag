<template>
  <v-container class="pa-0">
    <v-row class="navigation-panel" no-gutters>
      <v-spacer/>
      <v-col class="pa-0" cols="auto">
        <router-link custom v-slot="{ href, navigate }" to="/">
          <v-btn fab :href="href" @click="navigate">
            <v-icon>
              mdi-close
            </v-icon>
          </v-btn>
        </router-link>
      </v-col>
    </v-row>
    <v-tabs
        v-model="tab"
    >
      <v-tab>
        Панель администрации
      </v-tab>
      <v-tab>
        Панель запросов
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item>
        <v-card flat>
          <v-card-text>
            <admin-panel-users-table-component/>
          </v-card-text>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text>
            <admin-panel-requests-table-component/>
          </v-card-text>
        </v-card>
      </v-tab-item>
    </v-tabs-items>

  </v-container>
</template>

<script>
import AdminPanelUsersTableComponent from "../components/AdminPanelUsersTableComponent.vue";
import AdminPanelRequestsTableComponent from "../components/AdminPanelRequestsTableComponent.vue";

export default {
  data() {
    return {
      tab: null
    }
  },
  components: {
    AdminPanelUsersTableComponent,
    AdminPanelRequestsTableComponent
  }
}
</script>

<style scoped>
.navigation-panel {
  padding-bottom: 30px;
}
</style>