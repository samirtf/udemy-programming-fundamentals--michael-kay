package tf.samir.calculatordemo.ryanmichaelkay.buildlogic

import kotlinx.coroutines.Dispatchers
import tf.samir.calculatordemo.ryanmichaelkay.CalculatorActivity
import tf.samir.calculatordemo.ryanmichaelkay.data.CalculatorEvaluator
import tf.samir.calculatordemo.ryanmichaelkay.data.CalculatorValidator
import tf.samir.calculatordemo.ryanmichaelkay.data.SimpleCalculator
import tf.samir.calculatordemo.ryanmichaelkay.userinterface.CalculatorLogic
import tf.samir.calculatordemo.ryanmichaelkay.userinterface.CalculatorUI

object DependencyProvider {
    fun provideLogic(main: CalculatorActivity): CalculatorUI.Logic {
        return CalculatorLogic(
            main, SimpleCalculator(
                CalculatorValidator,
                CalculatorEvaluator,
            ),
            Dispatchers.Main
        )
    }
}