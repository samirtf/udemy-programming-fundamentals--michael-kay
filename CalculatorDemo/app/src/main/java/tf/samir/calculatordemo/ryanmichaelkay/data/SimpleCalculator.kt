package tf.samir.calculatordemo.ryanmichaelkay.data

import tf.samir.calculatordemo.ryanmichaelkay.domain.Calculator
import tf.samir.calculatordemo.ryanmichaelkay.domain.ResultWrapper

class SimpleCalculator(private val validator: Validator, private val evaluator: Evaluator): Calculator {

    override suspend fun evaluateExpression(
        exp: String,
        callback: (ResultWrapper<Exception, String>) -> Unit
    ) {
        if (validator.validateExpression(exp)) callback.invoke(
            evaluator.evaluateExpression(exp)
        )
    }

}