package ai.kaira.data.utils

import ai.kaira.data.BuildConfig.FLAVOR
class APIConfig {

   companion object{
       fun getBaseUrl():String{
           return when (FLAVOR) {
               "staging" -> {
                   "https://api.staging.kaira.ai/"
               }
               "production" -> {
                   "https://api.kaira.ai/"
               }
               else -> {
                   "http://localhost:8080"
               }
           }
       }

       fun getAPIVersion():String{
           return "v1"
       }

       fun getAPIKey():String{
           return when (FLAVOR) {
               "staging" -> {
                   "a327ca7f-c00f-4fc5-b26f-3337e14b27c5"
               }
               "production" -> {
                   "c7baeacf-ea34-4aab-8fc0-40f545376979"
               }
               else -> {
                   "e65f211b-cba1-4203-b246-41673c06529a"
               }
           }
       }
   }

}