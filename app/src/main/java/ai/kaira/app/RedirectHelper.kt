package ai.kaira.app

class RedirectHelper {

    companion object{
        private var redirects = HashMap<String,String>()
        fun enableRedirect(from:String,to:String){
            redirects[from] = to
        }
        fun redirectExists(from:String,to:String):Boolean{
            if(redirects.containsKey(from) && redirects[from] == to){
                redirects.remove(from)
                return true
            }
            return false
        }
    }
}