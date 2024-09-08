package com.bhavishaymankani.scogonetworksmachinetest.base

import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bhavishaymankani.scogonetworksmachinetest.R


open class BaseFragment : Fragment() {

    private lateinit var loader: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loader = AlertDialog.Builder(requireActivity()).run {
            setView(R.layout.layout_loader)
            setCancelable(false)
            create()
        }
        loader.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager = requireContext().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    fun showLoader() = loader.show()

    fun dismissLoader() = loader.dismiss()

    fun showAlertDialog(message: String, buttonText: String? = null, clickListener: OnClickListener) {
        val alertDialog = AlertDialog.Builder(requireActivity()).run {
            setCancelable(false)
            setNegativeButton( buttonText ?: getString(R.string.ok), clickListener)
            create()
        }
        alertDialog.setTitle(R.string.error)
        alertDialog.setMessage(message)
        alertDialog.show()
    }
}