package ai.kaira.app.banking.account.fragment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.FragmentTransactionListBinding
import ai.kaira.app.home.viewmodel.MyFinanceViewModel
import ai.kaira.app.ui.StickyHeaderDecoration
import ai.kaira.domain.financial.model.Transaction
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
                    val transactionsAdapter = TransactionListAdapter(myFinanceViewModel.transactionList(),myFinanceViewModel.getTransactionsListHeaders())
                    val decor = StickyHeaderDecoration(transactionsAdapter)
                    binding.transactionList.setHasFixedSize(true)
                    binding.transactionList.addItemDecoration(decor)
                    binding.transactionList.layoutManager = LinearLayoutManager(requireActivity())
                    binding.transactionList.adapter = transactionsAdapter
                }
                myFinanceViewModel.fetchMyTransactionList(accountId)
            }

            binding.transactionEt.addTextChangedListener( object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString().isBlank() && s.toString().length ==  before){

                    } else {
                        val transactions = myFinanceViewModel.filterTransactions(s.toString().toLowerCase())
                        val transactionsHeading = myFinanceViewModel.getTransactionsListHeaders(transactions as ArrayList<Transaction>)
                        (binding.transactionList.adapter as TransactionListAdapter).addData(transactions,transactionsHeading)
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            myFinanceViewModel.onLoad().observe(viewLifecycleOwner){ loading ->
                    if(loading){
                        binding.progressBar.visibility = View.VISIBLE
                    }else{
                        binding.progressBar.visibility = View.GONE
                    }
                }
        }?:run {
            findNavController().popBackStack()
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}