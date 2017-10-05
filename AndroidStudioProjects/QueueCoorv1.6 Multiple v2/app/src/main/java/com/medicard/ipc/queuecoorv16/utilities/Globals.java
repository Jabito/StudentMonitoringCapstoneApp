package com.medicard.ipc.queuecoorv16.utilities;

/**
 * Created by IPC on 8/4/2017.
 */

public class Globals {


    private static Globals instance = new Globals();

    // Getter-Setters
    public static Globals getInstance() {
        return instance;
    }

    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    private String TxtReg, TxtCoor , TxtReminder, vswitcher;

    private Globals() {
    }

    public String getTxtReg() {
        return TxtReg;
    }
    public void setTxtReg(String TxtReg) {
        this.TxtReg = TxtReg;
    }
    public String getTxtCoor() {
        return TxtCoor;
    }
    public void setTxtCoor(String TxtCoor) {
        this.TxtCoor = TxtCoor;
    }
    public String getTxtReminder() {
        return TxtReminder;
    }
    public void setTxtReminder(String TxtReminder) {
        this.TxtReminder = TxtReminder;
    }
    public String getVswitcher() {
        return vswitcher;
    }
    public void setVswitcher(String vswitcher) {
        this.vswitcher = vswitcher;
    }


}

