<template>
  <div class="block-box">
    <template v-if="btype=='S'">
      <b class="c" />
      <div class="clear" />
      <b class="c" />
      <b class="c" />
      <div class="clear" />
      <em></em>
      <b class="c" />
    </template>
    <template v-else-if="btype=='T' && position == 'right'">
      <em></em>
      <b class="c" />
      <div class="clear" />
      <b class="c" />
      <b class="c" />
      <div class="clear" />
      <em></em>
      <b class="c" />
    </template>
    <template v-else-if="btype=='T'">
      <b class="c" />
      <div class="clear" />
      <b class="c" />
      <b class="c" />
      <div class="clear" />
      <b class="c" />
      <div class="clear" />
    </template>
    <template v-else-if="btype=='O'">
      <b class="c" />
      <b class="c" />
      <b class="c" />
      <b class="c" />
    </template>
    <template v-else-if="btype=='J'">
      <b class="c" />
      <b class="c" />
      <div class="clear" />
      <b class="c" />
      <div class="clear" />
      <b class="c" />
    </template>
    <template v-else-if="btype=='I'">
      <em></em>
      <b class="c" />
      <div class="clear" />
      <em></em>
      <b class="c" />
      <div class="clear" />
      <em></em>
      <b class="c" />
      <div class="clear" />
      <em></em>
      <b class="c" />
    </template>
    <template v-else-if="btype=='Z'">
      <em></em>
      <b class="c" />
      <div class="clear" />
      <b class="c" />
      <b class="c" />
      <div class="clear" />
      <b class="c" />
    </template>
    <template v-else-if="btype=='L'">
      <b class="c" />
      <b class="c" />
      <div class="clear" />
      <em></em>
      <b class="c" />
      <div class="clear" />
      <em></em>
      <b class="c" />
      <div class="clear" />
    </template>

    <div class="clear" />
  </div>
</template>
<script>
export default {
  name: "Block",
  props: {
    btype: {
      type: String,
      default: "",
    },
    position: {
      type: String,
      default: "",
    },
  },
}
</script>
<style lang="less">

</style>

