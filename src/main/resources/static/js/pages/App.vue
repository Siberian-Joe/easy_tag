<template>
  <v-app>
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/@mdi/font@4.x/css/materialdesignicons.min.css" rel="stylesheet">

    <v-main>
      <div class="background d-flex justify-center">
        <company-menu :company="company"/>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import CompanyMenu from "components/CompanyMenu.vue"

export default {
  components: {
    CompanyMenu
  },
  data() {
    return {
      company: frontendCompany
    }
  }
}
</script>

<style>
.background {
  height: 100%;
  width: 100%;
  background-color: #202548;
}
</style>