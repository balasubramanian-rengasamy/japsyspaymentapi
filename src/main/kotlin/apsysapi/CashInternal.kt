package apsysapi

import japsapi.JApsDefine.APS_STATE_VALIDATED
import japsapi.JApsSession
import japsapi.JApsTrcOrder
import prime.model.ApiOutput
import java.lang.RuntimeException

class CashInternal {

    val apSysAPI = ApSysAPI()
    private fun login(): JApsSession = apSysAPI.Session()

    fun process(
        externalType:String,

        operationType:String        /*0*/,
        cashTransferType:String     /*1*/,

        creditAccount:String        /*2*/,
        creditText:String?           /*3*/,
        debitAccount:String         /*4*/,
        debitText:String?            /*5*/,
        operationCode:String        /*6*/,
        amount:Double                /*7.toFloat()*/,
        currency:String             /*8*/,
        executionDate:Int?           /*9.toInt()*/,
        valueDate:Int?               /*10.toInt()*/,
        beginValidDate:Int?          /*11.toInt()*/,
        endValidDate:Int?            /*12.toInt()*/,
        externalReference:String?    /*13*/,
        backOfficeMessage:String?    /*14*/,
        internalMessage:String?      /*15*/,
        forexMessage:String?         /*16*/,
        specialInstr0s:String?       /*17*/,
        specialInstr1s:String?       /*18*/,
        specialInstr2s:String?       /*19*/,
        specialInstr3s:String?       /*20*/,
        justificationCode:String?    /*21*/,
        justificationInstruction:String? /*22*/,
        justificationText:String?    /*23*/,
        adviceProd:String?           /*24*/,
        instSpecDonOrd0s:String?     /*25*/,
        instSpecDonOrd1s:String?     /*26*/,
        instSpecDonOrd2s:String?     /*27*/,
        instSpecDonOrd3s:String?     /*28*/,

        internalOrderAdviceProduction:String,
        internalOrderStatus:Int,
        internalOrderReceptionType:String,
        comments:String = ""
    ):ApiOutput {
        val session = login()
        try {
            val order = JApsTrcOrder()
            val creditRef = APIInterface.getAccountIdentifier(externalType, creditAccount, session)
                ?:throw RuntimeException("Unknown credit account: $creditAccount")


            order.accountCreditObject.idAccount = creditRef.idAccount
            order.accountCreditObject.type = creditRef.type
            order.accountCreditObject.currency = creditRef.currency
            order.accountCreditObject.termDate = creditRef.termDate

            order.freeTextCredit = creditText

            val debitRef = APIInterface.getAccountIdentifier(externalType, debitAccount, session)
                ?:throw RuntimeException("Unknown debit account: $debitAccount")

            order.accountDebitObject.idAccount = debitRef.idAccount
            order.accountDebitObject.type = debitRef.type
            order.accountDebitObject.currency = debitRef.currency
            order.accountDebitObject.termDate = debitRef.termDate
            order.freeTextDebit = debitText

            order.operationCode = operationCode
            order.amountObject.amount = amount
            //order.amountObject.currency = currency
            order.amountObject.currency = debitRef.currency

            order.executionDate = executionDate ?: 0
            order.valueDate = valueDate ?: 0
            order.beginValidityDate = beginValidDate ?: 0
            order.endValidityDate = if(endValidDate == null ) getEndValidatityDate(2) else {
                if (endValidDate == 0) getEndValidatityDate(2) else endValidDate
            }

            order.externalReference = externalReference
            order.facturationMessage = backOfficeMessage
            order.internalMessage = internalMessage
            order.traderMessage = forexMessage

            order.firstSpecialInstruction = specialInstr0s
            order.secondSpecialInstruction = specialInstr1s
            order.thirdSpecialInstruction = specialInstr2s
            order.fourthSpecialInstruction = specialInstr3s

            order.useJustification = justificationCode
            order.transferJustification = justificationInstruction
            order.transferJustificationText = justificationText
            order.adviceProductionIn = adviceProd

            order.instspecdonord0s = instSpecDonOrd0s
            order.instspecdonord1s = instSpecDonOrd1s
            order.instspecdonord2s = instSpecDonOrd2s
            order.instspecdonord3s = instSpecDonOrd3s

            order.comments = comments.toUpperCase()

            if (order.adviceProductionIn.isEmpty())
                order.adviceProductionIn = internalOrderAdviceProduction
            order.stateFlag = internalOrderStatus
            order.cdrecpttypc = internalOrderReceptionType

            /*
            Todo
             */
            order.stateFlag = APS_STATE_VALIDATED

            APIInterface.existOrder(order, session)

            val orderRef = APIInterface.validateOrder(order, session)!!

            val apSysRefno = "${orderRef.dtcptl}-${orderRef.nusesi}-${orderRef.fiches}"
            return ApiOutput(apSysRefno, null, "Completed")

        }
        catch (ex:Exception) {
            return ApiOutput(null, ex.message, "Error")
        }
        finally {
            session.logout()
        }
    }
}


