package com.example.demo.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var setupIntentClientSecret: String
    lateinit var customerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        val btnOrder = findViewById<Button>(R.id.btnOrder)

        btnOrder.setOnClickListener {
            runBlocking {
                "http://10.0.2.2:8000/api/payment/intent".httpGet().responseJson { _, _, result ->
                    if (result is Result.Success) {
                        val responseJson = result.get().obj()
                        Log.i("[FRANZ] RESPONSE", "$responseJson")
                        setupIntentClientSecret = responseJson.getString("setupIntent")
                        customerConfig = PaymentSheet.CustomerConfiguration(
                            responseJson.getString("customer"),
                            responseJson.getString("ephemeralKey")
                        )
                        customerId = responseJson.getString("customer")
                        val publishableKey = responseJson.getString("publishableKey")
                        PaymentConfiguration.init(applicationContext, publishableKey)

                        presentPaymentSheet()
                    }

                    if (result is Result.Failure) {
                        Log.i("[FRANZ] FAIL", "${result.error}")
                    }
                }
            }
        }
    }

    fun presentPaymentSheet() {
        paymentSheet.presentWithSetupIntent(
            setupIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "My merchant name",
                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business handles
                // delayed notification payment methods like US bank accounts.
                allowsDelayedPaymentMethods = false
            )
        )
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.i("[FRANZ] CANCELED", "CANCELED")
            }
            is PaymentSheetResult.Failed -> {
                Log.i("[FRANZ] FAILED", "${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen
                Log.i("[FRANZ] COMPLETED", "$Result")

                "http://10.0.2.2:8000/api/payment/customer/${customerId}".httpPost().responseJson { _, _, result ->
                    if (result is Result.Success) {
                        Log.i("[FRANZ] RESPONSE", "${Result}")
                    }

                    if (result is Result.Failure) {
                        Log.i("[FRANZ] FAIL", "${result.error}")
                    }
                }
            }
        }
    }
}