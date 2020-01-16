package ru.helptense.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import ru.helptense.App
import ru.helptense.R

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val verb0 = view.findViewById<TextView>(R.id.verb0)
        val verb1 = view.findViewById<TextView>(R.id.verb1)
        val verb2 = view.findViewById<TextView>(R.id.verb2)
        val verb3 = view.findViewById<TextView>(R.id.verb3)
        val verb4 = view.findViewById<TextView>(R.id.verb4)
        val verb5 = view.findViewById<TextView>(R.id.verb5)
        val verb6 = view.findViewById<TextView>(R.id.verb6)
        val verb7 = view.findViewById<TextView>(R.id.verb7)
        val verb8 = view.findViewById<TextView>(R.id.verb8)

        val search = view.findViewById<ImageView>(R.id.btn_search)
        val table = view.findViewById<TableLayout>(R.id.result_table)
        val status = view.findViewById<TextView>(R.id.status)
        val result = view.findViewById<MaterialCardView>(R.id.result_card)

        val verbInput = view.findViewById<EditText>(R.id.input_verb)

        search.setOnClickListener {
            val response = App.instance.getVerbs(verbInput.text.toString())

            result.visibility = View.VISIBLE
            table.visibility = if (response.found) View.VISIBLE else View.GONE
            status.text = if (response.found) "Глагол найден!" else "Глагол не найден :("
            verb0.text = response.verb0
            verb1.text = response.verb1
            verb2.text = response.verb2
            verb3.text = response.verb3
            verb4.text = response.verb4
            verb5.text = response.verb5
            verb6.text = response.verb6
            verb7.text = response.verb7
            verb8.text = response.verb8

        }
    }
}
