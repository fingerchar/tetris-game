"use strict";
import { getNextType } from "@/unit";
import Block from "@/unit/block";

export default {
  SHOW_DIALOG(state, payload) {
    state.showDialog = payload.show;
    state.dialogType = payload.type;
  },
  LOGIN(state, data) {
    state.token = data.token;
    state.userInfo = {
      nickname: data.user.nickname,
      balance: data.user.balance,
      email: data.user.email,
      address: data.user.address,
      avatar: data.user.avatar,
    };
    state.login = true;
  },
  LOGOUT(state) {
    state.userInfo = {};
    state.token = null;
    state.login = false;
  },
  GAME_DETAIL(state, payload) {
    state.config = Object.assign({}, payload);
  },
  TIMER(state, timer) {
    state.timer = timer;
  },
  NFT_LIST(state, nftList) {
    var nftDict = {};
    for (var i = 0; i < nftList.length; i++) {
      var nft = nftList[i];
      var metaContent = JSON.parse(nft.metaContent);
      var attributes = metaContent.attributes;
      if (!attributes) continue;
      for (var j = 0; j < attributes.length; j++) {
        var attribute = attributes[j];
        if (attribute.key == "Block") {
          nftDict[attribute.value] = nft;
        }
      }
    }
    state.nftDict = nftDict;
  },
  RELOAD(state, userInfo) {
    state.userInfo = {
      username: userInfo.username ? userInfo.username : state.userInfo.username,
      balance: userInfo.balance ? userInfo.balance : state.userInfo.balance,
      email: userInfo.email ? userInfo.email : state.userInfo.email,
      address: userInfo.address ? userInfo.address : state.userInfo.address,
    };
    state.login = true;
  },
  GAME_START(state){
    state.gameInfo = {};
  },
  GAME_CLOSE(state, payload) {
    state.gameInfo = Object.assign({}, state.gameInfo, {
      reward: payload.reward,
      points: payload.points,
    });
  },
  GET_BALANCE(state, payload) {
    if (!payload) {
      state.userInfo = Object.assign({}, state.userInfo, {
        amount: 0,
        lock_amount: 0,
      });
    } else {
      state.userInfo = Object.assign({}, state.userInfo, {
        amount: payload.amount,
        lock_amount: payload.lock_amount,
      });
    }
  },
  nextBlock(state, data) {
    if (!data) {
      data = getNextType();
    }
    state.next = data;
  },
  moveBlock(state, data) {
    state.cur = data.reset === true ? null : new Block(data);
  },
  speedStart(state, data) {
    state.speedStart = data;
  },
  speedRun(state, data) {
    state.speedRun = data;
  },
  startLines(state, data) {
    state.startLines = data;
  },
  matrix(state, data) {
    state.matrix = data;
  },
  lock(state, data) {
    state.lock = data;
  },
  clearLines(state, data) {
    state.clearLines = data;
  },
  points(state, data) {
    state.points = data;
  },
  max(state, data) {
    state.max = data;
  },
  reset(state, data) {
    state.reset = data;
  },
  drop(state, data) {
    state.drop = data;
  },
  pause(state, data) {
    console.log(data);
    state.pause = data;
  },
  music(state, data) {
    state.music = data;
  },
  focus(state, data) {
    state.focus = data;
  },
  key_drop(state, data) {
    state.keyboard["drop"] = data;
  },
  key_down(state, data) {
    state.keyboard["down"] = data;
  },
  key_left(state, data) {
    state.keyboard["left"] = data;
  },
  key_right(state, data) {
    state.keyboard["right"] = data;
  },
  key_rotate(state, data) {
    state.keyboard["rotate"] = data;
  },
  key_reset(state, data) {
    state.keyboard["reset"] = data;
  },
  key_music(state, data) {
    state.keyboard["music"] = data;
  },
  key_pause(state, data) {
    state.keyboard["pause"] = data;
  },
  key_info(state, data) {
    state.keyboard["info"] = data;
  },
};
