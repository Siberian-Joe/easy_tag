<template>
  <div class="ma-0" align="center">
    <v-col class="pa-0">
      <v-avatar size="150">
        <v-img size="150" :src="logo == null ? '/img/user.svg' : '/img/' + logo + '.png'"/>
      </v-avatar>
    </v-col>
    <v-col class="company-title">
      <v-card-text class="pa-0" align="center">
        <v-row justify="center" align="center" v-if="!isEdit">
          <v-col class="pa-0" cols="auto">
            <label class="company-title-text"> {{ name }} </label>
          </v-col>
          <v-col class="pa-0" cols="auto" v-if="editMode">
            <v-btn small icon @click="edit"><v-icon> mdi-pencil </v-icon></v-btn>
          </v-col>
        </v-row>
        <div v-else>
          <v-text-field dense hide-details outlined label="Название" v-model="name" @keyup.enter="save"/>
          <v-btn icon @click="save"><v-icon> mdi-check </v-icon></v-btn>
          <v-btn icon @click="cancel"><v-icon> mdi-close </v-icon></v-btn>
        </div>

      </v-card-text>
    </v-col>
  </div>
</template>

<script>
export default {
  props: ['name', 'logo', 'editMode'],
  data() {
    return {
      isEdit: false,
      nameTemp: this.name
    }
  },
  methods: {
    save() {

    },
    edit() {
      this.isEdit = true;
    },
    cancel() {
      this.isEdit = false;
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