package com.maicakapo.thailotto2021_1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maicakapo.thailotto2021_1.R
import com.maicakapo.thailotto2021_1.data.Lottery
import com.maicakapo.thailotto2021_1.databinding.FragmentMultiCheckingNextDrawCheckingBinding
import com.maicakapo.thailotto2021_1.other.Constants.CHECK_MULTI_MODE
import com.maicakapo.thailotto2021_1.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber


class MultiCheckingANDNextDrawCheckingFragment : BottomSheetDialogFragment() {

    lateinit var viewModel : MainViewModel
    lateinit var binding : FragmentMultiCheckingNextDrawCheckingBinding

    lateinit var dataStore: DataStore<Preferences>
    var userLottery1 = Lottery(0,"")
    var userLottery2 = Lottery(0,"")
    var userLottery3 = Lottery(0,"")
    var userLottery4 = Lottery(0,"")
    var userLottery5 = Lottery(0,"")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_multi_checking__next_draw_checking,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        dataStore = requireContext().createDataStore("SavedLottery")

      //  observeDrawDateListForSpinner()
        Timber.d("onCreateView")

        binding.tvClickCheck.setOnClickListener {
            //setupEditText1()
            userLottery1 = setupEditGroup(binding.edPosition1,binding.tvNotWon1,binding.tvClickForDetail1)
            userLottery2 = setupEditGroup(binding.edPosition2,binding.tvNotWon2,binding.tvClickForDetail2)
            userLottery3 = setupEditGroup(binding.edPosition3,binding.tvNotWon3,binding.tvClickForDetail3)
            userLottery4 = setupEditGroup(binding.edPosition4,binding.tvNotWon4,binding.tvClickForDetail4)
            userLottery5 = setupEditGroup(binding.edPosition5,binding.tvNotWon5,binding.tvClickForDetail5)
//            setupEditText2()
//            setupEditText3()
//            setupEditText4()
//            setupEditText5()
        }

        binding.tvClearForm.setOnClickListener {
            clearAllView()
        }

        binding.tvClickForDetail1.setOnClickListener {
            findNavController().navigate(MultiCheckingANDNextDrawCheckingFragmentDirections.actionMultiCheckingANDNextDrawCheckingFragmentToCheckingResultFragment(userLottery1))
        }

        binding.tvClickForDetail2.setOnClickListener {
            findNavController().navigate(MultiCheckingANDNextDrawCheckingFragmentDirections.actionMultiCheckingANDNextDrawCheckingFragmentToCheckingResultFragment(userLottery2))
        }

        binding.tvClickForDetail3.setOnClickListener {
            findNavController().navigate(MultiCheckingANDNextDrawCheckingFragmentDirections.actionMultiCheckingANDNextDrawCheckingFragmentToCheckingResultFragment(userLottery3))
        }

        binding.tvClickForDetail4.setOnClickListener {
            findNavController().navigate(MultiCheckingANDNextDrawCheckingFragmentDirections.actionMultiCheckingANDNextDrawCheckingFragmentToCheckingResultFragment(userLottery4))
        }
        binding.tvClickForDetail5.setOnClickListener {
            findNavController().navigate(MultiCheckingANDNextDrawCheckingFragmentDirections.actionMultiCheckingANDNextDrawCheckingFragmentToCheckingResultFragment(userLottery5))
        }

        binding.tvSavedLottery.setOnClickListener {
            lifecycleScope.launch {
                saveDataStore("p1",binding.edPosition1.text.toString())
                saveDataStore("p2",binding.edPosition2.text.toString())
                saveDataStore("p3",binding.edPosition3.text.toString())
                saveDataStore("p4",binding.edPosition4.text.toString())
                saveDataStore("p5",binding.edPosition5.text.toString())
                Toast.makeText(requireContext(),"บันทึกแล้ว",Toast.LENGTH_LONG).show()
                clearAllView()
            }
        }

        binding.tvRestoreSavedLottery.setOnClickListener {
            lifecycleScope.launch {
                clearAllView()
                binding.edPosition1.setText(readDataStore("p1")?: "")
                binding.edPosition2.setText(readDataStore("p2")?: "")
                binding.edPosition3.setText(readDataStore("p3")?: "")
                binding.edPosition4.setText(readDataStore("p4")?: "")
                binding.edPosition5.setText(readDataStore("p5")?: "")

            }
        }
        return binding.root
    }

    private fun clearAllView() {
        binding.edPosition1.text.clear()
        binding.edPosition2.text.clear()
        binding.edPosition3.text.clear()
        binding.edPosition4.text.clear()
        binding.edPosition5.text.clear()
        binding.tvClickForDetail1.visibility = View.GONE
        binding.tvClickForDetail2.visibility = View.GONE
        binding.tvClickForDetail3.visibility = View.GONE
        binding.tvClickForDetail4.visibility = View.GONE
        binding.tvClickForDetail5.visibility = View.GONE
        binding.tvNotWon1.visibility = View.GONE
        binding.tvNotWon2.visibility = View.GONE
        binding.tvNotWon3.visibility = View.GONE
        binding.tvNotWon4.visibility = View.GONE
        binding.tvNotWon5.visibility = View.GONE
    }
    private fun setupEditGroup(edPosition : EditText, tvNotWon : TextView, tvClickView : View) : Lottery{
        var userLottery = Lottery(0,"")
        if (edPosition.text.length==6){
            userLottery = viewModel.checkLottery(edPosition.text.toString(),CHECK_MULTI_MODE)

            if(userLottery.totalMoneyReward!=0L){

                tvClickView.visibility = View.VISIBLE
                tvNotWon.visibility = View.GONE
            }else{
                tvClickView.visibility = View.GONE
                tvNotWon.visibility = View.VISIBLE
            }
        }
        return userLottery
    }

//    private fun setupEditText1(){
//            if (binding.edPosition1.text.length==6){
//                userLottery1 = viewModel.checkLottery(binding.edPosition1.text.toString(),CHECK_MULTI_MODE)
//                Timber.d("isWon ??? $userLottery1")
//                if(userLottery1.totalMoneyReward!=0L){
//
//                    binding.tvClickForDetail1.visibility = View.VISIBLE
//                    binding.tvNotWon1.visibility = View.GONE
//                }else{
//                    binding.tvClickForDetail1.visibility = View.GONE
//                    binding.tvNotWon1.visibility = View.VISIBLE
//                }
//            }
//    }
//
//    private fun setupEditText2(){
//        if (binding.edPosition2.text.length==6){
//            userLottery2 = viewModel.checkLottery(binding.edPosition2.text.toString(),CHECK_MULTI_MODE)
//            Timber.d("isWon ??? $userLottery1")
//            if(userLottery2.totalMoneyReward!=0L){
//
//                binding.tvClickForDetail2.visibility = View.VISIBLE
//                binding.tvNotWon2.visibility = View.GONE
//            }else{
//                binding.tvClickForDetail2.visibility = View.GONE
//                binding.tvNotWon2.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    private fun setupEditText3(){
//        if (binding.edPosition3.text.length==6){
//            userLottery3 = viewModel.checkLottery(binding.edPosition3.text.toString(),CHECK_MULTI_MODE)
//            if(userLottery3.totalMoneyReward!=0L){
//
//                binding.tvClickForDetail3.visibility = View.VISIBLE
//                binding.tvNotWon3.visibility = View.GONE
//            }else{
//                binding.tvClickForDetail3.visibility = View.GONE
//                binding.tvNotWon3.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    private fun setupEditText4(){
//        if (binding.edPosition4.text.length==6){
//            userLottery4 = viewModel.checkLottery(binding.edPosition4.text.toString(),CHECK_MULTI_MODE)
//            if(userLottery4.totalMoneyReward!=0L){
//
//                binding.tvClickForDetail4.visibility = View.VISIBLE
//                binding.tvNotWon4.visibility = View.GONE
//            }else{
//                binding.tvClickForDetail4.visibility = View.GONE
//                binding.tvNotWon4.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    private fun setupEditText5(){
//        if (binding.edPosition5.text.length==6){
//            userLottery5 = viewModel.checkLottery(binding.edPosition5.text.toString(),CHECK_MULTI_MODE)
//            if(userLottery5.totalMoneyReward!=0L){
//
//                binding.tvClickForDetail5.visibility = View.VISIBLE
//                binding.tvNotWon5.visibility = View.GONE
//            }else{
//                binding.tvClickForDetail5.visibility = View.GONE
//                binding.tvNotWon5.visibility = View.VISIBLE
//            }
//        }
//    }

    private fun observePositionInSpinner(){
        viewModel.positionInSpinner.observe(viewLifecycleOwner, Observer {
            binding.spinner2.setSelection(it)
        })
    }

    private fun observeDrawDateListForSpinner(){
        viewModel.listOfSpinner.observe(viewLifecycleOwner, Observer {
            setSpinner(it)
        })
    }
    private fun setSpinner(list : MutableList<String>) {

        ArrayAdapter(requireContext(),R.layout.row_spinner,list).also {
                adapter ->
            adapter.setDropDownViewResource(R.layout.row_spinners_dropdown)

            binding.spinner2.adapter = adapter

//            lifecycleScope.launch{
//                position =readDataStore("selectedPositionInSpinner")?.toInt() ?: 0
//                Timber.d("Datastore position is $position")
//                withContext(Dispatchers.Main){
//                    binding.spinner2.setSelection(position)
//                }
//            }
            observePositionInSpinner()
            binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Timber.d("on Select $position")
                    viewModel.getLotteryResultByDrawDate(list[position])
                    viewModel.savePositionInSpinner(position)
//                    lifecycleScope.launch{
//                        saveDataStore("selectedPositionInSpinner",position.toString())
//                    }

                }

            }

        }
    }

    private suspend fun saveDataStore(key : String, value : String){
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit {savedLottery->
            savedLottery[dataStoreKey] = value
        }
    }

    private suspend fun readDataStore(key : String) : String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]

    }
    private var position : Int =0
    override fun onStart() {
        super.onStart()


        observeDrawDateListForSpinner()

//        setupEditText1()
//        setupEditText2()
//        setupEditText3()
//        setupEditText4()
//        setupEditText5()
        userLottery1 = setupEditGroup(binding.edPosition1,binding.tvNotWon1,binding.tvClickForDetail1)
        userLottery2 = setupEditGroup(binding.edPosition2,binding.tvNotWon2,binding.tvClickForDetail2)
        userLottery3 = setupEditGroup(binding.edPosition3,binding.tvNotWon3,binding.tvClickForDetail3)
        userLottery4 = setupEditGroup(binding.edPosition4,binding.tvNotWon4,binding.tvClickForDetail4)
        userLottery5 = setupEditGroup(binding.edPosition5,binding.tvNotWon5,binding.tvClickForDetail5)
        Timber.d("onStart")

    }
    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("on DestroyView")

    }




}