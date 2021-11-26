<template>
  <v-container class="pa-0">
    <v-row class="navigation-panel" no-gutters>
      <v-spacer/>
      <v-col class="pa-0" cols="auto">
        <router-link custom v-slot="{ href, navigate }" to="/">
          <v-btn fab :href="href" @click="navigate">
            <v-icon>
              mdi-arrow-left
            </v-icon>
          </v-btn>
        </router-link>
      </v-col>
    </v-row>
    <v-col class="indent pa-0" align="center">
      <v-col class="pa-0">
        <v-avatar size="150">
          <v-img size="150" src="/img/user.svg"/>
        </v-avatar>
      </v-col>
      <v-col class="company-title">
        <v-card-text class="pa-0" align="center">
          <v-row justify="center" align="center" v-if="!isEditFullName" no-gutters>
            <v-col class="pa-0" cols="auto">
              <label class="company-title-text"> {{ profile.fullName }} </label>
            </v-col>
            <v-col class="pa-0" cols="auto">
              <v-btn small icon @click="edit"><v-icon> mdi-pencil </v-icon></v-btn>
            </v-col>
          </v-row>
          <v-row v-else no-gutters>
            <v-text-field dense hide-details outlined label="Название" v-model="fullNameTemp" @keyup.enter="save"/>
            <v-btn icon @click="save"><v-icon> mdi-check </v-icon></v-btn>
            <v-btn icon @click="cancel"><v-icon> mdi-close </v-icon></v-btn>
          </v-row>
        </v-card-text>
      </v-col>
    </v-col>
    <v-card class="item scroll rounded-xl" elevation="5">
      <v-container class="pa-0">
        <v-row class="in-item pa-0" no-gutters>
          <v-col cols="auto">
            <v-icon class="icon icon-block" color="black" size="40">mdi-at</v-icon>
          </v-col>
          <v-col align-self="center">
            <label class="item-text" v-if="!isEditEmail"> {{ profile.email }} </label>
            <div v-else>
              <v-text-field class="field" :rules="emailRules" hide-details="auto" required dense outlined label="E-mail" v-model="emailTemp"/>
            </div>
          </v-col>
          <v-col cols="auto" align-self="center">
            <v-speed-dial class="edit-button" v-model="fab" direction="left" v-if="isEditEmail">
              <template v-slot:activator>
                <v-btn v-model="fab" small icon>
                  <v-icon v-if="fab">
                    mdi-close
                  </v-icon>
                  <v-icon v-else>
                    mdi-pencil
                  </v-icon>
                </v-btn>
              </template>
              <v-btn fab dark small color="green" href="/logout" @click="greenClick">
                <v-icon>mdi-check</v-icon>
              </v-btn>
              <v-btn fab dark small color="red" @click="redClick">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-speed-dial>
            <v-btn small icon @click="isEditEmail = !isEditEmail" v-else>
              <v-icon>
                mdi-settings
              </v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      fab: false,
      isEditFullName: false,
      isEditEmail: false,
      fullNameTemp: "",
      emailTemp: "",
      emailRules: [
        v => !!v || "Это поле обязательное",
        v => /^(([^<>()[\]\\.,;:\s@']+(\.[^<>()\\[\]\\.,;:\s@']+)*)|('.+'))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Неверная электронная почта'
      ],
    }
  },
  watch: {
    profile(newProfile) {
      this.fullNameTemp = newProfile.fullName;
      this.emailTemp = newProfile.email;
    }
  },
  mounted() {
    this.fullNameTemp = this.profile.fullName;
    this.emailTemp = this.profile.email;
  },
  computed: {
    ...mapState(["profile"])
  },
  methods: {
    ...mapActions(["updateFullNameAction", "updateEmailAction"]),
    edit() {
      this.isEdit = !this.isEdit;
    },
    save() {
      this.isEdit = !this.isEdit;

      this.updateFullNameAction(this.fullNameTemp);
    },
    cancel() {
      this.isEdit = !this.isEdit;

      this.fullNameTemp = this.profile.fullName;
    },
    greenClick() {
      this.isEditEmail = !this.isEditEmail;

      this.updateEmailAction(this.emailTemp);
    },
    redClick() {
      this.isEditEmail = !this.isEditEmail;
      this.fab = false;

      this.emailTemp = this.profile.email;
    }
  }
}
</script>

<style scoped>
.company-title {
  padding: 30px 0 30px 0;
}
.company-title-text {

  font-style: normal;
  font-weight: normal;
  font-size: 30px;
  line-height: 37px;
}

.indent {
  margin-top: 30px
}

.item {
  min-height: 70px;
  padding: 15px;
}
.item-text {
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  line-height: 18px;
}
.in-item {
  height: 100%;
}
.icon {
  width: 40px;
  height: 40px;
}
.icon-block {
  margin-right: 15px;
}
.edit-button {
  margin-left: 15px;
}
.field {
  margin: 5px;
}
</style>