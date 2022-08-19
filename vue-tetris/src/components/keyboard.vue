<template>
<div class="keyboard" :style="'margin-top:'+fillingNum+'px'" >
  <vbutton
    color="gray"
    size="s1"
    :top="0"
    :left="374"
    :label="rotation"
    arrow="translate(0, 63px)"
    :position="true"
    :active="keyboard['rotate']"
    ref="dom_rotate"
  />
  <vbutton
    color="gray"
    size="s1"
    :top="180"
    :left="374"
    :label="labelDown"
    arrow="translate(0,-71px) rotate(180deg)"
    :active="keyboard['down']"
    ref="dom_down"
  />
  <vbutton
    color="gray"
    size="s1"
    :top="90"
    :left="284"
    :label="labelLeft"
    arrow="translate(60px, -12px) rotate(270deg)"
    :active="keyboard['left']"
    ref="dom_left"
  />
  <vbutton
    color="gray"
    size="s1"
    :top='90'
    :left='464'
    :label="labelRight"
    arrow="translate(-60px, -12px) rotate(90deg)"
    :active="keyboard['right']"
    ref="dom_right"
  />
  <vbutton
    color="gray"
    size="s0"
    :top="100"
    :left="52"
    :label="labelDropSpace"
    :active="keyboard['drop']"
    ref="dom_space"
  />

  <vbutton
    color="green"
    size="s2"
    :top="0"
    :left="196"
    :label="labelInfoI"
    :active="keyboard['info']"
    @click="showInfo"
  />
  <vbutton
    color="yellow"
    size="s2"
    :top="0"
    :left="106"
    :label="labelSoundS"
    :active="keyboard['music']"
    ref="dom_s"
  />
  <vbutton
    color="red"
    size="s2"
    :top="0"
    :left="16"
    :label="labelPauseP"
    :active="keyboard['pause']"
    ref="dom_p"
  />
  <info-dialog
    :show="showInfoDialog"
    @close="showInfoDialog = false"
  >
  </info-dialog>
</div>

</template>
<script>
import infoDialog from '@/components/infoDialog';
import Vbutton from './button'
import { i18n, lan } from '@/unit/const'
import store from '@/store'
import todo from '@/control/todo'
export default {
  props: ['filling'],
  data() {
    return {
      fillingNum: 0,
      showInfoDialog: false,
    }
  },
  watch: {
    $props: {
      deep: true,
      handler(nextProps) {
        this.fillingNum = nextProps.filling + 20
      }
    }
  },
  computed: {
    keyboard() {
      return this.$store.state.keyboard
    },
    rotation: () => i18n.rotation[lan],
    labelLeft: () => i18n.left[lan],
    labelRight: () => i18n.right[lan],
    labelDown: () => i18n.down[lan],
    labelDropSpace: () => `${i18n.drop[lan]} (SPACE)`,
    labelInfoI: () => `${i18n.info[lan]}(I)`,
    labelSoundS: () => `${i18n.sound[lan]}(S)`,
    labelPauseP: () => `${i18n.pause[lan]}(P)`
  },
  mounted() {
    const touchEventCatch = {}

    const mouseDownEventCatch = {}
    /*
    document.addEventListener(
      'touchstart',
      e => {
        if (e.preventDefault) {
          e.preventDefault()
        }
      },
      true
    )
    document.addEventListener('touchend', (e) => {
      if (e.preventDefault) {
        e.preventDefault();
      }
    }, true);
    */

    // 阻止双指放大
    document.addEventListener('gesturestart', (event) => {
      event.preventDefault();
    });
    
    document.addEventListener(
      'mousedown',
      e => {
        if (e.preventDefault) {
          e.preventDefault()
        }
      },
      true
    )
    Object.keys(todo).forEach(key => {
      this.$refs[`dom_${key}`].$el.addEventListener(
        'mousedown',
        () => {
          if (touchEventCatch[key] === true) {
            return
          }
          todo[key].down(store)
          mouseDownEventCatch[key] = true
        },
        true
      )
      this.$refs[`dom_${key}`].$el.addEventListener(
        'mouseup',
        () => {
          if (touchEventCatch[key] === true) {
            touchEventCatch[key] = false
            return
          }
          todo[key].up(store)
          mouseDownEventCatch[key] = false
        },
        true
      )
      this.$refs[`dom_${key}`].$el.addEventListener(
        'mouseout',
        () => {
          if (mouseDownEventCatch[key] === true) {
            todo[key].up(store)
          }
        },
        true
      )
      this.$refs[`dom_${key}`].$el.addEventListener(
        'touchstart',
        () => {
          touchEventCatch[key] = true
          todo[key].down(store)
        },
        true
      )
      this.$refs[`dom_${key}`].$el.addEventListener(
        'touchend',
        () => {
          todo[key].up(store)
        },
        true
      )
    })
  },
  components: {
    Vbutton,
    infoDialog,
  },
  methods: {
    showInfo(){
      this.showInfoDialog = true;
    },
  },
}
</script>
<style lang="less">
.keyboard {
    width: 580px;
    height: 330px;
    margin: 20px auto 0;
    position: relative;
}
</style>
