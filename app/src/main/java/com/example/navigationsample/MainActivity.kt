package com.example.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationsample.ui.theme.NavigationSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp()
{
    //NavController is the central navigation API.
    //It tracks which destinations the user has visited, and allows the user to move between destinations.
    val navController = rememberNavController()

    //NavHost is a composable designed to hold your layouts. As you navigate through composables,
    //the content within the NavHost changes. Each screen in the navigation has its route:
    //A route is a string that defines the path to your composable. You can think of it as a key that
    // corresponds to a specific destination.
    NavHost(navController = navController, startDestination = "firstscreen" )
    {
        //we establish the screens we have inside the NavHost, defining their routes which is basically
        //the code name for that screen

        //FIRST SCREEN
        composable(route = "firstscreen"){
            FirstScreen {
                //pass in the "name" variable inside the FirstScreen composable into the lambda function
                //append the name variable behind the route of the second screen
                name -> navController.navigate("secondscreen/$name")
            }
        }

        //SECOND SCREEN
        //append the variables you wish to pass in to the second screen behind the base path
        composable(route = "secondscreen/{name}"){
            //create a variable for the variable passed to this screen
            //"it" here refers to the backstack of the app. search the arguments for the key which is name variable
            //that you appended to the route of the second screen. It's value will be assigned to this variable if that key
            //is found in the backstack(all the preceding screens to the current one)
            val name = it.arguments?.getString("name") ?: "no name"
            //When you create the composable screen receiving the variable, create it by passing in the variable you
            //declared above, which contains the value of the variable you passed in from the previous screen

            //Essentially, when you navigate from screen 1 to screen 2, and pass in a variable from screen 1 to screen 2,
            //you are essentially retrieving that variable's value from the backstack first, then composing screen 2, passing
            //in that variable as one of the parameters of screen 2's composable function
            SecondScreen (name, {
                navController.navigate("firstscreen")
            },
            {
                navController.navigate("thirdscreen")
            })
        }

        //THIRD SCREEN
        composable(route = "thirdscreen")
        {
            ThirdScreen {
                navController.navigate("firstscreen")
            }
        }

    }
}
