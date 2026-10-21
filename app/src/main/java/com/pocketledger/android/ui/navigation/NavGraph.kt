package com.pocketledger.android.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pocketledger.android.ui.components.PocketLedgerBottomNavBar
import com.pocketledger.android.ui.screens.accounts.AccountListScreen
import com.pocketledger.android.ui.screens.accounts.AccountListViewModel
import com.pocketledger.android.ui.screens.add_transaction.AddTransactionScreen
import com.pocketledger.android.ui.screens.add_transaction.AddTransactionViewModel
import com.pocketledger.android.ui.screens.budgets.BudgetListScreen
import com.pocketledger.android.ui.screens.budgets.BudgetListViewModel
import com.pocketledger.android.ui.screens.categories.CategoryListScreen
import com.pocketledger.android.ui.screens.categories.CategoryListViewModel
import com.pocketledger.android.ui.screens.dashboard.DashboardScreen
import com.pocketledger.android.ui.screens.dashboard.DashboardViewModel
import com.pocketledger.android.ui.screens.reports.ReportsScreen
import com.pocketledger.android.ui.screens.reports.ReportsViewModel
import com.pocketledger.android.ui.screens.settings.SettingsScreen
import com.pocketledger.android.ui.screens.settings.SettingsViewModel
import com.pocketledger.android.ui.screens.transactions.TransactionListScreen
import com.pocketledger.android.ui.screens.transactions.TransactionListViewModel

object NavRoutes {
    const val DASHBOARD = "dashboard"
    const val TRANSACTIONS = "transactions"
    const val BUDGETS = "budgets"
    const val REPORTS = "reports"
    const val SETTINGS = "settings"
    const val ACCOUNTS = "accounts"
    const val CATEGORIES = "categories"
    const val ADD_EDIT_TRANSACTION = "add_edit_transaction?transactionId={transactionId}"

    fun buildAddEditTransactionRoute(transactionId: Long? = null): String {
        return if (transactionId != null && transactionId > 0) {
            "add_edit_transaction?transactionId=$transactionId"
        } else {
            "add_edit_transaction?transactionId=0"
        }
    }
}

@Composable
fun PocketLedgerNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        NavRoutes.DASHBOARD,
        NavRoutes.TRANSACTIONS,
        NavRoutes.BUDGETS,
        NavRoutes.REPORTS,
        NavRoutes.SETTINGS,
        NavRoutes.ACCOUNTS,
        NavRoutes.CATEGORIES
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                PocketLedgerBottomNavBar(
                    currentRoute = currentRoute,
                    onItemSelected = { item ->
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(NavRoutes.DASHBOARD) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.DASHBOARD,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.DASHBOARD) {
                val viewModel: DashboardViewModel = hiltViewModel()
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToAddTransaction = {
                        navController.navigate(NavRoutes.buildAddEditTransactionRoute())
                    }
                )
            }

            composable(NavRoutes.TRANSACTIONS) {
                val viewModel: TransactionListViewModel = hiltViewModel()
                TransactionListScreen(
                    viewModel = viewModel,
                    onNavigateToAddTransaction = {
                        navController.navigate(NavRoutes.buildAddEditTransactionRoute())
                    },
                    onNavigateToEditTransaction = { txId ->
                        navController.navigate(NavRoutes.buildAddEditTransactionRoute(txId))
                    }
                )
            }

            composable(NavRoutes.BUDGETS) {
                val viewModel: BudgetListViewModel = hiltViewModel()
                BudgetListScreen(viewModel = viewModel)
            }

            composable(NavRoutes.REPORTS) {
                val viewModel: ReportsViewModel = hiltViewModel()
                ReportsScreen(viewModel = viewModel)
            }

            composable(NavRoutes.SETTINGS) {
                val viewModel: SettingsViewModel = hiltViewModel()
                SettingsScreen(viewModel = viewModel)
            }

            composable(NavRoutes.ACCOUNTS) {
                val viewModel: AccountListViewModel = hiltViewModel()
                AccountListScreen(viewModel = viewModel)
            }

            composable(NavRoutes.CATEGORIES) {
                val viewModel: CategoryListViewModel = hiltViewModel()
                CategoryListScreen(viewModel = viewModel)
            }

            composable(
                route = NavRoutes.ADD_EDIT_TRANSACTION,
                arguments = listOf(
                    navArgument("transactionId") {
                        type = NavType.LongType
                        defaultValue = 0L
                    }
                )
            ) {
                val viewModel: AddTransactionViewModel = hiltViewModel()
                AddTransactionScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
