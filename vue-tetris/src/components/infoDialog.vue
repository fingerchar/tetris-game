<template>
  <el-dialog :model-value="visible" :show-close="false" :close-on-click-modal="false" :custom-class="'tetris-dialog'" @close="$emit('close')" @closed="closed" @open="onOpen">
    <template #title>
      <div class="tetris-dialog-header">
        <div class="left">{{title}}</div>
        <span class="right">
          <img @click="$emit('close')" class="close-img" src="@/assets/close.png ">
        </span>
      </div>

    </template>
    <div class="tetris-dialog-body">
      <div class="inner">
        <div class="avatar" v-if="userInfo.avatar">
          <img :src="userInfo.avatar" alt="">
        </div>
        <div class="username" v-if="userInfo.nickname">
          {{ userInfo.nickname }}
        </div>
        <div class="email">
          {{ userInfo.email }}
        </div>
        <div class="balance">
          {{balance}}: <span class="value">{{ userInfo.amount ? userInfo.amount : 0 }}</span>
          {{ config.payToken.symbol }}
        </div>
        <div class="balance" v-if="userInfo.lock_amount">
          {{frozen}}: <span class="value">{{ userInfo.lock_amount }}</span>
          {{ config.payToken.symbol }}
        </div>
        <div class="get-paytoken" @click="goPaytoken">{{getSymbol}}{{ config.payToken.symbol }}</div>
        <div class="logout" @click="logoutDialog = true">
          {{logout}}
        </div>

        <div class="rules">
          <div class="rule">
            <div class="tip">开始游戏花费: {{ config.PlayFee }}</div>
          </div>
          <div class="title">奖励规则</div>
          <div class="rule" v-for="(rate,i) in config.scoreRate" :key="i" :index="i">
            <div class="tip">{{ rate.maxScore }} Point : {{ rate.amount }} {{ config.payToken.symbol}}</div>
          </div>
          <div class="title">使用道具</div>
          <div class="block-rule" v-for="(block, i) in blocks" :key="i" :index="i">
            <div class="m-right-50">
              <block class="fleft dialog-block-box" :btype="block"></block>
            </div>
            <div class="tip">{{ config.NftUseFee[block] }} {{ config.payToken.symbol}}</div>
            <div class="clear"></div>
          </div>
          <div class="clear"></div>
        </div>

      </div>
    </div>

    <logout-dialog :show="logoutDialog" @confirm="onLogout" @close="logoutDialog = false">
    </logout-dialog>

  </el-dialog>

</template>

<script>
  import { i18n, lan } from '@/unit/const'
  import states from '@/control/states'
  import block from '@/components/block';
  import logoutDialog from '@/components/logoutDialog';

  import { ElMessageBox } from 'element-plus'

  export default {
    name: "Dialog",
    components: { block, logoutDialog },
    data () {
      return {
        visible: this.show,
        blocks: ["S", "Z", "T", "O", "J", "L", "I"],
        logoutDialog: false,
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
      logout () {
        return i18n.logout[lan];
      },
      balance () {
        return i18n.balance[lan];
      },
      frozen () {
        return i18n.frozen[lan];
      },
      title () {
        return i18n.infoTitle[lan];
      },
      userInfo () {
        var userInfo = this.$store.state.userInfo;
        console.log("jjjjjjjjjjjjjj", userInfo);
        return userInfo;
      },
      startTip () {
        return i18n.startTip[lan];
      },
      logoutTip () {
        return i18n.logoutTip[lan];
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
    },
    methods: {
      onLogout () {
        console.log("onLogout");
        this.$store.dispatch("logout");
        this.logoutDialog = false;
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

  .avatar {
    width: 60px;
    height: 60px;
    margin: 0 auto 20px;
    border-radius: 60px;
    overflow: hidden;
    img {
      height: 100%;
      width: 100%;
    }
  }

  .username {
    font-size: 22px;
    font-weight: bold;
    margin-bottom: 20px;
    color: #b23b38;
  }
  .email {
    font-size: 22px;
    font-weight: bold;
    margin-bottom: 20px;
    color: #b23b38;
  }
  .fleft {
    float: left;
  }
  .title {
    font-size: 30px;
    font-weight: bold;
    margin: 20px auto;
  }

  .dialog-block-box {
    margin-bottom: 10px !important;
    margin-top: 0 !important;
  }

  .block-rule {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
  }
  .flex {
    dispatch: flex !important;
  }
  .align-center {
    align-items: center !important;
  }
  .justify-center {
    justify-content: center !important;
  }
  .flex1 {
    flex: 1 !important;
  }
  .m-right-50 {
    margin-right: 50px !important;
  }

  .balance {
    font-size: 22px;
    font-weight: bold;
    margin-bottom: 20px;
  }
  .get-paytoken {
    text-decoration: underline;
    cursor: pointer;
  }
  .get-paytoken:hover {
    color: #b23b38;
  }
  .logout {
    background: #b33a3a;
    font-size: 22px;
    font-weight: bold;
    padding: 20px 0;
    margin: 40px auto;
    cursor: pointer;
  }
</style>
