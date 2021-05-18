package ai.kaira.app.banking.institution.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.assessment.viewmodel.AssessmentViewModel
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.FragmentBankInstitutionListBinding
import ai.kaira.app.databinding.FragmentLoginToBankInstitutionBinding
import ai.kaira.domain.assessment.model.AssessmentAnswerClick
import ai.kaira.domain.banking.institution.model.Institution
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginToBankInstitutionFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var institutionViewModel: InstitutionViewModel

    lateinit var binding : FragmentLoginToBankInstitutionBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        institutionViewModel = ViewModelProvider(this, viewModelFactory).get(InstitutionViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login_to_bank_institution, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}