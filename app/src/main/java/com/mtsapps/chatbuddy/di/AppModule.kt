package com.mtsapps.chatbuddy.di

import android.content.Context
import androidx.room.Room
import com.mtsapps.chatbuddy.datasource.LocalDataSource
import com.mtsapps.chatbuddy.datasource.RemoteDataSource
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.repository.Repository
import com.mtsapps.chatbuddy.retrofit.ApiUtils
import com.mtsapps.chatbuddy.retrofit.OpenAIApi
import com.mtsapps.chatbuddy.room.ChatDao
import com.mtsapps.chatbuddy.room.ChatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /*@Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context){

    }*/

    @Provides
    @Singleton
    fun provideChatDao(@ApplicationContext context: Context) : ChatDao{
        val database : ChatDatabase by lazy {
            Room.databaseBuilder(context, ChatDatabase::class.java, "ChatDatabase").build()
        }
        return database.chatDao()

    }

    @Provides
    @Singleton
    fun provideApiHelper(): OpenAIApi {
        return ApiUtils.getOpenAIApi()
    }

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource,localDataSource: LocalDataSource): Repository {
        return Repository(remoteDataSource,localDataSource)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(chatDao: ChatDao) : LocalDataSource{
        return LocalDataSource(chatDao)
    }


    @Provides
    @Singleton
    fun provideRemoteDataSource(apiHelper: OpenAIApi): RemoteDataSource {
        return RemoteDataSource(apiHelper)
    }

    /* @Provides
     @Singleton
     fun provideMessagesDao(@ApplicationContext context: Context): MessagesDao {
         val db = Room.databaseBuilder(context, ChatGPTDataBase::class.java, "chatgpt_db.sqlite")
             .createFromAsset("chatgpt_db.sqlite").build()
         return db.getMessagesDao()
     }*/

}