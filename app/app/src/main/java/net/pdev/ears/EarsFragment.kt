package net.pdev.ears

import android.R.attr
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import java.util.Timer
import kotlin.concurrent.schedule
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.pdev.ears.databinding.EarsFragmentBinding
import android.util.Log
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.runOnUiThread
import kotlin.random.Random
import kotlin.random.nextUBytes
import androidx.emoji.text.EmojiCompat

import android.graphics.Paint.ANTI_ALIAS_FLAG
import androidx.core.graphics.get
import android.R.attr.bitmap
import android.graphics.Bitmap
import android.text.Layout

import android.text.StaticLayout

import android.text.TextPaint
import java.lang.Integer.min
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@kotlin.ExperimentalUnsignedTypes
class EarsFragment : Fragment() {

    private var _binding: EarsFragmentBinding? = null
    private val maxSize = 3 * 96
    private var downloadedLedData = false
    private var getEarDataTimer: Timer? = null
    private var ledStateDataTimer: Timer? = null
    private val ledModel: LedData by activityViewModels()
    private val batteryModel: BatteryData by activityViewModels()
    private var ledData: UByteArray = UByteArray(maxSize)
    private var ledMode: LedMode = LedMode.Static
    private var staticMode: Int = 0

    enum class LedMode {
        Off,
        Static,
        Random,
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        downloadedLedData = false
        _binding = EarsFragmentBinding.inflate(inflater, container, false)

        ledModel.getRawData().observe(viewLifecycleOwner, { bytes ->
            Log.i("EARS", "Update led color : ${bytes.size / 3} leds")
            ledData = bytes
            if (getEarDataTimer != null) {
                getEarDataTimer?.cancel()
                getEarDataTimer = null
            }
            updateEarLEDColors()
        })

        ledModel.getBrightness().observe(viewLifecycleOwner, { brightness ->
            this.binding.ledBrightness.progress = (brightness * 100).toInt()
        })

        this.binding.ledBrightness.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                var brightness: Double = progress / 100.0
                Log.i("LEDBright", "${progress}%")
                ledModel.setBrightness(brightness)
                binding.ledBrightnessLevel.text = "${progress}%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                ledModel.setRawData(ledData)
            }
        })

        batteryModel.getBatteryLevel().observe(viewLifecycleOwner, { level ->
            updateBatteryLevel(level)
        })

        return binding.root
    }


    fun updateBatteryLevel(level: UInt) {
        Log.i("EARS", "Update battery level : $level")
        this.binding.vBattText.text = "vBatt : $level mv"
    }

    fun getLEDColor(ledID: Int): ColorStateList {
        val rID = (ledID * 3);
        val gID = (ledID * 3) + 1;
        val bID = (ledID * 3) + 2;

        val r = if (rID < ledData.size) ledData[rID].toInt() else 0
        val g = if (gID < ledData.size) ledData[gID].toInt() else 0
        val b = if (bID < ledData.size) ledData[bID].toInt() else 0

        return ColorStateList.valueOf(
            Color.rgb(r.toInt(), g.toInt(), b.toInt())
        )
    }

    private fun updateEarLEDColors() {
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

    private fun ledModeOff() {
        ledModel.setRawData(UByteArray(maxSize))
    }

    private fun ledModeRandom() {
        ledModel.setRawData(Random.nextUBytes(maxSize))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.modeRandom.onClick {
            ledModeRandom()
            ledMode = LedMode.Random
        }

        this.binding.modeStatic.onClick {
            ledMode = LedMode.Static
        }

        this.binding.modeOff.onClick {
            ledModeOff()
            ledMode = LedMode.Off
        }

        this.binding.modeHappy.onClick {
            when(staticMode) {
                1 -> ledModeStatic("❤", "❤") // ❤
                2 -> ledModeStatic("H", "I") // HI
                3 -> ledModeStatic("\uD83C\uDF34", "\uD83C\uDF34") // 🌴
                4 -> ledModeStatic("\uD83C\uDF4D", "\uD83C\uDF4D") // 🍍
                5 -> ledModeStatic("\uD83C\uDF44", "\uD83C\uDF44") // 🍄
                6 -> ledModeStatic("\uD83D\uDCA5","\uD83D\uDCA5") // 💥
                7 -> ledModeStatic("\uD83C\uDF08","\uD83C\uDF08") // 🌈
                8 -> ledModeStatic("⭐","⭐") // ⭐
                9 -> ledModeStatic("\uD83D\uDD25","\uD83D\uDD25") // 🔥
                10 -> ledModeStatic("☢","☢") // ☢
                11 -> ledModeStatic("↘","↘") // ↘
                12 -> ledModeStatic("↖","↖") // ↖
                13 -> ledModeStatic("\uD83D\uDD05","\uD83D\uDD05") // 🔅
                14 -> ledModeStatic("❓","❓") // ❓
                15 -> ledModeStatic("❗","❗") // ❗
            else -> {
                    ledModeStatic("⚠", "⚠") // ⚠
                    staticMode = 0
                }
            }
            staticMode += 1
        }

        getEarDataTimer = Timer("getEarData", false)
        getEarDataTimer?.schedule(0, 500) {
            Log.i("getEarDataTimer", "getEarData tick")
            (activity as MainActivity).getEarColors()
        }

        ledStateDataTimer = Timer("updateLEDState", false)
        ledStateDataTimer?.schedule(0, 3000) {
//            Log.i("getEarDataTimer", "getEarData tick")
            (activity as MainActivity).getBatteryLevel()

            when (ledMode) {
                LedMode.Random -> {
                    runOnUiThread {
                        ledModeRandom()
                    }
                }
                LedMode.Off -> {
                }
                LedMode.Static -> {
                }
            }
        }
    }

    private fun ledModeStatic(lData: CharSequence, rData: CharSequence) {
        val width = 2048
        val height = width
        val lEmoji = EmojiCompat.get().process(lData)
        val rEmoji = EmojiCompat.get().process(rData)
        val earLeft = drawText(lEmoji, width, height)
        val earRight = drawText(rEmoji, width, height)

        var earPos = 0

        val skips = listOf<Pair<Int, Int>>(
            Pair(0, 0),
            Pair(1, 0),
            Pair(6, 0),
            Pair(7, 0),
            Pair(0, 1),
            Pair(7, 1),
            Pair(0, 2),
            Pair(7, 2),
            Pair(0, 5),
            Pair(7, 5),
            Pair(0, 6),
            Pair(7, 6),
            Pair(0, 7),
            Pair(1, 7),
            Pair(6, 7),
            Pair(7, 7),
        )

        val rGain = 1.5
        val gGain = 1.2
        val bGain = 0.8

        for (y in 0..7) {
            for (x in 0..7) {
                val coord: Pair<Int, Int> = Pair(x, y)
                if (coord in skips) {
                    continue
                }

                val color = Color.valueOf(earLeft?.getPixel(x, y)!!)
                val r: UByte = (color.red() * 0xFF).toInt().toUByte()
                val g: UByte = (color.green() * 0xFF).toInt().toUByte()
                val b: UByte = (color.blue() * 0xFF).toInt().toUByte()
                ledData[earPos++] = min((r.toDouble() * rGain).toInt(), 0xFF).toUByte()
                ledData[earPos++] = min((g.toDouble() * gGain).toInt(), 0xFF).toUByte()
                ledData[earPos++] = min((b.toDouble() * bGain).toInt(), 0xFF).toUByte()
            }
        }

        for (y in 0..7) {
            for (x in 7 downTo 0) {
                val coord: Pair<Int, Int> = Pair(x, y)
                if (coord in skips) {
                    continue
                }

                val color = Color.valueOf(earRight?.getPixel(x, y)!!)
                val r: UByte = (color.red() * 0xFF).toInt().toUByte()
                val g: UByte = (color.green() * 0xFF).toInt().toUByte()
                val b: UByte = (color.blue() * 0xFF).toInt().toUByte()
                ledData[earPos++] = min((r.toDouble() * rGain).toInt(), 0xFF).toUByte()
                ledData[earPos++] = min((g.toDouble() * gGain).toInt(), 0xFF).toUByte()
                ledData[earPos++] = min((b.toDouble() * bGain).toInt(), 0xFF).toUByte()
            }
        }
        ledMode = LedMode.Static
        ledModel.setRawData(ledData)
    }

    fun drawText(text: CharSequence, textWidth: Int, textHeight: Int): Bitmap? {
// Create bitmap and canvas to draw to
        val b = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)

// Draw background
        val paint = Paint(
            (ANTI_ALIAS_FLAG
                    or Paint.LINEAR_TEXT_FLAG)
        )
//        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER

        var scale = 1.0f
        var sized = false
        val bounds = Rect()
        val tolerance = 0.1f
        var step = 0.05f;
        while (!sized) {
            paint.textSize = textWidth.toFloat() * scale
            paint.getTextBounds(text, 0, 1, bounds)
            var height = bounds.height()
            var width = bounds.width()
            Log.i("EARS", "emoji $width x $height")
            if (//abs(width - textWidth) < (textWidth * tolerance) &&
                abs(height - textHeight) < (textHeight * tolerance)) {
                sized = true
            }
            else {
                if (height < textHeight) {
                    scale += step
                } else {
                    scale -= step
                }

            }
        }

        var height = bounds.height()
        var width = bounds.width()
        var hMid = (bounds.top + bounds.bottom) / 2
        var x = (textWidth) / 2.0f
        var y = ((textHeight) / 2.0f) - (hMid * 1.0f)

        c.drawText(
            text,
            0,
            text.length,
            x,
            y,
            paint
        )

        var temp = b
        var currentSize = textWidth

        while (currentSize > 8) {
            currentSize /= 4
            temp = BITMAP_RESIZER(temp, currentSize, currentSize)
        }
//        temp = BITMAP_RESIZER(temp, 8,8)

        return temp
    }

    fun BITMAP_RESIZER(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val ratioX = newWidth / bitmap.width.toFloat()
        val ratioY = newHeight / bitmap.height.toFloat()
        val middleX = newWidth / 2.0f
        val middleY = newHeight / 2.0f
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
        val canvas = Canvas(scaledBitmap)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(
            bitmap,
            middleX - bitmap.width / 2,
            middleY - bitmap.height / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )
        return scaledBitmap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (this.activity as MainActivity).disconnect()
        _binding = null
    }
}