package tf.samir.calculatordemo.ryanmichaelkay.domain

/**
 * Facade instead of Repository:
 * 1. A back end sub-system which is hidden behind an abstraction (interface)
 */
interface Calculator {
    suspend fun evaluateExpression(exp: String, callback: (ResultWrapper<Exception, String>) -> Unit)
}