package com.tuwaiq.mangareader.signUpPage

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.mangareader.LoginData
import com.tuwaiq.mangareader.R
import com.tuwaiq.mangareader.databinding.SignPageFragmentBinding

class SignPageFragment : Fragment() {


    private val signViewModel: SignPageViewModel by lazy { ViewModelProvider(this)[SignPageViewModel::class.java] }

    private lateinit var naveController: NavController
    private lateinit var binding: SignPageFragmentBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    var data = LoginData()
    var emil =data.emil
    var password =data.password
    var userName =data.userName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
       // firebaseAuth = Firebase.auth
        checkUser()
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

//        firebaseAuth = Firebase.auth
//         checkUser()

        //for log in
        binding.loginBtn.setOnClickListener {

           validateUser()
         // findNavController().navigate(R.id.action_signPageFragment_to_mainPageFragment)
        }

        binding.registerTxt.setOnClickListener {
            findNavController().navigate(R.id.action_signPageFragment_to_registerFragment)
        }
        return binding.root
    }

    private fun validateUser() {
        //check if the user put correct pass and emil

        emil = binding.emilTxt.text.toString().trim()
        password = binding.passwordTxt.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(data.emil).matches()) {
            binding.emilTxt.error = "worng emil format"
        } else if (TextUtils.isEmpty(data.password)) {
            binding.passwordTxt.error = "enter password"
        } else {
            loginUser()
        }
    }

    private fun loginUser() {

        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(emil,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //هنا حربط المعلومات بعدين مع البروفايل
                //why
                val firbaseUser = firebaseAuth.currentUser
                val emil = firbaseUser!!.email
                naveController.navigate(R.id.action_registerFragment_to_mainPageFragment)

            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context, "login failed ", Toast.LENGTH_LONG).show()
            }

    }
    private fun checkUser(){
    val firebaseUser =firebaseAuth.currentUser

    if (firebaseUser != null) {
        //كيف اسوي انتقال مباشر??
        findNavController().navigate(R.id.action_signPageFragment_to_mainPageFragment)

    }
    }


}

