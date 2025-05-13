package com.example.bankbudgetingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScannerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start the scanner
        IntentIntegrator(this).apply {
            setOrientationLocked(true)
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("Scan a barcode or QR code")
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // Handle cancelation
                setResult(Activity.RESULT_CANCELED)
            } else {
                // Handle the scanned result
                val scannedData = result.contents
                val intent = Intent()
                intent.putExtra("SCANNED_DATA", scannedData)
                setResult(Activity.RESULT_OK, intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
        finish()
    }
}