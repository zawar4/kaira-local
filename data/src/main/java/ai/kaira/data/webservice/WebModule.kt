package ai.kaira.data.webservice

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class WebModule {

    @Provides
    fun providesKairaRestApiRouter():KairaApiRouter{
        return KairaRetrofit.getKairaRouter()
    }

    @Provides
    fun providesAIRestApiRouter():AIApiRouter {
        return AIRetrofit.getAIRouter()
    }
}