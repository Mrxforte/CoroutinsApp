package com.example.coroutinsapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadUI()
    }

    private fun loadUI() {
        //        oddiy misol coroutinsiz
        fun simple() {
            for (i in 1..100) {
                Log.d("TAG", "simple: ${Thread.currentThread().name}")
            }
        }

        //        coroutin io misolida
        fun ioExample() {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("TAG", "io misolida: ${Thread.currentThread().name}")
            }
        }

        //        using the dispacher main
        fun mainDispacher() {
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("TAG", "main misolida: ${Thread.currentThread().name}")

            }
        }

        //        Swithching to the main thread
        fun switchToMainThread() {
            CoroutineScope(Dispatchers.IO).launch {
                for (i in 1..1000) {
                    println(i)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Hello we are swithced to the main thread",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

//            parallel shakilda foydalanish
            CoroutineScope(Dispatchers.IO).launch {

                suspend fun bir(): Int {
                    delay(3000)
                    println("Bir")
                    return 1
                }

                suspend fun ikki(): Int {
                    delay(1000)
                    println("Ikki")
                    return 2
                }

                val one = async {

                    bir()

                }

                val two = async {
                    ikki()

                }
                val result = one.await() + two.await()
//                    calling the one and two

            }
        }

    }
}