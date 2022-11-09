package md.sesrta.udianork.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import md.sesrta.udianork.R
import md.sesrta.udianork.databinding.FragmentGameBinding
import md.sesrta.udianork.ui.adapter.MainAdapter
import md.sesrta.udianork.ui.view_model.MainViewModel

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentGameBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 5)
            adapter = MainAdapter({ id ->
                viewModel.openChest(id)
            })
        }

        viewModel.itemsLiveData.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as? MainAdapter)?.submitList(it)
        }

        viewModel.scoreLiveData.observe(viewLifecycleOwner) {
            binding.scoreTextView.text = "Score: $it"
        }

        viewModel.totalScoreLiveData.observe(viewLifecycleOwner) {
            binding.totalScoreTextView.text = "Total score: $it"
        }

        viewModel.gameIsOn.observe(viewLifecycleOwner) {
            binding.startStopButton.setText(
                if (it) {
                    R.string.stop_text_start_stop_button
                } else {
                    R.string.start_text_start_stop_button
                }
            )
            if (it) {
                binding.startStopButton.setOnClickListener {
                    viewModel.stopGame()
                }
            } else {
                binding.startStopButton.setOnClickListener {
                    viewModel.startGame()
                }
            }
        }
    }
}