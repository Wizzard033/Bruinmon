package com.bruinmon;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.ArrayAdapter;
import android.widget.*;
import android.os.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.*;
//import com.example.android.common.activities.SampleActivityBase;

public class PreBattleActivity extends SampleActivityBase {

    private OutputStream outputStream;
    private InputStream inStream;
    private boolean mLogShown;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private BluetoothAdapter mBluetoothAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_battle);

        //listView = (ListView) findViewById(R.id.listViewer);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mDeviceList.add(mBluetoothAdapter.getName()); //hard-coded name of self device

        listView = findViewById(R.id.listViewer);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDeviceList);


/*
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothMessage fragment = new BluetoothMessage();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }*/


        // Register for broadcasts when a device is discovered.
       // IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
       // registerReceiver(mReceiver, filter);

       /* mDeviceList.add(mBluetoothAdapter.getName() + "\n" + mBluetoothAdapter.getAddress());
        listView.setAdapter(new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, mDeviceList));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        // Don't forget to unregister the ACTION_FOUND receiver.
        //unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    /*private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //if (BluetoothDevice.ACTION_FOUND.equals(action))
           // {
                Toast.makeText(getApplicationContext(), "fuck you", Toast.LENGTH_LONG).show();
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(device.getName() + " " + device.getAddress());
                //Log.i("BT", device.getName() + "\n" + device.getAddress());
                //listView.setAdapter(new ArrayAdapter<String>(context,
                       // android.R.layout.simple_list_item_1, mDeviceList));
           // }
        }
    };*/

    /** Called when the user touches the Battle vs AI button **/
    public void battleAI(View view) {
        Intent intent = new Intent(this, BattleActivity.class);
        startActivity(intent);
    }

    /** Called when the user touches the Battle vs Player button **/
    public void battlePlayer(View view) throws IOException {
        // TODO : Use Bluetooth to do a player vs. player battle
        Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
        //get paired devices, add to list
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        mDeviceList.clear();
        for(BluetoothDevice bt : pairedDevices)
            mDeviceList.add(bt.getName());
        listView.setAdapter(adapter);


        /*if(pairedDevices.size() > 0) {
            Object[] devices = (Object []) pairedDevices.toArray();
            BluetoothDevice device = (BluetoothDevice) devices[position++];
            ParcelUuid[] uuids = device.getUuids();
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
            socket.connect();
            outputStream = socket.getOutputStream();
            inStream = socket.getInputStream();
        }
        else {
            Log.e("error", "No appropriately paired devices.");
        }*/
}


    public void write(String s) throws IOException
    {
        outputStream.write(s.getBytes());
    }

    public void run()
    {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes = 0;
        int b = BUFFER_SIZE;

        while (true)
        {
            try
            {
                bytes = inStream.read(buffer, bytes, BUFFER_SIZE - bytes);
            }
            catch (IOException e)
            {
                e.printStackTrace();
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


