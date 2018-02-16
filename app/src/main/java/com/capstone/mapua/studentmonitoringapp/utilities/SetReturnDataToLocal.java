package com.capstone.mapua.studentmonitoringapp.utilities;

import com.capstone.mapua.studentmonitoringapp.database.DatabaseHandler;
import com.capstone.mapua.studentmonitoringapp.model.TapLog;

import java.util.ArrayList;

/**
 * Created by me on 2/16/2018.
 */

public class SetReturnDataToLocal {

    public static void setTapLogToDB(ArrayList<TapLog> tapLogs, DatabaseHandler databaseH) {
        for (TapLog tapLog : tapLogs) {
            databaseH.createTagLogList(new TapLog(
                    tapLog.getRfid(),
                    tapLog.getLogType(),
                    tapLog.getLogDateTime()));
        }
    }

}
