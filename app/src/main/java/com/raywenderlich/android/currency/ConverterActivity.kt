package com.raywenderlich.android.currency

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_converter.*
import java.security.CryptoPrimitive


class ConverterActivity : AppCompatActivity() {

    companion object {
        private val currencies = listOf(Dollar(), Euro(), Crypto())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                currencies.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currency.adapter = adapter

        convert.setOnClickListener {
            val low = currencyFromSelection()

            low.amount = lowAmount.text.toString().toDouble()

            lowAmount.text.toString().toDouble()

            lowAmountInDollars.text = String.format("$%.2f", low.totalValueInDollars())
        }
    }


    fun currencyFromSelection() =
            when (currencies[currency.selectedItemPosition]) {
                is Dollar -> Dollar()
                is Euro -> Euro()
                is Crypto -> Crypto()

            }
}
