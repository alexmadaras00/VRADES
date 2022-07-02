package com.example.vrades.di

import android.app.Application
import android.content.Context
import com.example.vrades.data.repositories.AuthRepositoryImpl
import com.example.vrades.data.repositories.PreferencesRepositoryImpl
import com.example.vrades.data.repositories.ProfileRepositoryImpl
import com.example.vrades.data.repositories.VradesRepositoryImpl
import com.example.vrades.domain.repositories.AuthRepository
import com.example.vrades.domain.repositories.PreferencesRepository
import com.example.vrades.domain.repositories.ProfileRepository
import com.example.vrades.domain.repositories.VradesRepository
import com.example.vrades.domain.use_cases.auth_repository.*
import com.example.vrades.domain.use_cases.profile_repository.*
import com.example.vrades.domain.use_cases.vrades_repository.*
import com.example.vrades.presentation.utils.Constants.DATA_AUDIO_TEST_REF
import com.example.vrades.presentation.utils.Constants.DATA_WRITING_TEST_REF
import com.example.vrades.presentation.utils.Constants.EMOTIONS_REF
import com.example.vrades.presentation.utils.Constants.IMAGE_REF
import com.example.vrades.presentation.utils.Constants.LIFEHACKS_REF
import com.example.vrades.presentation.utils.Constants.USERS_REF
import com.example.vrades.presentation.utils.Constants.USER_NAME_REF
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
        @Named(USERS_REF) usersRef: DatabaseReference,
        @Named(USER_NAME_REF) usersNameRef: DatabaseReference
    ): AuthRepository = AuthRepositoryImpl(auth, usersRef, usersNameRef)

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        addRealtimeUser = AddRealtimeUser(repository),
        getAuthState = GetAuthState(repository),
        getUserProfile = GetUserProfile(repository),
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
        storage: FirebaseStorage,
        @Named(USERS_REF) usersRef: DatabaseReference,
        @Named(USER_NAME_REF) usersNameRef: DatabaseReference,
        databaseReference: FirebaseDatabase
    ): ProfileRepository =
        ProfileRepositoryImpl(auth, storage, usersRef, usersNameRef, databaseReference)

    @Singleton
    @Provides
    fun provideProfileUseCases(repository: ProfileRepository) = ProfileUseCases(
        getLifeHacksByUserId = GetLifeHacksByUserId(repository),
        getTestsByUserId = GetTestsByUserId(repository),
        getUserById = GetUserById(repository),
        setProfilePictureInStorage = SetProfilePictureInStorage(repository),
        updateProfilePictureInRealtime = UpdateProfilePictureInRealtime(repository),
        addTestInRealtime = AddTestInRealtime(repository),
        generateAdvicesByTestResult = GenerateAdvicesByTestResult(repository),
        setDetectedMediaInStorage = SetDetectedMediaInStorage(repository),
        setDetectedAudioInStorage = SetDetectedAudioInStorage(repository),
        getTestByDate = GetTestByDate(repository)
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

    @Singleton
    @Provides
    fun providePreferencesRepository(context: Context): PreferencesRepository =
        PreferencesRepositoryImpl(context)


}