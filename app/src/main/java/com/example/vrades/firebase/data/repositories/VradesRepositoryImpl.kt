package com.example.vrades.firebase.data.repositories

import com.example.vrades.firebase.repositories.auth.AuthRepository
import com.example.vrades.firebase.repositories.domain.VradesRepository
import com.example.vrades.model.LifeHack
import com.example.vrades.model.Response
import com.example.vrades.model.Test
import com.example.vrades.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class VradesRepositoryImpl @Inject constructor(
    @Named(Constants.EMOTIONS_REF) private val emotionsRef: DatabaseReference,
    @Named(Constants.LIFEHACKS_REF) private val lifeHacksRef: DatabaseReference,
    @Named(Constants.DATA_AUDIO_TEST_REF) private val dataAudioTestRef: DatabaseReference,
    @Named(Constants.DATA_WRITING_TEST_REF) private val dataWritingTestRef: DatabaseReference
) : VradesRepository {
    override suspend fun getEmotions(): Flow<Response<List<String>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLifeHacks(): Flow<Response<List<LifeHack>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTests(): Flow<Response<List<Test>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDataAudioTest(): Flow<Response<List<String>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDataWritingTest(): Flow<Response<List<String>>> {
        TODO("Not yet implemented")
    }
}