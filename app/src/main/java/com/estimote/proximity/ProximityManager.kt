package com.estimote.proximity

import com.estimote.proximity.estimote.ProximityContent
import kotlin.collections.ArrayList

object ProximityManager {
    //TODO add data to change user/tags for beacon id
    //TODO unit test to check if all beacons that user has ^ has a mapping
    private val listOfLastScans : MutableList<List<ProximityContent>> = ArrayList()
    private var listOfAllBeacons : MutableList<ProximityContent> = ArrayList()
    private var currentBeaconTitle : String? = null
    private var beaconToFragmentMap : Map<String,String> = mapOf("Chambre" to "Scanner","Cuisine" to "Recipe","Place Publique" to "Scanner")
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
            changeToFragment(beaconTitle)
        }
    }
    private fun changeToFragment(beaconTitle: String){
        currentBeaconTitle=beaconTitle
        //TODO change content/fragment
        println("Changing to fragment of beacon $beaconTitle")
    }
}