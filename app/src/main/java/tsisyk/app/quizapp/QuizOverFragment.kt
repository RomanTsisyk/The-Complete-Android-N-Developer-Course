package tsisyk.app.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import tsisyk.app.quizapp.databinding.FragmentGameOverBinding

class QuizOverFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameOverBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_over, container, false)
        binding.tryAgainButton.setOnClickListener { view: View -> view.findNavController().navigate(R.id.action_quizFragment_to_quizOverFragment) }
        return binding.root
    }
}
