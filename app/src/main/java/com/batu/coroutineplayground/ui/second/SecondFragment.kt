package com.batu.coroutineplayground.ui.second

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.batu.coroutineplayground.databinding.SecondFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Test basic operation of StateFlow and SharedFlow
 *
 * @author Batu
 */
class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var binding: SecondFragmentBinding
    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SecondFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        setupViews()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.apply {
            lifecycleScope.launch {
                Log.v("badu", "111")
                test2.collect {
                    Log.d("badu", "result: $it")
                }
                Log.v("badu", "222")

            }

            lifecycleScope.launch {
                test3.collect {
                    Log.d("badu", "test 3 in box 1 result: $it")
                }
            }

            lifecycleScope.launch {
                test3.collect {
                    Log.d("badu", "test 3 in box 2 result: $it")
                }
            }
        }
    }

    private fun setupViews() {
        binding.apply {
            testButton.setOnClickListener {
                viewModel.loadSomethingWithSharedFlow()
            }

            testButto2.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.test3.collect {
                        Log.d("badu", "test 3 in box 3 result: $it")
                    }
                }
            }
        }
    }
}