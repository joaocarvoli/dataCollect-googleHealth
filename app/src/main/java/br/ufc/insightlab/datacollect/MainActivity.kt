package br.ufc.insightlab.datacollect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.health.connect.client.HealthConnectClient

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (HealthConnectClient.isAvailable(this.applicationContext)) {
      // Health Connect is available and installed.
      val healthConnectClient = HealthConnectClient.getOrCreate(this.applicationContext)
      println("Testing 1")
    } else {
      println("Testing 2")
    }
  }
}