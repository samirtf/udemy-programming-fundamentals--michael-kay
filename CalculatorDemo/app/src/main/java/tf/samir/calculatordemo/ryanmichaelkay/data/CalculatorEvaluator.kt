package tf.samir.calculatordemo.ryanmichaelkay.data

import tf.samir.calculatordemo.ryanmichaelkay.data.datamodel.Operand
import tf.samir.calculatordemo.ryanmichaelkay.data.datamodel.Operator
import tf.samir.calculatordemo.ryanmichaelkay.domain.ResultWrapper

object CalculatorEvaluator: Evaluator {

    override suspend fun evaluateExpression(exp: String): ResultWrapper<Exception, String> {
        val operators: MutableList<Operator> = getOperators(exp)
        val operands: MutableList<Operand> = getOperands(exp)

        while (operands.size > 1) {
            val firstOperand = operands[0]
            val secondOperand = operands[1]
            val firstOperator = operators[0]
            val secondOperator: Operator? = operators.getOrNull(1)

            if (evaluateCurrentPair(firstOperator, secondOperator)) {
                val result = Operand(evaluatePair(firstOperand, secondOperand, firstOperator))
                operators.remove(firstOperator)
                operands.remove(firstOperand)
                operands.remove(secondOperand)

                operands.add(0, result)
            } else {
                val thirdOperand = operands[2]
                val result = Operand(evaluatePair(secondOperand, thirdOperand, secondOperator!!))

                operators.remove(secondOperator)
                operands.remove(secondOperand)
                operands.remove(thirdOperand)

                operands.add(1, result)
            }
        }

        // Last exp in block is returned via Single Expression Syntax
        return ResultWrapper.build { operands[0].value }
    }

    // If op is * or / (evaluateFirst), or no more operatorDataModels to follow,
    // or next op is NOT (evaluateFirst)
    private fun evaluateCurrentPair(
        firstOperator: Operator,
        secondOperator: Operator?
    ): Boolean {
        return when {
            firstOperator.evaluateFirst -> true
            secondOperator == null -> true
            secondOperator.evaluateFirst -> false
            else -> false
        }
    }

    private fun getOperands(expression: String): MutableList<Operand> {
        val operands = expression.split("+", "-", "/", "*")
        val outPut: MutableList<Operand> = arrayListOf()

        // Kotlin's answer to enhanced for loop
        operands.indices.mapTo(outPut) {
            Operand(operands[it])
        }
        return outPut
    }

    private fun getOperators(expression: String): MutableList<Operator> {
        // this ugly stuff is called Regex; Regular ExpressionDataModel/
        // basically saying split based on number or decimal numbers.
        val operators = expression.split("\\d+(?:\\.\\d+)?".toRegex())
            .filterNot { it == "" }
            .toMutableList()
        val outPut: MutableList<Operator> = arrayListOf()

        operators.indices.mapTo(outPut) {
            Operator(operators[it])
        }
        return outPut
    }

    private fun evaluatePair(firstOperand: Operand, secondOperand: Operand, operator: Operator): String {
        when(operator.value) {
            "+" -> return (firstOperand.value.toFloat() + secondOperand.value.toFloat()).toString()
            "-" -> return (firstOperand.value.toFloat() - secondOperand.value.toFloat()).toString()
            "/" -> return (firstOperand.value.toFloat() / secondOperand.value.toFloat()).toString()
            "*" -> return (firstOperand.value.toFloat() * secondOperand.value.toFloat()).toString()
        }
        throw IllegalArgumentException("Illegal Operator.")
    }

}