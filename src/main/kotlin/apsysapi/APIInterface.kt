package apsysapi

import japsapi.*
import prime.utils.log


object APIInterface {

    fun getAccountIdentifier(externalType: String, externalId: String, session: JApsSession): JApsAccountRef? {
        try {
            val account = JApsAccount()
            session.getAccountFromExtIds(account, externalType, externalId)
            return account.accountRefObject.copy()
        } catch (e: JApsException) {
            log.error("APSYS account not found for type/reference: $externalType / $externalId")
        }
        return null
    }


    fun validateOrder(order: JApsTrcOrder, session: JApsSession): JApsOrderRef? {
        val result = session.initCashTransferOrder()
        if (result != 1) {
            log.error("Error during order environment initialization.")
            return null
        }
        try {
            val orderList = session.validateCashTransferOrder(order)
            for (i in 0 until orderList.size) {
                val err = orderList.fetch(i) as JApsError
                log.info("Error: ${err.code}-${err.errorType}-${err.label}")
                if (err.errorType == 3) {
                    log.error("This error avoids cash transfer order creation.")
                    log.error("Free cash transfer creation handler.")
                    session.freeCashTransferOrder(order.handle)
                    throw Exception("${err.code}-${err.label}")
                }
            }

            val orderRef = session.createCashTransferOrder(order.handle)
            log.info("Order created with reference: ${orderRef.dtcptl}-${orderRef.nusesi}-${orderRef.fiches}")
            return orderRef
        } catch (e: JApsException) {
            log.error("Cash Transfer Order creation error: $e")
            var errorMessage = "${e.errorCode}-${e.message}"
            if (e.errorCode == 111) {
                log.error("Check input data as a string is too long.")
                errorMessage = "${e.errorCode} - Check input data as a string is too long."
            }
            throw Exception(errorMessage)
        }
    }

    fun existOrder(order: JApsTrcOrder, session: JApsSession): Boolean {
        if (session.findCashTransferOrder(order) == 1) {
            val obj = order.orderRefObject
            val apsysReference = "${obj.dtcptl}-${obj.nusesi}-${obj.fiches}"

            val errMessage = "External reference (${order.externalReference}) already found in APSYS transaction ( $apsysReference )"
            log.error(errMessage)
            throw Exception(errMessage)
        } else {
            return false
        }
    }


    /*

     */
    fun existOrder(order: JApsTrcExtOrder, session: JApsSession): Boolean {
        if (session.findExternalCashTransferOrder(order) == 1) {
            val obj = order.orderRefObject
            val apsysReference = "${obj.dtcptl}-${obj.nusesi}-${obj.fiches}"

            val errMessage = "External reference (${order.externalReference}) already found in APSYS transaction ( $apsysReference )"
            log.error(errMessage)
            throw Exception(errMessage)
        } else {
            return false
        }
    }

    fun validateOrder(order: JApsTrcExtOrder, session: JApsSession): JApsOrderRef? {
        val result = session.initCashTransferOrder()
        if (result != 1) {
            log.error("Error during order environment initialization.")
            return null
        }
        try {
            val orderList = session.validateExternalCashTransferOrder(order)
            for (i in 0 until orderList.size) {
                val err = orderList.fetch(i) as JApsError
                log.info("Error: ${err.code} - ${err.errorType} - ${err.label}")
                /*
                Todo
                */
                if (err.errorType == 3 /*&& !XMLProperty.getBoolean("force.transaction.creation")*/) {
                    log.error("This error avoids cash transfer order creation.")
                    throw Exception("${err.code} - ${err.label}")
                }
            }

            val orderRef = session.createExternalCashTransferOrder(order.handle)
            log.info("Order created with reference:  ${orderRef.dtcptl}-${orderRef.nusesi}-${orderRef.fiches}")
            return orderRef
        } catch (e: JApsException) {
            log.error("Cash Transfer Order creation error: ", e)
            var errorMessage = "${e.errorCode} - ${e.message}"
            if (e.errorCode == 111) {
                log.error("Check input data as a string is too long.")
                errorMessage = "$errorMessage - Check input data as a string is too long."
            }
            throw Exception(errorMessage)
        }

    }

}
