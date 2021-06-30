package ai.kaira.app.banking.account.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.FragmentAccountOverviewBinding
import ai.kaira.app.databinding.TransactionItemLayoutBinding
import ai.kaira.app.home.viewmodel.MyFinanceViewModel
import ai.kaira.app.utils.Extensions.Companion.getFormattedAmount
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.banking.institution.model.BankAccountType
import ai.kaira.domain.banking.institution.model.BankingAccountingType
import ai.kaira.domain.banking.institution.model.BankingAggregator
import ai.kaira.domain.banking.institution.model.Institution
import android.content.Context
import android.view.View.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountOverviewFragment : Fragment() {

    private lateinit var binding : FragmentAccountOverviewBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var institutionViewModel : InstitutionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        institutionViewModel = ViewModelProvider(this,viewModelFactory).get(InstitutionViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account_overview, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { it ->
            if(it.containsKey("institution")) {
                val inflater : LayoutInflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val institution : Institution = it.getSerializable("institution") as Institution
                binding.amount.text = institution.amount.getFormattedAmount()

                binding.removeFinancialInstitution.setOnClickListener {
                    UIUtils.alertYesNo(
                        requireActivity(),
                        getString(R.string.warning),
                        getString(R.string.finance_institutions_remove_message)) {
                        institutionViewModel.removeInstitution(
                            BankingAggregator.wealthica.value,
                            institution.id.toString()
                        )
                    }
                }

                institutionViewModel.onInstitutionRemoved().observe(viewLifecycleOwner){
                    findNavController().popBackStack()
                }

                institution.accounts.forEach { account ->
                    val transactionItemLayoutBinding : TransactionItemLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.transaction_item_layout,binding.assetsAccountsParent,false)

                    var type : String = ""
                    if(account.type == BankAccountType.deposit) {
                        type = getString(R.string.financial_profile_type_deposit)
                    } else if(account.type == BankAccountType.investment) {
                        type = getString(R.string.financial_profile_type_investment)
                    } else if(account.type == BankAccountType.retirement) {
                        type = getString(R.string.financial_profile_type_retirement)
                    } else if(account.type == BankAccountType.otherRegisteredAccount) {
                        type = getString(R.string.financial_profile_type_otherregisteredaccount)
                    } else if(account.type == BankAccountType.other) {
                        type = getString(R.string.financial_profile_type_other)
                    } else if(account.type == BankAccountType.mortgage) {
                        type = getString(R.string.financial_profile_type_mortgage)
                    } else if(account.type == BankAccountType.saving) {
                        type = getString(R.string.financial_profile_type_saving)
                    } else if(account.type == BankAccountType.creditCard) {
                        type = getString(R.string.financial_profile_type_creditcard)
                    } else if(account.type == BankAccountType.loan) {
                        type = getString(R.string.financial_profile_type_loan)
                    }

                    transactionItemLayoutBinding.name.text = type
                    transactionItemLayoutBinding.amount.text = account.balance.getFormattedAmount(account.currency)
                    if(account.hideDetail()) {
                        transactionItemLayoutBinding.exploreBtn.visibility = INVISIBLE
                    } else {
                        transactionItemLayoutBinding.exploreBtn.visibility = VISIBLE
                        transactionItemLayoutBinding.root.setOnClickListener {
                            val bundle = Bundle()
                            bundle.putSerializable("account", account)
                            bundle.putString("institution_name", institution.name)
                            findNavController().navigate(R.id.navigation_account_detail,bundle)
                        }
                    }

                    if(account.accountingType == BankingAccountingType.assest) {
                        binding.assetsAccountsParent.addView(transactionItemLayoutBinding.root)
                    } else {
                        binding.liabilityAccountsParent.addView(transactionItemLayoutBinding.root)
                    }
                }
                binding.notifyChange()

            }
        } ?:run {
            findNavController().popBackStack()
        }
        institutionViewModel.onLoad().observe(viewLifecycleOwner){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}