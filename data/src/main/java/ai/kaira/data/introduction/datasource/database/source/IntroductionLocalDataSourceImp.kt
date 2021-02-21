package ai.kaira.data.introduction.datasource.database.source

import ai.kaira.data.introduction.datasource.database.dao.UserDao
import ai.kaira.data.introduction.datasource.database.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class IntroductionLocalDataSourceImp @Inject constructor(val userDao: UserDao,private val viewModelCoroutineScope: CoroutineScope) : IntroductionLocalDataSource {
    override fun insertUser(userEntity: UserEntity) {
        viewModelCoroutineScope.launch(IO) {
            userDao.insert(userEntity)
        }
    }

    override fun getUser(): UserEntity {
        return userDao.getUser()
    }
}