export default{
    // sessionStorage 基本操作
    getItem_session(key){
        return JSON.parse(window.sessionStorage.getItem(key) || '[]')
    },
    setItem_session(key, value){
        window.sessionStorage.setItem(key, JSON.stringify(value))
    },
    removeItem_session(key){
        window.sessionStorage.removeItem(key)
    },
    clear_session(){
        window.sessionStorage.clear()
    },

    // localStorage 基本操作
    getItem_local(key){
        return JSON.parse(window.localStorage.getItem(key) || '[]')
    },
    setItem_local(key, value){
        window.localStorage.setItem(key, JSON.stringify(value))
    },
    removeItem_local(key){
        window.localStorage.removeItem(key)
    },
    clear_local(){
        window.localStorage.clear()
    },
}