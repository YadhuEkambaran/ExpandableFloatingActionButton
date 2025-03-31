# ExpandableFloatingActionButton
This is a library for providing an expandable FloatingActionButton to Jetpack compose

This component supports three types of Expansion,

Circular:

<video width="320" height="240" controls>
  <source src="demo/CircularMenuItem.webm" type="video/webm">
  Your browser does not support the video tag.
</video>

![Circular Expansion](demo/CircularMenuItem.webm)

Horizontal:

![Horizontal Expansion](demo/Horizontal_Expanded%20FABs.png)

Vertical: 

![Vertical Expansion](demo/Vertical_Expanded%20FAB.png)


Usage Example: 

```kotlin
            ExpandableFloatingActionButton(
                state = expanded,
                expansionType = Circular(radius = 100),
                fabMenuAnchor = FabMenuAnchor(
                    icon = Icons.Filled.Add,
                    color = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                ),
                menuItems = generateMenuItem(), visibilityChangeCallback = { state ->
                    Log.d("MainScreen", "Expanded or collapsed $state")
                },
                modifier = Modifier.weight(1f)
            ) { menuItem ->
                Box(modifier = Modifier.padding(bottom = 0.dp)) {
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



