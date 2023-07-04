import { MessageBox, Message } from 'element-ui'

export default {
    //确认框
    myconfirm(content) {
        return MessageBox.confirm(content, "系统提示", {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: "warning",
        })
    },
    //提示框
    mymessage(content) {
        if (content === 'cancel') {
            return Message.info("取消删除")
        } else {
            return Message.error("删除失败")
        }
    },

}

