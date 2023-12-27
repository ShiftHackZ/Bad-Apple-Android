@file:OptIn(ExperimentalMaterial3Api::class)

package com.shifthackz.badapple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shifthackz.badapple.ui.screen.BadAppleScreen
import com.shifthackz.badapple.ui.screen.BadAppleViewModel
import com.shifthackz.badapple.ui.screen.BadAppleViewModelFactory
import com.shifthackz.catppuccin.compose.CatppuccinMaterial
import com.shifthackz.catppuccin.compose.CatppuccinTheme
import com.shifthackz.catppuccin.palette.Catppuccin

class BadAppleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        val viewModel: BadAppleViewModel by viewModels {
            BadAppleViewModelFactory(this)
        }
        setContent {
            CatppuccinTheme.Palette(
                palette = CatppuccinMaterial.Frappe(
                    primary = Catppuccin.Frappe.Rosewater,
                ),
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(text = "Bad Apple")
                                },
                            )
                        }
                    ) { padding ->
                        val state by viewModel.uiState.collectAsStateWithLifecycle()
                        BadAppleScreen(
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize(),
                            state = state,
                            listener = viewModel,
                        )
                    }
                }
            }
        }
    }
}
