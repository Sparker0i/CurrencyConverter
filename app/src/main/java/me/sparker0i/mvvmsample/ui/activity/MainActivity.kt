package me.sparker0i.mvvmsample.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
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
import me.sparker0i.mvvmsample.ui.model.CurrencyViewModel
import me.sparker0i.mvvmsample.ui.model.CurrencyViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var currencyViewModelFactory: CurrencyViewModelFactory
    lateinit var currencyViewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        currencyViewModel = ViewModelProviders.of(this , currencyViewModelFactory)
                .get(CurrencyViewModel::class.java)
        currencyViewModel.getRates()

        currencyViewModel.getCurrencyResult()
                .observe(this , Observer<List<Rates>> {
                    println(it)
                    tv_hello_world.text = "Hello ${it.size} Currencies"
                })

        currencyViewModel.getCurrencyError()
                .observe(this , Observer<String> {
                    tv_hello_world.text = "Hello Error ${it}"
                })
    }

    override fun onDestroy() {
        currencyViewModel.disposeElements()
        super.onDestroy()
    }
}
