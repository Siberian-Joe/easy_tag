<template>
  <v-data-table
      :headers="headers"
      :items="users"
      class="elevation-1 rounded-xl"
  >
    <template v-slot:top>
      <v-dialog
          v-model="dialog"
          persistent
          width="290"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn small icon v-bind="attrs" v-on="on"><v-icon>mdi-plus</v-icon></v-btn>
        </template>
        <v-card class="indent">
          <v-main>
            <v-card-title class="indent-bottom">
              <span class="text-h5">Создание ответа</span>
            </v-card-title>

            <v-card-text class="indent-bottom">
              <v-container class="pa-0">
                <v-col class="indent-bottom">
                  <v-text-field class="field" hide-details="auto" required dense outlined label="Описание" v-model="description"/>
                </v-col>
                <v-col class="pa-0" cols="auto">
                  <v-switch :disabled="disabled" inset v-model="accepted" :label="accepted ? 'Одобрено' : 'Отклонено'"/>
                </v-col>
              </v-container>
            </v-card-text>

            <v-card-actions class="pa-0">
              <v-spacer></v-spacer>
              <v-btn
                  color="blue darken-1"
                  text
                  @click="close"
              >
                Отмена
              </v-btn>
              <v-btn
                  color="blue darken-1"
                  text
                  @click="save"
              >
                Сохранить
              </v-btn>
            </v-card-actions>
          </v-main>
        </v-card>
      </v-dialog>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon
          small
          class="mr-2"
          @click="editItem(item)"
      >
        mdi-pencil
      </v-icon>
    </template>
  </v-data-table>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  data() {
    return {
      users: [],
      dialog: false,
      accepted: false,
      description: "",
      disabled: false,
      headers: [
        {
          text: "ФИО",
          align: "start",
          value: "fullName"
        },
        { text: "E-mail", value: "email" },
        { text: "Описание", value: "request.description" },
        { text: "Тип", value: "request.type" },
        { text: "Операции", value: "actions", sortable: false }
      ],
      editedIndex: -1,
      editedItem: {
        fullName: "",
        company: null,
        email: ""
      },
      defaultItem: {
        fullName: '',
        company: null,
        email: ""
      }
    }
  },
  async mounted () {
    await this.getRequestsFromServer();
    this.users = this.getUsers;
  },
  computed: {
    ...mapGetters(["getUsers"])
  },
  methods: {
    ...mapActions(["getRequestsFromServer", "sendResponse", "updateUserCompany"]),
    editItem (item) {
      if(item.request.type === "Создание компании")
        this.disabled = false;
      else
        this.disabled = true;

      this.editedIndex = this.users.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },
    close () {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
      this.accepted = false;
      this.description = "";
    },
    async save () {
      let type;
      if(this.users[this.editedIndex].request.type === "Создание компании" ) {
        if(this.accepted) {
          type = "ACCEPTED";
          this.users[this.editedIndex].company = { name: "Название", items: []};
        }
        else
          type = "DENIED";
      }
      else
        type = "QUESTION";

      await this.sendResponse({ description: this.description, type: type, request: this.users[this.editedIndex].request});
      await this.updateUserCompany({user: this.users[this.editedIndex], path: window.location.origin});

      this.users.splice(this.editedIndex, 1);

      this.accepted = false;
      this.description = "";
      this.close()
    },
  }
}
</script>

<style scoped>
.indent {
  padding: 30px;
}

.indent-bottom {
  padding: 0 0 30px 0;
}
</style>