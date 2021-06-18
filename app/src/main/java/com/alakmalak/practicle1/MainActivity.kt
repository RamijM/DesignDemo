package com.alakmalak.practicle1

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var selectedImage = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        clickEvents()
    }

    private fun init(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvProgress.text = Html.fromHtml(getString(R.string._3_7), Html.FROM_HTML_MODE_COMPACT);
            } else {
                tvProgress.text = Html.fromHtml(getString(R.string._3_7));
            }

            val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this, R.layout.spinner_text,
                resources.getStringArray(R.array.array_address_proof))

            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spAddressProof.adapter = spinnerArrayAdapter

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun clickEvents(){
        try {
            ivBack.setOnClickListener(this)
            imageCapture1.setOnClickListener(this)
            imageCapture2.setOnClickListener(this)
            etDOB.setOnClickListener(this)
            etIssueDate.setOnClickListener(this)
            etExpireDate.setOnClickListener(this)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onClick(p0: View?) {
        try {
            when (p0!!.id) {
                R.id.ivBack -> {
                    onBackPressed()
                }

                R.id.imageCapture1 -> {
                    selectedImage = 1
                    checkPermission()
                }
                R.id.imageCapture2 -> {
                    selectedImage = 2
                    checkPermission()
                }
                R.id.etDOB -> {
                    openCalendar(etDOB)
                }
                R.id.etIssueDate -> {
                    openCalendar(etIssueDate)
                }
                R.id.etExpireDate -> {
                    openCalendar(etExpireDate)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun openCalendar(dateEditText : AppCompatEditText){
        try {
            val dateSelected: Calendar = Calendar.getInstance()
            val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            val newCalendar: Calendar = dateSelected
            var datePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->
                    dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0)
                    dateEditText.setText(dateFormatter.format(dateSelected.time))
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
//            dateEditText.setText(dateFormatter.format(dateSelected.time))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun checkPermission(){
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE), 1001)
                }else{
                    CropImage
                        .activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this)
                }
            } else {
                CropImage
                    .activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri? = result?.uriContent
                val resultFilePath: String? = result?.getUriFilePath(applicationContext)

                var photo: Bitmap? = null
                try {
                    photo = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        resultUri
                    )
                    val matrix = Matrix()
                    val scaledBitmap = Bitmap.createScaledBitmap(
                        photo,
                        photo.width, photo.height, true
                    )
                    photo = Bitmap.createBitmap(
                        scaledBitmap,
                        0,
                        0,
                        scaledBitmap.width,
                        scaledBitmap.height,
                        matrix,
                        true
                    )
                    val baos = ByteArrayOutputStream()
                    photo.compress(
                        Bitmap.CompressFormat.JPEG,
                        100,
                        baos
                    ) //bm is the bitmap object
                    if (photo != null) {
                        if(selectedImage == 1){
                            Glide.with(applicationContext).load(photo).into(imageCapture1)
                        }else if(selectedImage == 2){
                            Glide.with(applicationContext).load(photo).into(imageCapture2)
                        }
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result!!.error
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1001 ->
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        !== PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        !== PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            this, arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            6665)
                    } else {
                        CropImage
                            .activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(this)
                    }
                }
        }
    }
}