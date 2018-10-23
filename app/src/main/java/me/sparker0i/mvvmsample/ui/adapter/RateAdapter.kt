package me.sparker0i.mvvmsample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.sparker0i.mvvmsample.R
import me.sparker0i.mvvmsample.model.Rates

class RateAdapter(
        rates: List<Rates>?
): RecyclerView.Adapter<RateAdapter.RateViewHolder>() {
    private var ratesList = ArrayList<Rates>()

    init {
        this.ratesList = rates as ArrayList<Rates>
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    fun addRates(rates: List<Rates>){
        val initPosition = ratesList.size
        ratesList.addAll(rates)
        notifyItemRangeInserted(initPosition, ratesList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_currency,
                parent, false)
        return RateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rateItem = ratesList.get(position)
        holder.rateListItem(rateItem)
    }

    class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currencySymbol = itemView.findViewById<TextView>(R.id.currencySymbol)
        var currencyValue = itemView.findViewById<TextView>(R.id.currencyValue)

        fun rateListItem(rate: Rates) {
            currencySymbol.text = rate.symbol
            currencyValue.text = rate.rate.toString()
        }
    }
}