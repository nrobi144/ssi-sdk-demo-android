package com.nagyrobi.tbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nagyrobi.tbd.ui.theme.SSISDKTheme
import com.nagyrobi.tbd.util.toStringList
import mobile.Mobile

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SSISDKTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background.copy(alpha = 0.7f)
                ) {
                    DidExample()
                }
            }
        }
    }
}

@Composable
fun DidExample() {
    var isKeyTypeSelectorExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val keyTypes = getKeyTypes()
    val selectedKeyType = keyTypes[selectedIndex]
    val didKey = Mobile.generateDIDKey(selectedKeyType).didKey
    val did = didKey?.let { Mobile.expandDIDKey(it)?.decodeToString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = { isKeyTypeSelectorExpanded = true })
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Key Type: $selectedKeyType",
                    modifier = Modifier
                        .padding(16.dp)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            DropdownMenu(
                expanded = isKeyTypeSelectorExpanded,
                onDismissRequest = { isKeyTypeSelectorExpanded = false }) {
                keyTypes.forEachIndexed { index, keyType ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        isKeyTypeSelectorExpanded = false
                    }) {
                        Text(text = keyType)
                    }
                }
            }
        }
        Text(text = didKey ?: "Unable to generate DID key")
        Text(text = did ?: "Unable to generate DID")
    }
}

fun getKeyTypes() = Mobile.getSupportedKeyTypes().toStringList()

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SSISDKTheme {
        DidExample()
    }
}