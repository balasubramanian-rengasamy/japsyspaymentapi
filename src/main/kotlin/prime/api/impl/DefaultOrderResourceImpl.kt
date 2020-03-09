package prime.api.impl

import apsysapi.CashExternal
import apsysapi.CashInternal
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import prime.api.OrderResource
import prime.model.ApiOutput
import javax.ws.rs.*
import javax.ws.rs.core.MediaType


@Path("order")
@Tag(name = "Order Processing", description = "Order Processing")
class DefaultOrderResourceImpl : OrderResource {

    @POST
    @Path("/internalTransfer")
    @Operation(description = "Internal transfer order creation")
    override fun internalTransfer(
        @QueryParam("externalType") externalType:String,
        @QueryParam("operationType") operationType:String        /*0*/,
        @QueryParam("cashTransferType") cashTransferType:String     /*1*/,

        @QueryParam("creditAccount") creditAccount:String        /*2*/,
        @QueryParam("creditText") creditText:String?           /*3*/,
        @QueryParam("debitAccount") debitAccount:String         /*4*/,
        @QueryParam("debitText") debitText:String?            /*5*/,
        @QueryParam("operationCode") operationCode:String        /*6*/,
        @QueryParam("amount") amount:Double                /*7.toFloat()*/,
        @QueryParam("currency") currency:String             /*8*/,
        @QueryParam("executionDate") executionDate:Int?           /*9.toInt()*/,
        @QueryParam("valueDate") valueDate:Int?               /*10.toInt()*/,
        @QueryParam("beginValidDate") beginValidDate:Int?          /*11.toInt()*/,
        @QueryParam("endValidDate") endValidDate:Int?            /*12.toInt()*/,
        @QueryParam("externalReference") externalReference:String?    /*13*/,
        @QueryParam("backOfficeMessage") backOfficeMessage:String?    /*14*/,
        @QueryParam("internalMessage") internalMessage:String?      /*15*/,
        @QueryParam("forexMessage") forexMessage:String?         /*16*/,
        @QueryParam("specialInstr0s") specialInstr0s:String?       /*17*/,
        @QueryParam("specialInstr1s") specialInstr1s:String?       /*18*/,
        @QueryParam("specialInstr2s") specialInstr2s:String?       /*19*/,
        @QueryParam("specialInstr3s") specialInstr3s:String?       /*20*/,
        @QueryParam("justificationCode") justificationCode:String?    /*21*/,
        @QueryParam("justificationInstruction") justificationInstruction:String? /*22*/,
        @QueryParam("justificationText") justificationText:String?    /*23*/,
        @QueryParam("adviceProd") adviceProd:String?           /*24*/,
        @QueryParam("instSpecDonOrd0s") instSpecDonOrd0s:String?     /*25*/,
        @QueryParam("instSpecDonOrd1s") instSpecDonOrd1s:String?     /*26*/,
        @QueryParam("instSpecDonOrd2s") instSpecDonOrd2s:String?     /*27*/,
        @QueryParam("instSpecDonOrd3s") instSpecDonOrd3s:String?     /*28*/,

        @QueryParam("internalOrderAdviceProduction") internalOrderAdviceProduction:String,
        @QueryParam("internalOrderStatus") internalOrderStatus:Int,
        @QueryParam("internalOrderReceptionType") internalOrderReceptionType:String,
        @QueryParam("comments") comments:String
    ): ApiOutput {
        return CashInternal()
            .process(
                externalType,
                operationType,
                cashTransferType,
                creditAccount,
                creditText,
                debitAccount,
                debitText,
                operationCode,
                amount,
                currency,
                executionDate,
                valueDate,
                beginValidDate,
                endValidDate,
                externalReference,
                backOfficeMessage,
                internalMessage,
                forexMessage,
                specialInstr0s,
                specialInstr1s,
                specialInstr2s,
                specialInstr3s,
                justificationCode,
                justificationInstruction,
                justificationText,
                adviceProd,
                instSpecDonOrd0s,
                instSpecDonOrd1s,
                instSpecDonOrd2s,
                instSpecDonOrd3s,

                internalOrderAdviceProduction,
                internalOrderStatus,
                comments)
    }

    @POST
    @Path("/externalTransfer")
    @Operation(description = "External transfer order creation")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun externalTransfer(
        @QueryParam("externalType") externalType:String,

        @QueryParam("operationType") operationType:String        /*0*/,
        @QueryParam("cashTransferType") cashTransferType:String     /*1*/,

        @QueryParam("creditAccount") creditAccount:String        /*2*/,
        @QueryParam("creditText") creditText:String?           /*3*/,
        @QueryParam("debitAccount") debitAccount:String         /*4*/,
        @QueryParam("debitText") debitText:String?            /*5*/,
        @QueryParam("operationCode") operationCode:String        /*6*/,
        @QueryParam("amount") amount:Double                /*7.toFloat()*/,
        @QueryParam("currency") currency:String             /*8*/,
        @QueryParam("executionDate") executionDate:Int?           /*9.toInt()*/,
        @QueryParam("valueDate") valueDate:Int?               /*10.toInt()*/,
        @QueryParam("beginValidDate") beginValidDate:Int?          /*11.toInt()*/,
        @QueryParam("endValidDate") endValidDate:Int?            /*12.toInt()*/,
        @QueryParam("externalReference") externalReference:String?    /*13*/,
        @QueryParam("backOfficeMessage") backOfficeMessage:String?    /*14*/,
        @QueryParam("internalMessage") internalMessage:String?      /*15*/,
        @QueryParam("forexMessage") forexMessage:String?         /*16*/,
        @QueryParam("specialInstr0s") specialInstr0s:String?       /*17*/,
        @QueryParam("specialInstr1s") specialInstr1s:String?       /*18*/,
        @QueryParam("specialInstr2s") specialInstr2s:String?       /*19*/,
        @QueryParam("specialInstr3s") specialInstr3s:String?       /*20*/,
        @QueryParam("justificationCode") justificationCode:String?    /*21*/,
        @QueryParam("justificationInstruction") justificationInstruction:String? /*22*/,
        @QueryParam("justificationText") justificationText:String?    /*23*/,
        @QueryParam("adviceProd") adviceProd:String?           /*24*/,
        @QueryParam("instSpecDonOrd0s") instSpecDonOrd0s:String?     /*25*/,
        @QueryParam("instSpecDonOrd1s") instSpecDonOrd1s:String?     /*26*/,
        @QueryParam("instSpecDonOrd2s") instSpecDonOrd2s:String?     /*27*/,
        @QueryParam("instSpecDonOrd3s") instSpecDonOrd3s:String?     /*28*/,

        @QueryParam("swiftAbaCode") swiftAbaCode:String?,
        @QueryParam("bankName") bankName:String?,
        @QueryParam("bankAddr1") bankAddr1:String?,
        @QueryParam("bankAddr2")  bankAddr2:String?,
        @QueryParam("bankAddr3") bankAddr3:String?,
        @QueryParam("beneficiaryName") beneficiaryName:String?,
        @QueryParam("beneficiaryAddr1") beneficiaryAddr1:String?,
        @QueryParam("beneficiaryAddr2") beneficiaryAddr2:String?,
        @QueryParam("beneficiaryAddr3") beneficiaryAddr3:String?,
        @QueryParam("beneficiaryCountry") beneficiaryCountry:String?,
        @QueryParam("forFurtherCreditTo") forFurtherCreditTo:String?,

        @QueryParam("internalOrderAdviceProduction") internalOrderAdviceProduction:String,
        @QueryParam("internalOrderStatus") internalOrderStatus:Int,
        @QueryParam("internalOrderReceptionType") internalOrderReceptionType:String,
        @QueryParam("comments") comments:String
    ): ApiOutput {
        return CashExternal().
        process(
            externalType,
            operationType,
            cashTransferType,
            creditAccount,
            creditText,
            debitAccount,
            debitText,
            operationCode,
            amount,
            currency,
            executionDate,
            valueDate,
            beginValidDate,
            endValidDate,
            externalReference,
            backOfficeMessage,
            internalMessage,
            forexMessage,
            specialInstr0s,
            specialInstr1s,
            specialInstr2s,
            specialInstr3s,
            justificationCode,
            justificationInstruction,
            justificationText,
            adviceProd,
            instSpecDonOrd0s,
            instSpecDonOrd1s,
            instSpecDonOrd2s,
            instSpecDonOrd3s,
            swiftAbaCode,
            bankName,
            bankAddr1,
            bankAddr2,
            bankAddr3,
            beneficiaryName,
            beneficiaryAddr1,
            beneficiaryAddr2,
            beneficiaryAddr3,
            beneficiaryCountry,
            forFurtherCreditTo,
            internalOrderAdviceProduction,
            internalOrderStatus,
            internalOrderReceptionType,
            comments)
    }
}