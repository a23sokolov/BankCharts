package a23sokolov.bankcharts.view

import a23sokolov.bankcharts.R
import a23sokolov.bankcharts.view.common.BaseActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViewModel(ViewModelProviders.of(this).get(MainViewModel::class.java))
    }

    private fun initViewModel(viewModel: MainViewModel) {
        afterViewModelInited(viewModel)
        downloadBtn.setOnClickListener { view ->
            viewModel.getPointsBtnClicked(10)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
