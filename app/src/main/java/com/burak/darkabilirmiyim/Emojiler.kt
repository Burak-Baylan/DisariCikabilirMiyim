package com.burak.darkabilirmiyim

private const val partyEmo = 0x1F973
val partyEmoString = getEmo(partyEmo)

private const val punchEmo = 0x1F44A
val punchEmoString = getEmo(punchEmo)

private const val waterEmo = 0x1F4A7
val waterEmoString = getEmo(waterEmo)

private const val bisikletEmo = 0x1F6B2
val bisikletEmoString = getEmo(bisikletEmo)

private const val gozKirpEmo = 0x1F609
val gozKirpEmoString = getEmo(gozKirpEmo)

private const val pleaseEmo = 0x1FA7A
val pleaseEmoString = getEmo(pleaseEmo)

private const val cryEmo = 0x1F622
val cryEmoString = getEmo(cryEmo)

private const val laughEmo = 0x1F92D
val laughString = getEmo(laughEmo)

private const val sadEmo = 0x1F625
val sadEmoString = getEmo(sadEmo)

private const val belge = 0x1F4C3
val belgeEmoString = getEmo(belge)

private fun getEmo(emoCode : Int) : String{
    return String(Character.toChars(emoCode))
}


