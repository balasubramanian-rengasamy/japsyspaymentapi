package prime.api

import japsapi.JApsAccountExtract
import japsapi.JApsSecurityList
import japsapi.JApsSecurityMovement
import japsapi.JApsValuation
import prime.model.Account
import prime.model.CashMovement
import prime.model.SecurityMovement
import java.io.Serializable

interface AccountResource : Serializable {

    fun retrieveAccounts(portfolioNo: Int):List<Account>?

    fun cashMoveListByAccount(
            accountNo:Int,
            accountType: String,
            currency: String,
            fromdate: Int,
            todate: Int
    ):List<CashMovement>?

    fun securityExternalIdentifier(
            idtis:String,
            gres:String,
            grecps:String
    ): JApsSecurityList?

    fun securityMovementList(
            accountNo:Int,
            fromdate: Int,
            todate: Int
    ):List<SecurityMovement>

    fun getValuationList(
            portfolioNo: Int,
            currency: String,
            date: Int): List<JApsValuation>?

    fun getAccountExtract(
            idPortfolio: Int,
            ids: String,
            startDate:Int,
            endDate:Int):List<JApsAccountExtract>
}