package com.yadhu.exfab

import android.os.Bundle
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface IFabMenuItem {
    val icon: ImageVector
    val action: () -> Unit
    val shape: Shape?
    val backgroundColor: Color?
    val modifier: Modifier
}

data class HorizontalMenuItem (
    override val icon: ImageVector,
    override val action: () -> Unit,
    override val modifier: Modifier = Modifier,
    override val shape: Shape? = null,
    override val backgroundColor: Color? = null,
    val extras: Bundle? = null // To add more custom data
) : IFabMenuItem

data class VerticalMenuItem (
    override val icon: ImageVector,
    override val action: () -> Unit,
    override val modifier: Modifier = Modifier,
    override val shape: Shape? = null,
    override val backgroundColor: Color? = null,
    val extras: Bundle? = null // To add more custom data
) : IFabMenuItem

data class CircularMenuItem (
    override val icon: ImageVector,
    override val action: () -> Unit,
    override val modifier: Modifier = Modifier,
    override val shape: Shape,
    override val backgroundColor: Color,
    val angle: Double
) : IFabMenuItem {
    init {
        if (angle >= 360) throw IllegalArgumentException("Wrong angle is passed")
    }
}

