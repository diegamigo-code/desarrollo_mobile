package com.example.desarrollo_mobile.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.ui.theme.WimOutline
import com.example.desarrollo_mobile.ui.theme.WimPrimary
import com.example.desarrollo_mobile.ui.theme.WimPrimaryLight

@Composable
fun WimBottomBar(
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        "Candidatas" to "👩‍💼",
        "Ranking" to "🏆",
        "Perfil" to "🌸"
    )

    Surface(
        color = Color.White.copy(alpha = 0.95f),
        shape = CircleShape,
        border = BorderStroke(1.dp, WimOutline.copy(alpha = 0.8f)),
        tonalElevation = 8.dp,
        shadowElevation = 6.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { (label, symbol) ->
                val active = selected == label

                val bgAnimation by animateColorAsState(
                    targetValue = if (active) WimPrimaryLight else Color.Transparent,
                    label = "TabBgAnimation"
                )

                val contentColorAnimation by animateColorAsState(
                    targetValue = if (active) WimPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    label = "TabTextColor"
                )

                val scaleAnimation by animateFloatAsState(
                    targetValue = if (active) 1.05f else 1.0f,
                    animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
                    label = "TabScaleAnimation"
                )

                Row(
                    modifier = Modifier
                        .scale(scaleAnimation)
                        .clip(CircleShape)
                        .background(bgAnimation)
                        .clickable { onSelect(label) }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(symbol, style = MaterialTheme.typography.titleMedium)
                    if (active) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = label,
                            color = contentColorAnimation,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
