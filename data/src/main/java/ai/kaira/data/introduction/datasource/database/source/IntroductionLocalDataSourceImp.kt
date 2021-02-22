package ai.kaira.data.introduction.datasource.database.source

import ai.kaira.data.introduction.datasource.database.dao.UserDao
import ai.kaira.data.introduction.datasource.database.entity.UserEntity
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IntroductionLocalDataSourceImp @Inject constructor(val userDao: UserDao,private val viewModelCoroutineScope: CoroutineScope) : IntroductionLocalDataSource {
    override fun insertUser(userEntity: UserEntity) {
        viewModelCoroutineScope.launch(IO) {
            userDao.insert(userEntity)
        }
    }

    override fun fetchUser(): UserEntity {
        return userDao.fetchUser()
    }

    override fun fetchUserAsync(): MutableLiveData<User?> {
        val userEntityLiveData = MutableLiveData<User?>()
        viewModelCoroutineScope.launch(IO) {
            val userEntity =  userDao.fetchUser()
            withContext(Main){
                if(userEntity == null){
                    userEntityLiveData.value = null
                }else{
                    userEntityLiveData.value = userEntity.maptoUser()
                }

            }
        }
        return userEntityLiveData
    }
}