package com.tuwaiq.mangareader.signUpPage

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.Constants
import com.tuwaiq.mangareader.LoginData
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.SignPageFragmentBinding


private const val TAG = "SignPageFragment"
class SignPageFragment : Fragment() {



    private lateinit var naveController: NavController
    private lateinit var binding: SignPageFragmentBinding
    private lateinit var progressDialog: ProgressDialog


    var data = LoginData()
    var emil =data.emil
    var password =data.password
    var userName =data.userName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Constants.firebaseAuth

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignPageFragmentBinding.inflate(layoutInflater)
        naveController =findNavController()
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("pleas wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)



        checkUser()

        //for log in
        binding.loginBtn.setOnClickListener {

           validateUser()
         // findNavController().navigate(R.id.action_signPageFragment_to_mainPageFragment)
        }

        binding.registerTxt.setOnClickListener {
            naveController.navigate(R.id.action_signPageFragment_to_registerFragment)


        }
        return binding.root
    }

// ???? ?????????? ???????????????? 
    private fun validateUser() {
        //check if the user put correct pass and emil
        emil = binding.emilTxt.text.toString().trim()
        password = binding.passwordTxt.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emil).matches()){
            binding.emilTxt.error ="not valid emil"
        }
    else if (TextUtils.isEmpty(password)){
        binding.passwordTxt.error ="enter password"
        }
       else{
              loginUser()
        }
    }
    private fun loginUser() {
        progressDialog.show()
       Constants.firebaseAuth.signInWithEmailAndPassword(emil,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //?????? ???????? ?????????????????? ?????????? ???? ??????????????????
                //why
                val firbaseUser = Constants.firebaseAuth.currentUser
                val emil = firbaseUser!!.email
                Toast.makeText(context, "successes log in with$emil ", Toast.LENGTH_LONG).show()
                naveController.navigate(R.id.action_registerFragment_to_mainPageFragment)
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context, "login failed $it", Toast.LENGTH_LONG).show()
                Log.e(TAG,"login error $it")
            }
    }
    private fun checkUser(){
    val firebaseUser =Constants.firebaseAuth.currentUser
        Log.d("fromSignPage", "fff $firebaseUser")
    if (firebaseUser != null) {

            val action = SignPageFragmentDirections.actionSignPageFragmentToMainPageFragment()
        findNavController().navigate(action)

    }
    }


}

