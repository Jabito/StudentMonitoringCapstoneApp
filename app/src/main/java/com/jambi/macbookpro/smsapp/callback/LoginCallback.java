package com.jambi.macbookpro.smsapp.callback;

import com.jambi.macbookpro.smsapp.model.LogInDetails;

/**
 * Created by IPC on 11/22/2017.
 */

public interface LoginCallback {

    void onSuccessSignIn(LogInDetails body);

    void onErrorSignIn(String s);
}
