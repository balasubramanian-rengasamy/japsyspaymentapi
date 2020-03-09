package apsysapi


import japsapi.*
import japsapi.JApsDefine.APS_RELEVE_MVT
import model.SecurityMovement
import org.apache.commons.lang3.exception.ExceptionUtils
import prime.model.Account
import prime.utils.log

object ApSysClient {

    val apSysAPI = ApSysAPI()

    private fun login(): JApsSession = apSysAPI.Session()

    fun retrieveAccounts(portfolioNos: List<Int>): List<Account>? {
        val session = login()
        try {
            val result = java.util.ArrayList<Account>()

            portfolioNos.forEach {portfolioNo ->
                val accList = JApsAccountList(session)
                accList.retrieveAccounts(portfolioNo)

                accList.accounts.forEach {
                    result.add(Account(it.accountRefObject.idAccount,
                        it.accountRefObject.iban,
                        it.accountRefObject.currency,
                        it.accountRefObject.termDate,
                        it.accountRefObject.type,
                        it.balance,
                        it.prepost,
                        it.prepostString
                    ))
                }
            }
            return result
        }
        catch (ex:Exception) {
            log.error(ExceptionUtils.getStackTrace(ex))
            return null
        }
        finally {
            session.logout()

        }
    }

    fun retrieveAccounts(portfolioNo: Int): List<Account>? = retrieveAccounts(listOf<Int>(portfolioNo))

    /**
     *
     */
    fun cashMoveListByAccount(
        accountNo:Int,
        accountType: String,
        currency: String,
        fromdate: Int,
        todate: Int
    ):List<JApsCashMovement>? {
        val session = login()
        try {
            val cmr = JApsCashMovementRequest()
            APS_RELEVE_MVT
            cmr.accountObject.idAccount = accountNo
            cmr.accountObject.type = accountType
            cmr.accountObject.currency = currency
            cmr.dateType = JApsDefine.APS_TYPDATE_CTA
            cmr.fromDate = fromdate
            cmr.toDate = todate
            cmr.language = JApsDefine.APS_LANGUE_ENGLISH
            cmr.checkOrigin = true

            val cashmvts = JApsCashMovementList(session)
            cashmvts.retrieveMovements(cmr)

            return cashmvts.cashMovements
        } finally {
            session.logout()
        }
    }

    fun securityExternalIdentifier(idtis:String, gres:String, grecps:String):JApsSecurityList? {
        val session = login()

        try {
            val seclist = JApsSecurityList(session)
            val seqreq =  JApsSecurityRequest()
            seqreq.idTitObject.idtis = idtis.trim()
            seqreq.idTitObject.gres = gres.trim()
            seqreq.idTitObject.grecps = grecps.trim()
            seclist.retrieveSecurities(seqreq)

            for (sec in seclist.securities) {
                println("Security External Identifier" + sec.idTitExtObject.idExts)
            }

            return seclist
        }
        catch (ex:Exception) {
            log.error(ex.message)
            return null
        }
        finally {
            session.logout()
        }

    }

    fun securityMovementList(
        accountNo:Int,
        fromdate: Int,
        todate: Int
    ):List<SecurityMovement> {
        val session = login()

        try {
            val req = JApsSecurityMovementRequest()

            req.idcll = accountNo
            req.fromDate = fromdate
            req.toDate = todate

            val smr1 = JApsSecurityMovementList(session)
            smr1.retrieveSecurityMovements(req)

            var securityMovementList = ArrayList<SecurityMovement>()

            smr1.securityMovements.forEach { smr ->
                val securityMovement = SecurityMovement(smr)

                val seclist = JApsSecurityList(session)
                val seqreq =  JApsSecurityRequest()
                seqreq.idTitObject.idtis = smr.securityObject.idtis.trim()
                seqreq.idTitObject.gres = smr.securityObject.gres.trim()
                seqreq.idTitObject.grecps = smr.securityObject.grecps.trim()
                seclist.retrieveSecurities(seqreq)

                securityMovement.isin = seclist.securities.map { it.ident.trim() }[0]

                securityMovementList.add(securityMovement)
            }

            return securityMovementList

        }finally {
            session.logout()
        }
    }


    fun getValuationList(idPortfolio: Int, currency: String, date: Int):List<JApsValuation>? {

        val session = login()
        val req = JApsValuationRequest(idPortfolio, currency, date)
        try {
            val result = session.getValuationList(req)

            val resultArray = ArrayList<JApsValuation>()

            for (i in 0 until result.size) {
                val v = result.fetch(i) as JApsValuation
                resultArray.add(v)
            }
            return resultArray
        }
        catch(ex:java.lang.Exception) {
            log.error(ex.message)
            return null
        }
        finally {
            session.logout()
        }
    }

    fun getAccountExtract(idPortfolio: Int,
                          ids:String, startDate:Int,
                          endDate:Int):List<JApsAccountExtract> {

        val session = login()
        val accountExtract = JApsAccountExtractList(session)
        val request =  JApsAccountExtractRequest()

        request.idAccount = idPortfolio
        request.ids = ids
        request.startDate = startDate
        request.endDate = endDate

        request.language = 0
        request.typeDate = 0
        request.lastProductionDate = 0
        request.productionDate = 0
        request.productionMode= 0
        request.reversalView = true

        accountExtract.retrieveAccountExtracts(request)

        return accountExtract.accountExtracts
    }


    fun orderCreation() {
        // val order = JApsTrcOrder()
    }
}


