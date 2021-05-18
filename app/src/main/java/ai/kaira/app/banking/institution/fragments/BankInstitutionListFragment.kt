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
import ai.kaira.domain.assessment.model.AssessmentAnswerClick
import ai.kaira.domain.banking.institution.model.Institution
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BankInstitutionListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var institutionViewModel: InstitutionViewModel

    lateinit var binding : FragmentBankInstitutionListBinding

    var institutionClickCallback: MutableLiveData<Int> = MutableLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        institutionViewModel = ViewModelProvider(this, viewModelFactory).get(InstitutionViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bank_institution_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val institutions = institutionViewModel.getAllInstitutions()
        val BMO  :Institution = institutions.find { it.name.equals("BMO") }!!
        val CIBC :Institution = institutions.find { it.name.equals("CIBC") }!!
        val DW :Institution = institutions.find { it.name.equals("Demo Wealthica") }!!
        val Desjardins :Institution = institutions.find { it.name.equals("Desjardins") }!!
        val HSBC :Institution = institutions.find { it.name.equals("HSBC Canada") }!!
        val LB :Institution = institutions.find { it.name.equals("Laurentian Bank") }!!
        val NB :Institution = institutions.find { it.name.equals("National Bank") }!!
        val RBC :Institution = institutions.find { it.name.equals("RBC") }!!
        val SB :Institution = institutions.find { it.name.equals("Scotia Bank") }!!
        val Tangerine :Institution = institutions.find { it.name.equals("Tangerine") }!!
        val TDB :Institution = institutions.find { it.name.equals("TD Bank") }!!
        val defaultInstitutions = ArrayList<Institution>()
        defaultInstitutions.add(BMO)
        defaultInstitutions.add(CIBC)
        defaultInstitutions.add(DW)
        defaultInstitutions.add(Desjardins)
        defaultInstitutions.add(HSBC)
        defaultInstitutions.add(LB)
        defaultInstitutions.add(NB)
        defaultInstitutions.add(RBC)
        defaultInstitutions.add(SB)
        defaultInstitutions.add(Tangerine)
        defaultInstitutions.add(TDB)
        institutions.sortWith(
            compareBy(String.CASE_INSENSITIVE_ORDER, { it.name.toString() })
        )
        binding.institutionsRecyclerView.adapter = InstitutionsRecyclerViewAdapter(defaultInstitutions,institutionClickCallback,R.layout.institution_view)
        binding.institutionsRecyclerView.layoutManager = GridLayoutManager(context,2)
        binding.institutionEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.institutionsRecyclerView.adapter = InstitutionsRecyclerViewAdapter(institutions,institutionClickCallback,R.layout.institution_view_linear)
                binding.institutionsRecyclerView.layoutManager = LinearLayoutManager(context)
                binding.cancelButton.visibility = View.VISIBLE
                binding.resultNumTv.visibility = View.VISIBLE
                binding.resultView.visibility = View.VISIBLE
                binding.resultNumTv.text = institutions.size.toString()+" results"
            }
            else{
                binding.cancelButton.visibility = View.GONE
            }
        }
        binding.institutionEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val list = institutions.filter { it.name.toString().toLowerCase().contains(s.toString().toLowerCase()) }
                binding.institutionsRecyclerView.adapter = InstitutionsRecyclerViewAdapter(list as ArrayList<Institution>,institutionClickCallback,R.layout.institution_view_linear)
                binding.cancelButton.visibility = View.VISIBLE
                binding.resultNumTv.visibility = View.VISIBLE
                binding.resultView.visibility = View.VISIBLE
                binding.resultNumTv.text = list.size.toString()+" results"
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.cancelButton.setOnClickListener {
            binding.institutionEt.text.clear()
            binding.institutionsRecyclerView.adapter = InstitutionsRecyclerViewAdapter(defaultInstitutions,institutionClickCallback,R.layout.institution_view)
            binding.institutionsRecyclerView.layoutManager = GridLayoutManager(context,2)
            binding.cancelButton.visibility = View.GONE
            binding.resultNumTv.visibility = View.GONE
            binding.resultView.visibility = View.GONE
            binding.institutionEt.clearFocus()
        }

        institutionClickCallback.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.loginToBankInstitutionFragment)
           Navigation.createNavigateOnClickListener(R.id.action_bankInstitutionListFragment_to_loginToBankInstitutionFragment,null)
        }
    }
}