package com.assignment.venuesearchapp.presentation.venue.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.venuesearchapp.R
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.databinding.ListItemViewBinding

class VenueListAdapter(
    private var venueList: List<Venue>,
    private val clickListener: (Venue) -> Unit
) : RecyclerView.Adapter<VenueListAdapter.VenueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val infaltor = LayoutInflater.from(parent.context)
        val binding:ListItemViewBinding  = DataBindingUtil.inflate(infaltor, R.layout.list_item_view, parent, false)
        return VenueViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(venueList[position], clickListener )
    }

    class VenueViewHolder(private val binding: ListItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(venue: Venue, clickListener: (Venue) -> Unit) {
            binding.titleTextView.text = venue.name
            binding.locaation.text = venue.location.address
            binding.cardView.setOnClickListener {
                clickListener(venue)
            }
        }
    }

    fun setVenueList(venueList: List<Venue>) {
        this.venueList = venueList
        notifyDataSetChanged()
    }
}