package space.arkady.foodapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesAdapter.differ.currentList[position])
                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction("Undo",
                View.OnClickListener {
                    viewModel.insertMeal(favoritesAdapter.differ.currentList[position])
                }).show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerFavorites)
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