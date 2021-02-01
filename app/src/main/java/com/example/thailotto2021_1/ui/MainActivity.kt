package com.example.thailotto2021_1.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.CollectionReference

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    @Inject
//    lateinit var firestore : CollectionReference
    lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private val allThaiLotto = Firebase.firestore.collection("ThaiLotto")
    private var prizeLotto : LotteryResult? = null
    val lotto1 = "384395"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        firebaseAnalytics = Firebase.analytics
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
//        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.findNavController())





    }

    private fun checkLotto(lotto: String="" , date : Int=0) {

        var prize1st : String = ""
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = allThaiLotto.whereEqualTo("date",date).get().await()
                querySnapshot?.let {

                    val allPrize = querySnapshot.toObjects<LotteryResult>()[0]

                    prize1st=querySnapshot.toObjects<LotteryResult>()[0].prize1st

                    withContext(Dispatchers.Main) {

                        if(lotto==prize1st) {
                            Toast.makeText(this@MainActivity, "คุณถูกรางวัล", Toast.LENGTH_LONG)
                                .show()
                            }
                        else{
                            Toast.makeText(this@MainActivity, "คุณไม่ถูกรางวัล", Toast.LENGTH_LONG).show()
                        }
                    }

                }


            }catch (e : Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "error $e.message", Toast.LENGTH_LONG).show()
                    Log.d("error",e.message!!)
                }
            }
        }
    }

    private fun checkLotto1stPrize(lotto : String, prize1 : String)  : Boolean {
//        Log.d("checkLotto1stPrize","is myLotto $lotto = ${prizeLotto?.prize1st}")
        return (lotto==prize1)
    }


    private fun subscribeToRealtimeUpdates(){
        allThaiLotto.whereEqualTo("Date",25631230)
                .addSnapshotListener(MetadataChanges.INCLUDE){ querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
            e?.let {
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }

            querySnapshot?.let {
                val sb = StringBuilder()
                for(document in it){
                    val lotto = document.toObject<LotteryResult>()
                    //Log.d("t1",lotto.prizeFirst3Digit[0])
                    sb.append("$lotto\n")
                    prizeLotto = lotto
                    Log.d("checkLotto1stPrize","autoupdate ${prizeLotto?.prize1st}")
                }

                val source = if (querySnapshot.metadata.isFromCache)
                    "from local cache"
                else
                    "from server"

                sb.append(source)

            }
        }
    }


}