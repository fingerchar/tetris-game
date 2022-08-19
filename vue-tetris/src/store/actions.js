"use strict";
import api from "@/api/index";
import { getToken, setToken, removeToken } from "@/control/auth";
import router from "@/router";
import utils from "@/control/utils";
export default {
  login({ commit, dispatch }, data) {
    return new Promise((resolve) => {
      api("user.login", data).then((res) => {
        if (utils.checkResponse(res)) {
          commit("LOGIN", res.data);
          setToken(res.data.token);
          dispatch("heartbeat");
        }
        resolve(res);
      });
    });
  },
  reload({ commit, dispatch }) {
    var token = getToken();
    if (!token) return;
    return new Promise((resolve) => {
      let data = { token: token };
      api("user.reload", data).then((res) => {
        if (utils.checkResponse(res)) {
          commit("LOGIN", res.data);
          setToken(res.data.token);
          dispatch("heartbeat");
        } else {
          dispatch("logout");
        }
        resolve(res);
      });
    });
  },
  getBalance({ state, commit }) {
    return new Promise((resolve) => {
      let config = state.config;
      if (!config.payToken) return resolve();

      let data = { token: config.payToken.address };
      api("user.balance", data).then((res) => {
        commit("GET_BALANCE", res.data);
        resolve(res);
      });
    });
  },
  gameDetail({ commit }) {
    return new Promise((resolve) => {
      api("game.detail").then((res) => {
        if (utils.checkResponse(res)) {
          commit("GAME_DETAIL", res.data);
        }
        resolve(res);
      });
    });
  },
  heartbeat({ state, commit, dispatch }) {
    if (state.timer) {
      clearTimeout(state.timer);
      commit("TIMER", null);
    }
    if (!state.login) return;
    return new Promise((resolve) => {
      dispatch("getBalance");
      dispatch("nftList");
      var timer = setTimeout(() => {
        dispatch("heartbeat");
      }, 20000);
      commit("TIMER", timer);
      resolve();
    });
  },
  nftList({ commit }) {
    return new Promise((resolve) => {
      let data = { page: 1, limit: 100 };

      api("nft.list", data).then((res) => {
        commit("NFT_LIST", res.data.list);
        resolve(res);
      });
    });
  },
  nftUse({ state }, nft) {
    return new Promise((resolve, reject) => {
      let data = {
        balanceToken: state.config.payToken,
        address: state.userInfo.address,
        tokenId: nft.tokenId,
      };
      api("nft.use", data).then((res) => {
        if (utils.checkResponse(res)) {
          state.userInfo.amount =
            state.userInfo.amount - state.config.NftUseFee[nft.block];
          resolve(res);
        } else {
          reject(res.errmsg);
        }
      });
    });
  },
  gameStart({ commit }) {
    return new Promise((resolve) => {
      api("game.start").then((res) => {
        if (utils.checkResponse(res)) {
          commit("GAME_START", res);
        } else {
          utils.message(res.errmsg);
        }
        resolve(res);
      });
    });
  },
  gamePush({ state}) {
    var data = {
      speedStart: state.speedStart,
      clearLines: state.clearLines,
      startLines: state.startLines,
      points: state.points,
    };
    return new Promise((resolve) => {
      api("game.push", data).then((res) => {
        resolve(res);
      });
    });
  },
  gameClose({ state, commit }) {
    var data = {
      token: state.config.token,
      score: state.points,
    };
    return new Promise((resolve) => {
      state.status = "end";
      api("game.finish", data).then((res) => {
        var payload = {
          points: data.score,
          reward: res.data,
        };
        commit("GAME_CLOSE", payload);
        commit("SHOW_DIALOG", {
          show: true,
          type: "close",
        });
        resolve(res);
      });
    });
  },
  logout({ commit }) {
    commit("LOGOUT");
    router.push("/login");
    removeToken();
  },
};
