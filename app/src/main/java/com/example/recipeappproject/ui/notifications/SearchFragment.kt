package com.example.recipeappproject.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recipeappproject.R
import com.example.recipeappproject.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
//lateinit var searchView: SearchView
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        searchViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        searchView.findViewById<SearchView>(R.id.search_bar)
//        searchView.clearFocus()
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//               return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//            }
//
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}