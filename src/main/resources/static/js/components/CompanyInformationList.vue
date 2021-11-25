<template>
  <v-card class="company-info scroll rounded-xl" elevation="5">
    <v-container class="pa-0">
      <company-list-item v-for="item in items" :key="item.id" :item="item" :editMode="editMode"/>
    </v-container>
  </v-card>
</template>

<script>
import CompanyListItem from "./CompanyListItem.vue";

export default {
  props: ["items", "editMode"],
  components: {
    CompanyListItem
  },
  data() {
    return {

    }
  }
}
</script>

<style scoped>
.company-info {
  background-color: #202548;
  max-height: 500px;
}

.scroll {
  overflow-y: auto;
}

</style>