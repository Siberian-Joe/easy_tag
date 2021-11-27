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
    <v-data-table
        :headers="headers"
        :items="users"
        class="elevation-1 rounded-xl"
    >
      <template v-slot:top>
        <v-toolbar
            flat
        >
          <v-toolbar-title>Панель администрации</v-toolbar-title>
          <v-divider
              class="mx-4"
              inset
              vertical
          ></v-divider>
          <v-spacer></v-spacer>
          <v-dialog
              v-model="dialog"
              max-width="500px"
          >
            <v-card class="indent">
              <v-main>
                <v-card-title class="indent-bottom">
                  <span class="text-h5">Редактирование</span>
                </v-card-title>

                <v-card-text class="indent-bottom">
                  <v-container class="pa-0">
                    <v-row no-gutters align="center">
                      <v-col class="pa-0">
                        <v-select outlined hide-details="auto" label="Роль" v-model="editedItem.role" :items="getConvertedRoles"></v-select>
                      </v-col>
                      <v-col class="pa-0" cols="auto">
                        <v-row no-gutters align="center">
                          <span class="indent-left-right"> Компания: </span>
                          <v-switch inset v-model="isAllowed" :label="isAllowed ? 'Разрешена' : 'Запрещена'"/>
                        </v-row>
                      </v-col>
                    </v-row>
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
        </v-toolbar>
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
  </v-container>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  data() {
    return {
      dialog: false,
      dialogDelete: false,
      isAllowed: false,
      headers: [
        {
          text: "ФИО",
          align: "start",
          value: "fullName"
        },
        { text: "Роль", value: "role" },
        { text: "E-mail", value: "email" },
        { text: "Компания", value: "company.name" },
        { text: "Операции", value: "actions", sortable: false }
      ],
      users: [],
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
      },
      roles: []
    }
  },
  watch: {
    dialog (val) {
      val || this.close()
    }
  },
  async mounted () {
    await this.getUsersFromServer();
    await this.getRolesFromServer();
    this.users = this.getUsers;
    this.roles = this.getRoles;
  },
  computed: {
    ...mapGetters(["getUsers", "getRoles"]),
    getConvertedRoles() {
      let roles = [];
      this.roles.forEach(role => {
        roles.push(role.role);
      })
      return roles;
    }
  },
  methods: {
    ...mapActions(["getUsersFromServer", "updateUserRoleAction", "getRolesFromServer", "deleteUserAction"]),
    editItem (item) {
      this.editedIndex = this.users.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
      if(this.editedItem.company !== null)
        return  this.isAllowed = true;
      else
        return this.isAllowed = false;
    },

    close () {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
      this.isAllowed = false;
    },
    save () {
      if(this.isAllowed && this.editedItem.company === null)
        this.editedItem.company = {};

      if (this.editedIndex > -1)
        Object.assign(this.users[this.editedIndex], this.editedItem);
      else
        this.users.push(this.editedItem)

      this.roles.forEach(role => {
        if(role.role === this.editedItem.role)
          this.editedItem.role = role;
      });

      this.updateUserRoleAction(this.editedItem);

      this.close()
    },
  }
}
</script>

<style scoped>
.navigation-panel {
  padding-bottom: 30px;
}

.indent {
  padding: 30px;
}

.indent-bottom {
  padding: 0 0 30px 0;
}

.indent-left-right {
  padding: 0 30px 0 30px;
}
</style>