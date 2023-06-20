package com.cynquil.mango.payphone.networking

import com.cynquil.amethyst.id

object PayphonePacket {
    val Query = "mango:query_payphone".id
    val Action = "mango:payphone_action".id

    val Compact = "mango:payphone_compact".id
    val Decompact = "mango:payphone_decompact".id
}