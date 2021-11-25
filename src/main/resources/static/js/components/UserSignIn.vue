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
export default {
  props:["changeIsSignUp", "setUser", "signIn"],
  data() {
    return {
      email: "",
      password: ""
    }
  },
  methods: {
    // clickSignIn() {
    //   this.signIn(this.email, this.password)
    // }
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

.navigation-panel {
  padding-bottom: 15px;
}

.error-info {
  background-color: #202548;
  color: #FFFFFF;
  margin: 15px 0 15px 0;
}

</style>