package ai.kaira.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedMainViewModel : ViewModel() {

    private val refreshMyFinancialFragmentLiveData = MutableLiveData<Unit>()

    fun refreshMyFinancialFragment(){
        refreshMyFinancialFragmentLiveData.value = Unit
    }

    fun onMyFinancialFragmentRefresh() : MutableLiveData<Unit> {
        return refreshMyFinancialFragmentLiveData
    }
}