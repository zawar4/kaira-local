package ai.kaira.app.banking.institution

import ai.kaira.app.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankInstitutionLoginHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_institution_login_host)

        if(intent.hasExtra("institution_type")){
            val institutionType : String? = intent.getStringExtra("institution_type")
            institutionType?.let {
                val bundle = Bundle()
                bundle.putString("institution_type",institutionType)
                val navController = findNavController(R.id.login_institutions_host_fragment)
                navController.navigate(R.id.loginToBankInstitutionFragment,bundle)
            }
        }
    }
}