package com.solaisc.studyfeedswitcher

import QuickLesson
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solaisc.studyfeedswitcher.ui.theme.StudyFeedSwitcherTheme
import com.solaisc.studyfeedswitcher.ui.theme.montserrat
import com.solaisc.studyfeedswitcher.ui.theme.poltawsky
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import quickLessons

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyFeedSwitcherTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) { innerPadding ->
                    val padding = innerPadding

                    val screen = LocalConfiguration.current
                    val landscapeMode = screen.orientation == ORIENTATION_LANDSCAPE

                    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
                    val currentItem = quickLessons.getOrNull(currentIndex) ?: return@Scaffold

                    fun nextIndex() {
                        if (currentIndex == quickLessons.lastIndex) {
                            currentIndex = 0
                        } else currentIndex++
                    }

                    fun prevIndex() {
                        if (currentIndex <= 0) {
                            currentIndex = quickLessons.lastIndex
                        } else currentIndex--
                    }

                    Container(
                        lesson = currentItem,
                        dragNext = { nextIndex() },
                        dragPrev = { prevIndex() },
                        landscapeMode = landscapeMode
                    )
                }
            }
        }
    }
}



@Composable
fun Container(
    lesson: QuickLesson,
    dragNext: () -> Unit,
    dragPrev: () -> Unit,
    landscapeMode: Boolean
) {
    Crossfade(targetState = lesson) { lesson ->
        var drag by rememberSaveable { mutableFloatStateOf(0f) }
        val dragThreshold = 500f

        var showSwipeText by rememberSaveable { mutableStateOf(true) }
        var timeLeft by rememberSaveable { mutableIntStateOf(3) }

        LaunchedEffect(drag, timeLeft) {
            if (drag == 0f) {
                showSwipeText = false
                if (timeLeft >= 1) {
                    delay(1000)
                    timeLeft = timeLeft - 1
                } else {
                    showSwipeText = true
                }
            } else {
                timeLeft = 3
                showSwipeText = false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            lesson.gradient.color1,
                            lesson.gradient.color2
                        )
                    ),
                    shape = RectangleShape
                )
                .clip(RectangleShape)
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp, bottom = 16.dp)
                .pointerInput(Unit) {
                    if (!landscapeMode) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { _, dragAmount ->
                                drag += dragAmount
                                showSwipeText = false
                            },
                            onDragEnd = {
                                if (drag < -dragThreshold) {
                                    dragNext()
                                } else if (drag > dragThreshold) {
                                    dragPrev()
                                }
                                drag = 0f

                            }
                        )
                    } else {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                drag += dragAmount
                            },

                            onDragEnd = {
                                if (drag < -dragThreshold) {
                                    dragNext()
                                } else if (drag > dragThreshold) {
                                    dragPrev()
                                }
                                drag = 0f
                            }
                        )
                    }
                },
        ) {
            lessonItem(
                title = lesson.title,
                description = lesson.description,
                subject = lesson.subject,
                modifier = Modifier
                    .padding(
                        bottom = if (!landscapeMode) {
                            64.dp
                        } else 24.dp
                    )
                    .align(Alignment.BottomCenter)
            )

            Icon(
                imageVector = if (!landscapeMode) { Icons.Default.KeyboardArrowUp } else Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .align(
                        if (!landscapeMode) {
                            Alignment.TopCenter
                        } else {
                            Alignment.CenterStart
                        }
                    )
            )

            if (showSwipeText) {
                Text(
                    text = "Swipe to see more",
                    fontSize = 16.sp,
                    fontFamily = montserrat,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(
                            top = if (!landscapeMode) {
                                48.dp
                            } else {
                                0.dp
                            }
                        )
                )
            }

            Icon(
                imageVector = if (!landscapeMode) { Icons.Default.KeyboardArrowDown } else Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .align(
                        if (!landscapeMode) {
                            Alignment.BottomCenter
                        } else {
                            Alignment.CenterEnd
                        }
                    )
            )
        }
    }
}

@Composable
fun lessonItem(
    title: String,
    description: String,
    subject: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = subject,
            fontSize = 14.sp,
            fontFamily = montserrat,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .background(
                    color = Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 30.sp,
            fontFamily = poltawsky,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = description,
            fontSize = 22.sp,
            fontFamily = montserrat,
            color = Color.White
        )

    }
}