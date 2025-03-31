package com.yadhu.exfab

/**
 * ExpansionType gives the option of different types of expansion
 * that is supported by the ExpandableFloatingActionButton.
 */
sealed class ExpansionType

/**
 * Shows the expanded menu items in a horizontal manner
 */
data object Horizontal: ExpansionType()

/**
 * Shows the expanded menu items in vertical manner
 */
data object  Vertical: ExpansionType()

/**
 * Shows the menu items in a circular manner. The radius parameter
 * passed is the radius of the circle.
 */
data class Circular(val radius: Int): ExpansionType()

/**
 * This type will only shows single Floating action button
 */
data object None: ExpansionType()



