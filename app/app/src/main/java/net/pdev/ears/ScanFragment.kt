package net.pdev.ears

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import net.pdev.ears.databinding.ScanFragmentBinding
import org.jetbrains.anko.support.v4.act
import net.pdev.ears.MainActivity

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ScanFragment : Fragment() {

    private var _binding: ScanFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = ScanFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.scanButton.setOnClickListener {
//            findNavController().navigate(R.id.action_scanFragment_to_earsFragment)
//        }
    }

    private fun setupRecyclerView() {
        binding.scanResultsRecyclerView.apply {
            adapter =         ((this.activity?) as MainActivity)

            layoutManager = LinearLayoutManager(
                this@ScanFragment,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.scanResultsRecyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}