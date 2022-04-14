package com.example.vrades.enums

enum class TestState(val position: Int) {
    TEST_STARTED(0),
    FACE_DETECTION_COMPLETED(1),
    AUDIO_DETECTION_COMPLETED(2),
    WRITING_DETECTION_COMPLETED(3)
}
