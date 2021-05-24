package ai.kaira.app.utils

import ai.kaira.data.BuildConfig

class Configuration {

    companion object {

        fun getAppCenterKey():String{
            return when (BuildConfig.FLAVOR) {
                "staging" -> {
                    "2bede2a7-8a47-4a47-a186-6f15fbd7549e"
                }
                "development" -> {
                    "e03aa8b0-aeb6-4b98-aa7e-119b0b7c4dfb"
                }
                "production" -> {
                    "292863de-eb17-46e7-b2e1-e3bf6240372e"
                }
                else -> {
                    "e03aa8b0-aeb6-4b98-aa7e-119b0b7c4dfb"
                }
            }
        }
    }
}