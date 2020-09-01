package com.assignment.venuesearchapp.presentation.venue.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.venuesearchapp.R
import com.assignment.venuesearchapp.data.api.RemoteAPIService
import com.assignment.venuesearchapp.data.api.RetrofitInstance
import com.assignment.venuesearchapp.data.db.VenueDatabase
import com.assignment.venuesearchapp.data.model.ErrorResponse
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.data.repositorydatasource.VenueLocalDataSourceImpl
import com.assignment.venuesearchapp.data.repositorydatasource.VenueRemoteDataSourceImpl
import com.assignment.venuesearchapp.data.repositorydatasource.VenueRepositoryImpl
import com.assignment.venuesearchapp.databinding.ActivityMainBinding
import com.assignment.venuesearchapp.domain.usecase.SearchVenueUseCase
import com.assignment.venuesearchapp.presentation.venue.details.VenueDetailsActivity
import com.assignment.venuesearchapp.util.AppConstants
import com.assignment.venuesearchapp.util.ConnectivityHelper
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    private var searchText: String = ""
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var viewModel: VenueViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val databaseDAO = VenueDatabase.getInstance(this).getVenueDAO()
        val apiService = RetrofitInstance.getRetrofitInstance(AppConstants.BASE_API_URL)!!.create(RemoteAPIService::class.java)
        val repository = VenueRepositoryImpl(
            VenueRemoteDataSourceImpl(
                AppConstants.CLIENT_ID,
                AppConstants.CLIENT_SECRET,
                apiService
            ),
            VenueLocalDataSourceImpl(databaseDAO)
        )


        val searchVenueUseCase = SearchVenueUseCase(repository)
        val viewModelFactory = VenueViewModelFactory(
            searchVenueUseCase,
            Dispatchers.IO,
            Dispatchers.Main
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(VenueViewModel::class.java)
        dataBinding.lifecycleOwner = this

        dataBinding.editTextSearch.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_SEARCH) {
                val enteredText = dataBinding.editTextSearch.text.toString().trim()
                if (enteredText.isNotEmpty()){ //{ && enteredText.trim() != this.searchText.trim()) {
                    searchText = enteredText
                    dataBinding.progressBar.visibility = View.VISIBLE
                    dataBinding.emptyTextView.visibility = View.GONE
                    hideKeyboard()
                    viewModel.searchVenue(searchText, ConnectivityHelper.isConnectedToNetwork(this))
                }
            }
            true
        }

        viewModel.errorInfo.observe(this, Observer {
            showErrorToast(it)
        })
        initRecyclerView()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(dataBinding.editTextSearch.windowToken, 0)
    }

    private fun initRecyclerView() {

        dataBinding.venueListRecyclerView.layoutManager = LinearLayoutManager(this)
        dataBinding.venueListRecyclerView.adapter =
            VenueListAdapter(
                listOf(),
                clickListener = { selectedVenue: Venue -> listItemClicked(selectedVenue) })

        viewModel.venueListData.observe(this, Observer {

            (dataBinding.venueListRecyclerView.adapter as VenueListAdapter).setVenueList(it)

            dataBinding.progressBar.visibility = View.GONE
            if (it.isEmpty()) {
                dataBinding.venueListRecyclerView.visibility = View.GONE
                dataBinding.emptyTextView.visibility = View.VISIBLE
                if (searchText.isNotEmpty()) {
                    dataBinding.emptyTextView.text =
                        resources.getString(R.string.err_no_result_found)
                } else {
                    dataBinding.emptyTextView.text = resources.getString(R.string.msg_please_search)
                }
            } else {
                dataBinding.venueListRecyclerView.visibility = View.VISIBLE
                dataBinding.emptyTextView.visibility = View.GONE
            }
            dataBinding.venueListRecyclerView.adapter!!.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(venue: Venue) {
        val intent = Intent(this, VenueDetailsActivity::class.java)
        intent.putExtra(AppConstants.VENUE_ID, venue.id)
        startActivity(intent)
    }

    private fun showErrorToast(error:ErrorResponse){
        var errorString:String ?
        if(AppConstants.ERROR_TYPE_NETWOTK_ERROR == error.meta.errorType){
            errorString = resources.getString(R.string.network_error_msg)
            errorString.let {
                if(viewModel.venueListData.value != null){
                    Toast.makeText(this, getString(R.string.network_unavailable_last_search_results_displayed_from_local_db), Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, errorString, Toast.LENGTH_LONG).show()
                }
            }
        }else {
            dataBinding.emptyTextView.text = error.meta.errorDetail
        }
    }

}