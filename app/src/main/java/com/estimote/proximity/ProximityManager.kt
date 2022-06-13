package com.estimote.proximity

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import com.estimote.proximity.estimote.ProximityContent
import com.estimote.proximity.fragments.Frag_Chambre
import com.estimote.proximity.fragments.Frag_Cuisine
import com.estimote.proximity.fragments.Frag_Place_Publique
import kotlin.collections.ArrayList

object ProximityManager {
    //TODO add data to change user/tags for beacon id
    //TODO unit test to check if all beacons that user has ^ has a mapping
    private var fragmentManager : FragmentManager? = null
    private val listOfLastScans : MutableList<List<ProximityContent>> = ArrayList()
    private var listOfAllBeacons : MutableList<ProximityContent> = ArrayList()
    private var currentBeaconTitle : String? = null
    private var beaconToFragmentMap : MutableMap<String,Fragment> =
        mapOf("Chambre_Mattys" to Frag_Chambre.newInstance(),"Cuisine" to Frag_Cuisine.newInstance(),"Place Publique" to Frag_Place_Publique.newInstance()) as MutableMap<String, Fragment>
    fun addListOfBeacons(listOfBeacons:List<ProximityContent>){
        if(listOfBeacons.isNotEmpty()){

            listOfLastScans.add(listOfBeacons)
            if (listOfLastScans.size > 3) {
                listOfLastScans.removeFirst()
            }
            updateFragment()

        }
    }
    fun updateFragment(){
        val beaconCount:MutableMap<String,Int> = mutableMapOf()
        for ( list in listOfLastScans ){
            for ( beacon in list ){
                var lastCount = 0
                if (beaconCount.containsKey(beacon.title)){
                    lastCount = beaconCount[beacon.title]!!
                    beaconCount[beacon.title] = lastCount+1
                }else{
                    beaconCount.put(beacon.title,lastCount+1)
                }
                if(!listOfAllBeacons.contains(beacon)){
                    listOfAllBeacons.add(beacon)
                }
            }
        }
        println(beaconCount)
        val beaconTitle: String
        if(listOfLastScans.last().size==1){
            beaconTitle = listOfLastScans.last()[0].title
        }
        else{
            //Get le plus grand nombre
            var biggestBeaconCountTitle : String = beaconCount.keys.elementAt(0)
            for (key in beaconCount.keys){
                if (beaconCount[key]!! > beaconCount[biggestBeaconCountTitle]!!){
                    biggestBeaconCountTitle = key
                }
            }
            beaconTitle = biggestBeaconCountTitle
        }
        if (currentBeaconTitle != beaconTitle){
            val to_fragment:Fragment = beaconToFragmentMap[beaconTitle]!!
            changeToFragment(to_fragment)
        }
    }
    private fun changeToFragment(fragment: Fragment){
        println("Changing to fragment $fragment")
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }
    fun setFragmentManager(_fragmentManager: FragmentManager){
        fragmentManager = _fragmentManager
    }
}