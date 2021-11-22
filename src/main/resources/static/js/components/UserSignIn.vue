<template>
  <v-container class="pa-0">
    <form method="post" action="/">
      <v-col class="indent" align="center">
        <v-text-field  id="username" name="username" dense hide-details outlined label="E-mail" v-model="email" @keyup.enter=""/>
      </v-col>
      <v-col class="indent" align="center">
        <v-text-field id="password" name="password" dense hide-details outlined label="Пароль" v-model="password" @keyup.enter="" type="password"/>
      </v-col>
      <v-col class="indent" align="center">
        <v-btn class="indent-buttons" type="submit">Войти</v-btn>
        <v-btn class="indent-buttons" @click="changeIsSignUp">Регистрация</v-btn>
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
    clickSignIn() {
      this.signIn(this.email, this.password)
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
</style>