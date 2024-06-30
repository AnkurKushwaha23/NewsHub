package com.example.newshub.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newshub.Ui.Fragments.BussinessFragment
import com.example.newshub.Ui.Fragments.EntertainmentFragment
import com.example.newshub.Ui.Fragments.HealthFragment
import com.example.newshub.Ui.Fragments.HomeFragment
import com.example.newshub.Ui.Fragments.ScienceFragment
import com.example.newshub.Ui.Fragments.SportsFragment
import com.example.newshub.Ui.Fragments.TechnologyFragment

class MyViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->HomeFragment()
            1->BussinessFragment()
            2->SportsFragment()
            3->EntertainmentFragment()
            4->HealthFragment()
            5->ScienceFragment()
            6->TechnologyFragment()
            else->HomeFragment()
        }
    }
}
