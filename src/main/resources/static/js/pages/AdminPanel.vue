<template>
  <v-container class="pa-0">
    <back-button-component/>
    <v-tabs v-model="tab">
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
import BackButtonComponent from "../components/BackButtonComponent.vue";

export default {
  data() {
    return {
      tab: null
    }
  },
  components: {
    AdminPanelUsersTableComponent,
    AdminPanelRequestsTableComponent,
    BackButtonComponent
  }
}
</script>

<style scoped>
</style>