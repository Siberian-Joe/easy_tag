<template>
  <v-container class="pa-0">
    <back-button-component/>
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
              <label class="company-title-text"> {{ getProfile.fullName }} </label>
            </v-col>
            <v-col class="pa-0" cols="auto">
              <v-btn small icon @click="edit"><v-icon> mdi-pencil </v-icon></v-btn>
            </v-col>
          </v-row>
          <v-col class="pa-0" v-else no-gutters>
            <v-text-field class="pa-0 ma-0" dense hide-details outlined label="ФИО" v-model="fullNameTemp" @keyup.enter="save"/>
            <v-btn icon @click="save"><v-icon> mdi-check </v-icon></v-btn>
            <v-btn icon @click="cancel"><v-icon> mdi-close </v-icon></v-btn>
          </v-col>
        </v-card-text>
      </v-col>
    </v-col>
    <v-card class="item indent-top rounded-xl" elevation="5">
      <v-container class="pa-0">
        <v-row class="in-item pa-0" no-gutters>
          <v-col class="pa-0" cols="auto">
            <v-icon class="icon icon-block" color="black" size="40">mdi-at</v-icon>
          </v-col>
          <v-col class="pa-0" align-self="center">
            <label class="item-text" v-if="!isEditEmail"> {{ getProfile.email }} </label>
            <div v-else>
              <v-text-field class="field pa-0 ma-0" :rules="emailRules" hide-details="auto" required dense outlined label="E-mail" v-model="emailTemp"/>
            </div>
          </v-col>
          <v-col class="pa-0" cols="auto" align-self="center">
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
            <v-btn small icon @click="editEmail" v-else>
              <v-icon>
                mdi-settings
              </v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
    <v-card class="item indent-top rounded-xl" elevation="5" v-if="getProfile.request !== undefined && getProfile.request !== null">
      <v-container class="pa-0">
        <v-row class="in-item pa-0" no-gutters>
          <v-col cols="auto">
            <v-icon class="icon icon-block" color="black" size="40">mdi-account-arrow-right</v-icon>
          </v-col>
          <v-col class="pa-0" cols="10">
            <v-col class="pa-0" align-self="center">
              <v-card-text class="item-text indent-15px-bottom text-h6"> {{ getProfile.request.type.type === "CREATE" ? "Создание компании" : "Вопрос" }} </v-card-text>
            </v-col>
            <v-col class="pa-0" cols="10" align-self="center">
              <v-card-text class="item-text pa-0"> {{ getProfile.request.description }} </v-card-text>
            </v-col>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
    <v-card class="item indent-top rounded-xl" elevation="5" v-else>
      <v-container class="pa-0">
        <v-row class="in-item pa-0" no-gutters>
          <v-col cols="auto">
            <v-icon class="icon icon-block" color="black" size="40">mdi-account-arrow-right</v-icon>
          </v-col>
          <v-col class="pa-0">
            <v-col class="pa-0" align-self="center">
              <v-card-text class="item-text"> Запросы не найдены </v-card-text>
            </v-col>
          </v-col>
          <v-col class="pa-0" align-self="center" align="end" cols="auto">
            <v-dialog v-model="dialog" persistent width="290">
              <template v-slot:activator="{ on, attrs }">
                <v-btn small icon v-bind="attrs" v-on="on"><v-icon>mdi-plus</v-icon></v-btn>
              </template>
              <v-card class="indent">
                <v-main>
                  <v-card-title class="indent-bottom">
                    <span class="text-h5">Создание запроса</span>
                  </v-card-title>
                  <v-card-text class="indent-bottom">
                    <v-container class="pa-0">
                      <v-col class="indent-bottom">
                        <v-select outlined hide-details="auto" dense label="Тип запроса" v-model="typeOfRequest" :items="typesOfRequest" item-text="name" item-value="type"></v-select>
                      </v-col>
                      <v-col class="pa-0" cols="auto">
                        <v-text-field class="field pa-0 ma-0" hide-details="auto" required dense outlined label="Описание" v-model="description"/>
                      </v-col>
                    </v-container>
                  </v-card-text>
                  <v-card-actions class="pa-0">
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="closeDialog">
                      Отмена
                    </v-btn>
                    <v-btn color="blue darken-1" text @click="saveDialog">
                      Сохранить
                    </v-btn>
                  </v-card-actions>
                </v-main>
              </v-card>
            </v-dialog>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
    <v-card class="item indent-top rounded-xl" elevation="5" v-if="getProfile.response !== undefined && getProfile.response !== null">
      <v-container class="pa-0">
        <v-row class="in-item pa-0" no-gutters>
          <v-col cols="auto">
            <v-icon class="icon icon-block" color="black" size="40">mdi-account-arrow-left</v-icon>
          </v-col>
          <v-col class="pa-0" cols="10">
            <v-col class="pa-0" align-self="center">
              <v-card-text class="item-text indent-15px-bottom text-h6"> {{ getProfile.response.type.type !== "QUESTION" ? "Создание компании" : "Вопрос" }} </v-card-text>
            </v-col>
            <v-col class="pa-0" align-self="center" cols="10">
              <v-card-text class="item-text pa-0"> {{ getProfile.response.description }} </v-card-text>
              <v-card-text class="item-text pa-0" v-if="getProfile.response.type.type !== 'QUESTION'"> Статус: {{ getProfile.response.type.type === "ACCEPTED" ? "Одобрено" : "Отклонено" }} </v-card-text>
            </v-col>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
    <v-card class="item indent-top rounded-xl" elevation="5" v-else>
      <v-container class="pa-0">
        <v-row class="in-item pa-0" no-gutters>
          <v-col cols="auto">
            <v-icon class="icon icon-block" color="black" size="40">mdi-account-arrow-left</v-icon>
          </v-col>
          <v-col class="pa-0">
            <v-col class="pa-0" align-self="center">
              <v-card-text class="item-text"> Ответы не найдены </v-card-text>
            </v-col>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-container>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import BackButtonComponent from "../components/BackButtonComponent.vue";

export default {
  components: {
    BackButtonComponent
  },
  data() {
    return {
      fab: false,
      isEditFullName: false,
      isEditEmail: false,
      dialog: false,
      typeOfRequest: null,
      typesOfRequest: [],
      fullNameTemp: "",
      emailTemp: "",
      description: "",
      emailRules: [
        v => !!v || "Это поле обязательное",
        v => /^(([^<>()[\]\\.,;:\s@']+(\.[^<>()\\[\]\\.,;:\s@']+)*)|('.+'))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Неверная электронная почта'
      ],
    }
  },
  async mounted() {
    await this.getTypesOfRequestFromServer();
    this.typesOfRequest = this.getTypesOfRequest;
  },
  computed: {
    ...mapGetters(["getProfile", "getTypesOfRequest"])
  },
  methods: {
    ...mapActions(["updateFullNameAction", "updateEmailAction", "postRequest", "getTypesOfRequestFromServer"]),
    edit() {
      this.isEditFullName = !this.isEditFullName;
      this.fullNameTemp = this.getProfile.fullName;
    },
    save() {
      this.isEditFullName = !this.isEditFullName;

      this.updateFullNameAction(this.fullNameTemp);
    },
    cancel() {
      this.isEditFullName = !this.isEditFullName;

      this.fullNameTemp = this.getProfile.fullName;
    },
    editEmail() {
      this.isEditEmail = !this.isEditEmail;
      this.emailTemp = this.getProfile.email;
    },
    greenClick() {
      this.isEditEmail = !this.isEditEmail;

      this.updateEmailAction(this.emailTemp);
    },
    redClick() {
      this.isEditEmail = !this.isEditEmail;
      this.fab = false;

      this.emailTemp = this.getProfile.email;
    },
    closeDialog() {
      this.dialog = false;
      this.typeOfRequest = null;
      this.description = "";
    },
    saveDialog() {
      this.dialog = false;

      this.postRequest({ description: this.description, type: this.typeOfRequest })
      this.typeOfRequest = null;
      this.description = "";
    }
  }
}
</script>

<style scoped>
.company-title {
  padding: 30px 0 0 0;
}
.company-title-text {

  font-style: normal;
  font-weight: normal;
  font-size: 30px;
  line-height: 37px;
}

.indent-15px-bottom {
  padding: 0 0 15px 0;
}

.indent {
  margin-top: 30px
}

.indent-top {
  margin: 30px 0 0 0;
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

.indent {
  padding: 30px;
}

.indent-bottom {
  padding: 0 0 30px 0;
}
</style>