package com.batu.coroutineplayground.ui.second

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.batu.coroutineplayground.databinding.SecondFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Test basic operation of StateFlow and SharedFlow.
 * Also the `repeatOnLifecycle` function
 *
 * @author Batu
 */
class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    enum class LaunchMethod{
        Normal, LaunchX, RepeatOn
    }

    private lateinit var binding: SecondFragmentBinding
    private lateinit var viewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("badu", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v("badu", "onCreateView")
        binding = SecondFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        setupViews()
        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
        Log.v("badu", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v("badu", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v("badu", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v("badu", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v("badu", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("badu", "onDestroy")
    }

    private fun setupViewModel() {
        viewModel.apply {
            Log.e("badu", "$this")
            viewLifecycleOwner.lifecycleScope.launch {
                Log.v("badu", "111")
                test2.collect {
                    Log.d("badu", "result: $it")
                }
                Log.v("badu", "222")

            }

            viewLifecycleOwner.lifecycleScope.launch {
                test3.collect {
                    Log.d("badu", "test 3 in box 1 result: $it")
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                test3.collect {
                    Log.d("badu", "test 3 in box 2 result: $it")
                }
            }
        }
        collectMutableStateFlowTest4(LaunchMethod.RepeatOn)
    }

    private fun collectMutableStateFlowTest4(launchMethod: LaunchMethod){
        when(launchMethod){
            LaunchMethod.Normal -> {
                // It would continue collecting emitted value, which would cause directly crash
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.test4.collect {
                        Log.i("badu", "(1) test 4 : $it")
                    }
                }
            }
            LaunchMethod.LaunchX -> {
                // It would suspend the coroutine but not cancel it, which would cause hidden issue,
                // then continue coroutine when back to correct lifecycle state.
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    viewModel.test4.collect {
                        Log.i("badu", "(1) test 4 : $it")
                    }
                }
            }
            LaunchMethod.RepeatOn -> {
                // It would cancel and relaunch coroutine in correct lifecycle state.
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.test4.collect {
                            Log.i("badu", "(1) test 4 : $it")
                        }
                    }
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