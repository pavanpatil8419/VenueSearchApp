package com.assignment.venuesearchapp.presentation.venue.details

import android.app.ActionBar
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import com.assignment.venuesearchapp.util.ConnectivityHelper
import kotlinx.coroutines.Dispatchers

class VenueDetailsActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityVenueDetailsBinding
    private lateinit var viewModel: VenueDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val venueID = intent.getStringExtra(AppConstants.VENUE_ID)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_venue_details)

        val databaseDAO = VenueDatabase.getInstance(this).getVenueDAO()
        val apiService = RetrofitInstance.getRetrofitInstance(AppConstants.BASE_API_URL)
            .create(RemoteAPIService::class.java)
        val repository = VenueRepositoryImpl(
            VenueRemoteDataSourceImpl(
                AppConstants.CLIENT_ID,
                AppConstants.CLIENT_SECRET,
                apiService
            ),
            VenueLocalDataSourceImpl(databaseDAO)
        )
        val getVenueDetailsUseCase = GetVenueDetailsUseCase(repository)
        val viewModelFactory = VenueDetailsViewModelFactory(
            getVenueDetailsUseCase,
            Dispatchers.IO,
            Dispatchers.Main
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(VenueDetailsViewModel::class.java)
        dataBinding.lifecycleOwner = this

        viewModel.searchVenue(venueID,
            ConnectivityHelper.isConnectedToNetwork(this))

        viewModel.venueDetailsLiveData.observe(this, Observer {
            val venueDetails = viewModel.venueDetailsLiveData.value
            if (venueDetails != null) {

                if (!venueDetails.photos.groups.isNullOrEmpty()) {
                    val group = venueDetails.photos.groups[0]
                    if (!group.items.isNullOrEmpty()) {
                        val item = group.items[0]
                        val imguri = item.prefix + AppConstants.IMAGE_SIZE + item.suffix
                        dataBinding.venuePhotosImageView.setImageURI(imguri)
                    }
                }
                dataBinding.venueTitle.text = venueDetails.name
                dataBinding.descriptionTitle.text = resources.getString(R.string.description_label)
                dataBinding.descriptionValue.text = venueDetails.description
                dataBinding.contactInfoTitle.text = resources.getString(R.string.contact_information_label)
                dataBinding.contactInfoValue.text = venueDetails.contact.phone
                dataBinding.addressTitle.text = resources.getString(R.string.address_label)
                dataBinding.addressValue.text = venueDetails.location.address
                dataBinding.ratingTitle.text = resources.getString(R.string.ratings_label)
                dataBinding.ratingValue.text = venueDetails.rating.toString()

            } else {
                dataBinding.venuePhotosImageView.visibility = View.GONE
                dataBinding.venueTitle.text = getString(R.string.venue_details_not_available)
            }
        })
    }
}