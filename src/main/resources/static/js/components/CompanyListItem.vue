<template>
  <v-card class="item rounded-xl" elevation="5" :href="editMode ? null : item.href">
    <v-row class="in-item pa-0" no-gutters >
      <v-col class="pa-0" cols="auto" tile>
        <v-img class="icon icon-block" size="40" :src="'/img/'+ item.icon + '.svg'" v-if="item.icon !== null"/>
        <v-icon class="icon icon-block" color="black" size="40" v-else>mdi-web</v-icon>
      </v-col>
      <v-col class="pa-0" cols="8" align-self="center" tile>
        <label class="item-text"> {{ item.name }} </label>
      </v-col>
      <v-col v-if="editMode" cols="auto" align-self="center">
        <v-dialog v-model="dialog" :persistent="loading" width="390">
          <template v-slot:activator="{ on, attrs }">
            <v-btn class="edit-button" small icon v-bind="attrs" v-on="on"><v-icon>mdi-settings</v-icon></v-btn>
          </template>
          <v-card class="indent" :loading="loading" :disabled="loading">
            <v-main>
              <v-card-title class="indent-bottom">
                <span class="text-h5">Создание ответа</span>
              </v-card-title>
              <v-card-text class="indent-bottom">
                <v-container class="pa-0">
                  <v-autocomplete v-model="icon" :items="icons" rounded solo color="blue-grey lighten-2" label="Иконка" item-text="name" item-value="value">
                    <template v-slot:selection="data">
                      <v-chip v-bind="data.attrs" :input-value="data.selected" @click="data.select">
                        <v-img class="mr-1" width="20" :src="'/img/'+ data.item.value + '.svg'"></v-img>
                        {{ data.item.name }}
                      </v-chip>
                    </template>
                    <template v-slot:item="data">
                      <template v-if="typeof data.item !== 'object'">
                        <v-list-item-content v-text="data.item"></v-list-item-content>
                      </template>
                      <template v-else>
                        <v-list-item-avatar>
                          <v-img class="mr-1" width="20" :src="'/img/'+ data.item.value + '.svg'"></v-img>
                        </v-list-item-avatar>
                        <v-list-item-content>
                          <v-list-item-title v-html="data.item.name"></v-list-item-title>
                          <v-list-item-subtitle v-html="data.item.group"></v-list-item-subtitle>
                        </v-list-item-content>
                      </template>
                    </template>
                  </v-autocomplete>
                  <v-text-field class="field" required dense  outlined label="Название" v-model="name"/>
                  <v-text-field class="field" dense hide-details outlined label="Ссылка" v-model="href"/>
                </v-container>
              </v-card-text>
              <v-card-actions class="pa-0">
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close">
                  Отмена
                </v-btn>
                <v-btn color="blue darken-1" text @click="remove">
                  Удалить
                </v-btn>
                <v-btn color="blue darken-1" text @click="save">
                  Сохранить
                </v-btn>
              </v-card-actions>
            </v-main>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  props: ["item", "editMode"],
  data() {
    return {
      fab: false,
      dialog: false,
      loading: false,
      name: this.item.name,
      nameTemp: this.item.name,
      href: this.item.href,
      hrefTemp: this.item.href,
      icons: [{ name: "Facebook", value:"facebook" }, { name: "Instagram", value:"instagram" }, { name: "Местоположение", value:"location" },
        { name: "Messenger", value:"messenger" }, { name: "Одноклассники", value:"odnoklassniki" }, { name: "Reddit", value:"reddit" },
        { name: "Сайт", value:"site" }, { name: "Skype", value:"skype" }, { name: "Snapchat", value:"snapchat" },
        { name: "Telegram", value:"telegram" }, { name: "Tik-Tok", value:"tik-tok" }, { name: "Twitter", value:"twitter" },
        { name: "ВК", value:"vkontakte" }, { name: "WhatsApp", value:"whatsapp" }, { name: "YouTube", value:"youtube" }],
      icon: null
    }
  },
  watch: {
    item(newItem) {
      this.name = newItem.name;
      this.nameTemp = newItem.name;
      this.href = newItem.href;
      this.hrefTemp = newItem.href;
    }
  },
  methods: {
    ...mapActions(["updateItemAction", "deleteItemAction"]),
    close() {
      this.dialog = false;
      this.item.name = this.nameTemp;
      this.name = this.item.name;

      this.item.href = this.hrefTemp;
      this.href = this.item.href;

      this.loading = false;
      this.icon = null;
    },
    async remove() {
      this.loading = true;

      await this.deleteItemAction(this.item);

      this.close();
    },
    async save() {
      this.loading = true;
      this.nameTemp = this.name;
      this.item.name = this.name;

      this.hrefTemp = this.href;
      this.item.href = this.href;

      this.item.name = this.name;
      this.item.href = this.href;

      this.item.icon = this.icon;

      await this.updateItemAction();

      this.close();
    }
  }
}
</script>

<style scoped>
.item {
  margin: 25px;
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