package rs.alexandar.startwars.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rs.alexandar.startwars.R

class ResidentRecyclerViewAdapter(context: Context, residentList: Array<String>?) :
    RecyclerView.Adapter<ResidentRecyclerViewAdapter.ViewHolder>() {

    private val residentList: List<String?>
    private val mInflater: LayoutInflater
    private val context: Context

    init {
        mInflater = LayoutInflater.from(context)
        this.residentList = residentList!!.toList()
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.resident_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
        val residentUrl = residentList[position]
        holder.residentUrl.text = residentUrl
    }

    override fun getItemCount(): Int {
        return residentList.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var residentUrl: TextView

        init {
            residentUrl = itemView.findViewById(R.id.residentUrl)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): String? {
        return residentList[id]
    }


}