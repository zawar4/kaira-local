package ai.kaira.app.banking.institution.fragments

import ai.kaira.app.R
import ai.kaira.app.RedirectHelper
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.institution.BankInstitutionLoginHostActivity
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.FragmentConnectBankInstitutionBinding
import ai.kaira.app.home.MyFinanceFragment
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParam
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ConnectBankInstitutionFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var institutionViewModel: InstitutionViewModel

    lateinit var binding : FragmentConnectBankInstitutionBinding

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.loginBtn.isEnabled = binding.userPasswordEt.text.length > 1 && binding.userPinEt.text.length > 1
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        institutionViewModel = ViewModelProvider(this, viewModelFactory).get(InstitutionViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_connect_bank_institution, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{ bundle ->
            if(bundle.containsKey("institution")) {

                val institution : Institution = arguments?.get("institution") as Institution
                populateInstitution(institution)
            }
            if(bundle.containsKey("institution_type")) {
                val type = bundle.get("institution_type") as String
                val languageLocale = LanguageConfig.getLanguageLocale(requireContext())
                val institutions = institutionViewModel.getAllInstitutions(languageLocale)
                val institution = institutions.filter{ it.type == type}
                populateInstitution(institution[0])
            }
        }
    }

    private fun populateInstitution(institution: Institution){
        val url = institution.getLogoUrl()
        Glide.with(binding.institutionIm.context).load(url).into(binding.institutionIm);
        institution.usernameInformations?.let { usernameInformation ->
            binding.userPinHeadingEt.text = usernameInformation.label
            if(usernameInformation.type != null){
                if(usernameInformation.type == "0"){
                    binding.userPinEt.inputType = InputType.TYPE_CLASS_NUMBER
                }else if(usernameInformation.type == "1"){
                    binding.userPinEt.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }
            }
        }

        institution.instructions?.let { instructions ->
            if(instructions.isNotBlank()){
                binding.instructionTv.visibility = VISIBLE
                binding.instructionTv.text = instructions
            }
        }

        institution.passwordInformations?.let{ passwordInformations ->
            binding.userPasswordHeadingEt.text = passwordInformations.label
            if(passwordInformations.type != null){
                if(passwordInformations.type == "0"){
                    binding.userPasswordEt.inputType = InputType.TYPE_CLASS_NUMBER
                }else if(passwordInformations.type == "1"){
                    binding.userPinEt.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }
            }
        }

        binding.passwordVisibilityBtn.setOnClickListener {
            if(binding.userPasswordEt.transformationMethod == null){
                binding.userPasswordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.passwordVisibilityBtn.setImageResource(R.drawable.visibility_off)
            }else{
                binding.userPasswordEt.transformationMethod = null
                binding.passwordVisibilityBtn.setImageResource(R.drawable.visibility_on)
            }
            binding.userPasswordEt.setSelection(binding.userPasswordEt.text.length)
        }

        binding.userPasswordEt.addTextChangedListener(textWatcher)
        binding.userPinEt.addTextChangedListener(textWatcher)

        institutionViewModel.onInstitutionConnected().observe(viewLifecycleOwner){

        }
        binding.loginBtn.setOnClickListener {
            val institutionParam = InstitutionParam(institution.type.toString(),binding.userPinEt.text.toString(),binding.userPasswordEt.text.toString(),institution.name.toString())
            val bundle = Bundle()
            bundle.putString("institutionType",institution.type)
            bundle.putSerializable("institutionCredentialsParam",InstitutionParamBody(institution.aggregator.value,institutionParam))
            findNavController().navigate(R.id.connectBankInstitutionLoadFragment,bundle)
        }

        institutionViewModel.onConnectivityError().observe(viewLifecycleOwner){
            UIUtils.networkConnectivityAlert(requireContext())
        }

        institutionViewModel.onError().observe(viewLifecycleOwner){ error ->
            UIUtils.networkCallAlert(requireContext(), error)
        }
    }
}