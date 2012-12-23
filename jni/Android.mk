TARGET_PLATFORM := android-3

ROOT_PATH := $(call my-dir)

########################################################################################################

include $(CLEAR_VARS)

LOCAL_MODULE     := openal
LOCAL_ARM_MODE   := arm
LOCAL_PATH       := $(ROOT_PATH)
LOCAL_C_INCLUDES := $(LOCAL_PATH) $(LOCAL_PATH)/../openal-android/include $(LOCAL_PATH)/../openal-android/OpenAL32/Include
LOCAL_SRC_FILES  := ../openal-android/OpenAL32/alAuxEffectSlot.c \
                    ../openal-android/OpenAL32/alBuffer.c        \
                    ../openal-android/OpenAL32/alEffect.c        \
                    ../openal-android/OpenAL32/alError.c         \
                    ../openal-android/OpenAL32/alExtension.c     \
                    ../openal-android/OpenAL32/alFilter.c        \
                    ../openal-android/OpenAL32/alListener.c      \
                    ../openal-android/OpenAL32/alSource.c        \
                    ../openal-android/OpenAL32/alState.c         \
                    ../openal-android/OpenAL32/alThunk.c         \
                    ../openal-android/Alc/ALc.c                  \
                    ../openal-android/Alc/alcConfig.c            \
                    ../openal-android/Alc/alcDedicated.c         \
                    ../openal-android/Alc/alcEcho.c              \
                    ../openal-android/Alc/alcModulator.c         \
                    ../openal-android/Alc/alcReverb.c            \
                    ../openal-android/Alc/alcRing.c              \
                    ../openal-android/Alc/alcThread.c            \
                    ../openal-android/Alc/ALu.c                  \
                    ../openal-android/Alc/bs2b.c                 \
                    ../openal-android/Alc/helpers.c              \
                    ../openal-android/Alc/hrtf.c                 \
                    ../openal-android/Alc/mixer.c                \
                    ../openal-android/Alc/panning.c              \
                    ../openal-android/Alc/backends/android.c     \
                    ../openal-android/Alc/backends/loopback.c    \
                    ../openal-android/Alc/backends/null.c        \

#                    ../openal-android/Alc/backends/opensl.c     \

LOCAL_CFLAGS     := -ffast-math -DAL_BUILD_LIBRARY -DAL_ALEXT_PROTOTYPES
LOCAL_LDLIBS     := -llog -Wl,-s

include $(BUILD_SHARED_LIBRARY)

########################################################################################################

include $(CLEAR_VARS)

LOCAL_MODULE     := tremolo
LOCAL_ARM_MODE   := arm
LOCAL_PATH       := $(ROOT_PATH)/tremolo
LOCAL_SRC_FILES  := bitwise.c      \
                    bitwiseARM.s   \
                    codebook.c     \
                    dpen.s         \
                    dsp.c          \
                    floor0.c       \
                    floor1.c       \
                    floor1ARM.s    \
                    floor1LARM.s   \
                    floor_lookup.c \
                    framing.c      \
                    info.c         \
                    mapping0.c     \
                    mdct.c         \
                    mdctARM.s      \
                    mdctLARM.s     \
                    misc.c         \
                    res012.c       \
                    speed.s        \
                    vorbisfile.c   \

LOCAL_CFLAGS     := -ffast-math -D_ARM_ASSEM_

include $(BUILD_STATIC_LIBRARY)

########################################################################################################

include $(CLEAR_VARS)

LOCAL_MODULE     := example
LOCAL_ARM_MODE   := arm
LOCAL_PATH       := $(ROOT_PATH)
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../openal-android/include $(LOCAL_PATH)/tremolo
LOCAL_SRC_FILES  := example.c
LOCAL_LDLIBS     := -llog -Wl,-s

LOCAL_STATIC_LIBRARIES := libtremolo
LOCAL_SHARED_LIBRARIES := libopenal

include $(BUILD_SHARED_LIBRARY)

########################################################################################################

include $(CLEAR_VARS)

LOCAL_MODULE     := openalwrapper
LOCAL_ARM_MODE   := arm
LOCAL_PATH       := $(ROOT_PATH)
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../openal-android/include $(LOCAL_PATH)/../openal-android/tremolo
LOCAL_SRC_FILES  := openalwrapper.c 							\
					org_pielot_openal_OpenAlBridge.c 	\
					
LOCAL_LDLIBS     := -llog -Wl,-s

LOCAL_STATIC_LIBRARIES := libtremolo
LOCAL_SHARED_LIBRARIES := libopenal

include $(BUILD_SHARED_LIBRARY)

########################################################################################################
