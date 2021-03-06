package ai.kaira.app.account.create

import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.databinding.ActivityInfoCreateAccountBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil

class InfoCreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_info_create_account)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this,UserIdentityCreateAccountActivity::class.java))
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}