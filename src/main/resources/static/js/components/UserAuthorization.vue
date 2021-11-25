<template>
  <user-sign-in :change-is-sign-up="changeIsSignUp" :set-user="setUser" v-if="!isSignUp" :signIn="signIn"/>
  <user-sign-up :sign-up="signUp" v-else/>
</template>

<script>
import UserSignIn from "./UserSignIn.vue";
import UserSignUp from "./UserSignUp.vue";
import axios from "axios";

export default {
  props:["changeIsAuthorization", "profile  ", "setUser"],
  components: {
    UserSignIn,
    UserSignUp
  },
  data() {
    return {
      isSignUp: false
    }
  },
  methods: {
    changeIsSignUp() {
      this.isSignUp = !this.isSignUp
    },
    signUp(email, fullName, password) {
      axios.post('/user', {
        email: email,
        fullName: fullName,
        password: password
      });
      this.changeIsAuthorization();
    },
    signIn(email, password) {
      // axios.post('/', {
      //   username: email,
      //   password: password
      // }).then(response => {
      //   console.log(response.data)
      // });
      axios('/', {
        method: 'POST',
        auth: {
          username: email,
          password: password
        }
      }).then(response => {
        console.log(response)
      });
    }
  }
}
</script>

<style scoped>

</style>