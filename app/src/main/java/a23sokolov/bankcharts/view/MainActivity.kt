package a23sokolov.bankcharts.view

import a23sokolov.bankcharts.R
import a23sokolov.bankcharts.common.BaseActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    private val adapter = TableAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initRecyclerView()
        initViewModel(ViewModelProviders.of(this).get(MainViewModel::class.java))
    }

    private fun initRecyclerView() {
        tableContent.layoutManager = LinearLayoutManager(this)
        tableContent.adapter = adapter
        tableContent.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL).also {
            it.setDrawable(this.resources.getDrawable(R.drawable.divider_decorator))
        })
    }

    private fun initViewModel(viewModel: MainViewModel) {
        afterViewModelInited(viewModel)
        viewModel.getPoints().observe(this, Observer {
            adapter.setPoints(it)
        })
        fab.setOnClickListener { view ->
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
