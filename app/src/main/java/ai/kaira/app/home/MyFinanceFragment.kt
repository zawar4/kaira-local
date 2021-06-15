package ai.kaira.app.home

import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.FinanceItemLayoutBinding
import ai.kaira.app.databinding.FragmentMyFinanceBinding
import ai.kaira.app.home.viewmodel.MyFinanceViewModel
import ai.kaira.app.utils.Extensions.Companion.getFormattedAmount
import ai.kaira.app.utils.Extensions.Companion.getFormattedDate
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.KairaAction
import ai.kaira.domain.banking.institution.model.BankingInstitutionSyncStatus
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
        myFinanceViewModel.onMyFinancialsFetched().observe(viewLifecycleOwner){ myFinancies ->
            binding.revenueParent.removeAllViews()
            binding.spendingParent.removeAllViews()
            binding.leewayParent.removeAllViews()
            binding.balanceSheetParent.removeAllViews()
            binding.insitutionsParent.removeAllViews()
            myFinancies?.let{
                binding.viewParent.visibility = VISIBLE
                val revenues = myFinancies.revenue
                val spending = myFinancies.spending
                val leeways = myFinancies.leeway
                val balanceSheet = myFinancies.balanceSheet
                val institutions = myFinancies.institutions
                val languageLocale = LanguageConfig.getLanguage(requireContext())
                val inflater : LayoutInflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                revenues.forEachIndexed { index, revenue ->
                    val itembinding : FinanceItemLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.finance_item_layout,binding.revenueParent,false)
                    if(revenue.amount < 0){
                        itembinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaSecLabel))
                    }else {
                        itembinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaGreen))
                    }
                    itembinding.amount.text = revenue.amount.getFormattedAmount()
                    itembinding.name.text = revenue.date.getFormattedDate(languageLocale)
                    binding.revenueParent.addView(itembinding.root)
                }

                spending.forEachIndexed { index, spending ->
                    val itembinding : FinanceItemLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.finance_item_layout,binding.spendingParent,false)
                    if(spending.amount < 0){
                        itembinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaSecLabel))
                    }else {
                        itembinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaGreen))
                    }
                    itembinding.amount.text = spending.amount.getFormattedAmount()
                    itembinding.name.text = spending.date.getFormattedDate(languageLocale)
                    binding.spendingParent.addView(itembinding.root)
                }

                leeways.forEachIndexed { index, leeways ->
                    val itembinding : FinanceItemLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.finance_item_layout,binding.leewayParent,false)
                    if(leeways.amount < 0){
                        itembinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaSecLabel))
                    }else {
                        itembinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaGreen))
                    }
                    itembinding.amount.text = leeways.amount.getFormattedAmount()
                    itembinding.name.text = leeways.date.getFormattedDate(languageLocale)
                    binding.leewayParent.addView(itembinding.root)
                }

                val assetBinding : FinanceItemLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.finance_item_layout,binding.balanceSheetParent,false)
                if(balanceSheet.assets.amount < 0){
                    assetBinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaSecLabel))
                }else {
                    assetBinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaGreen))
                }
                assetBinding.name.text = getString(R.string.finance_asset_title)
                assetBinding.amount.text = balanceSheet.assets.amount.getFormattedAmount()
                binding.balanceSheetParent.addView(assetBinding.root)

                val liabilityBinding : FinanceItemLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.finance_item_layout,binding.balanceSheetParent,false)
                if(balanceSheet.liabilities.amount < 0){
                    liabilityBinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaSecLabel))
                }else {
                    liabilityBinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaGreen))
                }
                liabilityBinding.name.text = getString(R.string.finance_liability_title)
                liabilityBinding.amount.text = balanceSheet.liabilities.amount.getFormattedAmount()
                binding.balanceSheetParent.addView(liabilityBinding.root)

                val totalAssetsBinding : FinanceItemLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.finance_item_layout,binding.balanceSheetParent,false)
                if(balanceSheet.amount < 0){
                    totalAssetsBinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaSecLabel))
                }else {
                    totalAssetsBinding.amount.setTextColor(ContextCompat.getColor(requireContext(),R.color.kairaGreen))
                }
                totalAssetsBinding.name.text = getString(R.string.finance_total_assets_title)
                totalAssetsBinding.amount.text = balanceSheet.amount.getFormattedAmount()
                totalAssetsBinding.exploreBtn.visibility = INVISIBLE
                binding.balanceSheetParent.addView(totalAssetsBinding.root)

                institutions.forEachIndexed { index, institution ->
                    val itembinding : ai.kaira.app.databinding.InstitutionViewLinearDashboardBinding = DataBindingUtil.inflate(inflater,R.layout.institution_view_linear_dashboard,binding.insitutionsParent,false)
                    Glide.with(requireContext()).load(institution.getLogoUrl()).into(itembinding.institutionIm)
                    institution.name?.let {
                        itembinding.institutionNameTv.text = institution.name
                    }
                    institution.syncError?.let {
                        itembinding.status.visibility = VISIBLE
                        itembinding.status.background = requireContext().getDrawable(R.drawable.status_error)
                        itembinding.status.text = getString(R.string.error)
                    }
                    institution.syncStatus?.let{ it ->
                        when(it){
                            BankingInstitutionSyncStatus.ERROR -> {
                                itembinding.status.visibility = VISIBLE
                                itembinding.status.background = requireContext().getDrawable(R.drawable.status_error)
                                itembinding.status.text = getString(R.string.error)
                            }
                            BankingInstitutionSyncStatus.OK -> {
                                itembinding.status.visibility = GONE
                                itembinding.status.background = null
                            }
                            BankingInstitutionSyncStatus.SYNCING -> {
                                itembinding.status.visibility = VISIBLE
                                itembinding.status.background = requireContext().getDrawable(R.drawable.status_syncing)
                                itembinding.status.text = getString(R.string.banking_institutions_status_syncing)
                            }
                        }
                    }
                    binding.insitutionsParent.addView(itembinding.root)
                }

            }
            binding.root.requestLayout()

        }
        myFinanceViewModel.fetchMyFinancials()

        myFinanceViewModel.onLoad().observe(viewLifecycleOwner){ loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        myFinanceViewModel.onConnectivityError().observe(viewLifecycleOwner) {
            UIUtils.networkConnectivityAlert(requireActivity())
        }

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