package ru.cherryperry.precomputedtext

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = adapter
        generateData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.api) {
            val value = !adapter.fast
            item.isChecked = value
            adapter.fast = value
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateData() {
        thread {
            val lorems = arrayOf(
                R.string.lorem1,
                R.string.lorem2,
                R.string.lorem3,
                R.string.lorem4,
                R.string.lorem5,
                R.string.lorem6,
                R.string.lorem7,
                R.string.lorem8,
                R.string.lorem9,
                R.string.lorem10
            )
            val boldFont = ResourcesCompat.getFont(this, R.font.black)!!
            val italicFont = ResourcesCompat.getFont(this, R.font.italic)!!
            val accentColor = ContextCompat.getColor(this, R.color.colorAccent)
            val primaryColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            val result = IntRange(0, 100)
                .asSequence()
                .map { getString(lorems[it % lorems.size]) }
                .map { string ->
                    val builder = SpannableStringBuilder(string)
                    builder.setSpan(
                        CustomTypefaceSpan(boldFont),
                        0,
                        1,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    builder.setSpan(
                        RelativeSizeSpan(2f),
                        0,
                        1,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    val firstWordEnd = string.indexOf(' ')
                    builder.setSpan(
                        ForegroundColorSpan(accentColor),
                        0,
                        firstWordEnd,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    val secondWordEnd = string.indexOf(' ', firstWordEnd + 1)
                    builder.setSpan(
                        CustomTypefaceSpan(italicFont),
                        firstWordEnd,
                        secondWordEnd,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    var lastSpaceIndex = string.indexOf('.')
                    var spaceIndex = string.indexOf(' ', lastSpaceIndex)
                    var odd = false
                    do {
                        if (odd) {
                            builder.setSpan(
                                ForegroundColorSpan(primaryColor),
                                lastSpaceIndex,
                                spaceIndex,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            odd = false
                        } else {
                            builder.setSpan(
                                RelativeSizeSpan(1.5f),
                                lastSpaceIndex,
                                spaceIndex,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            odd = true
                        }
                        lastSpaceIndex = spaceIndex
                        spaceIndex = string.indexOf(' ', lastSpaceIndex + 1)
                    } while (spaceIndex != -1)
                    builder
                }
                .map { it as CharSequence }
                .toList()
            runOnUiThread { adapter.data = result }
        }
    }
}
