package ai.kaira.data.banking.institution.datasource.local

import ai.kaira.domain.banking.institution.model.Institution
import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class InstitutionLocalDataSourceImp @Inject constructor(private val assetManager: AssetManager) : InstitutionLocalDataSource {

    override fun getAllInstitutions(): ArrayList<Institution> {
        val institutionsText = assetManager.open("institutions.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val myType = object : TypeToken<ArrayList<Institution>>() {}.type
        return gson.fromJson(institutionsText, myType)
    }
}