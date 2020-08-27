package com.assignment.venuesearchapp.presentation.venue.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.assignment.venuesearchapp.R
import com.assignment.venuesearchapp.data.api.RemoteAPIService
import com.assignment.venuesearchapp.data.api.RetrofitInstance
import com.assignment.venuesearchapp.data.db.VenueDatabase
import com.assignment.venuesearchapp.data.repositorydatasource.VenueLocalDataSourceImpl
import com.assignment.venuesearchapp.data.repositorydatasource.VenueRemoteDataSourceImpl
import com.assignment.venuesearchapp.data.repositorydatasource.VenueRepositoryImpl
import com.assignment.venuesearchapp.databinding.ActivityVenueDetailsBinding
import com.assignment.venuesearchapp.domain.usecase.GetVenueDetailsUseCase
import com.assignment.venuesearchapp.util.AppConstants

class VenueDetailsActivity : AppCompatActivity() {

    private lateinit var dataBinding:ActivityVenueDetailsBinding
    private lateinit var viewModel:VenueDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val venueID:String = intent.getStringExtra(AppConstants.VENUE_ID)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_venue_details)

        val databaseDAO = VenueDatabase.getInstance(this).getVenueDAO()
        val apiService = RetrofitInstance.getRetrofitInstance(AppConstants.BASE_API_URL)
            .create(RemoteAPIService::class.java)
        val repository = VenueRepositoryImpl(
            VenueRemoteDataSourceImpl(AppConstants.CLIENT_ID, AppConstants.CLIENT_SECRET, apiService),
            VenueLocalDataSourceImpl(databaseDAO)
        )

        val getVenueDetailsUseCase = GetVenueDetailsUseCase(repository)
        val viewModelFactory = VenueDetailsViewModelFactory(getVenueDetailsUseCase)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VenueDetailsViewModel::class.java)
        dataBinding.lifecycleOwner = this

        viewModel.searchVenue(venueID.toString())

        viewModel.venueDetailsLiveData.observe(this, Observer {
            //dataBinding.venueposterImageView.setImageURI(AppConstants.BASE_API_URL + viewModel.venueDetailsLiveData!!.)
            dataBinding.title.text = viewModel.venueDetailsLiveData!!.value!!.name.toString()
            val viewGroup1 = layoutInflater.inflate(R.layout.venue_details_row_item, null, false)
            (viewGroup1.findViewById(R.id.key) as TextView).text = "Description"
            (viewGroup1.findViewById(R.id.value) as TextView).text =
                viewModel.venueDetailsLiveData.value!!.description

            dataBinding.venueDetailsLayout.addView(viewGroup1)

            val viewGroup2 = layoutInflater.inflate(R.layout.venue_details_row_item, null, false)
            (viewGroup2.findViewById(R.id.key) as TextView).text = "Contact Information"
            (viewGroup2.findViewById(R.id.value) as TextView).text =
                viewModel.venueDetailsLiveData.value!!.contact.toString()
            dataBinding.venueDetailsLayout.addView(viewGroup2)

            val viewGroup3 = layoutInflater.inflate(R.layout.venue_details_row_item, null, false)
            (viewGroup3.findViewById(R.id.key) as TextView).text = "Address"
            (viewGroup3.findViewById(R.id.value) as TextView).text =
                viewModel.venueDetailsLiveData.value!!.location.formattedAddress.toString()
            dataBinding.venueDetailsLayout.addView(viewGroup3)


            val viewGroup4 = layoutInflater.inflate(R.layout.venue_details_row_item, null, false)
            (viewGroup4.findViewById(R.id.key) as TextView).text = "Rating"
            (viewGroup4.findViewById(R.id.value) as TextView).text =
                viewModel.venueDetailsLiveData.value!!.rating.toString()
            dataBinding.venueDetailsLayout.addView(viewGroup4)
        })
    }
}