package data.textproc

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.google.mlkit.nl.entityextraction.Entity
import com.google.mlkit.nl.entityextraction.EntityAnnotation
import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractionParams
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class MLKitProcessor {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private val entityExtractor = EntityExtraction.getClient(
        EntityExtractorOptions.Builder(EntityExtractorOptions.GERMAN).build()
    )

    fun processDocument(imageUri: Uri, context: Context, onResult: (String) -> Unit) {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(context, imageUri)
            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val title = extractTitle(visionText)
                    extractBillAmount(visionText) { billAmount ->
                        onResult("Suggested Title: $title \nSuggested Bill Amount: $billAmount")

                    }
                }
                .addOnFailureListener { e ->

                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun extractTitle(text: Text): String {
        return text.textBlocks[0].lines[0].text
    }

    private fun extractBillAmount(text: Text, onResult: (String) -> Unit) {
        var billAmount: String = "No Bill Amount found"
        val entityExtractor =
            EntityExtraction.getClient(
                EntityExtractorOptions.Builder(EntityExtractorOptions.GERMAN)
                    .build())

        entityExtractor
            .downloadModelIfNeeded()
            .addOnSuccessListener { _ ->
                val params =
                    EntityExtractionParams.Builder(text.text)
                        .setEntityTypesFilter(setOf(Entity.TYPE_MONEY))
                        .build()
                entityExtractor
                    .annotate(params)
                    .addOnSuccessListener { entityAnnotations ->
                        billAmount = findHighestBillAmount(entityAnnotations)
                        onResult(billAmount)
                    }
                    .addOnFailureListener {
                        onResult("No bill amount found")
                    }
            }
            .addOnFailureListener { _ ->
                onResult("No bill amount found")
            }
    }

    @SuppressLint("DefaultLocale")
    private fun findHighestBillAmount(entityAnnotations: List<EntityAnnotation>): String {
        var highestBillAmount: Double = 0.0

        for (entityAnnotation in entityAnnotations) {
            val moneyEntity = entityAnnotation.entities[0].asMoneyEntity()

            val integerPart = moneyEntity?.integerPart
            val fractionalPart = moneyEntity?.fractionalPart

            val billAmount = integerPart!!.toDouble() + fractionalPart!!.toDouble() / 100

            if (billAmount > highestBillAmount) {
                highestBillAmount = billAmount
            }
        }

        return String.format("%.2f", highestBillAmount)
    }
}