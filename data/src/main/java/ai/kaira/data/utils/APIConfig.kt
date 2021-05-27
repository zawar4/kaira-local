package ai.kaira.data.utils

import ai.kaira.data.BuildConfig.FLAVOR

class APIConfig {

   companion object{
       fun getBaseUrl():String{
           return when (FLAVOR) {
               "staging" -> {
                   "https://api.staging.kaira.ai/"
               }
               "development" -> {
                   "https://api.dev.kaira.ai/"
               }
               "production" -> {
                   "https://api.kaira.ai/"
               }

               else -> {
                   "http://localhost:8080"
               }
           }
       }

       fun getAIBaseUrl():String{
           return when (FLAVOR) {
               "staging" -> {
                   "https://staging.kaira-engine-api.com/"
               }
               "development" -> {
                   "https://dev.kaira-engine-api.com/"
               }
               "production" -> {
                   "https://kaira-engine-api.com/"
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
               "development" -> {
                   "e8bf2af8-9d45-11eb-b79d-9fd1bd5a80f7"
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