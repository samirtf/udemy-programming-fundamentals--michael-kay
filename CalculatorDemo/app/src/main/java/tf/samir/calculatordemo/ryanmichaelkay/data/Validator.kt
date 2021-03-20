package tf.samir.calculatordemo.ryanmichaelkay.data

interface Validator {
    suspend fun validateExpression(exp: String): Boolean
}