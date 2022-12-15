package id.bts.devin.alfagift_test.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import id.bts.devin.alfagift_test.R
import id.bts.devin.alfagift_test.presentation.utils.LiveResource
import id.bts.devin.alfagift_test.presentation.utils.StateResource
import id.bts.devin.alfagift_test.presentation.utils.showSnackBar
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var binding: VB

    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = setLayout(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideLoadingDialog()
    }

    protected abstract fun setLayout(inflater: LayoutInflater): VB

    protected abstract fun onFragmentCreated()

    protected fun showLoadingDialog() {
        val style = com.google.android.material.R.style.Theme_Material3_DayNight_Dialog_Alert
        if (loadingDialog == null) {
            loadingDialog = AlertDialog.Builder(layoutInflater.context, style)
                .setCancelable(false)
                .setView(R.layout.view_loading)
                .create()
            loadingDialog?.show()
        }
    }

    protected fun hideLoadingDialog() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

    protected fun <T> LiveResource<T>.observeData(
        onSuccess: ((data: T) -> Unit)? = null
    ) {
        this.observe(viewLifecycleOwner,
            onLoading = { showLoadingDialog() },
            onSuccess = { data ->
                if (data == null) {
                    hideLoadingDialog()
                    return@observe
                }
                onSuccess?.invoke(data)
                hideLoadingDialog()
            },
            onError = { throwable ->
                hideLoadingDialog()
                throwable.run {
                    printStackTrace()
                    binding.root.showSnackBar(message)
                }
            }
        )
    }

    protected fun <T> StateResource<T>.collectData(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onError: ((throwable: Throwable) -> Unit)? = null,
        onSuccess: ((data: T) -> Unit)? = null
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                this@collectData.collect(
                    onLoading = { showLoadingDialog() },
                    onSuccess = {
                        hideLoadingDialog()
                        onSuccess?.invoke(it)
                    },
                    onError = {
                        hideLoadingDialog()
                        binding.root.showSnackBar(it.message)
                        onError?.invoke(it)
                    }
                )
            }
        }
    }
}