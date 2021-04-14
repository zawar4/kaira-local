package ai.kaira.app.account.create

import ai.kaira.app.R
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class EmptyEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_email)
        val intent = getIntent()

        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.setClass(this, AccountVerifiedActivity::class.java)
        startActivity(intent)
        finish()
    }
}