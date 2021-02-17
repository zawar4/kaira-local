package ai.kaira.app.database

import ai.kaira.data.introduction.datasource.database.dao.UserDao
import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class DataBaseModule {


    @Provides
    @Singleton
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KairaDatabase{
        return KairaDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(kairaDatabase: KairaDatabase):UserDao{
        return kairaDatabase.userDao()
    }
}