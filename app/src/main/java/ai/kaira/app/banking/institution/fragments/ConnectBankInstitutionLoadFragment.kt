package ai.kaira.app.banking.institution.fragments

import ai.kaira.app.R
import ai.kaira.app.RedirectHelper
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.FragmentConnectBankInstitutionLoadBinding
import ai.kaira.app.home.MyFinanceFragment
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.KairaAction
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConnectBankInstitutionLoadFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var institutionViewModel: InstitutionViewModel

    lateinit var binding : FragmentConnectBankInstitutionLoadBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        institutionViewModel = ViewModelProvider(this, viewModelFactory).get(InstitutionViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_connect_bank_institution_load, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val institutionCredentialsParam : InstitutionParamBody = arguments?.get("institutionCredentialsParam") as InstitutionParamBody
        val institutionType : String = arguments?.getString("institutionType").toString()
        val url = "https://app.wealthica.com/images/institutions/$institutionType.png"
        Glide.with(binding.institutionIm.context).load(url).into(binding.institutionIm);

        institutionViewModel.onConnectivityError().observe(viewLifecycleOwner){
            UIUtils.networkConnectivityAlert(requireContext()) {
                findNavController().popBackStack(R.id.loginToBankInstitutionFragment,false)
            }
        }

        institutionViewModel.onError().observe(viewLifecycleOwner){ error ->
            UIUtils.networkCallAlert(requireContext(), error)
        }


        institutionViewModel.onErrorAction().observe(viewLifecycleOwner){ error ->
            error.kairaAction?.let{ action ->
                when(action){
                    KairaAction.UNKOWN_REDIRECT -> {
                        UIUtils.networkCallAlert(requireContext(), error.message) {
                            findNavController().popBackStack(R.id.loginToBankInstitutionFragment,false)
                        }
                    }
                    KairaAction.UNAUTHORIZED_REDIRECT ->{
                        UIUtils.networkCallAlert(requireContext(), error.message) {
                            requireActivity().finish()
                            var intent = Intent(requireContext(), LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)

                        }
                    }
                }
            }

        }

        institutionViewModel.onLoad().observe(viewLifecycleOwner){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        institutionViewModel.onInstitutionConnected().observe(viewLifecycleOwner){
            if(it){
                if(RedirectHelper.redirectExists(ConnectBankInstitutionLoadFragment::class.java.simpleName,MyFinanceFragment::class.java.simpleName)) {
                    val intent = Intent()
                    intent.putExtra("refresh_my_financial",true)
                    requireActivity().setResult(RESULT_OK,intent)
                    requireActivity().finish()
                } else {
                    val bundle = Bundle()
                    bundle.putString("institutionType",institutionType)
                    findNavController().navigate(R.id.bankInstitutionConnectedFragment,bundle)
                }

            }
        }

        institutionViewModel.connectInstitution(institutionCredentialsParam)
    }
}