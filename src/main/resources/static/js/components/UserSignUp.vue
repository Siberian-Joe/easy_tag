<template>
  <v-container class="pa-0">
    <v-col class="indent" align="center">
      <v-text-field dense hide-details outlined label="E-mail" v-model="email" @keyup.enter=""/>
    </v-col>
    <v-col class="indent" align="center">
      <v-text-field dense hide-details outlined label="ФИО" v-model="fullName" @keyup.enter=""/>
    </v-col>
    <v-col class="indent" align="center">
      <v-text-field dense hide-details outlined label="Пароль" v-model="firstPassword" @keyup.enter="" type="password"/>
    </v-col>
    <v-col class="indent" align="center">
      <v-text-field dense hide-details outlined label="Подтверждение пароля" v-model="secondPassword" @keyup.enter="" type="password"/>
    </v-col>
    <v-col class="indent-last" align="center">
      <v-btn class="indent-buttons" @click="clickSignUp">Зарегистрироваться</v-btn>
    </v-col>
  </v-container>
</template>

<script>
export default {
  props:["signUp"],
  data() {
    return {
      email: "",
      fullName: "",
      firstPassword: "",
      secondPassword: ""
    }
  },
  methods: {
    clickSignUp() {
      this.signUp(this.email,this.fullName, this.secondPassword)
    }
  }
}
</script>

<style scoped>
.indent {
  padding: 15px 0 15px 0;
}

.indent-last {
  padding: 15px 0 0 0;
}

.indent-buttons {
  margin: 0 10px 0 10px;
}
</style>