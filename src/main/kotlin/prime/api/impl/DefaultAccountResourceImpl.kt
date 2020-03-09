package prime.api.impl

import apsysapi.ApSysClient
import japsapi.JApsAccountExtract
import japsapi.JApsSecurityList
import japsapi.JApsValuation
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import prime.api.AccountResource
import prime.model.Account
import prime.model.CashMovement
import prime.model.SecurityMovement
import java.lang.Exception
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("order")
@Tag(name = "Account", description = "Account")
class DefaultAccountResourceImpl : AccountResource {

    @GET
    @Path("/allByPortfolioNo/{portfolioNo}")
    @Operation(description = "Retrieve cash accounts by portfolioNo")
    override fun retrieveAccounts(
        @PathParam("portfolioNo") portfolioNo: Int): List<Account>? {
        return ApSysClient.retrieveAccounts(portfolioNo)
    }

    @GET
    @Path("/cashMoveListByAccount/{accountNo}/{accountType}/{currency}/{fromdate}/{todate}")
    @Operation(description = "Retrieve cash accounts by portfolioNo")
    override fun cashMoveListByAccount(
        @PathParam("accountNo") accountNo: Int,
        @PathParam("accountType") accountType: String,
        @PathParam("currency") currency: String,
        @PathParam("fromdate") fromdate: Int,
        @PathParam("todate") todate: Int
    ): List<CashMovement>? {
        return ApSysClient.cashMoveListByAccount(
            accountNo,
            accountType,
            currency,
            fromdate,
            todate)!!.map { CashMovement(it) }
    }

    @GET
    @Path("/securityExternalIdentifier/{idtis}/{gres}/{grecps}")
    @Operation(description = "Retrieve securityExternalIdentifier")
    override fun securityExternalIdentifier(
        @PathParam("idtis") idtis: String,
        @PathParam("gres") gres: String,
        @PathParam("grecps") grecps: String): JApsSecurityList? {
        return ApSysClient.securityExternalIdentifier(idtis, gres, grecps)
    }

    @GET
    @Path("/securityMovementList/{accountNo}/{fromdate}/{todate}")
    @Operation(description = "Retrieve security transactions list by portfolioNo")
    override fun securityMovementList(
        @PathParam("accountNo") accountNo: Int,
        @PathParam("fromate") fromdate: Int,
        @PathParam("todate") todate: Int): List<SecurityMovement> {
        TODO("Not yet implemented")
    }

    @GET
    @Path("/getValuationList/{portfolioNo}/{currency}/{date}")
    @Operation(description = "getValuationList by portfolioNo")
    override fun getValuationList(
        @PathParam("portfolioNo") portfolioNo: Int,
        @PathParam("currency") currency: String,
        @PathParam("date") date: Int): List<JApsValuation>? {
        return ApSysClient.getValuationList(portfolioNo, currency, date)
    }

    @GET
    @Path("/getAccountExtract/{portfolioNo}/{Ids}/{startDate}/{endDate}")
    @Operation(description = "getAccountExtract by portfolioNo")
    override fun getAccountExtract(
        @PathParam("portfolioNo") idPortfolio: Int,
        @PathParam("Ids") ids: String,
        @PathParam("startDate") startDate: Int,
        @PathParam("endDate") endDate: Int
    ): List<JApsAccountExtract> {
        return ApSysClient.getAccountExtract(idPortfolio, ids, startDate, endDate)
    }
}