package dev.remaker.sketchubx

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.remaker.sketchubx.core.os.NetworkState
import dev.remaker.sketchubx.core.util.ext.connectivityManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    companion object {

        @Provides
        @Singleton
        fun provideNetworkState(
            @ApplicationContext context: Context
        ) = NetworkState(context.connectivityManager)
    }
}