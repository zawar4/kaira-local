package ai.kaira.app.banking.account.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.kaira.app.R
import ai.kaira.app.databinding.FragmentAccountDetailBinding
import ai.kaira.app.utils.Extensions.Companion.getFormattedAmount
import ai.kaira.domain.banking.institution.model.Account
import ai.kaira.domain.banking.institution.model.BankAccountType
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

class AccountDetailFragment : Fragment() {


    private lateinit var binding : FragmentAccountDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account_detail,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if(it.containsKey("account")) {
                val account : Account = it.getSerializable("account") as Account
                val institutionName : String? = it.getString("institution_name")
                binding.accountName.text = account.accountingType.name
                binding.balanceAmount.text = account.balance.getFormattedAmount(account.currency)
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

                binding.transactionGroup.isVisible = !account.hideTransactions()
                binding.accountName.text = type
                binding.bankName.text = institutionName
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}