package ai.kaira.app.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.FragmentMyFinanceBinding
import ai.kaira.app.databinding.FragmentMyProfileBinding
import ai.kaira.app.home.viewmodel.MyFinanceViewModel
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.KairaAction
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFinanceFragment : Fragment() {

    private lateinit var binding : FragmentMyFinanceBinding

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var myFinanceViewModel: MyFinanceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myFinanceViewModel = ViewModelProvider(this,viewModelFactory).get(MyFinanceViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_finance, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myFinanceViewModel.onMyFinancialsFetched().observe(viewLifecycleOwner){

        }
        myFinanceViewModel.fetchMyFinancials()

        myFinanceViewModel.onErrorAction().observe(viewLifecycleOwner){
               when(it.kairaAction){
                   KairaAction.UNAUTHORIZED_REDIRECT ->{
                       UIUtils.networkCallAlert(requireContext(), it.message) {
                           requireActivity().finish()
                           var intent = Intent(requireContext(), LoginActivity::class.java)
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                           startActivity(intent)

                       }
                   }
               }
        }
    }
}