package com.example.vrades.di

import android.app.Application
import android.content.Context
import com.example.vrades.firebase.domain.use_cases.*
import com.example.vrades.firebase.repositories.auth.AuthRepository
import com.example.vrades.firebase.repositories.auth.AuthRepositoryImpl
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
        signInWithGoogle = SignInWithGoogle(),
        logOut = LogOut(repository),
        resetPassword = ResetPassword(repository),
        signInWithEmailAndPassword = SignInWithEmailAndPassword(repository),
        signUp = SignUp(repository),
        isAccountInAuth = IsAccountInAuth(repository)
    )

}