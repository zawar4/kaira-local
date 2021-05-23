package ai.kaira.app.banking.institution.fragments

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.banking.institution.fragments.viewmodel.InstitutionViewModel
import ai.kaira.app.databinding.FragmentBankInstitutionListBinding
import ai.kaira.app.utils.Extensions.Companion.unaccent
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.domain.banking.institution.model.Institution
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.sentry.util.StringUtils
import javax.inject.Inject


@AndroidEntryPoint
class BankInstitutionListFragment : Fragment(),InstitutionsRecyclerViewAdapter.OnInstitutionClickListener {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var institutionViewModel: InstitutionViewModel

    lateinit var binding : FragmentBankInstitutionListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        institutionViewModel = ViewModelProvider(this, viewModelFactory).get(InstitutionViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bank_institution_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val languageLocale = LanguageConfig.getLanguageLocale(requireContext())
        val institutions = institutionViewModel.getAllInstitutions(languageLocale)
        val BMO  :Institution = institutions.find { it.name.toString().unaccent().equals("BMO") }!!
        val CIBC :Institution = institutions.find { it.name.toString().unaccent().equals("CIBC") }!!
        val DW :Institution = institutions.find { it.name.toString().unaccent().equals("Demo Wealthica") }!!
        val Desjardins :Institution = institutions.find { it.name.toString().unaccent().equals("Desjardins") }!!
        val HSBC :Institution = institutions.find { it.name.toString().unaccent().equals("HSBC Canada") }!!
        val LB :Institution = institutions.find { it.name.toString().unaccent().equals("Laurentian Bank") || it.name.toString().equals("Banque Laurentienne") }!!
        val NB :Institution = institutions.find { it.name.toString().unaccent().equals("National Bank") || it.name.toString().equals("Banque Nationale Réseau Indépendant (my-portfolio.ca)") }!!
        val RBC :Institution = institutions.find { it.name.toString().unaccent().equals("RBC") }!!
        val SB :Institution = institutions.find { it.name.toString().unaccent().equals("Scotia Bank") }!!
        val Tangerine :Institution = institutions.find { it.name.toString().unaccent().equals("Tangerine") }!!
        val TDB :Institution = institutions.find { it.name.toString().unaccent().equals("TD Bank") }!!
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
            compareBy(String.CASE_INSENSITIVE_ORDER, { it.name.toString().unaccent() })
        )

        binding.institutionsRecyclerView.adapter = InstitutionsRecyclerViewAdapter(defaultInstitutions,this,R.layout.institution_view)
        binding.institutionsRecyclerView.layoutManager = GridLayoutManager(context,2)
        binding.institutionEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.institutionsRecyclerView.adapter = InstitutionsRecyclerViewAdapter(institutions,this,R.layout.institution_view_linear)
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
        binding.institutionEt.addTextChangedListener(object : TextWatcher,
            InstitutionsRecyclerViewAdapter.OnInstitutionClickListener {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isBlank() && s.toString().length ==  before){

                } else {
                    val list = institutions.filter { it.name.toString().unaccent().toLowerCase().contains(s.toString().toLowerCase()) }
                    binding.institutionsRecyclerView.adapter = InstitutionsRecyclerViewAdapter(list as ArrayList<Institution>,this,R.layout.institution_view_linear)
                    binding.institutionsRecyclerView.layoutManager = LinearLayoutManager(context)
                    binding.cancelButton.visibility = View.VISIBLE
                    binding.resultNumTv.visibility = View.VISIBLE
                    binding.resultView.visibility = View.VISIBLE
                    binding.resultNumTv.text = list.size.toString()+" results"
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onInstitutionClick(institution: Institution) {
                val bundle = Bundle()
                bundle.putSerializable("institution",institution)
                findNavController().navigate(R.id.loginToBankInstitutionFragment,bundle)
            }
        })
        binding.cancelButton.setOnClickListener {
            binding.institutionEt.text.clear()
            binding.institutionsRecyclerView.adapter = InstitutionsRecyclerViewAdapter(defaultInstitutions,this,R.layout.institution_view)
            binding.institutionsRecyclerView.layoutManager = GridLayoutManager(context,2)
            binding.cancelButton.visibility = View.GONE
            binding.resultNumTv.visibility = View.GONE
            binding.resultView.visibility = View.GONE
            binding.institutionEt.clearFocus()
        }

    }

    override fun onInstitutionClick(institution: Institution) {
        val bundle = Bundle()
        bundle.putSerializable("institution",institution)
        findNavController().navigate(R.id.loginToBankInstitutionFragment,bundle)
    }

}