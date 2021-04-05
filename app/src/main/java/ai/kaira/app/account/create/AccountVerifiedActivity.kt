package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityAccountVerifiedBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class AccountVerifiedActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountVerifiedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_account_verified)
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}