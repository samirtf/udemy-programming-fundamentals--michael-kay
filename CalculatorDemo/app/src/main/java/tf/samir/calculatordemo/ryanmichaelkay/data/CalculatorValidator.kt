package tf.samir.calculatordemo.ryanmichaelkay.data

object CalculatorValidator : Validator {

    override suspend fun validateExpression(exp: String): Boolean {

        //check for valid starting/ending chars
        if (invalidStart(exp)) return false
        if (invalidEnd(exp)) return false
        if (invalidOperand(exp)) return false

        //Check for concurrent decimals and operators like "2++2"
        if (hasConcurrentOperators(exp)) return false
        if (hasConcurrentDecimals(exp)) return false
        if (hasTooManyDecimalsPerOperand(exp)) return false

        return true
    }


    private fun hasTooManyDecimalsPerOperand(expression: String): Boolean {
        val operands = expression.split("+", "-", "/", "*")
        operands.forEach { operand ->
            String
            val occurrences = operand.count { char ->
                Char
                char.equals('.')
            }

            if (occurrences > 1) return true
        }

        return false
    }

    /**
     * Error initially found by G. Trandafir!
     */
    private fun invalidOperand(expression: String): Boolean {
        val operands = expression.split("+", "-", "/", "*")

        operands.forEach { operand ->
            String
            if (operand.endsWith(".")) return true
            if (operand.startsWith(".")) return true
        }

        return false
    }


    private val validOperators = setOf("+", "-", "/", "*")

    private val validSymbols = validOperators + "."

    private fun invalidEnd(expression: String): Boolean {
        validSymbols.forEach { if (expression.endsWith(it)) return true }
        return false
    }

    private fun invalidStart(expression: String): Boolean {
        validSymbols.forEach { if (expression.startsWith(it)) return true }
        return false
    }

    private fun hasConcurrentDecimals(expression: String): Boolean {
        expression.indices
                .forEach {
                    if (it < expression.lastIndex) {
                        if (isConcurrentDecimal(expression[it], expression[it + 1])) {
                            return true
                        }
                    }
                }

        return false
    }

    private fun isConcurrentDecimal(current: Char, next: Char): Boolean {
        if (current.toString() == "." && next.toString() == ".") {
            return true
        }
        return false
    }

    private fun hasConcurrentOperators(expression: String): Boolean {
        expression.indices
                .forEach {
                    if (it < expression.lastIndex) {
                        if (isConcurrentOperator(expression[it], expression[it + 1])) {
                            return true
                        }
                    }
                }

        return false
    }

    private fun isConcurrentOperator(current: Char, next: Char): Boolean {
        if (isOperator(current) && isOperator(next)) {
            return true
        }
        return false
    }

    private fun isOperator(char: Char): Boolean {
        return when {
            //not sure why I had to toString() but char.equals("+") was not working as expected
            char.toString() == "+" -> true
            char.toString() == "-" -> true
            char.toString() == "*" -> true
            char.toString() == "/" -> true
            else -> false
        }
    }
}