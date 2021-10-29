package com.batu.coroutineplayground.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.batu.coroutineplayground.databinding.MainFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Do some test about launching job by different LifecycleScope launch functions.
 */
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private var job1: Job? = null
    private var job2: Job? = null
    private var job3: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("badu", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v("badu", "onCreateView")
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupViews()
        setupViewModel()
    }


    private fun setupViewModel() {
        viewModel.apply {
            test.observe(viewLifecycleOwner) {

            }
        }
    }

    private fun setupViews() {
        binding.apply {
            button11.setOnClickListener {
                launchLifecycleJob()
            }
            button12.setOnClickListener {
                cancelLifecycleJob()
            }
            button21.setOnClickListener {
                launchLifecycleJobWhenStarted()
            }
            button22.setOnClickListener {
                cancelLifecycleJobWhenStarted()
            }
            button31.setOnClickListener {
                launchLifecycleJobRepeatedlyWhenStart()
            }
            button32.setOnClickListener {
                cancelLifecycleJobRepeatedlyWhenStart()
            }

            testButton.setOnClickListener {

            }

            testButton2.setOnClickListener {

            }
        }
    }

    private fun launchLifecycleJob() {
        Log.i("badu", "launchLifecycleJob")
        job1?.cancel()
        job1 = viewLifecycleOwner.lifecycleScope.launch {
            for (i in 1..10) {
                delay(1000)
                Log.d("badu", "$i second")
            }
        }.apply {
            invokeOnCompletion {
                Log.d("badu", "invokeOnCompletion: $it")
                Log.i("badu", "-------------")
            }
        }
    }

    private fun cancelLifecycleJob() {
        job1?.cancel()
    }

    private fun launchLifecycleJobWhenStarted() {
        Log.i("badu", "launchLifecycleJobWhenStarted")
        job2?.cancel()
        job2 = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            for (i in 1..10) {
                delay(1000)
                Log.d("badu", "$i second")
            }
        }.apply {
            invokeOnCompletion {
                Log.d("badu", "invokeOnCompletion: $it")
                Log.i("badu", "-------------")
            }
        }
    }

    private fun cancelLifecycleJobWhenStarted() {
        job2?.cancel()
    }

    private fun launchLifecycleJobRepeatedlyWhenStart() {
        Log.i("badu", "launchLifecycleJobRepeatedlyWhenStart")
        // Start a coroutine in the lifecycle scope
        job3?.cancel()
        job3 = viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    for (i in 1..10) {
                        delay(1000)
                        Log.d("badu", "$i second")
                    }
                }.apply {
                    invokeOnCompletion {
                        Log.d("badu", "invokeOnCompletion: $it")
                        Log.i("badu", "-------------")
                    }
                }
            }
        }

        viewLifecycleOwner.repeatLaunchOnLifecycle {

        }

        viewLifecycleOwner.lifecycle.repeatLaunch {

        }
    }

    private fun cancelLifecycleJobRepeatedlyWhenStart() {
        job3?.cancel()
    }

}

fun LifecycleOwner.repeatLaunchOnLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch {
    repeatOnLifecycle(state, block)
}

fun Lifecycle.repeatLaunch(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit
) = coroutineScope.launch {
    repeatOnLifecycle(state, block)
}