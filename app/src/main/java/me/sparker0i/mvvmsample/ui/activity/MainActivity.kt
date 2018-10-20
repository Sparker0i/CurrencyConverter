package me.sparker0i.mvvmsample.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import me.sparker0i.mvvmsample.R
import me.sparker0i.mvvmsample.api.ApiClient
import me.sparker0i.mvvmsample.api.ApiInterface
import me.sparker0i.mvvmsample.model.Currency
import me.sparker0i.mvvmsample.model.Rates
import me.sparker0i.mvvmsample.ui.adapter.InfiniteScrollListener
import me.sparker0i.mvvmsample.ui.adapter.RateAdapter
import me.sparker0i.mvvmsample.ui.model.CurrencyViewModel
import me.sparker0i.mvvmsample.ui.model.CurrencyViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var currencyViewModelFactory: CurrencyViewModelFactory
    lateinit var currencyViewModel: CurrencyViewModel
    var rateAdapter = RateAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        initializeRecyclerView()

        currencyViewModel = ViewModelProviders.of(this , currencyViewModelFactory)
                .get(CurrencyViewModel::class.java)

        progressbar.visibility = View.VISIBLE
        loadData()

        currencyViewModel.getCurrencyResult().observe(this ,
                Observer<List<Rates>> {
                    if (it != null) {
                        val position = rateAdapter.itemCount
                        rateAdapter.addRates(it)
                        recycler_view.adapter = rateAdapter
                        recycler_view.scrollToPosition(position - 12)
                    }
                })

        currencyViewModel.getCurrencyError()
                .observe(this , Observer<String> {
                    if (it != null) {
                        Toast.makeText(this, "Could not load Currencies: " + it,
                                Toast.LENGTH_SHORT).show()
                    }
                })

        currencyViewModel.getCurrencyLoader().observe(this , Observer<Boolean> {
            if (it == false)
                progressbar.visibility = View.GONE
        })

        currencyViewModel.getRates()
    }

    override fun onDestroy() {
        currencyViewModel.disposeElements()
        super.onDestroy()
    }

    private fun initializeRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this , 1)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            addOnScrollListener(InfiniteScrollListener({loadData()} , gridLayoutManager))
        }
    }

    private fun loadData() {

    }
}
