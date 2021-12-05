<template>
  <v-row class="navigation-panel" no-gutters>
    <v-spacer/>
    <v-col class="pa-0" cols="auto">
      <router-link custom v-slot="{ href, navigate }" :to="'/'">
        <v-btn fab :href="href" @click="navigate">
          <v-icon>
            mdi-arrow-left
          </v-icon>
        </v-btn>
      </router-link>
    </v-col>
  </v-row>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["getProfile"])
  }
}
</script>

<style scoped>
.navigation-panel {
  padding-bottom: 30px;
}
</style>