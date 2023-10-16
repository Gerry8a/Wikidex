package com.gochoa.wikidex.data.local

import android.content.Context
import androidx.room.Room
import com.gochoa.wikidex.utils.Dictionary.POKEMON_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, PokemonDatabase::class.java, POKEMON_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideTaskDao(db: PokemonDatabase) = db.pokemoDao()
}