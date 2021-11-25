<template>
  <v-card class="item rounded-xl" elevation="5" :href="editMode ? null : item.href">
    <v-row class="in-item pa-0" no-gutters>
      <v-col cols="auto">
        <v-img class="icon icon-block" size="40" :src="'/img/'+ item.icon + '.svg'" v-if="item.icon !== null"/>
        <v-icon class="icon icon-block" color="black" size="40" v-else>mdi-web</v-icon>
      </v-col>
      <v-col align-self="center">
        <label class="item-text" v-if="!isEdit"> {{ item.name }} </label>
        <div v-else>
          <v-text-field class="field" :rules="fieldRules" counter="12" required dense  outlined label="Название" v-model="name" @keyup.enter="greenClick"/>
          <v-text-field class="field" dense hide-details outlined label="Ссылка" v-model="href" @keyup.enter="greenClick"/>
        </div>
      </v-col>
      <v-col v-if="editMode" cols="auto" align-self="center">
        <v-speed-dial class="edit-button" v-model="fab" direction="left">
          <template v-slot:activator>
            <v-btn v-model="fab" small icon>
              <v-icon v-if="fab">
                mdi-close
              </v-icon>
              <v-icon v-else-if="isEdit">
                mdi-pencil
              </v-icon>
              <v-icon v-else>
                mdi-settings
              </v-icon>
            </v-btn>
          </template>
          <v-btn fab dark small color="green" @click="greenClick">
            <v-icon v-if="!isEdit">mdi-pencil</v-icon>
            <v-icon v-else>mdi-check</v-icon>
          </v-btn>
          <v-btn fab dark small color="red" @click="redClick">
            <v-icon v-if="!isEdit">mdi-delete</v-icon>
            <v-icon v-else>mdi-close</v-icon>
          </v-btn>
        </v-speed-dial>
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
      fieldRules: [
          v => !!v || "Имя обязательно",
          v => v.length <= 12 || 'Имя должно быть не более 12 символов',
      ],
      isEdit: false,
      name: this.item.name,
      nameTemp: this.item.name,
      href: this.item.href,
      hrefTemp: this.item.href
    }
  },
  methods: {
    ...mapActions(["updateItemAction", "deleteItemAction"]),
    greenClick() {
      if(!this.isEdit)
        this.isEdit = !this.isEdit;
      else if(this.name.length <= 12) {
        this.isEdit = false;

        this.nameTemp = this.name;
        this.item.name = this.name;

        this.hrefTemp = this.href;
        this.item.href = this.href;

        this.item.name = this.name;
        this.item.href = this.href;

        this.updateItemAction();
      }
    },
    redClick() {
      if(!this.isEdit)
        this.deleteItemAction(this.item);
      else {
        this.isEdit = false;

        this.item.name = this.nameTemp;
        this.name = this.item.name;

        this.item.href = this.hrefTemp;
        this.href = this.item.href;
      }
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
</style>