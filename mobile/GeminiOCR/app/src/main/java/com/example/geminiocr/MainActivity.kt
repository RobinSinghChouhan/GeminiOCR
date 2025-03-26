package com.example.geminiocr

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geminiocr.ui.theme.GeminiOCRTheme
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeminiOCRTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Form(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Form(modifier: Modifier = Modifier) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("Waiting") }
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            question,
            onValueChange = {
                question=it
            },
            modifier = modifier,
            label = {
                Text("Enter Query")
            }
        )
        Text(text = answer, fontSize = 24.sp)
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        ElevatedButton(
            onClick = {
                val inputGemini = question
                val apiInterface = RetrofitClient.getInstance().create(GeminiInterface::class.java)
                apiInterface.getResponse(inputGemini).enqueue(object :Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        Log.i("MSG: ",response.body().toString())
                        answer = response.body().toString()
                        Toast.makeText(
                            context,
                            "Success!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.i("Error",call.toString())
                        Toast.makeText(
                            context,
                            "Error!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })

            }
        ) {
            Text("Ask")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormPreview()
{
    Form()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GeminiOCRTheme {
        Greeting("Android")
    }
}