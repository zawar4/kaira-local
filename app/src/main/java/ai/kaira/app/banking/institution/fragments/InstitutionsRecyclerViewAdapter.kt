package ai.kaira.app.banking.institution.fragments

import ai.kaira.app.R
import ai.kaira.app.utils.Extensions.Companion.isConnectedToInternet
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentAnswerClick
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.banking.institution.model.Institution
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.util.*
import kotlin.collections.ArrayList

class InstitutionsRecyclerViewAdapter(var institutions : ArrayList<Institution>, var institutionClickCallback: MutableLiveData<Int>,val layout:Int) : RecyclerView.Adapter<InstitutionsRecyclerViewAdapter.InstitutionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstitutionViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)

        return InstitutionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstitutionViewHolder, position: Int) {
        val institution : Institution = institutions[position]
        holder.textView.text = institution.name
        val url = "https://app.wealthica.com/images/institutions/"+institution.type+".png"
        Glide.with(holder.imageView.context).load(url).apply(RequestOptions.bitmapTransform(RoundedCorners(15))).into(holder.imageView);
        holder.parent.setOnClickListener {
            institutionClickCallback.value = position
        }
    }

    fun addInstitutions(institutions : ArrayList<Institution>){
        this.institutions = institutions
    }

    override fun getItemCount(): Int {
        return institutions.size
    }

    class InstitutionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.institution_name_tv)
        val imageView: ImageView = view.findViewById(R.id.institution_im)
        val parent: ConstraintLayout = view.findViewById(R.id.parent)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }
}