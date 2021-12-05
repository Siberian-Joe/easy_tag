<template>
  <v-container class="pa-0">
    <back-button-component/>
    <v-card class="error-info rounded-xl" elevation="5" align="center" v-if="this.$route.query.error">
      <v-container class="pa-0">
        <v-card-text v-text="'Неверный E-mail или пароль'"/>
      </v-container>
    </v-card>
    <form method="post" action="/login">
      <v-col class="indent" align="center">
        <v-text-field  id="username" name="username" dense hide-details outlined label="E-mail" v-model="email" @keyup.enter=""/>
      </v-col>
      <v-col class="indent" align="center">
        <v-text-field id="password" name="password" dense hide-details outlined label="Пароль" v-model="password" @keyup.enter="" type="password"/>
      </v-col>
      <v-col class="indent" align="center">
        <v-btn class="indent-buttons" type="submit">Войти</v-btn>
        <router-link to="/registration" custom v-slot="{ href, navigate }">
          <v-btn class="indent-buttons" :href="href" @click="navigate">Регистрация</v-btn>
        </router-link>
      </v-col>
    </form>
  </v-container>
</template>

<script>
import BackButtonComponent from "../components/BackButtonComponent.vue";

export default {
  components: {
    BackButtonComponent
  },
  data() {
    return {
      email: "",
      password: ""
    }
  }
}
</script>

<style scoped>
.indent {
  padding: 15px 0 15px 0;
}

.indent-buttons {
  margin: 0 10px 0 10px;
}

.error-info {
  background-color: #202548;
  color: #FFFFFF;
  margin: 15px 0 15px 0;
}
</style>