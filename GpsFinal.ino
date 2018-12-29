/*
 * Rui Santos 
 * Complete Project Details http://randomnerdtutorials.com
 */
#include <TinyGPS++.h>
#include <SoftwareSerial.h>


TinyGPSPlus gps;
// The serial connection to the GPS module
SoftwareSerial ss(4, 3);

void setup(){
  Serial.begin(9600);
  ss.begin(9600);
}

void loop(){
  while (ss.available() > 0){
    // get the byte data from the GPS
    //byte gpsData = ss.read();
    gps.encode(ss.read());
    //Serial.write(gpsData);
    if (gps.location.isUpdated()){
      Serial.print("Latitude= "); 
      Serial.print(gps.location.lat(), 6);
      Serial.print(" Longitude= "); 
      Serial.println(gps.location.lng(), 6);
    }
  }
}
