package com.capstone.mapua.studentmonitoringapp.callback;

/**
 * Created by IPC on 4/24/2018.
 */

public interface UserImageCallback {
    void onSuccess(UserImageDetails body);

    void onError(String s);
}
