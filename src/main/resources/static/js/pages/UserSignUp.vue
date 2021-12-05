<template>
  <v-container class="pa-0">
    <back-button-component/>
    <v-form ref="form" lazy-validation>
      <v-col class="indent" align="center">
        <v-text-field dense hide-details="auto" outlined :error-messages="errors" :rules="emailRules" label="E-mail" v-model="email" @keyup.enter=""/>
      </v-col>
      <v-col class="indent" align="center">
        <v-text-field dense hide-details="auto" outlined :rules="fullNameRules" label="ФИО" v-model="fullName" @keyup.enter=""/>
      </v-col>
      <v-col class="indent" align="center">
        <v-text-field dense hide-details="auto" outlined :rules="passwordRules" label="Пароль" v-model="password" @keyup.enter="" type="password"/>
      </v-col>
      <v-col class="indent" align="center">
        <v-text-field dense hide-details="auto" outlined :rules="passwordConfirmRules" label="Подтверждение пароля" v-model="confirmPassword" @keyup.enter="" type="password"/>
      </v-col>
      <v-col class="indent-last" align="center">
        <v-btn class="indent-buttons" @click="signUp">Зарегистрироваться</v-btn>
      </v-col>
    </v-form>
  </v-container>
</template>

<script>
import axios from "axios";
import BackButtonComponent from "../components/BackButtonComponent.vue";

export default {
  components: {
    BackButtonComponent
  },
  data() {
    return {
      email: "",
      fullName: "",
      password: "",
      confirmPassword: "",
      error: false,
      errors: [],
      emailRules: [
        v => !!v || "Это поле обязательное",
        v => /^(([^<>()[\]\\.,;:\s@']+(\.[^<>()\\[\]\\.,;:\s@']+)*)|('.+'))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Неверная электронная почта'
      ],
      fullNameRules: [
        v => !!v || "Это поле обязательное",
      ],
      passwordRules: [
        v => !!v || "Это поле обязательное",
        v => /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/.test(v) || 'Пароль должен содержать по крайней мере строчную букву, одну цифру, специальный символ и одну заглавную букву'
      ],
      passwordConfirmRules: [
        v => !!v || "Это поле обязательное",
        v => v === this.password || "Пароли не совпадают"
      ]
    }
  },
  methods: {
    signUp() {
      if(this.$refs.form.validate())
        axios.post('/user', {
          email: this.email,
          fullName: this.fullName,
          password: this.confirmPassword
        }).then(response => {
          if(response.data !== "")
            this.$router.push("/login");
          else
            this.errors.push("Электронная почта уже занята")
        });
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