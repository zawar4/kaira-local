package ai.kaira.app.banking.institution.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.kaira.app.R
import ai.kaira.app.RedirectHelper
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.institution.BankInstitutionLoginHostActivity
import ai.kaira.app.banking.institution.BankInstitutionsHostActivity
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.FragmentBankInstitutionConnectedBinding
import ai.kaira.app.databinding.FragmentConnectBankInstitutionLoadBinding
import ai.kaira.app.home.MainActivity
import ai.kaira.app.home.MyFinanceFragment
import ai.kaira.app.utils.Extensions.Companion.ignoreInstitutionAddition
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import android.app.Activity
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class BankInstitutionConnectedFragment : Fragment() {


    lateinit var binding : FragmentBankInstitutionConnectedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bank_institution_connected, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val institutionType : String = arguments?.getString("institutionType").toString()
        val url = "https://app.wealthica.com/images/institutions/$institutionType.png"
        Glide.with(binding.institutionIm.context).load(url).into(binding.institutionIm);

        requireContext().ignoreInstitutionAddition()
        binding.yesBtn.setOnClickListener {
            findNavController().popBackStack(R.id.bankInstitutionListFragment,false)
        }

        binding.noBtn.setOnClickListener {

            if(RedirectHelper.redirectExists(BankInstitutionsHostActivity::class.java.simpleName, MyFinanceFragment::class.java.simpleName) ||
                RedirectHelper.redirectExists(BankInstitutionsHostActivity::class.java.simpleName,FinancialInstitutionActivity::class.java.simpleName)) {
                val intent = Intent()
                requireActivity().setResult(Activity.RESULT_OK,intent)
                requireActivity().finish()
            } else {
                requireActivity().finish()
                var intent = Intent(requireContext(), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}