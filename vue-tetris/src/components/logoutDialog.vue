<template>
  <el-dialog :model-value="visible" :show-close="false" :close-on-click-modal="false" :custom-class="'tetris-dialog'" @close="$emit('close')" @closed="closed" @open="onOpen" destroy-on-close>
    <div class="tetris-dialog-body">
      <div class="inner">
        <div class="tip">{{ logoutTip}}</div>

        <div class="tetris-dialog-footer">
          <div class="btn btn-primary" @click="onConfirm">{{ confirm }}</div>
          <div class="btn btn-cancel" @click="onCancel">{{ cancel }}</div>
        </div>

      </div>
    </div>

  </el-dialog>

</template>

<script>
  import { i18n, lan } from '@/unit/const'
  import states from '@/control/states'

  export default {
    name: "Dialog",
    data () {
      return {
        visible: this.show,
      }
    },
    props: {
      show: {
        type: Boolean,
        default: false
      },
      ctype: {
        type: String,
        default: "",
      },
    },
    created () {
    },
    watch: {
      show (val) {
        this.visible = this.show;
      },
    },
    computed: {
      logoutTip () {
        return i18n.logoutTip[lan];
      },
      confirm () {
        return i18n.confirm[lan];
      },
      close () {
        return i18n.close[lan];
      },
      cancel () {
        return i18n.cancel[lan];
      },
    },
    methods: {
      onConfirm () {
        this.$emit("confirm");
      },
      onCancel () {
        this.$emit('close');
      },
      closed () {
      },
      onOpen () {
      },
    },
  }

</script>
<style lang="less" scoped>
  .tetris-dialog-footer {
    display: flex;
    justify-content: center;
  }
</style>
