package tf.samir.calculatordemo.ryanmichaelkay

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tf.samir.calculatordemo.ryanmichaelkay.data.Evaluator
import tf.samir.calculatordemo.ryanmichaelkay.data.SimpleCalculator
import tf.samir.calculatordemo.ryanmichaelkay.data.Validator
import tf.samir.calculatordemo.ryanmichaelkay.domain.Calculator
import tf.samir.calculatordemo.ryanmichaelkay.domain.ResultWrapper
import tf.samir.calculatordemo.ryanmichaelkay.userinterface.CalculatorUI
import tf.samir.calculatordemo.ryanmichaelkay.userinterface.ViewEvent

internal const val VALID_EXPRESSION: String = "2+2"
internal const val VALID_ANSWER: String = "4"
internal const val INVALID_EXPRESSION: String = "2+++"

class CalculatorTest {

    private lateinit var calc: Calculator
    private val validator = ValidatorFake()
    private val evaluator = EvaluatorFake()
    private val logicFake = CalculatorLogicFake()

    /**
     * 1. Give expression to validator: true = valid
     * 2. Give expression to evaluator: String = successful
     * 3. return result
     */
    @Test
    fun `On Evaluate Valid Expression`() = runBlocking {
        calc = SimpleCalculator(validator, evaluator)

        calc.evaluateExpression(VALID_EXPRESSION, logicFake::handleResult)

        val result = logicFake.result
        if (result is ResultWrapper.Success) {
            assertEquals(result.value, "4")
        } else {
            assertTrue(false)
        }
    }

    /**
     * 1. Give expression to validator: false = invalid
     * 2. return result: Exception
     *
     */
    @Test
    fun `On Evaluate invalid Expression`() = runBlocking {
        calc = SimpleCalculator(validator, evaluator)
        evaluator.succeed = false
        calc.evaluateExpression(INVALID_EXPRESSION, logicFake::handleResult)

        val result = logicFake.result
        if (result is ResultWrapper.Error) {
            assertTrue(true)
        } else {
            assertTrue(false)
        }
    }
}

class ValidatorFake : Validator {
    internal var succeed: Boolean = true
    internal var called: Boolean = false

    override suspend fun validateExpression(exp: String): Boolean {
        called = true
        return succeed
    }
}

class EvaluatorFake : Evaluator {

    internal var succeed: Boolean = true
    internal var called: Boolean = false

    override suspend fun evaluateExpression(exp: String): ResultWrapper<Exception, String> {
        called = true
        return if (succeed)  ResultWrapper.build { VALID_ANSWER }
        else ResultWrapper.build { throw Exception() }
    }

}

class CalculatorLogicFake : CalculatorUI.Logic {
    var viewEventCalled = false
    var handleResultCalled = false
    var result: ResultWrapper<Exception, String>? = null

    override fun handleViewEvent(event: ViewEvent) {
        viewEventCalled = true
    }

    override fun handleResult(result: ResultWrapper<Exception, String>) {
        this.result = result
        handleResultCalled = true
    }

}