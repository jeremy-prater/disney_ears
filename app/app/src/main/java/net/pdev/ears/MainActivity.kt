package net.pdev.ears

import android.Manifest
import android.app.Activity
import android.bluetooth.*
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.os.Handler
import android.view.MenuItem
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import net.pdev.ears.databinding.ActivityMainBinding
import org.jetbrains.anko.alert
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import java.nio.ByteBuffer
import java.util.*

private const val ENABLE_BLUETOOTH_REQUEST_CODE = 1
private const val LOCATION_PERMISSION_REQUEST_CODE = 2

@kotlin.ExperimentalUnsignedTypes
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val ledModel: LedData by viewModels()
    private val batteryModel: BatteryData by viewModels()

//    private var scanning = false
//    private val handler = Handler()
//
//    // Stops scanning after 10 seconds.
//    private val SCAN_PERIOD: Long = 10000
//
//    private fun scanLeDevice() {
//        if (!scanning) { // Stops scanning after a pre-defined scan period.
//            handler.postDelayed({
//                scanning = false
//                bluetoothLeScanner.stopScan(leScanCallback)
//            }, SCAN_PERIOD)
//            scanning = true
//            bluetoothLeScanner.startScan(leScanCallback)
//        } else {
//            scanning = false
//            bluetoothLeScanner.stopScan(leScanCallback)
//        }
//    }
//
//    private val leDeviceListAdapter = LeDeviceListAdapter()
//    // Device scan callback.
//    private val leScanCallback: ScanCallback = object : ScanCallback() {
//        override fun onScanResult(callbackType: Int, result: ScanResult) {
//            super.onScanResult(callbackType, result)
//            leDeviceListAdapter.addDevice(result.device)
//            leDeviceListAdapter.notifyDataSetChanged()
//        }
//    }

    private val isLocationPermissionGranted
        get() = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    fun Context.hasPermission(permissionType: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permissionType) ==
                PackageManager.PERMISSION_GRANTED
    }

    private val scanResults = mutableListOf<ScanResult>()
    val scanResultAdapter: ScanResultAdapter by lazy {
        ScanResultAdapter(scanResults) { result ->
            // User tapped on a scan result
            if (isScanning) {
                stopBleScan()
            }
            with(result.device) {
                Log.w("ScanResultAdapter", "Connect device : $address")
                connectGatt(null, true, gattCallback)
            }
        }
    }

    private var earsGatt: BluetoothGatt? = null

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_content_main)
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()


    private var isScanning = false
        set(value) {
            field = value
            Log.i("ble-scanning", "$isScanning")
            runOnUiThread {
                if (value) {
                    binding.startScan.hide()
                } else {
                    binding.startScan.show()
                }
            }
        }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if (indexQuery != -1) { // A scan result already exists with the same address
                scanResults[indexQuery] = result
                scanResultAdapter.notifyItemChanged(indexQuery)
            } else {
                if (result.device.name == "Disney BLEars") {
                    with(result.device) {
                        Log.i(
                            "BLE Scan",
                            "Found BLE device! Name: ${name ?: "Unnamed"}, address: $address"
                        )
                    }
                    scanResults.add(result)
                    scanResultAdapter.notifyItemInserted(scanResults.size - 1)
                }
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("BLE Scan", "onScanFailed: code $errorCode")
        }
    }

    fun setEarColors(led_data: UByteArray) {
        if (earsGatt == null) {
            return
        }
        earsGatt?.services?.forEach { service ->
            // Service : 0000ea45-0000-1000-8000-00805f9b34fb
            if (service.uuid == UUID.fromString("0000ea45-0000-1000-8000-00805f9b34fb")) {
                service.characteristics.forEach { characteristic ->
                    // LED : 00001ed5-0000-1000-8000-00805f9b34fb
                    if (characteristic.uuid == UUID.fromString("00001ed5-0000-1000-8000-00805f9b34fb")) {
                        characteristic.value = led_data.toByteArray()
                        if (earsGatt?.writeCharacteristic(characteristic) == false) {
                            Log.w("BLE", "Failed to write ear led characteristic")
                        }
                    }
                }
            }
        }
    }

    fun getEarColors() {
        if (earsGatt == null) {
            return
        }
        earsGatt?.services?.forEach { service ->
            // Service : 0000ea45-0000-1000-8000-00805f9b34fb
            if (service.uuid == UUID.fromString("0000ea45-0000-1000-8000-00805f9b34fb")) {
                service.characteristics.forEach { characteristic ->
                    // LED : 00001ed5-0000-1000-8000-00805f9b34fb
                    if (characteristic.uuid == UUID.fromString("00001ed5-0000-1000-8000-00805f9b34fb")) {
                        if (earsGatt?.readCharacteristic(characteristic) == false) {
                            Log.w("BLE", "Failed to read ear led characteristic")
                        }
                    }
                }
            }
        }
    }

    fun disconnect() {
        if (earsGatt == null) {
            return
        }
        Log.i("BLE", "Disconnecting BLE")
        earsGatt?.disconnect()
        earsGatt?.close()
        earsGatt = null
    }


    fun getBatteryLevel() {
        if (earsGatt == null) {
            return
        }
        earsGatt?.services?.forEach { service ->
            // Service : 0000ea45-0000-1000-8000-00805f9b34fb
            if (service.uuid == UUID.fromString("0000ea45-0000-1000-8000-00805f9b34fb")) {
                service.characteristics.forEach { characteristic ->
                    // Battery : 0000ba11-0000-1000-8000-00805f9b34fb
                    if (characteristic.uuid == UUID.fromString("0000ba11-0000-1000-8000-00805f9b34fb")) {
                        if (earsGatt?.readCharacteristic(characteristic) == false) {
                            Log.w("BLE", "Failed to read battery characteristic")
                        }
                    }
                }
            }
        }
    }

    fun littleEndianConversion(bytes: ByteArray): Int {
        var result = 0
        for (i in bytes.indices) {
            result = result or (bytes[i].toInt() shl 8 * i)
        }
        return result
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            with(characteristic) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        if (uuid == UUID.fromString("00001ed5-0000-1000-8000-00805f9b34fb")) {
                            Log.i("BLE", "Got LED values")
                            ledModel.setLEDData(characteristic.value.toUByteArray())
                        } else if (uuid == UUID.fromString("0000ba11-0000-1000-8000-00805f9b34fb")) {
                            Log.i("BLE", "Got battery values")
                            var bytes = characteristic.value.toUByteArray().toByteArray()
                            batteryModel.setBatteryLevel(littleEndianConversion(bytes).toUInt())
                        } else {

                        }
                    }
                    BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                        Log.e("BluetoothGattCallback", "Read not permitted for $uuid!")
                    }
                    else -> {
                        Log.e(
                            "BluetoothGattCallback",
                            "Characteristic read failed for $uuid, error: $status"
                        )
                    }
                }
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            with(characteristic) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        Log.i("BluetoothGattCallback", "Wrote to characteristic $uuid");
                    }
                    BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH -> {
                        Log.e("BluetoothGattCallback", "Write exceeded connection ATT MTU!")
                    }
                    BluetoothGatt.GATT_WRITE_NOT_PERMITTED -> {
                        Log.e("BluetoothGattCallback", "Write not permitted for $uuid!")
                    }
                    else -> {
                        Log.e(
                            "BluetoothGattCallback",
                            "Characteristic write failed for $uuid, error: $status"
                        )
                    }
                }
            }
        }

        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val deviceAddress = gatt.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w("BluetoothGattCallback", "Successfully connected to $deviceAddress")

                    earsGatt = gatt

                    runOnUiThread {
                        if (earsGatt?.discoverServices() == true) {
                            Log.i("BluetoothGattCallback", "Gatt Discovery")
                            navController.navigate(R.id.earsFragment)
                        } else {
                            Log.w("BluetoothGattCallback", "failed to gatt discovery")
                            earsGatt?.disconnect()
                            earsGatt?.close()
                        }
                    }
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.w("BluetoothGattCallback", "Successfully disconnected from $deviceAddress")
                    navController.navigate(R.id.earsFragment)
                    gatt.close()
                }
            } else {
                Log.w(
                    "BluetoothGattCallback",
                    "Error $status encountered for $deviceAddress! Disconnecting..."
                )
                gatt.close()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!bluetoothAdapter.isEnabled) {
            promptEnableBluetooth()
        }
    }

    private fun promptEnableBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ENABLE_BLUETOOTH_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) {
                    promptEnableBluetooth()
                }
            }
        }
    }

    private fun startBleScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isLocationPermissionGranted) {
            Log.w("startBLEScan", "requesting location perms")
            requestLocationPermission()
        } else {
            Log.w("startBLEScan", "Scanning!")

            scanResults.clear()
            scanResultAdapter.notifyDataSetChanged()
            bleScanner.startScan(null, scanSettings, scanCallback)
            isScanning = true

        }
    }

    private fun stopBleScan() {
        bleScanner.stopScan(scanCallback)
        isScanning = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_DENIED) {
                    requestLocationPermission()
                } else {
                    startBleScan()
                }
            }
        }
    }

    private fun requestLocationPermission() {
        if (isLocationPermissionGranted) {
            return
        }
        runOnUiThread {
            alert {
                title = "Location permission required"
                message = "Starting from Android M (6.0), the system requires apps to be granted " +
                        "location access in order to scan for BLE devices."
                isCancelable = false
                positiveButton(android.R.string.ok) {
                    requestPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            }.show()
        }
    }

    private fun Activity.requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }

    override fun onStart() {
        super.onStart()

//        navController.navigate(R.id.earsFragment)

        startBleScan()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.startScan.setOnClickListener { view ->
            Snackbar.make(view, "Starting BLE scan...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            Log.i("main", "clicked scan...")
            startBleScan()
        }

        ledModel.getLEDData().observe(this, { colors ->
            // update ears
            setEarColors(colors)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}

