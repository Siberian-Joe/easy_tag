<template>
  <v-card class="menu align-self-center rounded-xl" elevation="15">
    <v-container class="in-menu">
      <v-row>
        <v-col cols="auto">
          <v-btn small height="50">
            <v-icon>
              mdi-qrcode-scan
            </v-icon>
          </v-btn>
        </v-col>
        <v-spacer/>
        <v-col cols="auto">
          <v-speed-dial
              v-model="fab"
              direction="left"
          >
            <template v-slot:activator>
              <v-btn
                  v-model="fab"
                  fab
              >
                <v-icon v-if="fab">
                  mdi-close
                </v-icon>
                <v-icon v-else>
                  mdi-account-circle
                </v-icon>
              </v-btn>
            </template>
            <v-btn
                fab
                dark
                small
                color="green"
                @click="editMode = !editMode"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn
                fab
                dark
                small
                color="indigo"
            >
              <v-icon>mdi-plus</v-icon>
            </v-btn>
            <v-btn
                fab
                dark
                small
                color="red"
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </v-speed-dial>
        </v-col>
      </v-row>

      <v-col class="pa-0">
        <company-title :name="company.name" :logo="company.logo" :editMode="editMode"/>
      </v-col>
      <v-col class="pa-0" align="end" v-if="editMode">
        <v-btn small icon @click="addItem"><v-icon>mdi-plus</v-icon></v-btn>
      </v-col>
      <v-col class="pa-0">
        <company-information-list :items="items" :editMode="editMode" :saveItem="saveItem" :deleteItem="deleteItem"/>
      </v-col>
    </v-container>
  </v-card>
</template>

<script>
import CompanyTitle from './CompanyTitle.vue';
import CompanyInformationList from "./CompanyInformationList.vue";
import axios from "axios";

export default {
  props: ["company"],
  components: {
    CompanyTitle,
    CompanyInformationList
  },
  data() {
    return {
      editMode: true,
      fab: false,
      items: this.makeQueue(this.company.items)
      // items: [
      //   {
      //     id: "0",
      //     name: "Tik-tok",
      //     icon: "tik-tok",
      //     href: "https://vk.com/siberian_joe"
      //   },
      //   {
      //     id: "1",
      //     name: "Instagram",
      //     icon: "instagram",
      //     href: "https://vk.com/siberian_joe"
      //   },
      //   {
      //     id: "2",
      //     name: "Telegram",
      //     icon: "telegram",
      //     href: "https://vk.com/siberian_joe"
      //   },
      //   {
      //     id: "3",
      //     name: "VK",
      //     icon: "vkontakte",
      //     href: "https://vk.com/siberian_joe"
      //   },
      //   {
      //     id: "4",
      //     name: "Skype",
      //     icon: "skype",
      //     href: "skype:live:83c7467ff7689aed"
      //   }]
    }
  },
  methods: {
    addItem() {
      this.items.push({id: this.items.length, name: "Новый элемент", icon: null})
      axios.put('/company/items/' + this.company.id, {
        id: this.company.id,
        name: this.company. name,
        items: this.items
      });
    },
    saveItem() {
      axios.put('/company/items/' + this.company.id, {
        id: this.company.id,
        name: this.company. name,
        items: this.items
      });
    },
    deleteItem(item) {
      this.deleteItemInQueue(this.items, item)
      axios.put('/company/items/' + this.company.id, {
        id: this.company.id,
        name: this.company. name,
        items: this.items
      });
    },
    makeQueue(items) {
      let newItems = [];
      for(let i = 0; i < items.length; i++)
        for(let k = 0; k < items.length; k++)
          if(items[k].id === i.toString())
            newItems[i] = items[k];
      return newItems;
    },
    deleteItemInQueue(items, item) {
      for(let i = items.length - 1; i > items.indexOf(item); i--)
        items[i].id = (i - 1).toString();
      items.splice(items.indexOf(item), 1);
    }
  }
}
</script>

<style scoped>

.menu {
  margin: 20px;

  width: 460px;

  background: #FFFFFF;
  box-shadow: 0 0 10px 10px rgba(0, 0, 0, 0.8);
}

.in-menu {
  padding: 30px;
}
</style>