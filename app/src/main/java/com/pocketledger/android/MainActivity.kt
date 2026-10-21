package com.pocketledger.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pocketledger.android.ui.navigation.PocketLedgerNavGraph
import com.pocketledger.android.ui.theme.PocketLedgerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocketLedgerTheme {
                PocketLedgerNavGraph()
            }
        }
    }
}
