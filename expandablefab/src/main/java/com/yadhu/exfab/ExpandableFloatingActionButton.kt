package com.yadhu.exfab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * ExpandableFloatingActionButton is a FAB which shows
 * provides some extra menu items on clicking on it. It gets the menu Items
 * from the user and shows on clicking on the Main FAB(Anchor FAB)
 *
 * @param expansionType Defines the type of the expansion. it can be [Horizontal],
 * [Vertical], [Circular]
 * @param menuItems Defines the menu items which needs to be shown to the user
 * on clicking on the Anchor FAB. Menu Items can be of different types,
 * [Horizontal] - the menu items should be of [HorizontalMenuItem]
 * [Vertical] - the menu items should be of [VerticalMenuItem]
 * [Circular] - the menu items should be of [CircularMenuItem]
 * if the type does not match then this component would throw an exception
 * @param modifier it is for customizing the root layout of this Component.
 * @param fabMenuAnchor it is for customizing the Anchor FAb
 * @param state it is for updating the expansion and collapse of the menu items from outside
 * @param visibilityChangeCallback it provides the update of expansion and collapsing of menu items
 * @param expandedItemView it is for customizing the menu item Component, by default the component
 * is a FAB, but it can be changed to anything as user likes.
 */
@Composable
fun ExpandableFloatingActionButton(
    expansionType: ExpansionType,
    menuItems: List<IFabMenuItem>,
    modifier: Modifier = Modifier,
    fabMenuAnchor: IFabMenuAnchor? = null,
    state: Boolean = false,
    visibilityChangeCallback: ((Boolean) -> Unit)? = null,
    expandedItemView: @Composable ((IFabMenuItem) -> Unit)? = null
) {

    var expanded by remember { mutableStateOf(state) }
    fun toggle() {
        expanded = !expanded
        visibilityChangeCallback?.invoke(expanded)
    }

    when (expansionType) {
        is Horizontal -> {
            HorizontalExpandedView(
                fabMenuAnchor = fabMenuAnchor,
                expanded = expanded,
                menuItems = menuItems,
                modifier = modifier,
                toggleCallback = ::toggle,
                content = expandedItemView
            )
        }

        is Vertical -> {
            VerticalExpandedView(
                fabMenuAnchor = fabMenuAnchor,
                expanded = expanded,
                toggleCallback = ::toggle,
                menuItems = menuItems,
                modifier = modifier,
                content = expandedItemView
            )
        }

        is Circular -> {
            CircularExpandedView(
                radius = expansionType.radius,
                fabMenuAnchor = fabMenuAnchor,
                expanded = expanded,
                toggleCallback = ::toggle,
                menuItems = menuItems
            )
        }

        None -> {
            AnchorView()
        }
    }
}

/**
 * This is the component for showing Menu Items vertically,
 *
 * @param expanded state of expansion, whether expanded ot collapsed
 * @param fabMenuAnchor Object for customizing the anchor view
 * @param menuItems menu items to show, when user clicks the anchor view of type [VerticalMenuItem]
 * @param modifier for customizing the root container
 * @param toggleCallback callback for updating the expansion status
 * @param content composable for each menu item
 */
@Composable
private fun VerticalExpandedView(
    expanded: Boolean,
    fabMenuAnchor: IFabMenuAnchor?,
    menuItems: List<IFabMenuItem>,
    modifier: Modifier,
    toggleCallback: () -> Unit,
    content: @Composable ((IFabMenuItem) -> Unit)? = null
) {

    Column(
        modifier = modifier.wrapContentSize()
    ) {

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            LazyColumn {
                items(count = menuItems.size) { index ->
                    if (menuItems[index] !is VerticalMenuItem) {
                        throw IllegalArgumentException("Wrong type of MenuItem passed")
                    }

                    if (content != null) {
                        content(menuItems[index])
                    } else {
                        DefaultExpandedItemView(menuItem = menuItems[index])
                    }
                }
            }
        }

        AnchorView(
            fabMenuAnchor = fabMenuAnchor,
            toggleCallback = toggleCallback
        )
    }
}

/**
 * This is the component for showing Menu Items horizontally,
 *
 * @param expanded state of expansion, whether expanded ot collapsed
 * @param fabMenuAnchor Object for customizing the anchor view
 * @param menuItems menu items to show, when user clicks the anchor view of type [HorizontalMenuItem]
 * @param modifier for customizing the root container
 * @param toggleCallback callback for updating the expansion status
 * @param content composable for each menu item
 */
@Composable
private fun HorizontalExpandedView(
    expanded: Boolean,
    fabMenuAnchor: IFabMenuAnchor?,
    menuItems: List<IFabMenuItem>,
    modifier: Modifier = Modifier,
    toggleCallback: () -> Unit,
    content: @Composable ((IFabMenuItem) -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
    ) {

        AnimatedVisibility(
            visible = expanded,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            LazyRow(modifier = Modifier.wrapContentSize()) {
                items(count = menuItems.size) { index ->
                    if (menuItems[index] !is HorizontalMenuItem) {
                        throw IllegalArgumentException("Wrong type of MenuItem passed")
                    }

                    if (content != null) {
                        content(menuItems[index])
                    } else {
                        DefaultExpandedItemView(menuItem = menuItems[index])
                    }
                }
            }
        }

        AnchorView(fabMenuAnchor = fabMenuAnchor) {
            toggleCallback()
        }
    }
}

/**
 * This is the component for showing Menu Items around the anchor view,
 * the menu items are positioned based the angles mentioned in the [CircularMenuItem]
 * all the menu items will be FAB and user won't be able to customize the view
 *
 * @param radius it is the distance between the anchor view and the menu item
 * @param expanded state of expansion, whether expanded ot collapsed
 * @param fabMenuAnchor Object for customizing the anchor view
 * @param menuItems menu items to show, when user clicks the anchor view of type [CircularMenuItem]
 * @param modifier for customizing the root container
 * @param toggleCallback callback for updating the expansion status
 */
@Composable
private fun CircularExpandedView(
    radius: Int,
    expanded: Boolean,
    fabMenuAnchor: IFabMenuAnchor?,
    menuItems: List<IFabMenuItem>,
    modifier: Modifier = Modifier,
    toggleCallback: () -> Unit,
) {
    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = expanded,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            menuItems.forEach { item ->
                val fab = item as? CircularMenuItem ?: throw IllegalArgumentException()

                val radian = Math.toRadians(fab.angle - 180)
                val x = (radius * cos(radian)).dp
                val y = (radius * sin(radian)).dp

                FloatingActionButton(
                    onClick = {
                        fab.action()
                        toggleCallback()
                    },
                    shape = fab.shape,
                    backgroundColor = fab.backgroundColor,
                    modifier = fab.modifier
                        .offset(x = x, y = y)
                        .size(ChildFabSize)
                ) {
                    Image(
                        imageVector = fab.icon,
                        contentDescription = null
                    )
                }
            }
        }

        AnchorView(fabMenuAnchor, toggleCallback)
    }
}

/**
 * It is the Anchor view
 *
 * @param fabMenuAnchor for customizing the Anchor view
 * @param toggleCallback callback for updating the expanded state
 */
@Composable
private fun AnchorView(
    fabMenuAnchor: IFabMenuAnchor? = null,
    toggleCallback: (() -> Unit)? = null
) {
    FloatingActionButton(
        onClick = { toggleCallback?.invoke() },
        backgroundColor = fabMenuAnchor?.color ?: Color.Green,
        shape = fabMenuAnchor?.shape ?: CircleShape,
        modifier = fabMenuAnchor?.modifier ?: Modifier.size(ParentFabSize)
    ) {
        Icon(
            imageVector = fabMenuAnchor?.icon ?: Icons.Filled.Add,
            contentDescription = "Anchor"
        )
    }
}

/**
 * This is the Default menu item
 *
 * @param menuItem for customizing the Menu items
 */
@Composable
private fun DefaultExpandedItemView(menuItem: IFabMenuItem) {
    Box(modifier = Modifier.padding(DefaultFabPadding)) {
        FloatingActionButton(
            onClick = { menuItem.action() },
            backgroundColor = menuItem.backgroundColor ?: MaterialTheme.colors.primary,
            shape = menuItem.shape ?: CircleShape,
            modifier = menuItem.modifier
        ) {
            Icon(imageVector = menuItem.icon, contentDescription = null)
        }
    }
}

private val ParentFabSize = 56.dp
private val ChildFabSize = 48.dp
private val DefaultFabPadding = 8.dp