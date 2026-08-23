package com.zeroclaw.android.car

import androidx.car.app.CarAppService
import androidx.car.app.Session

class CarService : CarAppService() {
    override fun onCreateSession(): Session {
        return MyCarSession()
    }
}
