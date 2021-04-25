package ai.kaira.app.banking

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivitySecurityInfoBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class SecurityInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecurityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_security_info)
        binding.backBtn.setOnClickListener {
            finish()
        }

    }
}