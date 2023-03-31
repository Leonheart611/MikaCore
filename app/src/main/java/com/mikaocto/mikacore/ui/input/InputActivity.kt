package com.mikaocto.mikacore.ui.input

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.mikaocto.mikacore.databinding.ActivityInputBinding
import com.mikaocto.mikacore.model.InputItem
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private val options = ScanOptions()
    private val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        settingsBarcode()
        viewModel.getUserData()
        binding.tilBarcodeValue.setEndIconOnClickListener {
            barcodeLauncher.launch(options)
        }
        binding.btnSave.setOnClickListener {
            saveData()
        }
        binding.tbInputItem.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun settingsBarcode() {
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a barcode")
        options.setCameraId(0) // Use a specific camera of the device
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
    }

    private val barcodeLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                binding.etBarcodeValue.setText(result.contents.toString())
            }
        }

    private fun saveData() {
        val qty = binding.etQuantity.text.toString()
        if (qty.isNotBlank()) {
            viewModel.saveData(
                InputItem(
                    employeeName = viewModel.user?.name ?: "",
                    date = getNormalDate(),
                    barcodeValue = binding.etBarcodeValue.text.toString(),
                    time = getTime(),
                    quantity = binding.etQuantity.text.toString().toInt(),
                    itemName = binding.etItemName.text.toString()
                )
            )
            Toast.makeText(this, "Success Save Data", Toast.LENGTH_LONG).show()
        } else {
            binding.tilQuantity.error = "Wajib di isi"
        }
    }

    private fun getNormalDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.format(Date())
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss")
        return sdf.format(Date())
    }

}