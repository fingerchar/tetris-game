<template>
  <el-dialog :model-value="visible" :show-close="false" :close-on-click-modal="false" :custom-class="'tetris-dialog'" @close="$emit('close')" @closed="closed" @open="onOpen" destroy-on-close>
    <div class="tetris-dialog-body">
      <div class="inner">
        <template v-if="ctype == 'start' ">
          <div class="tip">{{ startTip }}</div>
          <div class="count">
            {{ config.PlayFee}} {{ config.payToken.symbol }}
          </div>
        </template>
        <template v-else-if="ctype == 'close'">
          <div class="tip">{{ closeTip }}</div>
          <div class="count">{{ gameInfo.points }} {{ point }}</div>
          <div class="tip">{{ reward }}</div>
          <div class="count red">
            {{ gameInfo.reward }} {{ config.payToken.symbol }}
          </div>
        </template>
        <template v-else-if="ctype == 'useNft'">
          <div class="tip">{{ useNftTip }} ( <span class="t-underline" @click="goPaytoken">{{getSymbol}}{{ config.payToken.symbol }}</span> )</div>
        </template>
        <template v-else>
          <div class="tip">{{ logoutTip}}</div>
        </template>

        <div class="tetris-dialog-footer" v-if="ctype != 'close' && ctype != 'useNft'">
          <div class="btn btn-primary" @click="onConfirm">{{ confirm }}</div>
          <div class="btn btn-cancel" @click="onCancel">{{ cancel }}</div>
        </div>

        <div class="tetris-dialog-footer" v-else>
          <div class="btn btn-cancel" @click="onCancel">{{ close }}</div>
        </div>
        <div v-if="startErr" class="errmsg">{{startErr}}(<span class="t-underline" @click="goPaytoken">{{getSymbol}}{{ config.payToken.symbol }}</span>)</div>
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
        startErr: ''
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
      startTip () {
        return i18n.startTip[lan];
      },
      closeTip () {
        return i18n.closeTip[lan];
      },
      point () {
        return i18n.point[lan];
      },
      reward () {
        return i18n.reward[lan];
      },
      logoutTip () {
        return i18n.logoutTip[lan];
      },
      useNftTip () {
        return i18n.useNftTip[lan];
      },
      getSymbol () {
        return i18n.getSymbol[lan];
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
      config () {
        return this.$store.state.config
      },
      gameInfo () {
        return this.$store.state.gameInfo;
      },
      paytoken () {
        let address = this.$store.state.config.token;
        let tokens = this.$store.state.paytokens;
        for (let i in tokens) {
          if (address == tokens[i].address) {
            return tokens[i]
          }
        }
        return {}
      }
    },
    methods: {
      onConfirm () {
        if (this.ctype == 'start') {
          let data = {
            token: this.paytoken.address
          }
          this.$api('game.start', data).then(res => {
            if (res.errno == 0) {
              states.start();
              this.$store.state.status = 'start'
              this.$emit('close');
            } else {
              this.startErr = res.errmsg
            }
          })
        } else if (this.ctype == "close") {
          this.$emit('close');
        } else if (this.ctype == "logout") {
          this.$store.dispatch("logout");
          this.$emit('close');
        }
      },
      onCancel () {
        this.startErr = ""
        this.$emit('close');
        if (this.ctype == "useNft") {
          states.pause(false)
        }
      },
      closed () {
      },
      onOpen () {
      },
      goPaytoken () {
        window.open('https://fingernft.fingerchar.com/pools')
      }
    },
  }

</script>
<style lang="less" scoped>
  .tetris-dialog-footer {
    display: flex;
    justify-content: center;
  }
  .errmsg {
    color: #b23b38;
    line-height: 36px;
  }
  .t-underline {
    cursor: pointer;
    line-height: 36px;
    text-decoration: underline;
  }
  .t-underline:hover {
    color: #b23b38;
  }
</style>
