package ai.kaira.app.banking.institution

import ai.kaira.app.R
import ai.kaira.app.RedirectHelper
import ai.kaira.app.banking.institution.fragments.FinancialInstitutionActivity
import ai.kaira.app.home.MyFinanceFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankInstitutionsHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_institutions_host)

        if(intent.hasExtra("from")) {
            if(intent.getStringExtra("from").equals(FinancialInstitutionActivity::class.java.simpleName)) {
                RedirectHelper.enableRedirect(BankInstitutionsHostActivity::class.java.simpleName, FinancialInstitutionActivity::class.java.simpleName)
            } else if(intent.getStringExtra("from").equals(MyFinanceFragment::class.java.simpleName)) {
                RedirectHelper.enableRedirect(BankInstitutionsHostActivity::class.java.simpleName, MyFinanceFragment::class.java.simpleName)
            }
        }
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.institutions_host_fragment)
        if(navController.getBackStackEntry(R.id.bankInstitutionListFragment) == navController.currentBackStackEntry) {
            val intent = Intent()
            setResult(RESULT_OK,intent)
            finish()
        } else {
            super.onBackPressed()
        }
    }
}