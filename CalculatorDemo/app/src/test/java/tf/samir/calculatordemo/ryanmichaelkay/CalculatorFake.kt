package tf.samir.calculatordemo.ryanmichaelkay

import tf.samir.calculatordemo.ryanmichaelkay.domain.Calculator
import tf.samir.calculatordemo.ryanmichaelkay.domain.ResultWrapper

class CalculatorFake : Calculator {

    var succeed = false

    override suspend fun evaluateExpression(
        exp: String,
        callback: (ResultWrapper<Exception, String>) -> Unit
    ) {
        if (succeed) callback.invoke(ResultWrapper.build { "4" })
        else callback.invoke(ResultWrapper.build { throw java.lang.Exception() })
    }
}