LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := hSolution
LOCAL_SRC_FILES := hSolution_native.c
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../jni
LOCAL_LDLIBS := -llog

APP_ABI := all
APP_STL := c++_static

LOCAL_SHARED_LIBRARIES := EdgeCrypto

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_LDLIBS := -llog
LOCAL_MODULE := EdgeCrypto
LOCAL_SRC_FILES := ../jniLibs/$(TARGET_ARCH_ABI)/libEdgeCrypto.so

include $(PREBUILT_SHARED_LIBRARY)