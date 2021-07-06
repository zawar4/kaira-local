package ai.kaira.app.banking.account.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.FragmentTransactionListBinding
import ai.kaira.app.home.viewmodel.MyFinanceViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransactionListFragment : Fragment() {

    private lateinit var binding : FragmentTransactionListBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var myFinanceViewModel: MyFinanceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myFinanceViewModel = ViewModelProvider(this,viewModelFactory).get(MyFinanceViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_transaction_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { it ->
            if (it.containsKey("account_id")) {
                val accountId = it.getString("account_id").toString()
                myFinanceViewModel.onMyTransactionListFetched().observe(viewLifecycleOwner) {

                }
                myFinanceViewModel.fetchMyTransactionList(accountId)
            }
        }?:run {
            findNavController().popBackStack()
        }
    }
}