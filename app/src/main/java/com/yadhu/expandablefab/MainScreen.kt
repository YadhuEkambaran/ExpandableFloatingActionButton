package com.yadhu.expandablefab

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fax
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yadhu.exfab.Circular
import com.yadhu.exfab.CircularMenuItem
import com.yadhu.exfab.ExpandableFloatingActionButton
import com.yadhu.exfab.FabMenuAnchor
import com.yadhu.exfab.Horizontal
import com.yadhu.exfab.HorizontalMenuItem
import com.yadhu.exfab.IFabMenuItem
import com.yadhu.exfab.Vertical
import com.yadhu.exfab.VerticalMenuItem

private const val TAG = "MainScreen"
@Composable
fun MainScreen() {
    var expanded by remember {
        mutableStateOf(false)
    }

    Scaffold(floatingActionButton = {
        Row {
            ExpandableFloatingActionButton(
                state = expanded,
                expansionType = Vertical,
                fabMenuAnchor = FabMenuAnchor(
                    icon = Icons.Filled.Add,
                    color = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                ),
                menuItems = generateVerticalMenuItem(), visibilityChangeCallback = { state ->
                    Log.d("MainScreen", "Expanded or collapsed $state")
                },
                modifier = Modifier.wrapContentSize()
            ) { menuItem ->
                Box(modifier = Modifier.padding(bottom = 8.dp)) {
                    FloatingActionButton(
                        onClick = { menuItem.action()
                            expanded = !expanded},
                        backgroundColor = menuItem.backgroundColor ?: Color.Green,
                        shape = menuItem.shape ?: CircleShape,
                        modifier = menuItem.modifier
                    ) {
                        Icon(imageVector = menuItem.icon, contentDescription = null)
                    }
                }
            }

            ExpandableFloatingActionButton(
                state = expanded,
                expansionType = Horizontal,
                fabMenuAnchor = FabMenuAnchor(
                    icon = Icons.Filled.Add,
                    color = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                ),
                menuItems = generateHorizontalMenuItem(), visibilityChangeCallback = { state ->
                    Log.d("MainScreen", "Expanded or collapsed $state")
                },
                modifier = Modifier.fillMaxWidth()
            ) { menuItem ->
                Box(modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)) {
                    FloatingActionButton(
                        onClick = { menuItem.action()
                            expanded = !expanded},
                        backgroundColor = menuItem.backgroundColor ?: Color.Green,
                        shape = menuItem.shape ?: CircleShape,
                        modifier = menuItem.modifier
                    ) {
                        Icon(imageVector = menuItem.icon, contentDescription = null)
                    }
                }
            }

//            ExpandableFloatingActionButton(
//                state = expanded,
//                expansionType = Circular(radius = 100),
//                fabMenuAnchor = FabMenuAnchor(
//                    icon = Icons.Filled.Add,
//                    color = Color.White,
//                    shape = CircleShape,
//                    modifier = Modifier
//                ),
//                menuItems = generateMenuItem(), visibilityChangeCallback = { state ->
//                    Log.d("MainScreen", "Expanded or collapsed $state")
//                },
//                modifier = Modifier.weight(1f)
//            ) { menuItem ->
//                Box(modifier = Modifier.padding(bottom = 0.dp)) {
//                    FloatingActionButton(
//                        onClick = { menuItem.action()
//                            expanded = !expanded},
//                        backgroundColor = menuItem.backgroundColor ?: Color.Green,
//                        shape = menuItem.shape ?: CircleShape,
//                        modifier = menuItem.modifier
//                    ) {
//                        Icon(imageVector = menuItem.icon, contentDescription = null)
//                    }
//                }
//            }
        }

    }) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.Gray)
        ) {

        }
    }
}

private fun generateMenuItem(): List<IFabMenuItem> {
    val fabs = ArrayList<IFabMenuItem>()
    val menuItem1 =
        CircularMenuItem(
            icon = Icons.Filled.Favorite,
            action = { Log.d(TAG, "Menu Item 1 clicked") },
            shape = CircleShape,
            backgroundColor = Color.White,
            angle = 0.0
        )
    val menuItem2 = CircularMenuItem(
        icon = Icons.Filled.Fax,
        action = { Log.d(TAG, "Menu Item 2 clicked")},
        shape = CircleShape,
        backgroundColor = Color.White,
        angle = 45.0
    )
    val menuItem3 = CircularMenuItem(
        icon = Icons.Filled.AccountBox,
        action = { Log.d(TAG, "Menu Item 3 clicked")},
        shape = CircleShape,
        backgroundColor = Color.White,
        angle = 90.0
    )
    fabs.add(menuItem1)
    fabs.add(menuItem2)
    fabs.add(menuItem3)

    return fabs
}

private fun generateHorizontalMenuItem(): List<IFabMenuItem> {
    val fabs = ArrayList<IFabMenuItem>()
    val menuItem1 =
        HorizontalMenuItem(
            icon = Icons.Filled.Favorite,
            action = { Log.d(TAG, "Menu Item 1 clicked") },
            shape = CircleShape,
            backgroundColor = Color.White
        )
    val menuItem2 = HorizontalMenuItem(
        icon = Icons.Filled.Fax,
        action = { Log.d(TAG, "Menu Item 2 clicked")},
        shape = CircleShape,
        backgroundColor = Color.White
    )
    val menuItem3 = HorizontalMenuItem(
        icon = Icons.Filled.AccountBox,
        action = { Log.d(TAG, "Menu Item 3 clicked")},
        shape = CircleShape,
        backgroundColor = Color.White
    )
    fabs.add(menuItem1)
    fabs.add(menuItem2)
    fabs.add(menuItem3)

    return fabs
}

private fun generateVerticalMenuItem(): List<IFabMenuItem> {
    val fabs = ArrayList<IFabMenuItem>()
    val menuItem1 = VerticalMenuItem(
            icon = Icons.Filled.Favorite,
            action = { Log.d(TAG, "Menu Item 1 clicked") },
            shape = CircleShape,
            backgroundColor = Color.White
        )
    val menuItem2 = VerticalMenuItem(
        icon = Icons.Filled.Fax,
        action = { Log.d(TAG, "Menu Item 2 clicked")},
        shape = CircleShape,
        backgroundColor = Color.White
    )
    val menuItem3 = VerticalMenuItem(
        icon = Icons.Filled.AccountBox,
        action = { Log.d(TAG, "Menu Item 3 clicked")},
        shape = CircleShape,
        backgroundColor = Color.White
    )
    fabs.add(menuItem1)
    fabs.add(menuItem2)
    fabs.add(menuItem3)

    return fabs
}