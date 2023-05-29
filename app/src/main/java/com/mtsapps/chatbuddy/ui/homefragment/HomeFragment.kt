package com.mtsapps.chatbuddy.ui.homefragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.chatbuddy.MainActivity
import com.mtsapps.chatbuddy.R
import com.mtsapps.chatbuddy.databinding.CustomDialogBinding
import com.mtsapps.chatbuddy.databinding.FragmentHomeBinding
import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.CustomMessage
import com.mtsapps.chatbuddy.utils.AdaptiveItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeFragmentViewModel by viewModels()
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
            addItemDecoration(AdaptiveItemDecoration())
        }
        binding.rvHome.adapter?.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.rvHome.layoutManager?.scrollToPosition(homeAdapter.itemCount - 1)
            }
        })

        binding.editTextHome.addTextChangedListener {
            binding.sendButton.isEnabled = it!!.isNotEmpty()
            binding.sendButton.isActivated = it.isNotEmpty()

        }

        binding.sendButton.setOnClickListener {
            if (binding.sendButton.isActivated && binding.editTextHome.text != null) {
                sendPromptWithEnter(binding.editTextHome)

            }
        }

        binding.editTextHome.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (binding.sendButton.isActivated && binding.editTextHome.text != null) {
                    sendPromptWithEnter(binding.editTextHome)

                }
            }
            true
        }



        binding.materialToolbar.setOnMenuItemClickListener {
            when (it.title) {
                getString(R.string.history) -> {
                    val main = requireActivity() as MainActivity
                    main.startAd()
                    Navigation.findNavController(binding.materialToolbar)
                        .navigate(R.id.toHistoryPage)
                }
                getString(R.string.save)  -> {


                    val dialog = Dialog(requireContext(), R.style.CustomDialogTheme)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val dialogBinding = CustomDialogBinding.inflate(layoutInflater)
                    dialog.setContentView(dialogBinding.root)
                    val text = dialog.findViewById<TextView>(R.id.dialog_text)
                    text.text = getString(R.string.saveTheChat)
                    dialogBinding.positiveButton.text = getString(R.string.yes)
                    dialogBinding.negativeButton.text = getString(R.string.no)

                    dialogBinding.positiveButton.setOnClickListener {
                        homeViewModel.addChat()
                        dialog.dismiss()
                        val navController: NavController =
                            requireActivity().findNavController(R.id.fragmentContainerView)
                        binding.rvHome.visibility = View.INVISIBLE
                        binding.homeProgressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            navController.run {
                                popBackStack()
                                navigate(R.id.homeFragment)
                            }
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.chatSaved),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }, 1000)

                    }
                    dialogBinding.negativeButton.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.show()


                }
            }

            true
        }


        fetchMessages()
    }

    private fun fetchMessages() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.viewState.collect { item ->
                    binding.materialToolbar.menu.get(0).isEnabled = item.message!!.size > 1
                    homeAdapter.submitList(item.message)
                }
            }
        }
    }

    fun sendPromptWithEnter(view: EditText) {
        homeViewModel.sendRequest(
            ApiRequest(
                messages = mutableListOf(
                    CustomMessage(
                        "user",
                        view.text.toString()
                    )
                )
            )
        )
        view.editableText.clear()
    }

}