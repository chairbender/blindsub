This project comes with a modified version of OpenAL that makes openal fast for android. It implements a JNI for calling some OpenAL functions. To get it to work,
you need to compile the C code by getting the Android NDK and executing "PATH/TO/NDK/ndk-build" inside the openal-android folder. If you need to change the C code at all,
re-run this to re-generate the JNI.