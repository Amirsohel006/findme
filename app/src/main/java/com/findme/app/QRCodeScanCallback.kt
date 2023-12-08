package com.findme.app

interface QRCodeScanCallback {
    fun onQRCodeScanned(qrCodeData: String)

}