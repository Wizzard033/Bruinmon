package com.bruinmon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.widget.Toast;
import android.view.Gravity;
import android.bluetooth.BluetoothServerSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class PreBattleActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_battle);

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    /** Called when the user touches the Battle vs AI button **/
    public void battleAI(View view) {
        Intent intent = new Intent(this, BattleActivity.class);
        startActivity(intent);
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }

    /** Called when the user touches the Battle vs Player button **/
    public void battlePlayer(View view) throws IOException {
        // TODO : Use Bluetooth to do a player vs. player battle
        UUID myUIID = UUID.randomUUID();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) { // Check if device supports bluetooth
            // Device does not support Bluetooth
            System.err.println("Bluetooth not supported on device.");
            System.exit(-1);
        }
        int REQUEST_ENABLE_BT = 1;
        if (!mBluetoothAdapter.isEnabled()) {// Enable bluetooth if not already enabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            //make device discoverable for 300 seconds (5 mins)
            Intent discoverableIntent =
                    new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
        String myDeviceAddress = mBluetoothAdapter.getAddress();
        String myDeviceName = mBluetoothAdapter.getName();
        String status = myDeviceName +  " : " + myDeviceAddress;

        Toast.makeText(this, status, Toast.LENGTH_LONG).show();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }

    }

    public void connectToPlayer(View view) throws IOException { //server setup
        //called when bluetooth connection button is pressed

        Toast.makeText(this, "Needs to be implemented with threads!", Toast.LENGTH_LONG).show();
        /*UUID myUIID = UUID.randomUUID();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) { // Check if device supports bluetooth
            // Device does not support Bluetooth
            System.err.println("Bluetooth not supported on device.");
            System.exit(-1);
        }
        int REQUEST_ENABLE_BT = 1;
        if (!mBluetoothAdapter.isEnabled()) {// Enable bluetooth if not already enabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            //make device discoverable for 300 seconds (5 mins)
            Intent discoverableIntent =
                    new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
        String myDeviceAddress = mBluetoothAdapter.getAddress();
        String myDeviceName = mBluetoothAdapter.getName();
        String status = myDeviceName +  " : " + myDeviceAddress;

        Toast.makeText(this, status, Toast.LENGTH_LONG).show();

        //connect via server
        BluetoothServerSocket server = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            server = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(myDeviceName, myUIID);
        } catch (IOException e) {
            Toast.makeText(this, "Server failed to initialize.", Toast.LENGTH_LONG).show();
        }
        BluetoothSocket socket = null;
        while (true) {
            try {
                socket = server.accept();
            } catch (IOException e) {
                Toast.makeText(this, "Socket accept() failed.", Toast.LENGTH_LONG).show();
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                //manageMyConnectedSocket(socket);
                server.close();
                break;
            }
        }

        //connect via client
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();


        InputStream is;
        OutputStream os;
        BluetoothSocket remoteSocket = null;
        BluetoothDevice client = null;
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                if (deviceName.equals("VirtualBox"))
                {
                    client = device;
                    remoteSocket = device.createRfcommSocketToServiceRecord(myUIID);
                    BluetoothSocket hostSocket =  mBluetoothAdapter.getRemoteDevice(myDeviceAddress).createRfcommSocketToServiceRecord(myUIID);
                    remoteSocket.connect();
                    String isConnected = device.getName() + " : " + device.getAddress();
                    Toast t = Toast.makeText(this, status, Toast.LENGTH_LONG);
                    t.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                    t.show();
                    is = remoteSocket.getInputStream();
                    os = remoteSocket.getOutputStream();
                }
            }
        }

        // Cancel discovery because it otherwise slows down the connection.
        mBluetoothAdapter.cancelDiscovery();
        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            remoteSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                remoteSocket.close();
            } catch (IOException closeException) {
                Toast.makeText(this, "Could not close client socket.", Toast.LENGTH_LONG).show();
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        //manageMyConnectedSocket(mmSocket);

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            remoteSocket = client.createRfcommSocketToServiceRecord(myUIID);
        } catch (IOException e) {
            //Log.e(TAG, "Socket's create() method failed", e);
            Toast.makeText(this, "Socket's create() method failed.", Toast.LENGTH_LONG).show();
        }*/
    }
}


