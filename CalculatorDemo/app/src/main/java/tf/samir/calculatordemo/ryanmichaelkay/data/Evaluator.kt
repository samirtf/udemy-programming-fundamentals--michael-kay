package tf.samir.calculatordemo.ryanmichaelkay.data

import tf.samir.calculatordemo.ryanmichaelkay.domain.ResultWrapper

interface Evaluator {
    suspend fun evaluateExpression(exp: String): ResultWrapper<Exception, String>
}