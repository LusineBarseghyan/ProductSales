package com.example.quickstartlessons.module.account.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickstartlessons.R
import com.example.quickstartlessons.databinding.FragmentPostBinding
import com.example.quickstartlessons.module.base.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel



class PostFragment : BaseFragment() {
    private lateinit var binding: FragmentPostBinding
    private val adapter = PostAdapter()
    private val viewModel by viewModel<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.post()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeLiveData()
        binding.toolBar.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun setupView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeLiveData() {
        viewModel.postLiveData.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
        viewModel.postErrorLiveData.observe(viewLifecycleOwner) {
            showErrorMessageDialog(getString(R.string.error_data), it.toString())
        }
    }
}