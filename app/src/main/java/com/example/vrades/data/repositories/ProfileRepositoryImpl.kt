package com.example.vrades.data.repositories

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.vrades.domain.model.LifeHack
import com.example.vrades.domain.model.Response
import com.example.vrades.domain.model.Response.Success
import com.example.vrades.domain.model.Test
import com.example.vrades.domain.model.User
import com.example.vrades.domain.repositories.ProfileRepository
import com.example.vrades.presentation.utils.Constants
import com.example.vrades.presentation.utils.Constants.ADVICES
import com.example.vrades.presentation.utils.Constants.DATE
import com.example.vrades.presentation.utils.Constants.DETAILS
import com.example.vrades.presentation.utils.Constants.EMAIL
import com.example.vrades.presentation.utils.Constants.EMOTIONS_SCORE
import com.example.vrades.presentation.utils.Constants.ERROR_REF
import com.example.vrades.presentation.utils.Constants.ICON
import com.example.vrades.presentation.utils.Constants.IMAGE
import com.example.vrades.presentation.utils.Constants.IS_COMPLETED
import com.example.vrades.presentation.utils.Constants.IS_TUTORIAL_ENABLED
import com.example.vrades.presentation.utils.Constants.LIFEHACKS_REF
import com.example.vrades.presentation.utils.Constants.NAME
import com.example.vrades.presentation.utils.Constants.RESULT
import com.example.vrades.presentation.utils.Constants.STATE
import com.example.vrades.presentation.utils.Constants.TEST
import com.example.vrades.presentation.utils.Constants.TESTS
import com.example.vrades.presentation.utils.Constants.TRIGGER_EMOTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storageMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    @Named(Constants.USERS_REF) private val usersRef: DatabaseReference,
    @Named(Constants.USER_NAME_REF) private val usersNameRef: DatabaseReference,
    private val database: FirebaseDatabase
) : ProfileRepository {

    override suspend fun getUserById(): Flow<Response<User>> = flow {
        try {
            emit(Response.Loading)
            val lifeHacks = mutableListOf<LifeHack>()
            val tests = mutableListOf<Test>()

            if (auth.currentUser != null) {
                usersRef.child(auth.currentUser!!.uid).get().await().apply {
                    println("currentUser: ${usersRef.child(auth.currentUser!!.uid).get()}")
                    child(ADVICES).children.forEach { lifeHack ->
                        if (lifeHack.exists()) {
                            lifeHacks.add(
                                LifeHack(
                                    lifeHack.key.toString(),
                                    lifeHack.child(ICON).getValue(String::class.java).toString(),
                                    lifeHack.child(DETAILS).getValue(String::class.java).toString()
                                )
                            )
                        }

                    }

                    child(TESTS).children.forEach { test ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (test.exists()) {
                                val emotions = mutableMapOf<String,Float>()
                                test.child(EMOTIONS_SCORE).children.forEach {
                                    val value = it.getValue(Float::class.java)
                                    emotions[it.key.toString()] = value as Float
                                }
                                tests.add(
                                    Test(
                                        test.child(DATE).getValue(String::class.java),
                                        test.child(STATE).getValue(Int::class.java),
                                        test.child(RESULT).getValue(String::class.java)
                                            .toString(),
                                        emotions,
                                        test.child(IS_COMPLETED).getValue(Boolean::class.java)

                                    )
                                )
                            }

                        }
                    }
                    if (this.exists()) {
                        val user = User(
                            child(EMAIL).getValue(String::class.java).toString(),
                            child(NAME).getValue(String::class.java).toString(),
                            child(IMAGE).getValue(String::class.java).toString(),
                            child(IS_TUTORIAL_ENABLED).getValue(Boolean::class.java),
                            lifeHacks,
                            tests
                        )
                        emit(Success(user))
                    }
                }
            } else println("No User logged in")
        } catch (e: Exception) {

            emit(Response.Error(e.message ?: e.message!!))

        }
    }

    override suspend fun setProfilePictureInStorage(picture: Uri): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading)
            val filePath = storage.getReference("Users/" + auth.currentUser!!.uid + ".png")
            filePath.putFile(picture).await().also {
                val location =
                    filePath.putFile(picture).await().storage.downloadUrl.await().toString()
                emit(Success(location))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.message!!))
        }
    }

    override suspend fun updateProfilePictureInRealtime(picture: String): Flow<Response<Boolean>> =
        flow {
            try {
                emit(Response.Loading)
                usersRef.child(auth.currentUser!!.uid).child(IMAGE)
                    .setValue(picture).await().also {
                        emit(Success(true))
                    }
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.message!!))
            }
        }

    override suspend fun addTestInRealtime(test: Test): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            var k = 0
            usersRef.child(auth.currentUser!!.uid).child(TESTS).get()
                    .await().children.forEach { _ ->
                        k++
                    }
            usersRef.child(auth.currentUser!!.uid).child(TESTS).child(TEST + (k + 1).toString())
                .setValue(test,ServerValue.TIMESTAMP).await().also {
                    emit(Success(true))
                }

        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))

        }
    }

    override suspend fun generateAdvicesByTestResult(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            lateinit var lastResult: String
            lateinit var advice: LifeHack
            usersRef.child(auth.currentUser!!.uid).child(TESTS).get().await().children.forEach {
                lastResult = it.child(RESULT).getValue(String::class.java).toString()
            }
            database.reference.child(LIFEHACKS_REF).get().await().children.forEach {
                if (it.child(TRIGGER_EMOTION).getValue(String::class.java) == lastResult) {
                    advice = LifeHack(
                        it.key.toString(),
                        it.child(IMAGE).getValue(String::class.java).toString(),
                        it.child(DETAILS).getValue(String::class.java).toString()
                    )
                }
            }
            usersRef.child(auth.currentUser!!.uid).child(ADVICES).child(advice.name)
                .setValue(advice).await().also {
                    emit(Success(true))
                }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.message!!))
        }
    }

    override suspend fun setDetectedMediaInStorage(picture: Uri): Flow<Response<String>> = flow {
        emit(Response.Loading)
        val filePath = storage.getReference("/Media/" + auth.currentUser!!.uid)
        Log.i(TAG, "URI: $filePath")
        filePath.putFile(picture).await().also {
            val location =
                filePath.putFile(picture).await().storage.downloadUrl.await().toString()

            emit(Success(location))
        }
    }.catch {
        emit(Response.Error(it.message.toString()))
    }

    override suspend fun setDetectedAudioInStorage(audio: Uri): Flow<Response<String>> = flow {
        emit(Response.Loading)
        val filePath = storage.getReference("/Media/" + auth.currentUser!!.uid + ".mp3")
        Log.i(TAG, "URI: $filePath")
        val metadata = storageMetadata {
            contentType = "audio/mpeg"
        }
        filePath.putFile(audio).await().also {
            val location =
                filePath.putFile(audio, metadata).await().storage.downloadUrl.await().toString()
            println("Location: $location")
            emit(Success(location))
        }
    }.catch {
        emit(Response.Error(it.message.toString()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTestsByUserId(): Flow<Response<List<Test>>> = flow {
        try {
            emit(Response.Loading)
            val tests = mutableListOf<Test>()

            usersRef.child(auth.currentUser!!.uid).child(TESTS).get()
                .await().children.forEach { test ->
                    val emotionsList = mutableMapOf<String,Float>()
                    test.child(EMOTIONS_SCORE).children.forEach {
                        val value = it.getValue(Float::class.java)
                        emotionsList[it.key.toString()] = value as Float
                    }
                    tests.add(
                        Test(
                            test.child(DATE).getValue(String::class.java),
                            test.child(STATE).getValue(Int::class.java)!!,
                            test.child(RESULT).getValue(String::class.java)!!,
                            emotionsList,
                            test.child(IS_COMPLETED).getValue(Boolean::class.java)!!
                        )
                    )
                    emit(Success(tests))
                }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }
    }

    override suspend fun getTestByDate(date: String): Flow<Response<MutableMap<String, Float>>> = flow{
        try {
            emit(Response.Loading)
            val tests = mutableListOf<Test>()
            usersRef.child(auth.currentUser!!.uid).child(TESTS).get()
                .await().children.forEach { test ->
                    val emotionsList = mutableMapOf<String, Float>()
                    test.child(EMOTIONS_SCORE).children.forEach {
                        val value = it.getValue(Float::class.java)
                        emotionsList[it.key.toString()] = value as Float
                    }
                    tests.add(
                        Test(
                            test.child(DATE).getValue(String::class.java),
                            test.child(STATE).getValue(Int::class.java)!!,
                            test.child(RESULT).getValue(String::class.java)!!,
                            emotionsList,
                            test.child(IS_COMPLETED).getValue(Boolean::class.java)!!
                        )
                    )
                }
            tests.forEach {
                if(it.date == date){
                    println("FOUND TEST: $it")
                    emit(Success(it.emotionsScore))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }
    }

    override suspend fun getAdvicesByUserId(): Flow<Response<List<LifeHack>>> = flow {
        try {
            emit(Response.Loading)
            val lifeHacks = mutableListOf<LifeHack>()
            usersRef.child(auth.currentUser!!.uid).child(ADVICES).get()
                .await().children.forEach { lifeHack ->
                    lifeHacks.add(
                        LifeHack(
                            lifeHack.key!!,
                            lifeHack.child(IMAGE).getValue(String::class.java)!!,
                            lifeHack.child(DETAILS).getValue(String::class.java)!!
                        )
                    )
                }
            emit(Success(lifeHacks))
            println("Here, received tests")

        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_REF))
        }
    }

}
