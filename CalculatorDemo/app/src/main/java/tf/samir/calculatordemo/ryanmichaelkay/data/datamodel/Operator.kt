package tf.samir.calculatordemo.ryanmichaelkay.data.datamodel

import java.lang.IllegalArgumentException

/**
 * Data class for an Operator. Operator is one of:
 * - char "*"; multiply
 * - char "/"; divide
 * - char "+"; add
 * - char "-"; Subtract
 *
 * "*" and "/" are to be evaluated before "+" and "-" as per BEDMAS
 * Created by R_KAY on 9/25/2017.
 */

data class Operator(val value: String) {
    val evaluateFirst: Boolean = checkPriority(value)

    private fun checkPriority(operatorValue: String): Boolean {
        return when (operatorValue) {
            "*" -> true
            "/" -> true
            "+" -> false
            "-" -> false
            else -> throw  IllegalArgumentException("Illegal Operator.")
        }
    }

}