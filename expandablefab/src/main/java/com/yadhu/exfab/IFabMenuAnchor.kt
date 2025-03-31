package com.yadhu.exfab

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * This class provides the behaviour of the Anchor FAB.
 * Using this class, the Anchor FAB can be customized.
 */
sealed interface IFabMenuAnchor {
    val icon: ImageVector
    val color: Color
    val shape: Shape
    val modifier: Modifier
}

private data class IFabMenuAnchorImpl(
    override val icon: ImageVector,
    override val color: Color,
    override val shape: Shape,
    override val modifier: Modifier
) : IFabMenuAnchor

/**
 * Returns a new [IFabMenuAnchor]
 */
fun FabMenuAnchor(
    icon: ImageVector,
    color: Color,
    shape: Shape,
    modifier: Modifier = Modifier
): IFabMenuAnchor = IFabMenuAnchorImpl(icon, color, shape, modifier)