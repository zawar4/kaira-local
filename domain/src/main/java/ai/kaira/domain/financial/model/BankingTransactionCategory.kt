package ai.kaira.domain.financial.model

import ai.kaira.domain.assessment.model.AssessmentType
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class BankingTransactionCategory(val value : Int) {

    @SerializedName("0")
    undefined(0),
    @SerializedName("1")
    alcoholicBeveragesTobaccoAndOthersExpense(1),
    @SerializedName("2")
    animalsExpense(2),
    @SerializedName("3")
    clothingAndFootwearExpense(3),
    @SerializedName("4")
    communicationsExpense(4),
    @SerializedName("5")
    educationExpense(5),
    @SerializedName("6")
    foodAndNonAlcoholicBeveragesExpense(6),
    @SerializedName("7")
    goodsAndServicesAssociatedWithHousingExpense(7),
    @SerializedName("8")
    healthExpense(8),
    @SerializedName("9")
    housingHeatingAndLightingExpense(9),
    @SerializedName("10")
    insuranceAndFinancialServicesExpense(10),
    @SerializedName("11")
    otherGoodsAndServicesExpense(11),
    @SerializedName("12")
    recreationAndCultureExpense(12),
    @SerializedName("13")
    restaurantCafesExpense(13),
    @SerializedName("14")
    transportExpense(14),
    @SerializedName("15")
    travelsExpense(15),
    @SerializedName("16")
    personalCareExpense(16),
    @SerializedName("17")
    taxes(17),
    @SerializedName("100")
    employmentIncomeExpense(100),
    @SerializedName("101")
    consultancyAndEntrepriseIncomeExpense(101),
    @SerializedName("102")
    governmentBenefitIncomeExpense(102),
    @SerializedName("103")
    rentIncomeExpense(103),
    @SerializedName("104")
    retirementIncome(104),
    @SerializedName("105")
    investmentIncome(105),
    @SerializedName("106")
    otherIncome(106),
    @SerializedName("203")
    creditCardPaymentsTransfer(203),
    @SerializedName("204")
    savingInvestment(204);



    companion object {
        fun getSpendingCategories() : ArrayList<BankingTransactionCategory> {
            return arrayListOf(
                alcoholicBeveragesTobaccoAndOthersExpense,
            animalsExpense,
            clothingAndFootwearExpense,
            communicationsExpense,
            educationExpense,
            foodAndNonAlcoholicBeveragesExpense,
            goodsAndServicesAssociatedWithHousingExpense,
            healthExpense,
            housingHeatingAndLightingExpense,
            insuranceAndFinancialServicesExpense,
            otherGoodsAndServicesExpense,
            recreationAndCultureExpense,
            restaurantCafesExpense,
            transportExpense,
            travelsExpense,
            personalCareExpense,
            taxes
            )
        }

        fun getRevenueCategories() : ArrayList<BankingTransactionCategory> {
            return arrayListOf(
                employmentIncomeExpense,
                consultancyAndEntrepriseIncomeExpense,
                governmentBenefitIncomeExpense,
                rentIncomeExpense,
                retirementIncome,
                investmentIncome,
                otherIncome
            )
        }

        fun getTransferCategories() : ArrayList<BankingTransactionCategory> {
            return arrayListOf(
                creditCardPaymentsTransfer,
                savingInvestment
            )
        }
        operator fun invoke(type: Int) = AssessmentType.values().firstOrNull { it.value == type }
    }
}