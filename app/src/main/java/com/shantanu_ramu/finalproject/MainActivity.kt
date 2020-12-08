@file:Suppress("SpellCheckingInspection")

package com.shantanu_ramu.finalproject

//import com.google.firebase.firestore.FirebaseFirestore

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.system.exitProcess

//import java.util.jar.Manifest

typealias LumaListener = (luma: Double) -> Unit
typealias BarcodeListner = (barluma: Double) -> Unit
var start_res_activity : Boolean = false


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    var context : Context = this

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar= findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val conte = getApplicationContext()
        context = this
        var sp : SharedPreferences = getSharedPreferences("login_details", MODE_PRIVATE)
        Toast.makeText(this, "Welcome " + sp.getString("userName", "User"), Toast.LENGTH_LONG).show()

        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "New Actions Coming Soon", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//        val navView: NavigationView = findViewById(R.id.nav_view)
        navView= findViewById(R.id.nav_view)

        val headerView : View = navView.getHeaderView(0)
        val navUsername : TextView = headerView.findViewById(R.id.navbarUsername)
        val navUserEmail : TextView = headerView.findViewById(R.id.navbarEmail)
        navUsername.text = sp.getString("userName", "Android Studio")
        navUserEmail.text = sp.getString("userEmail", "android.studio@android.com")

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_recent, R.id.manual_entry_button, R.id.nav_exit
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navView.setNavigationItemSelectedListener(this)


/*        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_exit -> {
                    Toast.makeText(applicationContext, "Exit", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Exit Button Clicked")
                    true
                }
                else -> false
            }
        }*/

//        this.supportActionBar?.hide()
//        this.supportActionBar?.displayOptions.
        val actionBar = supportActionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Set up the listener for take photo button
//        camera_capture_button.setOnClickListener { takePhoto() }
        fab.setOnClickListener {
            takePhoto()
           startActivity(Intent(this, Result::class.java))
//            startActivity(Intent(this, ManualEntry::class.java))
        }


        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sp : SharedPreferences = getSharedPreferences("login_details", MODE_PRIVATE)
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "Log Out Successful", Toast.LENGTH_LONG).show()
                val editor = sp.edit()
                editor.clear()
                editor.commit()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                true
            }
/*            R.id.nav_exit -> {
                Toast.makeText(applicationContext, "Exiting", Toast.LENGTH_SHORT).show()
//                finishAffinity()
                true
            }*/
            else -> super.onOptionsItemSelected(item)
        }
/*        val id = item.itemId
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }*/
    }
/*
    val nav_view = R.id.nav_view
    nav_view.set*/

/*    fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_recent -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            R.id.manual_entry1 -> {
                val intent = Intent(this, ManualEntry::class.java)
                startActivity(intent)

            }
            R.id.nav_settings -> {
*//*                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)*//*
                val actionSettings = findViewById<View>(R.id.action_settings) as MenuItem
                onOptionsItemSelected(actionSettings);

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        Log.d(TAG, "Item is : ${p0.itemId}")
        when (p0.itemId) {
            R.id.nav_exit -> {

                Toast.makeText(applicationContext, "Exiting", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Exiting The App")
                exitProcess(0)
            }

            R.id.manual_entry1 -> {
//                navController.navigate(R.id.)
                Toast.makeText(applicationContext, "Manual Entry", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ManualEntry::class.java))
                Log.d(TAG, "Recent Activity")
            }

            R.id.nav_home -> {
//                Toast.makeText(applicationContext, "Exiting", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Home")
            }

            R.id.nav_recent -> {
                Toast.makeText(applicationContext, "Top Search", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, RecyclerViewActivity::class.java))
                Log.d(TAG, "Exiting The App")
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun takePhoto() {
        Utils.getFireData()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }

            val barAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarImageAnalyzer { barluma ->
                        Log.d(TAG, "Barcode Value: $barluma")
                    })
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, barAnalyzer
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
//        var context : Context = this
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    //Added Luminosity and  Barcode Scanning Functionality here
    public fun toastFun(rawValue: String) {
        Toast.makeText(this, rawValue, Toast.LENGTH_LONG).show()
    }

    public fun itemNotPresentFirestore(msg: String) {
        Toast.makeText(this, " $msg", Toast.LENGTH_LONG).show()
    }


    public fun intentToResult() {
        val intent = Intent(this, Result::class.java)
//        val intent = Intent(getActivity(), Result.class)
        startActivity(intent)
    }

    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }

    private inner class BarImageAnalyzer(private val listener1: BarcodeListner) : ImageAnalysis.Analyzer {

        val options = BarcodeScannerOptions.Builder().build()
        val ma = MainActivity()
        val resultActivity = Result()

        @SuppressLint("UnsafeExperimentalUsageError")

        override fun analyze(imageProxy: ImageProxy) {
//            val ac = MainActivity()
//            var barluma : String? = null
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )
                // Passed image to an ML Kit Vision API

                val scanner = BarcodeScanning.getClient(options)
                val result = scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        // Task completed successfully
                        for (barcode in barcodes) {
                            val rawValue = barcode.rawValue
//                            barluma = barcode.rawValue
//                            Log.d(TAG, "passed with value: $rawValue")

//                            rawValue?.let { ac.toastFun(it) }
/*                            Utils.datahand(rawValue)
                            Utils.barValueComparision(rawValue.toString())*/
//                            val resultActivity = Result()
                            resultActivity.append_res_value(rawValue.toString())
                            Log.d(TAG, "Passed with Value: $rawValue")

//                            val context: Context = ma.context
//                            ma.intentToResult()
//                            ma.fab.performClick()
//                            ma.fab.callOnClick()
/*                            if (rawValue != null){
                                start_res_activity = true
                            }*/
//                            var context = R.layout.activity_result

/*                            if (rawValue != null) {
                                val intent = Intent(this@MainActivity, Result::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@MainActivity, "Given Item is not Present in Our Databse", Toast.LENGTH_SHORT).show()
                            }*/

                            val intent = Intent(this@MainActivity, Result::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                            startActivity(intent)
//                            toastFun(raw)


                        }

                    }
                    .addOnFailureListener {
                        // Task failed with an exception
                        Log.d(TAG, "failed") // for unit testing
                    }
                    .addOnSuccessListener {
//                        Log.d(TAG, "Value is : $rawValue")
                        imageProxy.close()
//                        Log.d(TAG, "Success Listner") //for unit testing
                    }
            }
//            listener1(123.0)
        }
    }





}