package prime.api

import prime.model.ApiOutput
import javax.ejb.Local

@Local
abstract interface OrderResource {
/*
    abstract fun internalTransfer(
            fromAccount:String,
            narrativePayee:String?,
            currency:String?,
            amount:Double?,
            beneficiaryAccount:String,
            narrativeSender:String?,
            specialInstruction:String?,
            supplimentaryInfo1:String?,
            supplimentaryInfo2:String?,
            externalReference:String?,
            justificationCode:String?
    ):String

         */

        abstract fun internalTransfer(
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
                comments:String
    ): ApiOutput
/*
    abstract fun externalTransfer(
            fromAccount:String,
            currency:String?,
            amount:Double?,
            valueDate:String?,
            narrativePayee:String?,
            beneficiaryAccount:String?,
            beneficiaryName:String?,
            beneficiaryAddr1:String?,
            beneficiaryAddr2:String?,
            beneficiaryAddr3:String?,

            narrativeSender:String?,
            forFurtherCreditTo:String?,
            swiftAbaCode:String?,
            bankName:String?,
            bankAddr1:String?,
            bankAddr2:String?,
            bankAddr3:String?,

            beneficiaryCountry:String?,
            specialInstruction:String?,
            supplimentaryInfo1:String?,
            supplimentaryInfo2:String?,
            externalReference:String?,
            justificationCode:String?,

            intermediaryBankSwiftCode:String?,
            intermediaryBankName:String?,
            intermediaryBankAddress1:String?,
            intermediaryBankAddress2:String?,
            intermediaryBankAddress3:String?,
            intermediaryBankAccount:String
    ):String
 */


        abstract fun externalTransfer(
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

                swiftAbaCode:String?,
                bankName:String?,
                bankAddr1:String?,
                bankAddr2:String?,
                bankAddr3:String?,
                beneficiaryName:String?,
                beneficiaryAddr1:String?,
                beneficiaryAddr2:String?,
                beneficiaryAddr3:String?,
                beneficiaryCountry:String?,
                forFurtherCreditTo:String?,

                internalOrderAdviceProduction:String,
                internalOrderStatus:Int,
                internalOrderReceptionType:String,
                comments:String
        ):ApiOutput
}
