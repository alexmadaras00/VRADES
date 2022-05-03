package com.example.vrades.di

import com.example.vrades.utils.Constants.EMOTIONS_REF
import com.example.vrades.utils.Constants.USERS_REF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseRealtime(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    @Named(USERS_REF)
    fun provideUsersRef(db: FirebaseDatabase): DatabaseReference = db.reference.child(USERS_REF)

    @Singleton
    @Provides
    @Named(EMOTIONS_REF) // differentiate
    fun provideEmotionsRef(db: FirebaseDatabase): DatabaseReference = db.reference.child(
        EMOTIONS_REF)
}