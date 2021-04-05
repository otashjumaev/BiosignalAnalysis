package org.hiro.biosignals.fragment


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.hiro.biosignals.MainActivity
import org.hiro.biosignals.R

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    var detached = true

    fun pressBack() {
        activity?.let { act ->
            if (act is MainActivity) {
                act.onBackPressed()
            }
        }
    }

    fun getMain(): MainActivity {
        return activity as MainActivity
    }

    fun closeKeyboard() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        var view = activity?.currentFocus
        if (view == null && activity != null) {
            view = View(activity)
        }
        val binder = view?.windowToken
        inputManager?.hideSoftInputFromWindow(binder, 0)
    }

    fun closeKeyboard(view: View) {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val binder = view.windowToken
        inputManager?.hideSoftInputFromWindow(binder, 0)
    }

    override fun onDetach() {
        super.onDetach()
        detached = true
        closeKeyboard()
    }

    fun progressOn() {
        if (progressDialog != null) {
            progressDialog!!.cancel()
        }
        progressDialog =
            AlertDialog.Builder(requireContext())
                .setView(R.layout.dialog_progress_view)
                .setCancelable(false)
                .create()
                .apply {
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
        progressDialog!!.show()
    }

    fun progressOff() {
        if (progressDialog != null)
            progressDialog!!.cancel()
        progressDialog = null
    }

    fun showError(errorMessage: String?) {
        context?.apply {
            AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage(errorMessage)
                .setPositiveButton("OK", null)
                .show()
        }
    }
}