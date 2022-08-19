import { ElMessage } from "element-plus";


export default {
  checkResponse(response){
    if(!response.errno) return true;
    return false;
  },
  message(message, type = "") {
    ElMessage.closeAll();
    ElMessage({
      showClose: false,
      message: message,
      type: type || "error",
      offset: 40,
      duration: 2000,
    });
  },

}
