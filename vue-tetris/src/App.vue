<template>
  <div>
    <router-view />
    <c-dialog
      :show="showDialog"
      :ctype="dialogType"
      @close="closeDialog"
      ></c-dialog>
  </div>
</template>

<script>

import { mapState } from 'vuex'
import CDialog from '@/components/dialog';
export default {
  name: 'App',
  components: {
    CDialog,
  },
  beforeCreate: async function(){
    this.$store.dispatch("gameDetail");
    this.$store.dispatch("reload");
  },
  computed: {
    ...mapState([
      "showDialog",
      "dialogType",
    ]),
  },
  methods: {
    closeDialog(){
      this.$store.commit("SHOW_DIALOG", { show: false, type: "" });
    },
  },
}
</script>

<style lang="less">

</style>
