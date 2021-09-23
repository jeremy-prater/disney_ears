package net.pdev.ears

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.hardware.camera2.params.ColorSpaceTransform
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

    fun updateLedColors(bytes: UByteArray)  {
        Log.i("EARS", "Update led color : ${bytes.size}")
    }

    fun updateBatteryLevel(level: Int) {
        Log.i("EARS", "Update battery level : $level")
    }

    fun getLEDColor(ledID: Int): ColorStateList {

        // Just randomize it for now
        return ColorStateList.valueOf(
            Color.rgb(
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256)
            )
        )
    }

    fun updateEarLEDColors() {
        Log.i("LEDS", "update")
        (this.binding.led0.drawable!! as GradientDrawable).color = getLEDColor(0);
        (this.binding.led1.drawable!! as GradientDrawable).color = getLEDColor(1);
        (this.binding.led2.drawable!! as GradientDrawable).color = getLEDColor(2);
        (this.binding.led3.drawable!! as GradientDrawable).color = getLEDColor(3);
        (this.binding.led4.drawable!! as GradientDrawable).color = getLEDColor(4);
        (this.binding.led5.drawable!! as GradientDrawable).color = getLEDColor(5);
        (this.binding.led6.drawable!! as GradientDrawable).color = getLEDColor(6);
        (this.binding.led7.drawable!! as GradientDrawable).color = getLEDColor(7);
        (this.binding.led8.drawable!! as GradientDrawable).color = getLEDColor(8);
        (this.binding.led9.drawable!! as GradientDrawable).color = getLEDColor(9);
        (this.binding.led10.drawable!! as GradientDrawable).color = getLEDColor(10);
        (this.binding.led11.drawable!! as GradientDrawable).color = getLEDColor(11);
        (this.binding.led12.drawable!! as GradientDrawable).color = getLEDColor(12);
        (this.binding.led13.drawable!! as GradientDrawable).color = getLEDColor(13);
        (this.binding.led14.drawable!! as GradientDrawable).color = getLEDColor(14);
        (this.binding.led15.drawable!! as GradientDrawable).color = getLEDColor(15);
        (this.binding.led16.drawable!! as GradientDrawable).color = getLEDColor(16);
        (this.binding.led17.drawable!! as GradientDrawable).color = getLEDColor(17);
        (this.binding.led18.drawable!! as GradientDrawable).color = getLEDColor(18);
        (this.binding.led19.drawable!! as GradientDrawable).color = getLEDColor(19);
        (this.binding.led20.drawable!! as GradientDrawable).color = getLEDColor(10);
        (this.binding.led21.drawable!! as GradientDrawable).color = getLEDColor(21);
        (this.binding.led22.drawable!! as GradientDrawable).color = getLEDColor(22);
        (this.binding.led23.drawable!! as GradientDrawable).color = getLEDColor(23);
        (this.binding.led24.drawable!! as GradientDrawable).color = getLEDColor(24);
        (this.binding.led25.drawable!! as GradientDrawable).color = getLEDColor(25);
        (this.binding.led26.drawable!! as GradientDrawable).color = getLEDColor(26);
        (this.binding.led27.drawable!! as GradientDrawable).color = getLEDColor(27);
        (this.binding.led28.drawable!! as GradientDrawable).color = getLEDColor(28);
        (this.binding.led29.drawable!! as GradientDrawable).color = getLEDColor(29);
        (this.binding.led30.drawable!! as GradientDrawable).color = getLEDColor(30);
        (this.binding.led31.drawable!! as GradientDrawable).color = getLEDColor(31);
        (this.binding.led32.drawable!! as GradientDrawable).color = getLEDColor(32);
        (this.binding.led33.drawable!! as GradientDrawable).color = getLEDColor(33);
        (this.binding.led34.drawable!! as GradientDrawable).color = getLEDColor(34);
        (this.binding.led35.drawable!! as GradientDrawable).color = getLEDColor(35);
        (this.binding.led36.drawable!! as GradientDrawable).color = getLEDColor(36);
        (this.binding.led37.drawable!! as GradientDrawable).color = getLEDColor(37);
        (this.binding.led38.drawable!! as GradientDrawable).color = getLEDColor(38);
        (this.binding.led39.drawable!! as GradientDrawable).color = getLEDColor(39);
        (this.binding.led40.drawable!! as GradientDrawable).color = getLEDColor(40);
        (this.binding.led41.drawable!! as GradientDrawable).color = getLEDColor(41);
        (this.binding.led42.drawable!! as GradientDrawable).color = getLEDColor(42);
        (this.binding.led43.drawable!! as GradientDrawable).color = getLEDColor(43);
        (this.binding.led44.drawable!! as GradientDrawable).color = getLEDColor(44);
        (this.binding.led45.drawable!! as GradientDrawable).color = getLEDColor(45);
        (this.binding.led46.drawable!! as GradientDrawable).color = getLEDColor(46);
        (this.binding.led47.drawable!! as GradientDrawable).color = getLEDColor(47);
        (this.binding.led48.drawable!! as GradientDrawable).color = getLEDColor(48);
        (this.binding.led49.drawable!! as GradientDrawable).color = getLEDColor(49);
        (this.binding.led50.drawable!! as GradientDrawable).color = getLEDColor(50);
        (this.binding.led51.drawable!! as GradientDrawable).color = getLEDColor(51);
        (this.binding.led52.drawable!! as GradientDrawable).color = getLEDColor(52);
        (this.binding.led53.drawable!! as GradientDrawable).color = getLEDColor(53);
        (this.binding.led54.drawable!! as GradientDrawable).color = getLEDColor(54);
        (this.binding.led55.drawable!! as GradientDrawable).color = getLEDColor(55);
        (this.binding.led56.drawable!! as GradientDrawable).color = getLEDColor(56);
        (this.binding.led57.drawable!! as GradientDrawable).color = getLEDColor(57);
        (this.binding.led58.drawable!! as GradientDrawable).color = getLEDColor(58);
        (this.binding.led59.drawable!! as GradientDrawable).color = getLEDColor(59);
        (this.binding.led60.drawable!! as GradientDrawable).color = getLEDColor(60);
        (this.binding.led61.drawable!! as GradientDrawable).color = getLEDColor(61);
        (this.binding.led62.drawable!! as GradientDrawable).color = getLEDColor(62);
        (this.binding.led63.drawable!! as GradientDrawable).color = getLEDColor(63);
        (this.binding.led64.drawable!! as GradientDrawable).color = getLEDColor(64);
        (this.binding.led65.drawable!! as GradientDrawable).color = getLEDColor(65);
        (this.binding.led66.drawable!! as GradientDrawable).color = getLEDColor(66);
        (this.binding.led67.drawable!! as GradientDrawable).color = getLEDColor(67);
        (this.binding.led68.drawable!! as GradientDrawable).color = getLEDColor(68);
        (this.binding.led69.drawable!! as GradientDrawable).color = getLEDColor(69);
        (this.binding.led70.drawable!! as GradientDrawable).color = getLEDColor(70);
        (this.binding.led71.drawable!! as GradientDrawable).color = getLEDColor(71);
        (this.binding.led72.drawable!! as GradientDrawable).color = getLEDColor(72);
        (this.binding.led73.drawable!! as GradientDrawable).color = getLEDColor(73);
        (this.binding.led74.drawable!! as GradientDrawable).color = getLEDColor(74);
        (this.binding.led75.drawable!! as GradientDrawable).color = getLEDColor(75);
        (this.binding.led76.drawable!! as GradientDrawable).color = getLEDColor(76);
        (this.binding.led77.drawable!! as GradientDrawable).color = getLEDColor(77);
        (this.binding.led78.drawable!! as GradientDrawable).color = getLEDColor(78);
        (this.binding.led79.drawable!! as GradientDrawable).color = getLEDColor(79);
        (this.binding.led80.drawable!! as GradientDrawable).color = getLEDColor(80);
        (this.binding.led81.drawable!! as GradientDrawable).color = getLEDColor(81);
        (this.binding.led82.drawable!! as GradientDrawable).color = getLEDColor(82);
        (this.binding.led83.drawable!! as GradientDrawable).color = getLEDColor(83);
        (this.binding.led84.drawable!! as GradientDrawable).color = getLEDColor(84);
        (this.binding.led85.drawable!! as GradientDrawable).color = getLEDColor(85);
        (this.binding.led86.drawable!! as GradientDrawable).color = getLEDColor(86);
        (this.binding.led87.drawable!! as GradientDrawable).color = getLEDColor(87);
        (this.binding.led88.drawable!! as GradientDrawable).color = getLEDColor(88);
        (this.binding.led89.drawable!! as GradientDrawable).color = getLEDColor(89);
        (this.binding.led90.drawable!! as GradientDrawable).color = getLEDColor(90);
        (this.binding.led91.drawable!! as GradientDrawable).color = getLEDColor(91);
        (this.binding.led92.drawable!! as GradientDrawable).color = getLEDColor(92);
        (this.binding.led93.drawable!! as GradientDrawable).color = getLEDColor(93);
        (this.binding.led94.drawable!! as GradientDrawable).color = getLEDColor(94);
        (this.binding.led95.drawable!! as GradientDrawable).color = getLEDColor(95);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.testLED.onClick {
            Log.i("ears", "clicked update")
            updateEarLEDColors()
        }

        (this.activity as MainActivity).getEarColors()
        (this.activity as MainActivity).getBatteryLevel()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}