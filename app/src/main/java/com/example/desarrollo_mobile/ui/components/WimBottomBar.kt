package com.example.desarrollo_mobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.navigation.Screen
import com.example.desarrollo_mobile.ui.theme.*

@Composable
fun WimBottomBar(actual: String, destinos: List<Pair<String, String>>, navegar: (String) -> Unit) {
    Surface(modifier = Modifier.navigationBarsPadding().padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(26.dp), color = WimSurface, border = BorderStroke(1.dp, WimOutline),
        shadowElevation = 3.dp) {
        Row(Modifier.fillMaxWidth().selectableGroup().padding(vertical = 4.dp)) {
            destinos.forEach { (ruta, titulo) ->
                val icon = when (ruta) {
                    Screen.Dashboard.route -> Icons.Default.Home
                    Screen.Perfil.route -> Icons.Default.Person
                    Screen.RankingConsolidado.route -> Icons.Default.Star
                    else -> Icons.Default.List
                }
                NavigationBarItem(selected = actual == ruta, onClick = { navegar(ruta) },
                    icon = { Icon(icon, null) }, label = { Text(titulo, style = MaterialTheme.typography.labelMedium) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = WimPrimaryLight,
                        selectedIconColor = WimPrimaryStrong, selectedTextColor = WimPrimaryStrong,
                        unselectedIconColor = WimMuted, unselectedTextColor = WimMuted))
            }
        }
    }
}
