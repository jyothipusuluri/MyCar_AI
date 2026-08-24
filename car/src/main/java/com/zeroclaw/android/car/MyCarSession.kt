package com.zeroclaw.android.car

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.Session

class MyCarSession : Session() {
    override fun onCreateScreen(carContext: CarContext): Screen {
        return HomeScreen(carContext)
    }
}
