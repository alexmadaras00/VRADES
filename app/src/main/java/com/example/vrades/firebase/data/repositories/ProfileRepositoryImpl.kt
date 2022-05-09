package com.example.vrades.firebase.repositories.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.vrades.firebase.repositories.domain.ProfileRepository
import com.example.vrades.model.LifeHack
import com.example.vrades.model.Response
import com.example.vrades.model.Test
import com.example.vrades.model.User
import com.example.vrades.utils.Constants
import com.example.vrades.utils.Constants.ADVICES
import com.example.vrades.utils.Constants.AGE
import com.example.vrades.utils.Constants.DATE
import com.example.vrades.utils.Constants.DETAILS
import com.example.vrades.utils.Constants.EMAIL
import com.example.vrades.utils.Constants.IMAGE
import com.example.vrades.utils.Constants.IS_COMPLETED
import com.example.vrades.utils.Constants.IS_TUTORIAL_ENABLED
import com.example.vrades.utils.Constants.NAME
import com.example.vrades.utils.Constants.OCCUPATION
import com.example.vrades.utils.Constants.RESULT
import com.example.vrades.utils.Constants.STATE
import com.example.vrades.utils.Constants.TESTS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @Named(Constants.USERS_REF) private val usersRef: DatabaseReference
) : ProfileRepository {

    override suspend fun getUserById(): Flow<Response<User>> = flow {
        try {
            emit(Response.Loading)
            val lifeHacks = mutableListOf<LifeHack>()
            val tests = mutableListOf<Test>()
            println("users: ${usersRef.get().await().children}")
            usersRef.child(auth.currentUser!!.uid).get().await().apply {
                child(ADVICES).children.forEach { lifeHack ->
                    lifeHacks.add(
                        LifeHack(
                            lifeHack.key!!,
                            lifeHack.child(IMAGE).getValue(String::class.java)!!,
                            lifeHack.child(DETAILS).getValue(String::class.java)!!
                        )
                    )
                }
                child(TESTS).children.forEach { test ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        tests.add(
                            Test(
                              test.child(DATE).getValue(String::class.java),
                                test.child(STATE).getValue(Int::class.java)!!,
                                test.child(RESULT).getValue(String::class.java)!!,
                                test.child(IS_COMPLETED).getValue(Boolean::class.java)!!
                            )
                        )
                    }
                }

                val user = User(
                    child(EMAIL).getValue(String::class.java)!!,
                    child(NAME).getValue(String::class.java)!!,
                    child(AGE).getValue(Int::class.java),
                    child(OCCUPATION).getValue(String::class.java),
                    child(IS_TUTORIAL_ENABLED).getValue(Boolean::class.java),
                    lifeHacks,
                    tests
                )
                emit(Response.Success(user))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: Constants.ERROR_REF))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTestsByUserId(): Flow<Response<List<Test>>> = flow {
        try {
            emit(Response.Loading)
            val tests = mutableListOf<Test>()
            println("id: ${usersRef.child(auth.currentUser!!.uid)}")

            usersRef.child(auth.currentUser!!.uid).child(TESTS).get()
                .await().children.forEach { test ->
                    println(test.child(DATE).getValue(String::class.java))
                    println(test.child(STATE).getValue(Int::class.java))
                    println(test.child(RESULT).getValue(String::class.java))
                    println(test.child(IS_COMPLETED).getValue(Boolean::class.java))
                    tests.add(
                        Test(
                            test.child(DATE).getValue(String::class.java),
                            test.child(STATE).getValue(Int::class.java)!!,
                            test.child(RESULT).getValue(String::class.java)!!,
                            test.child(IS_COMPLETED).getValue(Boolean::class.java)!!
                        )
                    )
                    emit(Response.Success(tests))
                }
            println("Tests: $tests")
            println("Users: ${usersRef.get().await().children}")
            println("id: ${auth.currentUser!!.uid}")
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: Constants.ERROR_REF))
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
            emit(Response.Success(lifeHacks))


        } catch (e: Exception) {
            emit(Response.Error(e.message ?: Constants.ERROR_REF))
        }
    }

}
