package com.jambi.macbookpro.smsapp.callback;

import com.jambi.macbookpro.smsapp.model.TapDetails;

/**
 * Created by IPC on 11/23/2017.
 */

public interface TapCallback {

    void onSuccessTapDetails(TapDetails body);

    void onErrorTapDetails(String s);
}
