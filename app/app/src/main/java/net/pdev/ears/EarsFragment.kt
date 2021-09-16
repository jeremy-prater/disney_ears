package net.pdev.ears

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import net.pdev.ears.databinding.EarsFragmentBinding
import org.jetbrains.anko.sdk27.coroutines.onClick
import android.util.Log
import kotlin.random.Random


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EarsFragment : Fragment() {

    private var _binding: EarsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = EarsFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun updateEarLEDColors() {
        Log.i("LEDS", "update")
        var led1 = this.binding.led1.drawable!! as GradientDrawable
        led1.color = ColorStateList.valueOf(Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.testLED.onClick {
            Log.i("ears", "clicked update")
            updateEarLEDColors()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}