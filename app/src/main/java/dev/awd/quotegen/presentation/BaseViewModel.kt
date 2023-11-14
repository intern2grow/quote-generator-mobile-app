package dev.awd.quotegen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

//T represents our ViewState
//U represents our ViewEffect
abstract class BaseViewModel<S> : ViewModel() {

    var dataStates: MutableStateFlow<DataState<S>> =
        MutableStateFlow(DataState.Loading(null))
        protected set

    /*var _viewEffects: MutableStateFlow<DataState<E>> =
        MutableStateFlow(DataState.Loading(null))
        protected set*/

    protected fun launchRequest(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                dataStates.emit(
                    DataState.Error(getViewState(), e)
                )
            }
        }

    }

    protected fun getViewState(): S? = dataStates.value.toData()
}