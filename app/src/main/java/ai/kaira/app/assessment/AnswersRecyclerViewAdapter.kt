package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentAnswerClick
import ai.kaira.domain.assessment.model.AssessmentType
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class AnswersRecyclerViewAdapter(var answers : ArrayList<AssessmentAnswer>, var answerClickCallback: MutableLiveData<AssessmentAnswerClick>, var assessmentType:AssessmentType) : RecyclerView.Adapter<AnswersRecyclerViewAdapter.AnswerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.answer_item_layout, parent, false)

        return AnswerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer : AssessmentAnswer = answers[position]
        holder.textView.text = answer.title

        if(answer.selected){
            if(assessmentType == AssessmentType.PSYCHOLOGICAL) {
                holder.textView.setBackgroundResource(R.drawable.kaira_third_filled_rectangle)
            }else{
                holder.textView.setBackgroundResource(R.drawable.kaira_fourth_filled_rectangle)
            }
            holder.textView.setTextColor(Color.WHITE)
        }else{
            holder.textView.setBackgroundResource(R.drawable.kaira_accent_semi_filled_rectangle)
            holder.textView.setTextColor(ContextCompat.getColor(holder.textView.context,R.color.kairaSecLabel))
        }
        holder.textView.setOnClickListener {
            if(assessmentType == AssessmentType.PSYCHOLOGICAL){
                holder.textView.setBackgroundResource(R.drawable.kaira_third_filled_rectangle)
            }else{
                holder.textView.setBackgroundResource(R.drawable.kaira_fourth_filled_rectangle)
            }
            holder.textView.setTextColor(Color.WHITE)
            answerClickCallback.value = AssessmentAnswerClick(answer.id,position,Calendar.getInstance().timeInMillis.toDouble())
        }

    }

    fun addAnswers(answers : ArrayList<AssessmentAnswer>){
        this.answers = answers
    }

    override fun getItemCount(): Int {
        return answers.size
    }

    class AnswerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.answer_tv)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }
}