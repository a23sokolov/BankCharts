package a23sokolov.bankcharts.common

import a23sokolov.bankcharts.R
import android.app.ProgressDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

/**
 * Created by a.v.sokolov
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    private var progressDialog: ProgressDialog? = null

    //todo добавить логику для автоматического вызова в родительском классе(BaseActivity)
    fun afterViewModelInited(viewModel: VM) {
        viewModel.init()
        viewModel.getLoadingState().observe(this, Observer {
            processLoadingState(it)
        })
    }

    fun processLoadingState(state: LoadingState?) {
        if (state == null) {
            return
        }

        if (state.isLoading) {
            showProgress()
        } else {
            hideProgress()
            if (state.errorMsg != null) {
                showError(state.errorMsg)
            }
        }
    }

    fun showProgress() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            return
        }
        progressDialog = ProgressDialog.show(
            this,
            getString(R.string.progress_title),
            getString(R.string.progress_message),
            true,
            false
        )
    }

    fun hideProgress() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    fun showError(msg: String?) {
        Toast.makeText(this, msg ?: getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
    }
}