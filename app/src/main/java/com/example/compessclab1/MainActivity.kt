package com.example.compessclab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.compessclab1.ui.theme.CompEssCLab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompEssCLab1Theme {
                MyApp(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp))
            }
        }
    }
}


@Composable
fun MyApp(modifier: Modifier) {
    var showOnBoarding by remember {
        mutableStateOf(true)
    }
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (showOnBoarding) {
            StartButton {
                showOnBoarding = false
            }
        } else {
            Greetings()
        }
    }
}


@Composable
fun Greetings() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = List(100) {
                ("Lorem Ipsum is simply dummy text of the printing and typesetting industry ").repeat(5)
            }
        ) { index, item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Cyan.copy(alpha = 0.2f))) {
                ExpandableCard(index = index, data = item)
            }
        }
    }
}

@Composable
fun StartButton(onClickAction: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Welcome to the Basics of Codelab!")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onClickAction ) {
            Text("Continue")
        }
    }
}


@Composable
fun ExpandableCard(index: Int, data: String) {

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    
    var animationState = animateDpAsState(
        if (isExpanded) 20.dp else 0.dp,
        animationSpec = SpringSpec(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .animateContentSize(SpringSpec(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)),
//            .padding(bottom = animationState.value.coerceAtLeast(0.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier
            .weight(1f)
        ) {

            Text("Hello")

            Text("$index",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold))


            if (isExpanded)
                Text(data)
        }

        IconButton(onClick = {
            isExpanded = !isExpanded
        }, enabled = true) {
            Icon(
                imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (isExpanded) "Show More" else "Show Less"
            )
        }
    }
}