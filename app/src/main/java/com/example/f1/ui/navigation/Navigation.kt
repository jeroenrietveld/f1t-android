package com.example.f1.ui.navigation

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.os.ConfigurationCompat
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.f1.R
import com.example.f1.ui.MainDestinations
import com.example.f1.ui.theme.F1Theme
import com.google.accompanist.insets.navigationBarsPadding

private val TextIconSpacing = 2.dp
private val NavigationHeight = 56.dp
private val NavigationLabelTransformOrigin = TransformOrigin(0f, 0.5f)
private val NavigationIndicatorShape = RoundedCornerShape(percent = 50)
private val NavigationItemPadding = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

enum class NavigationSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.navigation_home, Icons.Default.Home, "home/home"),
    TELEMETRY(R.string.navigation_telemetry, Icons.Sharp.Build, "home/telemetry"),
}

fun NavGraphBuilder.addHomeGraph(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable(NavigationSections.HOME.route) { from ->
        Home(onTrackClick = { }, modifier)
    }
    composable(NavigationSections.TELEMETRY.route) { from ->
        Telemetry(
            onClickNewSession = {
                navController.navigate(MainDestinations.NEW_TELEMETRY_SESSION_ROUTE)
            },
            modifier
        )
    }
}

@Composable
fun NavigationBar(
    navController: NavController,
    tabs: Array<NavigationSections>
//    color: Color = JetsnackTheme.colors.iconPrimary,
//    contentColor: Color = JetsnackTheme.colors.iconInteractive
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sections = remember { NavigationSections.values() }
    val routes = remember { sections.map { it.route } }

    if (currentRoute in routes) {
        val currentSection = sections.first { it.route == currentRoute }

        Surface {
            val springSpec = SpringSpec<Float>(
                stiffness = 800f,
                dampingRatio = 0.8f
            )
            NavigationBarLayout(
                selectedIndex = currentSection.ordinal,
                itemCount = routes.size,
                indicator = { NavigationBarIndicator() },
                animSpec = springSpec,
                modifier = Modifier.navigationBarsPadding(start = false, end = false)
            ) {
                tabs.forEach { section ->
                    val selected = section == currentSection
                    val tint by animateColorAsState(
                        if (selected) {
                            MaterialTheme.colors.primary
                        } else {
                            MaterialTheme.colors.primaryVariant
                        }
                    )

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = section.icon,
                                tint = tint,
                                contentDescription = null
                            )
                        },
                        text = {
                               Text(
                                   text = stringResource(section.title).uppercase(
                                       ConfigurationCompat.getLocales(
                                           LocalConfiguration.current
                                       ).get(0)
                                   ),
                                   color = tint,
                                   style = MaterialTheme.typography.button,
                                   maxLines = 1
                               )
                        },
                        selected = selected,
                        onSelected = {
                                     if (section.route != currentRoute ) {
                                         navController.navigate(section.route) {
                                             launchSingleTop = true
                                             restoreState = true
                                             popUpTo(findStartDestination(navController.graph).id) {
                                                 saveState = true
                                             }
                                         }
                                     }
                        },
                        animSpec = springSpec,
                        // modifier = Modifier
                    )
                }
            }
        }
    }
}

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

/**
 * Copied from similar function in NavigationUI.kt
 *
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
 */
private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

@Composable
fun NavigationBarLayout(
    selectedIndex: Int,
    itemCount: Int,
    animSpec: AnimationSpec<Float>,
    indicator: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val selections = remember(itemCount) {
        List(itemCount) { i ->
            Animatable(if (i == selectedIndex) 1f else 0f)
        }
    }
    selections.forEachIndexed { index, selection ->
        val target = if(index == selectedIndex) 1f else 0f
        LaunchedEffect(target, animSpec) {
            selection.animateTo(target, animSpec)
        }
    }

    // Animate the position of the indicator
    val indicatorIndex = remember { Animatable(0f) }
    val targetIndicatorIndex = selectedIndex.toFloat()
    LaunchedEffect(targetIndicatorIndex) {
        indicatorIndex.animateTo(targetIndicatorIndex, animSpec)
    }

    Layout(
        modifier = modifier.height(NavigationHeight),
        content = {
            content()
            Box(Modifier.layoutId("indicator"), content = indicator)
        }
    ) { measurables, constraints ->
        check(itemCount == (measurables.size - 1))

        // Divide the width into n+1 slots and give the selected item 2 slots
        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = 2 * unselectedWidth
        val indicatorMeasurable = measurables.first { it.layoutId == "indicator" }

        val itemPlaceables = measurables
            .filterNot { it == indicatorMeasurable }
            .mapIndexed { index, measurable ->
                // Animate item's width based upon the selection amount
                val width = lerp(unselectedWidth, selectedWidth, selections[index].value)
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width
                    )
                )
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
            constraints.copy(
                minWidth = selectedWidth,
                maxWidth = selectedWidth
            )
        )

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
        ) {
            val indicatorLeft = indicatorIndex.value * unselectedWidth
            indicatorPlaceable.placeRelative(x = indicatorLeft.toInt(), y = 0)
            var x = 0
            itemPlaceables.forEach { placeable ->
                placeable.placeRelative(x = x, y = 0)
                x += placeable.width
            }
        }
    }
}

@Composable
fun NavigationBarItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.selectable(selected = selected, onClick = onSelected),
        contentAlignment = Alignment.Center
    ) {
        val animationProgress by animateFloatAsState(if (selected) 1f else 0f, animSpec)
        NavigationBarItemLayout(icon = icon, text = text, animationProgress = animationProgress)
    }
}

@Composable
fun NavigationBarItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
) {
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .layoutId("icon")
                    .padding(horizontal = TextIconSpacing),
                content = icon
            )
            val scale = lerp(0.6f, 1f, animationProgress)
            Box(
                modifier = Modifier
                    .layoutId("text")
                    .padding(horizontal = TextIconSpacing)
                    .graphicsLayer {
                        alpha = animationProgress
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = NavigationLabelTransformOrigin
                    },
                content = text
            )
        }
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)

        placeTextAndIcon(
            textPlaceable,
            iconPlaceable,
            constraints.maxWidth,
            constraints.maxHeight,
            animationProgress
        )
    }
}

private fun MeasureScope.placeTextAndIcon(
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
): MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2

    val textWidth = textPlaceable.width * animationProgress
    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = iconX + iconPlaceable.width

    return layout(width, height) {
        iconPlaceable.placeRelative(iconX.toInt(), iconY)
        if (animationProgress != 0f) {
            textPlaceable.placeRelative(textX.toInt(), textY)
        }
    }
}

@Composable
fun NavigationBarIndicator(
    strokeWidth: Dp = 2.dp,
    color: Color = MaterialTheme.colors.primarySurface,
    shape: Shape = NavigationIndicatorShape
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .then(NavigationItemPadding)
            .border(strokeWidth, color, shape)
    )
}

@Preview
@Composable
private fun NavigationBarPreview() {
    F1Theme {
        NavigationBar(
            navController = rememberNavController(),
            tabs = NavigationSections.values()
        )
    }
}