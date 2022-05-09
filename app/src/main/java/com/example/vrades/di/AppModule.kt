package com.example.vrades.di

import android.app.Application
import android.content.Context
import com.example.vrades.firebase.data.repositories.VradesRepositoryImpl
import com.example.vrades.firebase.domain.use_cases.auth_repository.*
import com.example.vrades.firebase.domain.use_cases.profile_repository.GetLifeHacksByUserId
import com.example.vrades.firebase.domain.use_cases.profile_repository.GetTestsByUserId
import com.example.vrades.firebase.domain.use_cases.profile_repository.GetUserById
import com.example.vrades.firebase.domain.use_cases.profile_repository.ProfileUseCases
import com.example.vrades.firebase.domain.use_cases.vrades_repository.*
import com.example.vrades.firebase.repositories.auth.AuthRepository
import com.example.vrades.firebase.repositories.auth.AuthRepositoryImpl
import com.example.vrades.firebase.repositories.data.ProfileRepositoryImpl
import com.example.vrades.firebase.repositories.domain.ProfileRepository
import com.example.vrades.firebase.repositories.domain.VradesRepository
import com.example.vrades.utils.Constants.DATA_AUDIO_TEST_REF
import com.example.vrades.utils.Constants.DATA_WRITING_TEST_REF
import com.example.vrades.utils.Constants.EMOTIONS_REF
import com.example.vrades.utils.Constants.IMAGE_REF
import com.example.vrades.utils.Constants.LIFEHACKS_REF
import com.example.vrades.utils.Constants.USERS_REF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
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
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        @Named(USERS_REF) usersRef: DatabaseReference
    ): AuthRepository = AuthRepositoryImpl(auth, usersRef)

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        addRealtimeUser = AddRealtimeUser(repository),
        getAuthState = GetAuthState(repository),
        getUserProfile = GetUserProfile(repository),
        signInWithGoogle = SignInWithGoogle(repository),
        logOut = LogOut(repository),
        resetPassword = ResetPassword(repository),
        signInWithEmailAndPassword = SignInWithEmailAndPassword(repository),
        signUp = SignUp(repository),
        isAccountInAuth = IsAccountInAuth(repository),
        isUserAuthenticated = IsUserAuthenticated(repository)
    )

    @Singleton
    @Provides
    fun provideProfileRepository(
        auth: FirebaseAuth,
        @Named(USERS_REF) usersRef: DatabaseReference
    ): ProfileRepository = ProfileRepositoryImpl(auth, usersRef)

    @Singleton
    @Provides
    fun provideProfileUseCases(repository: ProfileRepository) = ProfileUseCases(
        getLifeHacksByUserId = GetLifeHacksByUserId(repository),
        getTestsByUserId = GetTestsByUserId(repository), getUserById = GetUserById(repository)
    )

    @Singleton
    @Provides
    fun provideVradesRepository(
        @Named(EMOTIONS_REF) emotionsRef: DatabaseReference,
        @Named(LIFEHACKS_REF) lifeHacksRef: DatabaseReference,
        @Named(DATA_AUDIO_TEST_REF) dataAudioTestRef: DatabaseReference,
        @Named(DATA_WRITING_TEST_REF) dataWritingTestRef: DatabaseReference,
        @Named(IMAGE_REF) imageRef: DatabaseReference
    ): VradesRepository = VradesRepositoryImpl(
        emotionsRef,
        lifeHacksRef,
        dataAudioTestRef,
        dataWritingTestRef,
        imageRef
    )

    @Singleton
    @Provides
    fun provideVradesUseCases(repository: VradesRepository) = VradesUseCases(
        getEmotions = GetEmotions(repository),
        getLifeHacks = GetLifeHacks(repository),
        getDataAudioTest = GetDataAudioTest(repository),
        getDataWritingTest = GetDataWritingTest(repository),
        getPictureByName = GetPictureByName(repository),
        getPictures = GetPictures(repository)
    )


}