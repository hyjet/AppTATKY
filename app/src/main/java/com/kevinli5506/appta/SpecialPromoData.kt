package com.kevinli5506.appta

import android.graphics.Color

object SpecialPromoData {
    private val promoName = "Diskon Sir Salon"
    private val promoDetail = "Anda Berhak Mendapatkan Diskon 20% dari kami"
    private val promoCode = "ABCDE"
    private val promoImage = R.drawable.sir_salon
    private val promoprice = 10000

    val listPromo : ArrayList<SpecialPromo>
    get() {
        val list = arrayListOf<SpecialPromo>()
        for (i in 1..8){
            val promo = SpecialPromo()
            promo.name = promoName
            promo.detail = promoDetail
            promo.image = promoImage
            promo.code = promoCode
            promo.price = promoprice

            list.add(promo)
        }
        return list
    }
}