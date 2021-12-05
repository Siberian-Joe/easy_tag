<template>
  <div class="ma-0" align="center">
    <v-col class="pa-0">
      <v-avatar size="150">
        <v-img size="150" :src="logo == null ? '/img/user.svg' : '/logo/' + logo"/>
      </v-avatar>
    </v-col>
    <v-col class="company-title">
      <v-card-text class="pa-0" align="center">
        <v-row justify="center" align="center" no-gutters>
          <v-col class="pa-0" cols="auto">
            <label class="company-title-text"> {{ name }} </label>
          </v-col>
          <v-col class="pa-0" cols="auto" v-if="editMode">
            <v-dialog v-model="dialog" persistent width="290" :persistent="loading">
              <template v-slot:activator="{ on, attrs }">
                <v-btn small icon v-bind="attrs" v-on="on"><v-icon> mdi-pencil </v-icon></v-btn>
              </template>
              <v-card class="indent" :disabled="loading" :loading="loading">
                <v-main>
                  <v-card-title class="indent-bottom">
                    <span class="text-h5">Измненения</span>
                  </v-card-title>
                  <v-card-text class="indent-bottom">
                    <v-container class="pa-0">
                      <v-col class="indent-bottom">
                        <v-file-input class="pa-0 ma-0" :rules="rules" :accept="fileTypes" prepend-icon="mdi-camera-burst" label="Логотип" v-model="file" :error-messages="errorMessage" hide-details="auto" dense outlined/>
                      </v-col>
                      <v-col class="pa-0" cols="auto">
                        <v-text-field class="field pa-0 ma-0" hide-details="auto" required dense outlined label="Название" v-model="nameTemp"/>
                      </v-col>
                    </v-container>
                  </v-card-text>
                  <v-card-actions class="pa-0">
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="cancel">
                      Отмена
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
      loading: false,
      dialog: false,
      errorMessage: "",
      nameTemp: "",
      file: null,
      fileTypes: ["image/png", "image/jpeg", "image/bmp"],
      rules: [
        value => !value || value.size < 2000000 || 'Размер аватара должен быть меньше 2 МБ!',
      ]
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
    ...mapActions(["setCompanyNameAction", "setCompanyLogo"]),
    async save() {
      if(this.file !== null && this.fileTypes.indexOf(this.file.type) !== -1) {
        this.loading = true;

        await this.setCompanyNameAction(this.nameTemp);
        await this.setCompanyLogo(this.file);

        this.cancel()
      }
      else if(this.file === null) {
        this.loading = true;

        await this.setCompanyNameAction(this.nameTemp);

        this.cancel()
      }
      else
        this.errorMessage = "Неверный тип файла";
    },
    cancel() {
      this.loading = false;
      this.dialog = false;
      this.errorMessage = "";

      this.file = null;
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

.indent {
  margin-top: 30px
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