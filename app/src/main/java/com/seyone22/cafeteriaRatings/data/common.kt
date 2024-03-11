package com.seyone22.cafeteriaRatings.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.InputStream
import androidx.compose.ui.graphics.Color as ComposeColor


// Function to copy the image to your app's private directory
fun copyImageToAppDirectory(context: Context, imageUri: Uri): String? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        if (inputStream == null) {
            Log.e("TAG", "Input stream is null. Image not found.")
            return null
        }

        val outputDir = context.filesDir // Your app's private directory
        val outputFile = File(outputDir, "background_image.jpg")

        // Create the output file (if it doesn't exist)
        if (!outputFile.exists()) {
            outputFile.createNewFile()
        }

        val outputStream = outputFile.outputStream()
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        outputFile.absolutePath
    } catch (e: Exception) {
        Log.e("TAG", "Error copying image: ${e.message}")
        e.printStackTrace()
        null
    }
}

fun calculateAverageBrightness(bitmap: Bitmap): Int {
    var totalBrightness = 0f
    val pixelCount = bitmap.width * bitmap.height

    for (x in 0 until bitmap.width) {
        for (y in 0 until bitmap.height) {
            val pixel = bitmap.getPixel(x, y)
            val brightness = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)) / 3
            totalBrightness += brightness
        }
    }
    Log.d("TAG", "calculateAverageBrightness: ${totalBrightness / pixelCount}")
    return (totalBrightness / pixelCount).toInt()
}

fun foregroundColor(bitmap: Bitmap) : ComposeColor {
    return if (calculateAverageBrightness(bitmap) >= 128) {
        ComposeColor.Black
    } else {
        ComposeColor.White
    }
}