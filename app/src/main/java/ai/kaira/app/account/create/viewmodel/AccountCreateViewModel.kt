package ai.kaira.app.account.create.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ErrorAction
import ai.kaira.domain.KairaAction
import ai.kaira.domain.ResultState
import ai.kaira.domain.account.create.usecase.AccountCreateUseCase
import ai.kaira.domain.introduction.model.User
import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData


class AccountCreateViewModel (private val accountCreateUseCase: AccountCreateUseCase) : BaseViewModel() {

    private val groupCodeExistsLiveData = MediatorLiveData<Boolean>()

    private val emailExistsLiveData = MediatorLiveData<Boolean>()

    private val createAccountLiveData = MediatorLiveData<User>()

    private val sendVerificationEmailLiveData = MediatorLiveData<Unit>()

    private val verifyAccountLiveData = MediatorLiveData<Unit>()

    fun fetchUser(): MutableLiveData<User?> {
        return accountCreateUseCase.fetchUser.fetchUserAsync()
    }
    fun onGroupCodeExists():MediatorLiveData<Boolean>{
        return groupCodeExistsLiveData
    }

    fun groupCodeExists(groupCode:String){
        val liveDataSource = accountCreateUseCase.groupCodeExists(groupCode)
        groupCodeExistsLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS -> {
                    showLoading(false)
                    result.data?.let{
                        groupCodeExistsLiveData.value = it
                    }
                    groupCodeExistsLiveData.removeSource(liveDataSource)
                }
                ResultState.LOADING -> {
                    showLoading(true)
                }
                ResultState.ERROR -> {
                    showLoading(false)
                    result.message?.let { showError(it) }
                    groupCodeExistsLiveData.removeSource(liveDataSource)
                }
                ResultState.EXCEPTION -> {
                    showLoading(false)
                    /*
                    result.message?.let { showError(it) }
                     */
                    showConnectivityError()
                    groupCodeExistsLiveData.removeSource(liveDataSource)
                }
            }
        }
    }

    fun onEmailExists():MediatorLiveData<Boolean>{
        return emailExistsLiveData
    }

    fun emailExists(email:String){
        val liveDataSource = accountCreateUseCase.emailExists(email)
        emailExistsLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS -> {
                    showLoading(false)
                    result.data?.let{
                        emailExistsLiveData.value = it
                    }
                    emailExistsLiveData.removeSource(liveDataSource)
                }
                ResultState.LOADING -> {
                    showLoading(true)
                }
                ResultState.ERROR -> {
                    showLoading(false)
                    result.message?.let { showError(it) }
                    emailExistsLiveData.removeSource(liveDataSource)
                }
                ResultState.EXCEPTION -> {
                    showLoading(false)
                    //result.message?.let { showError(it) }
                    showConnectivityError()
                    emailExistsLiveData.removeSource(liveDataSource)
                }
            }
        }
    }

    fun onAccountCreated():MediatorLiveData<User>{
        return createAccountLiveData
    }

    fun createAccount(firstName:String,lastName:String,language:String,email:String,password:String,groupCode:String,bankingAggregator:Int){
        val liveDataSource = accountCreateUseCase.fetchUserCreateAccount(firstName,lastName,language, email, password, groupCode,bankingAggregator = bankingAggregator)
        createAccountLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    createAccountLiveData.removeSource(liveDataSource)
                    result.data?.let{
                        createAccountLiveData.value = it
                    }
                }
                ResultState.ERROR ->{
                    result.message?.let{ it->
                        showError(it)
                    }
                    createAccountLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.EXCEPTION ->{
                    /*result.message?.let{ it->
                        showError(it)
                    }*/
                    showConnectivityError()
                    createAccountLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
            }
        }
    }



    fun onAccountVerified():MediatorLiveData<Unit>{
        return verifyAccountLiveData
    }

    fun verifyAccount(token:String){
        val liveDataSource = accountCreateUseCase.verifyAccount(token)
        verifyAccountLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    verifyAccountLiveData.removeSource(liveDataSource)
                    verifyAccountLiveData.value = Unit
                }
                ResultState.ERROR ->{
                    showLoading(false)
                    verifyAccountLiveData.removeSource(liveDataSource)
                    if (result.kairaAction != null) {
                        result.kairaAction?.let { it2 ->
                            errorAction(ErrorAction(result.message.toString(), it2))
                        }
                    } else {
                        result.message?.let { error ->
                            showError(error)
                        }
                    }
                }
                ResultState.EXCEPTION ->{
                    /*result.message?.let{ it->
                        showError(it)
                    }*/
                    showConnectivityError()
                    verifyAccountLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
            }
        }
    }

    fun sendVerificationEmail(email:String,token:String){
        val liveDataSource = accountCreateUseCase.sendVerificationEmail(email,token)
        sendVerificationEmailLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    createAccountLiveData.removeSource(liveDataSource)
                    sendVerificationEmailLiveData.value = Unit
                }
                ResultState.ERROR ->{
                    sendVerificationEmailLiveData.removeSource(liveDataSource)
                    showLoading(false)
                    if (result.kairaAction != null) {
                        result.kairaAction?.let { it2 ->
                            errorAction(ErrorAction(result.message.toString(), it2))
                        }
                    } else {
                        result.message?.let { error ->
                            showError(error)
                        }
                    }
                }
                ResultState.EXCEPTION ->{
                    showConnectivityError()
                    sendVerificationEmailLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
            }
        }
    }

    fun onVerificationEmailSent(): MediatorLiveData<Unit> {
        return sendVerificationEmailLiveData
    }

    fun validateGroupCode(groupCode:String):Boolean{
        return  groupCode.length == 8
                &&
                groupCode.substring(0,4).all {
                it.isLetter()
                }
                &&
                groupCode.substring(4,8).all {
                it.isDigit()
                }
    }

    fun isValidEmail(target: String): Boolean {
        return if(target.isBlank()){
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
    fun isValidPassword(password:String):Boolean {
        val regularExpression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,32}".toRegex()
        return regularExpression.matches(password)
    }

    fun arePasswordsSame(password1:String,password2:String): Boolean {
        return password1 == password2
    }
}