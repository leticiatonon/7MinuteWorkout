package com.tonon.a7minuteworkout

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_i_m_c.*
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round

class IMCActivity : AppCompatActivity() {

    val IMC_VIEW = "MASSA CORPORAL"
    val CALORIES_VIEW = "CALORIAS DIÁRIAS"

    var currentVisibleView: String = IMC_VIEW


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_m_c)

        supportActionBar!!.hide()
        toolbar()
    }

    private fun toolbar() {
        val toolbar = toolbar_imc_activity

        toolbar.setNavigationIcon(getDrawable(R.drawable.back))
        toolbar.setTitle("7 Minutes Workout")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView.equals(IMC_VIEW)){
                if (validateMetricUnits()){
                    val heightValue  = java.lang.Float.parseFloat(etMetricUnitHeight.text.toString())
                    val weightValue = Integer.parseInt(etMetricUnitWeight.text.toString())

                    val imc = weightValue / (heightValue*heightValue)
                    displayIMCResult(imc)
                }else{
                    Toast.makeText(this, "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show()
                }
            }else{
                if (validateDailyCalories()){
                    val heightValue  = java.lang.Float.parseFloat(etDailyCalHeight.text.toString())
                    val weightValue = Integer.parseInt(etDailyCaltWeight.text.toString())
                    val ageValue = Integer.parseInt(etDailyCalAge.text.toString())

                    val dailyCal = ((weightValue * 10) + (heightValue * 6.25).toFloat() - (ageValue * 5)) - 161
                    displayDailyCalResult(dailyCal)

                }else{
                    Toast.makeText(this, "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleIMCView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMetricUnits){
                makeVisibleIMCView()
            }else{
                makeVisibleCaloriesView()
            }
        }
    }

    private fun makeVisibleIMCView() {
        currentVisibleView = IMC_VIEW // Current View is updated here.
        llMetricUnitsView.visibility = View.VISIBLE // METRIC UNITS VIEW is Visible
        llUsUnitsView.visibility = View.GONE // US UNITS VIEW is hidden

        etMetricUnitHeight.text!!.clear() // height value is cleared if it is added.
        etMetricUnitWeight.text!!.clear() // weight value is cleared if it is added.

        tvYourIMC.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        IMCvalue.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        IMCtype.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        IMCdescription.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
    }


    private fun makeVisibleCaloriesView() {
        currentVisibleView = CALORIES_VIEW
        llMetricUnitsView.visibility = View.GONE
        llUsUnitsView.visibility = View.VISIBLE

        etDailyCaltWeight.text!!.clear()
        etDailyCalHeight.text!!.clear()
        etDailyCalAge.text!!.clear()

        tvYourIMC.visibility = View.INVISIBLE
        IMCvalue.visibility = View.INVISIBLE
        IMCtype.visibility = View.INVISIBLE
        IMCdescription.visibility = View.INVISIBLE
    }


    private fun displayIMCResult(imc: Float){
        val imcLabel: String
        val imcDescription: String

        if (imc <= 18.5){
            imcLabel = "Você está muito abaixo do peso!"
            imcDescription = "Você precisa se cuidar, coma mais"
        } else if (imc <= 24.9) {
            imcLabel = "Normal"
            imcDescription = "Parabéns! Você está com uma boa forma"
        } else if (imc <= 29.9) {
            imcLabel = "Sobrepeso"
            imcDescription = "Você precisa se cuidar, comece a malhar"
        } else if (imc <= 34.9) {
            imcLabel = "Obesidade moderada"
            imcDescription = "Você realmente precisa se cuidar, comece a malhar"
        } else if (imc <= 39.9 ) {
            imcLabel = "Obesidade severa"
            imcDescription = "Você está em uma situação perigosa, cuide da saúde!"
        } else {
            imcLabel = "Obesidade muito severa"
            imcDescription = "Você está em uma situação perigosa, cuide da saúde!!"
        }

        IMCresult.visibility = View.VISIBLE

        /*
        yourIMC.visibility = View.VISIBLE
        IMCvalue.visibility = View.VISIBLE
        IMCtype.visibility = View.VISIBLE
        IMCdescription.visibility = View.VISIBLE
         */

        //val imcValue = BigDecimal(imc.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        //IMCvalue.text = imcValue
        IMCtype.text = imcLabel
        IMCdescription.text = imcDescription
    }

    private fun displayDailyCalResult( dailyCal: Float){
        val dailyCalText = dailyCalvalue

        dailyCalText.setText("Você deve consumir $dailyCal calorias por dia")
    }


    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if (etMetricUnitWeight.text.toString().isEmpty()){
            isValid = false
        }else if (etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateDailyCalories(): Boolean{
        var isValid = true

        when {
            etDailyCaltWeight.text.toString().isEmpty() -> isValid = false
            etDailyCalHeight.text.toString().isEmpty() -> isValid = false
            etDailyCalAge.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }
}