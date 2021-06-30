package ai.kaira.app.banking.account.activity

import ai.kaira.app.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountOverViewHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_over_view_host)

        intent?.let {
            if(intent.hasExtra("institution")){
                val institution = intent.getSerializableExtra("institution")
                val bundle = Bundle()
                bundle.putSerializable("institution",institution)
                val navController = findNavController(R.id.account_over_view_fragment)
                navController.setGraph(R.navigation.account_over_view_nav,bundle)
            }
        } ?:run {
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(RESULT_OK,intent)
        finish()
    }
}