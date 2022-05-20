package com.example.vrades.di

import com.example.vrades.utils.Constants.DATA_AUDIO_TEST_REF
import com.example.vrades.utils.Constants.DATA_WRITING_TEST_REF
import com.example.vrades.utils.Constants.EMOTIONS_REF
import com.example.vrades.utils.Constants.IMAGE_REF
import com.example.vrades.utils.Constants.LIFEHACKS_REF
import com.example.vrades.utils.Constants.USERS_REF
import com.example.vrades.utils.Constants.USER_NAME_REF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
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
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorageInstance(): FirebaseStorage = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    @Named(USERS_REF)
    fun provideUsersRef(db: FirebaseDatabase): DatabaseReference = db.reference.child(USERS_REF)

    @Singleton
    @Provides
    @Named(USER_NAME_REF)
    fun provideUsersNameRef(db: FirebaseDatabase): DatabaseReference = db.reference.child(USER_NAME_REF)

    @Singleton
    @Provides
    @Named(EMOTIONS_REF) // differentiate
    fun provideEmotionsRef(db: FirebaseDatabase): DatabaseReference = db.reference.child(
        EMOTIONS_REF)

    @Singleton
    @Provides
    @Named(LIFEHACKS_REF) // differentiate
    fun provideLifeHacksRef(db: FirebaseDatabase): DatabaseReference = db.reference.child(
        LIFEHACKS_REF)

    @Singleton
    @Provides
    @Named(DATA_AUDIO_TEST_REF) // differentiate
    fun provideDataAudioTest(db: FirebaseDatabase): DatabaseReference = db.reference.child(
        DATA_AUDIO_TEST_REF
    )

    @Singleton
    @Provides
    @Named(DATA_WRITING_TEST_REF) // differentiate
    fun provideDataWritingTest(db: FirebaseDatabase): DatabaseReference = db.reference.child(
        DATA_WRITING_TEST_REF
    )

    @Singleton
    @Provides
    @Named(IMAGE_REF)
    fun provideImagesRef(db: FirebaseDatabase): DatabaseReference = db.reference.child(IMAGE_REF)
}