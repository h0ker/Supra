package com.hoker.supra.presentation.visual_text_transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class AsteriskVisualTransformation: VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val transformedText = AnnotatedString("*".repeat(text.length))

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = offset
            override fun transformedToOriginal(offset: Int): Int = offset
        }

        return TransformedText(transformedText, offsetMapping)
    }
}