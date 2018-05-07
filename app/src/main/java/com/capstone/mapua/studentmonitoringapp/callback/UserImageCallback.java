package com.capstone.mapua.studentmonitoringapp.callback;

import com.capstone.mapua.studentmonitoringapp.model.UserImageDetails;

/**
 * Created by jj on 4/24/2018.
 */

public interface UserImageCallback {
    void onSuccess(UserImageDetails body);

    void onError(String s);
}
