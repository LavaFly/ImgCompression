#include <Wire.h>                             //für I2C
unsigned char rdata[16];                      //Array für Empfangene Bytes
unsigned int devs[] = {0,0,0,0};              //I2C Addressen von Devices
int addcounter = 0;                           //Addressencounter
int devcounter = 0;                           //Devicecounter
void setup() {
  Serial.begin(9600);                         
  Wire.begin(9600);
  i2c_scan();                                 //ein einfacher I2C-Addressenscan
  if(true){                                   //true = einlesen, false = ausgeben
  String trashcan = Serial.readStringUntil('\n');//Screen(Bash) sendet am Anfang Daten, welche ich nicht brauch
  } else {
    delay(7000);
    Serial.println("begin");
    delay(2000);
    output_everything();
  }
  
}
void loop() {
  if(Serial.available() == 16) read_Serial(); //Wenn 16 Chars im seriellen Buffer sind, soll die Methode ausgeführt werden
}
void read_Serial(){
  for(int i = 0;i<16;i++){                    //Buffer in Array schreiben
    rdata[i] = Serial.read();                 //
    Serial.print((char)rdata[i]); // DEBUG
  }
  Serial.println("");             // DEBUG
  write_to_EEPROM(devs[devcounter], addcounter * 16, rdata);//Array in EEPROM schreiben
  addcounter++;                               //Addressenindex erhöhen
  if(addcounter == 4096){                     //Wenn das Ende vom EEPROM erreicht wurde
    devcounter++;                             //soll es zum nächsten wechseln
    addcounter = 0;                           //und wieder von vorne Anfangen
    Serial.println("next ROM");
  }
}
void write_to_EEPROM(int eAddr, int mAddr, char* to_write){
  Wire.beginTransmission(eAddr);              //beginnt Übertragung
  Wire.write((int)(mAddr >> 8));              //16 Bit Addresse in zwei teilen senden
  Wire.write((int)(mAddr & 0xFF));            //zuerst High-Byte, dann Low-Byte
  char count = 0;
  do{                                         //24LC518 hat 128 Byte große Pages, bei jedem Write wird immer die ganze Page neu geschrieben
    Wire.write((byte) to_write[count]);       //Es kann immer nur eine Page beschrieben werden, ergo max 128 Bytes
    count++;                                  //Die Wire-Lilbrary hat aber nur einen 32 Byte Buffer(-2 Bytes für die Addresse)
  } while(to_write[count]);                   //Um Pagewriteerrors zu vermeiden, werden immer nur 16 Bytes beschrieben
  Wire.endTransmission();
  delay(10);                                  //max Schreibdauer liegt bei 5ms, ich geh auf Nummer sicher
}
void readEEPROM(byte deviceaddress, unsigned int eeaddress) {
  unsigned char i=0;                          
  Wire.beginTransmission(deviceaddress);
  Wire.write((int)(eeaddress >> 8));   
  Wire.write((int)(eeaddress & 0xFF)); 
  Wire.endTransmission();                     //Das schreiben ist unabhängig von den Pages
  Wire.requestFrom(deviceaddress,16);         //und braucht auch keinen delay
  while(Wire.available()) rdata[i++] = Wire.read();
}
void i2c_scan(){                              //einfacher i2c addressen scanner
  byte i = 0; 
  for(byte paddr = 1; paddr<128;paddr++){     //
    Wire.beginTransmission((int)paddr);
    if(Wire.endTransmission () == 0){         //Wire.endTransmission gibt 0 zurück, wenn die Methode erfolgreich war
      devs[i] = paddr;                        //Wenn die Methode erfolgreich war, dann muss ein Gerät gefunden worden sein
      i++;                                    
      delay(1);                               //ist Konvention
    }
  }
}
void output_everything(){
  for(int i = 0;i<4;i++){
    for(int j = 0;j<4096;j++){
      readEEPROM(devs[i],(j*16));
      for(int k = 0;k<16;k++){
        Serial.print((char)rdata[k]);
      }
      Serial.println("");
      delay(10);
    }
  }
}
void debug_method(){                          
  delay(3000);
  Serial.println("start");
  if(false){                                  //Writetest 
    for(int i = 0; i<20;i++){
      //Serial.println(all[i]);
      //strcpy(rdata,all[i].c_str());
      //write_to_EEPROM(0x50,(16*i), rdata);
      delay(100);
    }
  } 
  if(false) {                                 //Readtest
    for(int i = 0;i<24;i++){
      readEEPROM(0x50,(i*16));
      for(int j = 0;j<16;j++){
        Serial.print((char)rdata[j]);
      }
      Serial.println("");
      delay(1000);
    }
  }
}
