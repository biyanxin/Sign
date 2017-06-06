package com.example.wangli.signin;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import net.vidageek.mirror.dsl.Mirror;

/**
 * Created by Wangli on 2016/11/1.
 */
public class GetAdress {
    public static String getBtAddressViaReflection() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Object bluetoothManagerService = new Mirror().on(bluetoothAdapter).get().field("mService");
        if (bluetoothManagerService == null) {
            Log.w("", "couldn't find bluetoothManagerService");
            return null;
        }
        Object address = new Mirror().on(bluetoothManagerService).invoke().method("getAddress").withoutArgs();
        if (address != null && address instanceof String) {
            Log.w("", "using reflection to get the BT MAC address: " + address);
            return (String) address;
        } else {
            return null;
        }
    }
}
