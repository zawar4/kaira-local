package ai.kaira.app.banking.institution

import ai.kaira.app.R
import ai.kaira.app.RedirectHelper
import ai.kaira.app.banking.institution.fragments.FinancialInstitutionActivity
import ai.kaira.app.home.MyFinanceFragment
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

        var bundle : Bundle ?= null
        if(intent.hasExtra("institution_type")){
            if(intent.hasExtra("from")){
                if(intent.getStringExtra("from").equals(FinancialInstitutionActivity::class.java.simpleName)) {
                    RedirectHelper.enableRedirect(BankInstitutionLoginHostActivity::class.java.simpleName, FinancialInstitutionActivity::class.java.simpleName)
                } else if(intent.getStringExtra("from").equals(MyFinanceFragment::class.java.simpleName)) {
                    RedirectHelper.enableRedirect(BankInstitutionLoginHostActivity::class.java.simpleName, MyFinanceFragment::class.java.simpleName)
                }
            }
            val institutionType : String? = intent.getStringExtra("institution_type")
            institutionType?.let {
                bundle = Bundle()
                bundle?.putString("institution_type",institutionType)
            }
        }
        val navController = findNavController(R.id.login_institutions_host_fragment)
        navController.setGraph(R.navigation.connect_institution_login_nav_graph,bundle)
    }
}