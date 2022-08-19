import { createRouter, createWebHistory } from "vue-router";
import Home from '@/views/Home';
import Login from '@/views/Login';


export const routers = [{
  path: "/",
  component: Home,
  auth: true,
},{
  path: '/login',
  component: Login,
}];


const router = createRouter({
  scrollBehavior(){
    document.getElementById('app').scrollIntoView();
  },
  history: createWebHistory(),
  routes: routers
});

export default router;
