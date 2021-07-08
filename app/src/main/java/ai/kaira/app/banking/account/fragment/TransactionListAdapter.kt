package ai.kaira.app.banking.account.fragment

import ai.kaira.app.R
import ai.kaira.app.ui.StickyHeaderAdapter
import ai.kaira.app.utils.Extensions.Companion.getFormattedAmount
import ai.kaira.domain.financial.model.Transaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionListAdapter constructor(private var transactionList : ArrayList<Transaction>, private var headers : ArrayList<String>) : RecyclerView.Adapter<TransactionListAdapter.BodyViewHolder> (), StickyHeaderAdapter<TransactionListAdapter.HeaderViewHolder> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item_layout, parent, false)
        return BodyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BodyViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.amount?.text = transaction.amount.getFormattedAmount(transaction.currency)
        holder.name?.text = transaction.name
        holder.displayCategory?.text = transaction.displayCategory
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun getHeader(position: Int): String {
       return headers[position]
    }

    public fun addData(transactionList : ArrayList<Transaction>,headers : ArrayList<String>) {
        this.transactionList = transactionList
        this.headers = headers
        notifyDataSetChanged()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sticky_header_layout, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindHeaderViewHolder(viewHolder: HeaderViewHolder, position: Int) {
        viewHolder.header?.text = getHeader(position)
    }

    class BodyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var name : TextView? = itemView?.findViewById(R.id.name) as TextView?
        var displayCategory : TextView? = itemView?.findViewById(R.id.display_category) as TextView?
        var amount : TextView? = itemView?.findViewById(R.id.amount) as TextView?
        var exploreBtn : ImageView? = itemView?.findViewById(R.id.explore_btn) as ImageView?

    }

    class HeaderViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var header : TextView? = itemView?.findViewById(R.id.header) as TextView?
    }
}