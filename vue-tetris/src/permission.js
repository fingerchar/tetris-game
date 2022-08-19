import router from "./router";
import { getToken } from "@/control/auth";


const whiteList = ["/login"]; // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  const hasToken = getToken();
 
  if(hasToken){
    if(whiteList.indexOf(to.path) !== -1){
      next({
        path: "/",
        replace: true,
      });
    }else{
      next();
    }
  }else{
    if(whiteList.indexOf(to.path) !== -1){
      next();
    }else{
      next({
        path: '/login',
        query: {
          ...to.query,
        },
        replace: true,
      });
    }
  }
});

