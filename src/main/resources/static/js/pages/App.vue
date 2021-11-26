<template>
  <v-app>
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/@mdi/font@4.x/css/materialdesignicons.min.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">

    <v-main>
      <div class="background d-flex justify-center">
        <v-card :class="'menu align-self-center rounded-xl ' + (this.$route.fullPath === '/adminpanel' ? 'extended-menu' : '')" elevation="15">
          <router-view/>
        </v-card>
      </div>
    </v-main>
  </v-app>
</template>

<script>

export default {
}
</script>

<style>
.menu {
  margin: 20px;
  padding: 30px;

  width: 460px;

  background: #FFFFFF;
  box-shadow: 0 0 10px 10px rgba(0, 0, 0, 0.8);

  font-family: 'Montserrat', sans-serif;
}
.background {
  height: 100%;
  width: 100%;
  background-color: #202548;
}

.extended-menu {
  width: 920px;
}
</style>