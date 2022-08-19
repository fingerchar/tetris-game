
export default {
  user: {
    login: {
      url: "/user/login",
      method: "post",
    },
    reload: {
      url: "/user/reload",
      method: "post",
    },
    balance: {
      url: "/user/balance",
      method: "post",
    },
  },
  nft: {
    list: {
      url: "/nft/list",
      method: "post",
    },
    use: {
      url: "/nft/use",
      method: "post",
    },
  },
  game: {
    start: {
      url: "/game/start",
      method: "post",
    },
    push: {
      url: "/game/push",
      method: "post",
    },
    finish: {
      url: "/game/finish",
      method: "post",
    },
    detail: {
      url: "/game/detail",
      method: "post",
    }
  },
  paytoken: {
    list: {
      url: "/paytoken/list",
      method: "post",
    },
  }
}
