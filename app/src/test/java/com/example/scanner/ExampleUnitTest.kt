package com.example.scanner

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.nl.entityextraction.Entity
import com.google.mlkit.nl.entityextraction.EntityAnnotation
import com.google.mlkit.nl.entityextraction.EntityExtractor
import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractionParams
import com.google.mlkit.nl.entityextraction.MoneyEntity
import data.textproc.MLKitProcessor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun moneyConversion_isCorrect() {
        val monEnt = MoneyEntity("Euro", 45, 123)
        val converted = MLKitProcessor.assembleDouble(monEnt)

        assertEquals(123.45, converted, 0.0001)
    }

    @Test
    fun extractTitle_isCorrect() {

    }
}