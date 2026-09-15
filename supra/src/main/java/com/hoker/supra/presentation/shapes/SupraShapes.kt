package com.hoker.supra.presentation.shapes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * The Instrument radius scale: rounded everywhere, generous, but never a pill by default.
 */
object SupraShapes {
    val control = RoundedCornerShape(12.dp)  //Buttons, fields, pickers, selectors, snackbar, dialogs
    val row = RoundedCornerShape(14.dp)      //List rows, the most repeated shape
    val cap = RoundedCornerShape(10.dp)      //SupraHardwareButton inner cap
    val capPlate = RoundedCornerShape(13.dp) //Its plate: cap + lip
    val material = RoundedCornerShape(24.dp) //Texture plates
    val surface = RoundedCornerShape(30.dp)  //Scaffold content
    val bezel = RoundedCornerShape(8.dp)     //Outer chrome
    val pill = RoundedCornerShape(50)        //Opt-in only: capsule variants, one per screen at most
}
