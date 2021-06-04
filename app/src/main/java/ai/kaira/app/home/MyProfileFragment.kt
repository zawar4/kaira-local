package ai.kaira.app.home

import ai.kaira.app.R
import ai.kaira.app.account.login.LoginActivity
import ai.kaira.app.databinding.FragmentMyProfileBinding
import ai.kaira.app.utils.Extensions.Companion.clearToken
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class MyProfileFragment : Fragment() {

    private lateinit var binding:FragmentMyProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_profile, container, false)
        binding.logoutBtn.setOnClickListener {
            requireContext().clearToken();
            requireActivity().finish()
            var intent = Intent(requireContext(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        return binding.root
    }

}