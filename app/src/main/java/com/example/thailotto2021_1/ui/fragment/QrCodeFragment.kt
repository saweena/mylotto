package com.example.thailotto2021_1.ui.fragment

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.other.Event
import com.example.thailotto2021_1.other.EventObserver
import com.example.thailotto2021_1.other.QrCodeUtility
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

class QrCodeFragment : Fragment(),EasyPermissions.PermissionCallbacks {
    private lateinit var codeScanner: CodeScanner
    lateinit var viewModel : MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        return inflater.inflate(R.layout.fragment_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requestPermission()
        observeNavigateCheckingResultFragment()
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS // or CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                val drawNumber = it.toString().substring(0,5)
                val lottery = it.toString().substring(9,15)
                Timber.d("drawdate = $drawNumber ,,,, $lottery")
                viewModel.getLotteryResultByDrawNumberAndCheck(drawNumber,lottery)

            }
        }

        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            activity.runOnUiThread {
                Toast.makeText(requireContext(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        observeNoDrawNumber()

    }
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun requestPermission(){
        if (QrCodeUtility.hasCameraPermission(requireContext())){
            return
        }
        EasyPermissions.requestPermissions(this,"กดปุ่ม 'ตกลง' หากต้องการสแกน QR code",1, Manifest.permission.CAMERA)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }else{
            requestPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)

    }

    private fun observeNoDrawNumber(){
        viewModel.noDrawNumber.observe(viewLifecycleOwner,EventObserver{
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        })
    }

    private fun observeNavigateCheckingResultFragment() {
        viewModel.navigateToCheckingResultFragment.observe(viewLifecycleOwner, EventObserver {
            it?.let {
                findNavController().navigate(QrCodeFragmentDirections.actionQrCodeFragmentToCheckingResultFragment(it))

            }
        })
    }
}