<template>
  <v-data-table
      :headers="headers"
      :items="users"
      class="elevation-1 rounded-xl"
  >
    <template v-slot:top>
      <v-dialog v-model="dialog" max-width="400px" :persistent="loading">
        <v-card class="indent rounded-xl" :disabled="loading" :loading="loading">
          <v-main>
            <v-card-title class="indent-bottom">
              <span class="text-h5">Редактирование</span>
            </v-card-title>
            <v-card-text class="indent-bottom">
              <v-container class="pa-0">
                <v-row no-gutters align="center">
                  <v-col class="pa-0" cols="auto">
                    <v-select outlined hide-details="auto" label="Роль" v-model="editedItem.role" :items="roles" item-text="name" item-value="role"></v-select>
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
              <v-btn color="blue darken-1" text @click="close">
                Отмена
              </v-btn>
              <v-btn color="blue darken-1" text @click="save">
                Сохранить
              </v-btn>
            </v-card-actions>
          </v-main>
        </v-card>
      </v-dialog>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)">
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
      roles: [],
      dialog: false,
      isAllowed: false,
      loading: false,
      headers: [
        {
          text: "ФИО",
          align: "start",
          value: "fullName"
        },
        { text: "Роль", value: "role.name" },
        { text: "E-mail", value: "email" },
        { text: "Компания", value: "company.name" },
        { text: "Операции", value: "actions", sortable: false }
      ],
      editedIndex: -1,
      editedItem: {
        role: null,
        company: null
      },
      defaultItem: {
        role: null,
        company: null
      }
    }
  },
  async mounted () {
    await this.getUsersFromServer();
    await this.getRolesFromServer();
    this.users = this.getUsers;
    this.roles = this.getRoles;
  },
  computed: {
    ...mapGetters(["getUsers", "getRoles"])
  },
  methods: {
    ...mapActions(["getUsersFromServer", "updateUserRoleAction", "getRolesFromServer", "deleteUserAction", "updateUserCompany", "deleteCompany"]),
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
      this.loading = false;
    },
    async save () {
      this.loading = true;
      if(this.isAllowed && this.editedItem.company === null)
        this.editedItem.company = { name: "Название", items: []};

      this.roles.forEach(role => {
        if(role.role === this.editedItem.role)
          this.editedItem.role = role;
      });

      if (this.editedIndex > -1)
        Object.assign(this.users[this.editedIndex], this.editedItem);
      else
        this.users.push(this.editedItem)

      if(this.editedItem.company !== null && !this.isAllowed) {
        await this.deleteCompany(this.editedItem);
        this.users[this.editedIndex].company = null;
      }
      else if (this.isAllowed)
        await this.updateUserCompany({ user: this.editedItem, path: window.location.origin });

      await this.updateUserRoleAction(this.editedItem);

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

.indent-left-right {
  padding: 0 30px 0 30px;
}
</style>