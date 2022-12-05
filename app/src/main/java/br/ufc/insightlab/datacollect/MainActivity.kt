package br.ufc.insightlab.datacollect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // build a set of permissions for required data types
    val permissions =
      setOf(
        HealthPermission.createReadPermission(HeartRateRecord::class),
        HealthPermission.createWritePermission(HeartRateRecord::class),
        HealthPermission.createReadPermission(StepsRecord::class),
        HealthPermission.createWritePermission(StepsRecord::class)
      )

    if (HealthConnectClient.isAvailable(this.applicationContext)) {
      // Health Connect is available and installed.
      val healthConnectClient = HealthConnectClient.getOrCreate(this.applicationContext)
      val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()

      val requestPermissions =
        registerForActivityResult(requestPermissionActivityContract) { granted ->
          if (granted.containsAll(permissions)) {
            println("Permissions accepted")
          } else {
            println("Lacking permissions")
          }
        }
      suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions(permissions)
        if (granted.containsAll(permissions)) {
          // Permissions already granted, proceed with inserting or reading data.
          println("Permission guarantee")
        } else {
          requestPermissions.launch(permissions)
        }
      }

      setContentView(R.layout.activity_main)
    } else {
      println("Testing 2")
    }


  }
}