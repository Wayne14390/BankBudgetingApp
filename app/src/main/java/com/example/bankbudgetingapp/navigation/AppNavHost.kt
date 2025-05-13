package com.example.bankbudgetingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bankbudgetingapp.ui.theme.screens.SplashScreen
import com.example.bankbudgetingapp.ui.theme.screens.profile.UpdateProfileScreen
import com.example.bankbudgetingapp.ui.theme.screens.analysis.AnalysisScreen
import com.example.bankbudgetingapp.ui.theme.screens.budget.AddBudgetScreen
import com.example.bankbudgetingapp.ui.theme.screens.budget.UpdateBudgetScreen
import com.example.bankbudgetingapp.ui.theme.screens.home.HomeScreen
import com.example.bankbudgetingapp.ui.theme.screens.login.LoginScreen
import com.example.bankbudgetingapp.ui.theme.screens.profile.ViewProfileScreen
import com.example.bankbudgetingapp.ui.theme.screens.register.RegisterScreen
import com.example.bankbudgetingapp.ui.theme.screens.budget.ViewBudgetScreen


@Composable
fun AppNavHost(navController: NavHostController= rememberNavController(),startDestination:String= ROUTE_SPLASH){
    NavHost(navController = navController, startDestination = startDestination) {
        composable(ROUTE_SPLASH){ SplashScreen  {
            navController.navigate(ROUTE_REGISTER){
                popUpTo(ROUTE_SPLASH){inclusive=true}} }
        }
        composable(ROUTE_REGISTER) { RegisterScreen(navController)}
        composable(ROUTE_LOGIN) { LoginScreen(navController)}
        composable(ROUTE_HOME) { HomeScreen(navController)}
        composable(ROUTE_ANALYSIS) { AnalysisScreen(navController) }
        composable(UPDATE_PROFILE) { UpdateProfileScreen(navController) }
        composable(VIEW_BUDGET) {     ViewBudgetScreen(navController) }
        composable(VIEW_PROFILE) { ViewProfileScreen(navController) }
        composable(ADD_BUDGET) { AddBudgetScreen(navController) }
        composable("$UPDATE_BUDGET/{budgetId}") {
                passedData -> UpdateBudgetScreen(
            navController, passedData.arguments?.getString("budgetId")!! )
        }
    }
    }





