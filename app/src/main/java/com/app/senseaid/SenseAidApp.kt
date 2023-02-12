package com.app.senseaid

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.senseaid.Routes.ADD_REVIEW_SCREEN
import com.app.senseaid.Routes.AVERAGE_RATING
import com.app.senseaid.Routes.HOME_SCREEN
import com.app.senseaid.Routes.LOCATION_SCREEN
import com.app.senseaid.Routes.DEFAULT_ID
import com.app.senseaid.Routes.LOCATION_ID
import com.app.senseaid.Routes.REVIEW_ID
import com.app.senseaid.Routes.REVIEW_SCREEN
import com.app.senseaid.Routes.TOTAL_RATING
import com.app.senseaid.screens.add_review.AddReviewContentScreen
import com.app.senseaid.screens.home.HomeScreen
import com.app.senseaid.screens.location.LocationScreen
import com.app.senseaid.screens.review.ReviewScreen
import com.app.senseaid.ui.theme.SenseAidTheme
import kotlinx.coroutines.CoroutineScope

object Routes {
    const val HOME_SCREEN = "home"
    const val DEFAULT_ID = "-1"
    const val LOCATION_SCREEN = "location"
    const val LOCATION_ID = "locationId"
    const val REVIEW_SCREEN = "review"
    const val REVIEW_ID = "reviewId"
    const val ADD_REVIEW_SCREEN = "addReview"
    const val TOTAL_RATING = "totalRatings"
    const val AVERAGE_RATING = "avgRating"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenseAidApp() {
    val navController: NavHostController = rememberNavController()
    SenseAidTheme {
        Scaffold() { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HOME_SCREEN,
                modifier = Modifier.padding(innerPadding)
            ) {
                senseAidGraph(navController)
            }
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(navController,coroutineScope) { SenseAidAppState(navController, coroutineScope) }

fun NavGraphBuilder.senseAidGraph(navController: NavHostController) {
    composable(HOME_SCREEN) {
        HomeScreen(
            onItemPress = { route -> navController.navigate(route) }
        )
    }

    composable(
        route = "$LOCATION_SCREEN/{$LOCATION_ID}",
        arguments = listOf(navArgument(LOCATION_ID) { type = NavType.StringType })
    ) {
        LocationScreen(
            locationId = it.arguments?.getString(LOCATION_ID) ?: DEFAULT_ID,
            onReviewPress = { route -> navController.navigate(route) },
            onBackPress = { navController.popBackStack() },
            onAddReviewPress = { route -> navController.navigate(route) }
        )
    }

    composable(
        route = "$REVIEW_SCREEN/{$LOCATION_ID}/{$REVIEW_ID}",
        arguments = listOf(
            navArgument(LOCATION_ID) { type = NavType.StringType },
            navArgument(REVIEW_ID) { type = NavType.StringType }
        )
    ) {
        ReviewScreen(
            locationId = it.arguments?.getString(LOCATION_ID) ?: DEFAULT_ID,
            reviewId = it.arguments?.getString(REVIEW_ID) ?: DEFAULT_ID,
            onBackPress = { navController.popBackStack() }
        )
    }

    composable(
        route = "$ADD_REVIEW_SCREEN/{$LOCATION_ID}/{$TOTAL_RATING}/{$AVERAGE_RATING}",
        arguments = listOf(
            navArgument(LOCATION_ID) { type = NavType.StringType },
            navArgument(TOTAL_RATING) { type = NavType.StringType },
            navArgument(AVERAGE_RATING) { type = NavType.StringType }
        )
    ) {
        AddReviewContentScreen(
            locationId = it.arguments?.getString(LOCATION_ID) ?: DEFAULT_ID,
            totalRatings = it.arguments?.getString(TOTAL_RATING) ?: DEFAULT_ID,
            avgRating = it.arguments?.getString(AVERAGE_RATING) ?: DEFAULT_ID,
            onBackPress = { navController.popBackStack() },
            onSubmitPress = { navController.popBackStack() }
        )
    }
}
