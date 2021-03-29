 package com.example.ominousbeepingapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ominousbeepingapp.ui.theme.OminousBeepingAppTheme
import com.example.ominousbeepingapp.ui.theme.RubineRed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OminousBeepingAppTheme {
                OminousBeepingApp()
            }
        }
    }
}

@Composable
fun OminousBeepingApp() {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            BeepingButtonCanvas()
            OminousBeepingAppText()
        }
    }
}

@Composable
fun BeepingButtonCanvas() {
    var beeping by remember { mutableStateOf(false)}
    var animatedRingColor = animateRingColor(
        initialColor = Color.Black,
        targetColor = RubineRed,
        duration = 500
    )

    Canvas(
     modifier = Modifier
         .fillMaxWidth()
         .height(550.dp)
         .clickable(
             onClick = {
                 beeping = !beeping
            }
         )
    ) {
         val canvasWidth = size.width
         val canvasHeight = size.height
         fun drawAnimatedColorRing(radius: Float) {
             drawCircle(
                 color = animatedRingColor,
                 radius = radius,
                 center = center,
                 style = Stroke(width = size.width * 0.075f)
             )
         }

         // center circle
         drawCircle(
             color = RubineRed,
             center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
             radius = size.width / 8
         )

         if(beeping) {
             drawAnimatedColorRing(radius = size.width / 4)
             drawAnimatedColorRing(radius = size.width / 2.625F)
             drawAnimatedColorRing(radius = size.width / 2)
         }
    }
}

@Composable
fun animateRingColor(initialColor: Color, targetColor: Color, duration: Int) : Color {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
         initialValue = initialColor,
         targetValue = targetColor,
         animationSpec = infiniteRepeatable(
             animation = keyframes {
                 durationMillis = duration
             },
             repeatMode = RepeatMode.Reverse,
         )
    )
    return color
}

@Composable
fun OminousBeepingAppText() {
    Text(
        text = "OMINOUS BEEPING APP",
        color = MaterialTheme.colors.primary,
        fontSize = 50.sp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OminousBeepingAppTheme {
        OminousBeepingAppText()
    }
}

@Preview(showBackground = true)
@Composable
fun OminousBeepingAppPreview() {
    OminousBeepingAppTheme {
        OminousBeepingApp()
    }
}