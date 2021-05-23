package ai.kaira.app.banking.institution.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.FragmentBankInstitutionListBinding
import ai.kaira.app.databinding.FragmentConnectBankInstitutionLoadBinding
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
            UIUtils.networkConnectivityAlert(requireContext())
        }

        institutionViewModel.onError().observe(viewLifecycleOwner){ error ->
            UIUtils.networkCallAlert(requireContext(), error)
        }

        institutionViewModel.onLoad().observe(viewLifecycleOwner){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        institutionViewModel.onInstitutionConnected().observe(viewLifecycleOwner){

        }

        institutionViewModel.connectInstitution(institutionCredentialsParam)
    }
}