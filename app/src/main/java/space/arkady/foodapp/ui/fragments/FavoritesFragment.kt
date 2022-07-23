package space.arkady.foodapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import space.arkady.foodapp.databinding.FragmentFavoritesBinding
import space.arkady.foodapp.ui.activities.MainActivity
import space.arkady.foodapp.ui.adapters.FavoriteMealsAdapter
import space.arkady.foodapp.ui.viewmodels.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeFavorites()
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoriteMealsAdapter()
        binding.recyclerFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoriteMealsLiveData().observe(requireActivity()) { meals ->
            favoritesAdapter.differ.submitList(meals)
        }
    }
}