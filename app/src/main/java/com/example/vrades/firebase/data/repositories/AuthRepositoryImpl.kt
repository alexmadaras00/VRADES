package com.example.vrades.firebase.repositories.auth

import com.example.vrades.model.Response
import com.example.vrades.model.Response.Loading
import com.example.vrades.model.User
import com.example.vrades.utils.Constants.ERROR_REF
import com.example.vrades.utils.Constants.USERS_REF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @Named(USERS_REF) private val usersRef: DatabaseReference
) : AuthRepository {

    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    override suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String) =
        flow {
            try {
                emit(Loading) // Thread pauses, "suspend", like a state, like a return (observer, live)
                auth.signInWithEmailAndPassword(email, password).await().also {
                    emit(Response.Success(true))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: ERROR_REF))
            }
        }

    override suspend fun createUserInRealtime(fullName: String) = flow {
        try {
            emit(Loading)
            auth.currentUser?.apply {
//                println("User created: $uid in the field: ${usersRef.key}")
                val newUser = User(email.toString(), fullName)
                usersRef.child(uid).setValue(newUser).await().also {
                    emit(Response.Success(true))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }
    }

    override suspend fun signOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)
            auth.signOut()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }

    }

    override suspend fun sendPasswordResetEmail(email: String): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)
            auth.sendPasswordResetEmail(email).await().also {
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ) = flow {
        try {
            emit(Loading)
            auth.createUserWithEmailAndPassword(email, password).await().also { authResult ->
                authResult.user!!.updateProfile(userProfileChangeRequest {
                    displayName = email
                }).await().also {
                    emit(Response.Success(true))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }
    }

    override fun getUserAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
            println("State: ${auth.currentUser}")
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }

    }

    override fun isAccountInAuth(email: String): Flow<Response<Int>> = flow {
        try {
            emit(Loading)
            auth.fetchSignInMethodsForEmail(email).await().also { result ->
                emit(Response.Success(result.signInMethods!!.size))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }

    }

    override fun getUserProfile(): Flow<Response<User>> = flow {
        try {
            emit(Loading)
            val profile = auth.currentUser
            profile?.apply {
                val user = User(email!!, displayName!!)
                emit(Response.Success(user))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }
    }
    override suspend fun firebaseSignInWithGoogle(idToken: String) = flow {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            authResult.additionalUserInfo?.apply {
                emit(Response.Success(isNewUser))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }
    }

}