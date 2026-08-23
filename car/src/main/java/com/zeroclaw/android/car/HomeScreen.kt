package com.zeroclaw.android.car

import android.content.Intent
import android.net.Uri
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.ItemList
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import androidx.car.app.model.Row

class HomeScreen(private val carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): PaneTemplate {
        val itemListBuilder = ItemList.Builder()

        val assistantRow = Row.Builder()
            .setTitle("Open Assistant")
            .setOnClickListener {
                // Deep link into the phone app. Phone should handle mycar://assistant/open
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("mycar://assistant/open")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                carContext.startCarAppActivity(intent)
            }
            .build()

        itemListBuilder.addItem(assistantRow)

        val pane = Pane.Builder().setRows(itemListBuilder.build()).build()

        return PaneTemplate.Builder(pane)
            .setHeaderAction(Action.BACK)
            .setTitle("MyCar AI")
            .build()
    }
}
