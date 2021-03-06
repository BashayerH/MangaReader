package com.tuwaiq.mangareader.register

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.InfoUser
import com.tuwaiq.mangareader.LoginData
import com.tuwaiq.mangareader.databinding.RegisterFragmentBinding
import com.tuwaiq.mangareader.mangaApi.models.DataManga
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

private const val TAG = "RegisterFragment"

class RegisterFragment : Fragment() {




    var data = LoginData()
    var emil =data.emil
    var password =data.password
    var userName =data.userName
    var confPassword=data.confPass
    private lateinit var binding:RegisterFragmentBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var naveController:NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       Constants.firebaseAuth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(layoutInflater)

        naveController =findNavController()
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("pleas wait")
        progressDialog.setMessage("creating account on...")
        progressDialog.setCanceledOnTouchOutside(false)




        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //log in
        binding.imageView.setOnClickListener {
            validateRegister()
            //  findNavController().navigate(R.id.action_registerFragment_to_mainPageFragment)
        }

    }
     fun validateRegister() {
            userName = binding.userNameETRg.text.toString()
             emil = binding.emilTxtRg.text.toString().trim()
              password =  binding.passRg.text.toString()
             confPassword =  binding.conformPassRg.text.toString()
              UIUtil.hideKeyboard(context,view).apply {
                  when(view) {
                      binding.emilTxtRg ->{}
                      binding.userNameETRg.text ->{}
                      binding.passRg.text ->{}
                      binding.conformPassRg.text ->{}
                  }
              }
        when{
            userName.isEmpty() -> showToast("enter username")
            emil.isEmpty() ->showToast("enter a correct emil")
            password.isEmpty() -> showToast("enter password")
            password.length < 6 -> showToast("the password must be at least 6 digit")
            password != confPassword -> showToast("passwords not match")
            else -> registerUser(emil,password,userName)

        }
    }

    private fun registerUser(emil: String, password: String, username:String) {
      Constants.firebaseAuth.createUserWithEmailAndPassword(emil,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val firebaseUser = Constants.firebaseAuth.currentUser

                   val person =InfoUser(
                 binding.userNameETRg.text.toString()
                       , binding.emilTxtRg.text.toString())
                  Constants.infoUserCollection.document(firebaseUser?.uid!!)
                        .set(person).addOnSuccessListener {
                        showToast("the info is saved")
                            Log.d(TAG,"info $it")
                    }

                    showToast("the account is create ")

                    val action = RegisterFragmentDirections.actionRegisterFragmentToMainPageFragment()
                    naveController.navigate(action)
                }else{

                    showToast("there is something wrong, the account cont be init")
                    Log.e(TAG,"",task.exception)

                }
            }
        val updateProfile = userProfileChangeRequest {
            displayName = username
        }
       Constants.firebaseAuth.currentUser?.updateProfile(updateProfile)

    }

    private fun showToast(msg:String){
        Toast.makeText(requireContext(),msg, Toast.LENGTH_LONG).show()
    }

}