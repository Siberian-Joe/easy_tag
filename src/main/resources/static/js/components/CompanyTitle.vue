<template>
  <div class="ma-0" align="center">
    <v-col class="pa-0">
      <v-avatar size="150">
        <v-img size="150" :src="logo == null ? '/img/user.svg' : '/img/logos/' + logo + '.jpg'"/>
      </v-avatar>
    </v-col>
    <v-col class="company-title">
      <v-card-text class="pa-0" align="center">
        <v-row justify="center" align="center" v-if="!isEdit" no-gutters>
          <v-col class="pa-0" cols="auto">
            <label class="company-title-text"> {{ name }} </label>
          </v-col>
          <v-col class="pa-0" cols="auto" v-if="editMode">
            <v-btn small icon @click="edit"><v-icon> mdi-pencil </v-icon></v-btn>
          </v-col>
        </v-row>
        <v-row v-else no-gutters>
          <v-text-field dense hide-details outlined label="Название" v-model="nameTemp" @keyup.enter="save"/>
          <v-btn icon @click="save"><v-icon> mdi-check </v-icon></v-btn>
          <v-btn icon @click="cancel"><v-icon> mdi-close </v-icon></v-btn>
        </v-row>
      </v-card-text>
    </v-col>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {


  props: ['name', 'logo', 'editMode'],
  data() {
    return {
      isEdit: false,
      nameTemp: ""
    }
  },
  watch: {
    name(newName) {
      this.nameTemp = newName;
    }
  },
  mounted() {
    this.nameTemp = this.name;
  },
  methods: {
    ...mapActions(["setCompanyNameAction"]),
    edit() {
      this.isEdit = !this.isEdit;
    },
    save() {
      this.isEdit = !this.isEdit;

      this.setCompanyNameAction(this.nameTemp);
    },
    cancel() {
      this.isEdit = !this.isEdit;

      this.nameTemp = this.name;
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
</style>