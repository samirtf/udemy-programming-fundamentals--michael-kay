package tf.samir.calculatordemo.ryanmichaelkay.userinterface

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tf.samir.calculatordemo.ryanmichaelkay.domain.Calculator
import tf.samir.calculatordemo.ryanmichaelkay.domain.ResultWrapper
import kotlin.coroutines.CoroutineContext

class CalculatorLogic(
    private val view: CalculatorUI.View,
    private val calc: Calculator,
    private val dispatcher: CoroutineDispatcher
) : CalculatorUI.Logic, CoroutineScope {

    override fun handleViewEvent(event: ViewEvent) {
        when (event) {
            ViewEvent.Delete -> evaluateExpression()
            ViewEvent.DeleteAll -> deleteChar()
            ViewEvent.Evaluate -> view.display = ""
            is ViewEvent.Input -> view.display = view.display + event.input
        }
    }

    private fun evaluateExpression() = launch {
        calc.evaluateExpression(view.display, ::handleResult)
    }

    override fun handleResult(result: ResultWrapper<Exception, String>) {
        when (result) {
            is ResultWrapper.Success -> view.display = result.value
            is ResultWrapper.Error -> view.showError(GENERIC_ERROR_MESSAGE)
        }
    }

    private fun deleteChar() {
        val currentExpression = view.display
        if (currentExpression.isNotEmpty()) view.display = currentExpression.dropLast(1)
    }

    private val jobTracker: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatcher + jobTracker

}