package com.pocketledger.android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    data object Dashboard : BottomNavItem("dashboard", "Home", Icons.Default.Home)
    data object Transactions : BottomNavItem("transactions", "Transactions", Icons.AutoMirrored.Filled.ReceiptLong)
    data object Budgets : BottomNavItem("budgets", "Budgets", Icons.Default.PieChart)
    data object Reports : BottomNavItem("reports", "Reports", Icons.Default.BarChart)
    data object Settings : BottomNavItem("settings", "Settings", Icons.Default.Settings)
}

val bottomNavItems = listOf(
    BottomNavItem.Dashboard,
    BottomNavItem.Transactions,
    BottomNavItem.Budgets,
    BottomNavItem.Reports,
    BottomNavItem.Settings
)

@Composable
fun PocketLedgerBottomNavBar(
    currentRoute: String?,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onItemSelected(item) },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        softWrap = false,
                        fontSize = 11.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}
