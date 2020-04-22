package com.kevinli5506.appta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {
private val list: ArrayList<SpecialPromo> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.addAll(SpecialPromoData.listPromo)
        view.home_rv_special_promo.layoutManager = GridLayoutManager(view.context,2)
        val specialPromoAdapter = HomeSpecialPromoAdapter(list,4)
        view.home_rv_special_promo.adapter = specialPromoAdapter
    }


}
