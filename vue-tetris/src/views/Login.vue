<template>
  <div class="connect">
    <div class="connect-section">
      <div class="connect-logo">
        <img src="@/assets/logo.png" alt="">
      </div>
      <div class="tip">{{ connectTip }}</div>
      <div class="connect-link" @click="connect">
        {{ connectText }}
      </div>
    </div>
  </div>
</template>

<script>
  import { i18n, lan } from '@/unit/const'

  export default {
    name: "Login",
    components: {},
    data () {
      var info = this.$route.query;
      return {
        info: info,
      }
    },
    computed: {
      connectTip: () => i18n.connectTip[lan],
      connectText: () => i18n.connect[lan],
      config () {
        return this.$store.state.config;
      }
    },
    mounted () {
      var query = this.$route.query;
      if (this.info.code) {
        this.login();
      }
    },
    methods: {
      connect () {
        if (!this.config.appId) {
          this.$utils.message("appId is empty", "error");
          return;
        }
        var url = this.config.redirectUri;
        var redirect_uri = window.location.origin + '/login';
        window.location.href = url +
          "?client_id=" + this.config.appId +
          "&response_type=" + this.config.responseType +
          "&product_id=" + this.config.productId +
          "&redirect_uri=" + redirect_uri;
      },
      login () {
        var data = {
          code: this.info.code,
        }
        this.$store.dispatch('login', data).then(res => {
          if (this.$utils.checkResponse(res)) {
            this.$store.state.userInfo = res.data
            this.$router.push('/')
          } else {
            this.$utils.message(res.errmsg);
          }
        })
      },
    },
  }

</script>

<style lang="less">
  .connect-section {
    padding: 40px;
    margin: 0 auto;
    margin-top: 30%;
    max-width: 600px;
    width: 100%;
    font-size: 16px;
    background: #3d3740;
    border-radius: 10px;

    .connect-logo {
      width: 120px;
      margin: 0 auto;
      img {
        width: 100%;
      }
    }

    .tip {
      color: #fff;
      text-align: center;
      padding: 30px;
    }
    .connect-link {
      cursor: pointer;
      padding: 10px 0;
      background: #177b2a;
      border-radius: 5px;
      color: #fff;
      margin: 0 auto;
      width: 250px;
      text-align: center;
    }
  }
</style>
