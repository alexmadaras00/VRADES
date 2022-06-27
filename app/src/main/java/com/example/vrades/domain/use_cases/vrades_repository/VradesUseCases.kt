package com.example.vrades.domain.use_cases.vrades_repository

data class VradesUseCases(
    val getEmotions: GetEmotions,
    val getLifeHacks: GetLifeHacks,
    val getDataAudioTest: GetDataAudioTest,
    val getDataWritingTest: GetDataWritingTest,
    val getPictureByName: GetPictureByName,
    val getPictures: GetPictures
)
