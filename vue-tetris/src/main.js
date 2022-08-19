import { createApp } from 'vue'
import App from './App.vue'
import 'babel-polyfill'
import store from "./store";
import router from "./router";
import api from "@/api/index.js";
import utils from '@/control/utils.js';
import less from 'less'

const app = createApp(App) // 创建实例
app.config.globalProperties.$api = api;

import './permission'
import 'element-plus/dist/index.css';
import ElementPlus from "element-plus"

import '@/unit/const';
import '@/control';
import { subscribeRecord } from '@/unit';
subscribeRecord(store); // 将更新的状态记录到localStorage
import '@/styles/index.less';


app.config.globalProperties.$utils = utils;


app.use(ElementPlus);

app
  .use(less)
  .use(store)
  .use(router)
  .mount("#app");

export default app;
