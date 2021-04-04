package ai.kaira.app.account.create.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ResultState
import ai.kaira.domain.account.model.Account
import ai.kaira.domain.account.usecase.AccountCreateUseCase
import ai.kaira.domain.introduction.model.User
import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData


class AccountCreateViewModel (private val accountCreateUseCase: AccountCreateUseCase) : BaseViewModel() {

    private val groupCodeExistsLiveData = MediatorLiveData<Boolean>()

    private val emailExistsLiveData = MediatorLiveData<Boolean>()

    private val createAccountLiveData = MediatorLiveData<User>()

    private val sendVerificationEmailLiveData = MediatorLiveData<Unit>()

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
                    groupCodeExistsLiveData.value = true
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
                    result.message?.let { showError(it) }
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
                    emailExistsLiveData.value = result.data
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
                    result.message?.let { showError(it) }
                    emailExistsLiveData.removeSource(liveDataSource)
                }
            }
        }
    }

    fun onAccountCreated():MediatorLiveData<User>{
        return createAccountLiveData
    }

    fun createAccount(firstName:String,lastName:String,language:String,email:String,password:String,groupCode:String){
        val liveDataSource = accountCreateUseCase.fetchUserCreateAccount(firstName,lastName,language, email, password, groupCode)
        createAccountLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    createAccountLiveData.removeSource(liveDataSource)
                    createAccountLiveData.value = result.data
                }
                ResultState.ERROR ->{
                    result.message?.let{ it->
                        showError(it)
                    }
                    createAccountLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.EXCEPTION ->{
                    result.message?.let{ it->
                        showError(it)
                    }
                    createAccountLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
            }
        }
    }

    fun sendVerificationEmail(email:String){
        val liveDataSource = accountCreateUseCase.sendVerificationEmail(email)
        sendVerificationEmailLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    createAccountLiveData.removeSource(liveDataSource)
                    sendVerificationEmailLiveData.value = Unit
                }
                ResultState.ERROR ->{
                    result.message?.let{ it->
                        showError(it)
                    }
                    sendVerificationEmailLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.EXCEPTION ->{
                    result.message?.let{ it->
                        showError(it)
                    }
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