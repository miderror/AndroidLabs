package com.example.androiddev

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androiddev.databinding.ActivityRegistrationBinding


class ClickableText(
    private val color: Int, private val onClick: () -> Unit
) : ClickableSpan() {

    override fun onClick(widget: View) {
        onClick()
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
        ds.color = color
    }
}

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tbRegistration.setNavigationOnClickListener {
            finish()
        }

        val clickablePolicy = ClickableText(
            color = ContextCompat.getColor(this, R.color.primary_color), onClick = {})

        val clickableAgreement = ClickableText(
            color = ContextCompat.getColor(this, R.color.primary_color), onClick = {})

        val text = binding.tvTermsConditions.text.toString()
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            clickablePolicy, 37, 65, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            clickableAgreement, 118, 145, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvTermsConditions.text = spannableString
    }
}