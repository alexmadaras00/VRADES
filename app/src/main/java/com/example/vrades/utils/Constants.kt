package com.example.vrades.utils

object Constants {

    const val ERROR_REF: String = "Unexpected error!"

    const val REALTIME_DATABASE = "Realtime Database"

    const val APP = "CONTEXT"

    //References
    const val USERS_REF: String = "users"
    const val EMOTIONS_REF: String = "emotions"
    const val LIFEHACKS_REF = "lifeHacks"
    const val LIFEHACKS_NAME_REF = "lifeHacksName"
    const val DATA_AUDIO_TEST_REF = "dataAudioTest"
    const val DATA_WRITING_TEST_REF = "dataWritingTest"
    const val IMAGE_REF = "images"
    const val USER_NAME_REF = "usersName"


    //Fields
    const val NAME = "username"
    const val EMAIL = "email"
    const val OCCUPATION = "occupation"
    const val AGE = "age"
    const val TESTS = "tests"
    const val TEST = "test"
    const val ADVICES = "advices"
    const val IS_TUTORIAL_ENABLED = "is_tutorial_enabled"
    const val DETAILS = "details"
    const val IMAGE = "image"
    const val DATE = "date"
    const val RESULT = "result"
    const val IS_COMPLETED = "isCompleted"
    const val STATE = "state"
    const val TRIGGER_EMOTION = "trigger_emotion"

    // Guest Profile Pic
    const val DEFAULT_PROFILE_PICTURE =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/Users%2Fguest.png?alt=media&token=73f3ffd6-2ddc-4081-b455-5f9ce0544604"

    // Images
    const val ARROW_TUTORIAL =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/arrow_tutorial.png?alt=media&token=185df4e8-9a00-4e8f-9f01-1d8963a53e10"
    const val AUDIO_BACK =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/audio_back.png?alt=media&token=9cf68a05-22e4-4e4a-868a-dd75d1a65fb1"
    const val AUDIO_RESULT =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/audio_result.png?alt=media&token=e58cc0d9-cc6c-4fff-8ce2-3bc036c34b90"
    const val BACKGROUND_APP =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/background_app.png?alt=media&token=ff1b2864-597e-4da7-bcf0-ddb393fb8894"
    const val BACKGROUND_CUSTOM =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/background_custom.png?alt=media&token=5ec27492-c1d8-458d-ad3d-c7aee27935e7"
    const val BACKGROUND_ITEM_LIFEHACK =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/background_item_lifehack.png?alt=media&token=97c813b0-f1c9-422d-b439-9fe6ecc1faf4"
    const val BACKGROUND_TEST =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/background_test.png?alt=media&token=7fb925f3-a183-4418-929a-30a8b2d10943"
    const val CAMERA_LAYOUT =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/camera_layout.png?alt=media&token=e166227e-a613-416f-823e-c429698a79df"
    const val CAMERA_SWITCH =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/cameraswitch_black_48dp.png?alt=media&token=759fe884-c674-4805-830a-0c800a5e9446"
    const val COMPLETED_TUTORIAL =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/completed_tutorial.png?alt=media&token=e8b007a7-c286-44b2-9886-80f5cf99f8f8"
    const val DETECT_ICON =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/detect_icon.png?alt=media&token=fbc5ecae-767d-41e6-85be-26bd9e89c4e1"
    const val FACE_BLACK =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/face_black_48dp.png?alt=media&token=2a70b477-17e8-428a-8a26-4f8bbe138609"
    const val FACE_IMAGE_CARD =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/face_image_card.png?alt=media&token=3c1953a2-afa9-452a-b4f8-371545a9a10c"
    const val FACE_WHITE =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/face_white.png?alt=media&token=7cfa96bf-c2c4-493f-a3b6-06005a8f03fa"
    const val INFO_WHITE =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/info_white_48dp.png?alt=media&token=fd37f61d-3864-4e05-9615-8ca4d170ca8f"
    const val INSTAGRAM =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/instagram.png?alt=media&token=ae5c85c8-a5d1-4388-825e-354987f34f47"
    const val MEDITATION_ICON =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/meditation_icon_138394.png?alt=media&token=6eaaf1aa-6275-40d4-bfcd-1760bace1c73"
    const val PROFILE_ICON =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/profile_icon.png?alt=media&token=148075fe-a8ca-4c83-bbf1-bb1cd36606a6"
    const val RECYCLER_VIEW_PROFILE_BACKGROUND =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/recycler_view_profile_background.png?alt=media&token=0e6c16da-988e-44f3-b413-05fab9465fb3"
    const val REVIEW_TEXT_BACKGROUND =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/review_text_background.png?alt=media&token=92c5c5b6-7ec7-4f9e-8eca-b3e6f7c387b6"
    const val SETTINGS_BLACK_BACK =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/settings_voice_black_48dp.png?alt=media&token=df79d0c3-ac77-4618-9d39-a1fa5a287def"
    const val SHARE_WHITE =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/share_white_36dp.png?alt=media&token=45c539ce-91f2-422d-9668-9e9ea6e9e35c"
    const val TEST_RESULT_CARD_BACKGROUND =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/test_result_card_background.png?alt=media&token=fab357d3-5e4d-46cf-9cae-74a57a2dd28f"
    const val TUTORIAL_BACKGROUND =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/tutorial_background.png?alt=media&token=f1c1854d-edc9-4873-9800-ca7da95f3c5d"
    const val TUTORIAL_ICON =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/tutorial_icon.png?alt=media&token=83e2569f-412a-4f2d-8d4d-9abbc84fc90c"
    const val WRITING_RESULT =
        "https://firebasestorage.googleapis.com/v0/b/vrades-d5b47.appspot.com/o/writing_result.png?alt=media&token=8533604c-d5e0-4fff-950f-f93a74bf6209"
}