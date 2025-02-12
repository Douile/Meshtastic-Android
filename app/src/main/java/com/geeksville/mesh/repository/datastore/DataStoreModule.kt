package com.geeksville.mesh.repository.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.geeksville.mesh.LocalOnlyProtos.LocalConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideLocalConfigDataStore(@ApplicationContext appContext: Context): DataStore<LocalConfig> {
        return DataStoreFactory.create(
            serializer = LocalConfigSerializer,
            produceFile = { appContext.dataStoreFile("local_config.pb") },
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { LocalConfig.getDefaultInstance() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}
