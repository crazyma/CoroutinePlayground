# CoroutinePlayground
Some test about coroutine and flow, especially the lifecyle handling.

- MainFragment: Do some test about launching job by different LifecycleScope launch functions.
- SecondFragment: Test basic operation of StateFlow and SharedFlow. Also the `repeatOnLifecycle` function
- FlowObserver: A customized warp class to handle lifecycle properly using Flow. [Reference](https://proandroiddev.com/livedata-vs-sharedflow-and-stateflow-in-mvvm-and-mvi-architecture-57aad108816d)
