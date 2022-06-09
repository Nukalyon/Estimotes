package com.estimote.proximity.fragments

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estimote.proximity.R


/**
 * A simple [Fragment] subclass.
 * Use the [Frag_Chambre.newInstance] factory method to
 * create an instance of this fragment.
 */
class Frag_Chambre : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chambre, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() : Fragment =
            Frag_Chambre().apply {
            }
    }
}