<template>
  <div class="decorate">
    <div class="topBorder">
      <span class="l mr" style="width:40px;" />
      <span class="l mr" />
      <span class="l mr" />
      <span class="l mr" />
      <span class="l mr" />
      <span class="r ml" style="width:40px;" />
      <span class="r ml" />
      <span class="r ml" />
      <span class="r ml" />
      <span class="r ml" />
    </div>
    <h1>{{title}}</h1>
    <div class="view">
      <div class="nft-box" :class="nftDict.S ? 'active' : ''" @click="useBlock('S')">
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <em></em>
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>

      <div class="nft-box" :class="nftDict.T ? 'active' : ''" @click="useBlock('T')">
        <em></em>
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <em></em>
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>

      <div class="nft-box" :class="nftDict.O ? 'active' : ''" @click="useBlock('O')">
        <b class="c" />
        <b class="c" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>

      <div class="nft-box" :class="nftDict.T ? 'active' : ''" @click="useBlock('T')">
        <em></em>
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <em></em>
        <b class="c" />
        <div class="clear" />
 
        <!--
        <em></em>
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
        -->
      </div>

      <p/>

      <div class="nft-box" :class="nftDict.J ? 'active' : ''" @click="useBlock('J')">
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>

      <div class="nft-box" :class="nftDict.I ? 'active' : ''" @click="useBlock('I')">
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
        <div class="clear" />
      </div>
    </div>

    <div class="view l">
      <div class="nft-box" :class="nftDict.Z ? 'active' : ''" @click="useBlock('Z')">
        <em></em>
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>

      <div class="nft-box" :class="nftDict.T ? 'active' : ''" @click="useBlock('T')">
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>
      <div class="nft-box" :class="nftDict.O ? 'active' : ''" @click="useBlock('O')">
        <b class="c" />
        <b class="c" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>
      <div class="nft-box" :class="nftDict.T ? 'active' : ''" @click="useBlock('T')">
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>
      <div class="nft-box" :class="nftDict.L ? 'active' : ''" @click="useBlock('L')">
        <b class="c" />
        <b class="c" />
        <div class="clear" />
        <em></em>
        <b class="c" />
        <div class="clear" />
        <em></em>
        <b class="c" />
        <div class="clear" />
      </div>

      <p/>
      <div class="nft-box" :class="nftDict.I ? 'active' : ''" @click="useBlock('I')">
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
        <b class="c" />
        <div class="clear" />
      </div>
    </div>
  </div>

</template>
<script>
import { i18n, lan } from '@/unit/const'
export default {
  name: 'Decorate',
  computed: {
    status() {
      return this.$store.state.status;
    },
    nftList() {
      return this.$store.state.nftList;
    },
    title: () => i18n.title[lan],
    github: () => i18n.github[lan],
    QRTitle: () => i18n.QRNotice[lan],
    QRCode: () => i18n.QRCode[lan],
    nftDict(){
      return this.$store.state.nftDict;
    },
  },
  created(){
    
  },
  methods: {
    useBlock(block){
      let nft = this.nftDict[block];
      if(!nft) return;
      if(this.status == 'start'){
        this.$store.dispatch('nftUse', nft).then(res=>{
          this.$store.commit('nextBlock', block)
        })
      }
    },
  }
}
</script>
<style lang="less">
.decorate {
  h1 {
    position: absolute;
    width: 100%;
    text-align: center;
    font-weight: normal;
    top: -12px;
    left: 0;
    margin: 0;
    padding: 0;
    font-size: 30px;
  }
  .topBorder {
    position: absolute;
    height: 10px;
    width: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    overflow: hidden;
    span {
      display: block;
      width: 10px;
      height: 10px;
      overflow: hidden;
      background: #000;
      &.mr {
        margin-right: 10px;
      }
      &.ml {
        margin-left: 10px;
      }
    }
  }
  .view {
    position: absolute;
    right: -70px;
    top: 20px;
    width: 44px;
    em {
      display: block;
      width: 22px;
      height: 22px;
      overflow: hidden;
      float: left;
    }
    p {
      height: 22px;
      clear: both;
    }
    &.l {
      right: auto;
      left: -70px;
    }
  }
}


</style>
