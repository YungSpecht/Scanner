package data.textproc

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MLKitModule {

    @Provides
    fun provideMLKitProcessor(): MLKitProcessor {
        return MLKitProcessor()
    }

}