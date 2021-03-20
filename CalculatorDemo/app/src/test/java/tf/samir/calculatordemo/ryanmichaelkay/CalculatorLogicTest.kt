package tf.samir.calculatordemo.ryanmichaelkay

import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tf.samir.calculatordemo.ryanmichaelkay.userinterface.CalculatorLogic
import tf.samir.calculatordemo.ryanmichaelkay.userinterface.ViewEvent

class CalculatorLogicTest {
    private lateinit var viewFake: CalculatorViewFake
    private lateinit var calcFake: CalculatorFake
    private val dispatcher = Dispatchers.Unconfined

    lateinit var calculatorLogic: CalculatorLogic

    /**
     * On Evaluate:
     * 1. Get current display state from View
     * 2. Give state to calcFake for evaluation
     * 3. Verify callback invoked on View
     */
    @Test
    fun `On Evaluate event success`() {
        viewFake = CalculatorViewFake()
        calcFake = CalculatorFake()
        calcFake.succeed = true

        calculatorLogic = CalculatorLogic(
            viewFake,
            calcFake,
            dispatcher
        )

        calculatorLogic.handleViewEvent(ViewEvent.Evaluate)

        assertTrue(viewFake.getDisplayCalled)
        assertTrue(viewFake.setDisplayCalled)
    }

    @Test
    fun `On Evaluate event exception`() {
        viewFake = CalculatorViewFake()
        calcFake = CalculatorFake()

        calculatorLogic = CalculatorLogic(
            viewFake,
            calcFake,
            dispatcher
        )

        calculatorLogic.handleViewEvent(ViewEvent.Evaluate)

        assertTrue(viewFake.getDisplayCalled)
        assertTrue(viewFake.showErrorCalled)
        assertFalse(calcFake.succeed)
    }

    /**
     * On Delete:
     * 1. Tell VM to delete symbol
     */
    @Test
    fun `On Delete event`() {
        viewFake = CalculatorViewFake()
        calcFake = CalculatorFake()
        viewFake.expression = "1+1"

        calculatorLogic = CalculatorLogic(
            viewFake,
            calcFake,
            dispatcher
        )

        calculatorLogic.handleViewEvent(ViewEvent.DeleteAll)

        assertTrue(viewFake.expression == "")
    }

    /**
     * On Delete:
     * 1. Get current display state from VM
     * 2. If length of display state > 0, delete last char
     * 3. Give state back to VM
     */
    @Test
    fun `On Delete All event`() {
        viewFake = CalculatorViewFake()
        calcFake = CalculatorFake()
        viewFake.expression = "1+1"

        calculatorLogic = CalculatorLogic(
            viewFake,
            calcFake,
            dispatcher
        )

        calculatorLogic.handleViewEvent(ViewEvent.DeleteAll)

        assertTrue(viewFake.expression == "")
    }

    /**
     * On Delete:
     * 1. Get current display state from VM
     * 2. If length of display state > 0, delete last char
     * 3. Give state back to VM
     */
    @Test
    fun `On Input called`() {
        viewFake = CalculatorViewFake()
        calcFake = CalculatorFake()
        viewFake.expression = "1"

        calculatorLogic = CalculatorLogic(
            viewFake,
            calcFake,
            dispatcher
        )

        calculatorLogic.handleViewEvent(ViewEvent.Input("+"))
        assertTrue(viewFake.getDisplayCalled)
        assertTrue(viewFake.expression == "1+")
    }

}