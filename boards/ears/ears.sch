EESchema Schematic File Version 4
EELAYER 30 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L LED:WS2812B D1
U 1 1 613B0EE1
P 4050 1350
F 0 "D1" H 4394 1396 50  0000 L CNN
F 1 "WS2812B" H 4394 1305 50  0000 L CNN
F 2 "LED_SMD:LED_WS2812B_PLCC4_5.0x5.0mm_P3.2mm" H 4100 1050 50  0001 L TNN
F 3 "https://cdn-shop.adafruit.com/datasheets/WS2812B.pdf" H 4150 975 50  0001 L TNN
	1    4050 1350
	1    0    0    -1  
$EndComp
$Comp
L RF_Module:ESP32-WROOM-32D U3
U 1 1 613B1B98
P 1850 2300
F 0 "U3" H 1850 3881 50  0000 C CNN
F 1 "ESP32-WROOM-32D" H 1850 3790 50  0000 C CNN
F 2 "RF_Module:ESP32-WROOM-32" H 1850 800 50  0001 C CNN
F 3 "https://www.espressif.com/sites/default/files/documentation/esp32-wroom-32d_esp32-wroom-32u_datasheet_en.pdf" H 1550 2350 50  0001 C CNN
	1    1850 2300
	1    0    0    -1  
$EndComp
$Comp
L Regulator_Linear:AZ1117-3.3 U1
U 1 1 613BA165
P 1800 4450
F 0 "U1" H 1800 4692 50  0000 C CNN
F 1 "AZ1117-3.3" H 1800 4601 50  0000 C CNN
F 2 "Package_TO_SOT_SMD:SOT-223" H 1800 4700 50  0001 C CIN
F 3 "https://www.diodes.com/assets/Datasheets/AZ1117.pdf" H 1800 4450 50  0001 C CNN
	1    1800 4450
	1    0    0    -1  
$EndComp
$Comp
L Regulator_Linear:SPX2920T-5.0_TO263 U2
U 1 1 613BF9A8
P 2700 4450
F 0 "U2" H 2700 4692 50  0000 C CNN
F 1 "SPX2920T-5.0_TO263" H 2700 4601 50  0000 C CNN
F 2 "Package_TO_SOT_SMD:TO-263-3_TabPin2" H 2700 4675 50  0001 C CIN
F 3 "http://www.zlgmcu.com/Sipex/LDO/PDF/spx2920.pdf" H 2700 4400 50  0001 C CNN
	1    2700 4450
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0101
U 1 1 613C4885
P 1800 5900
F 0 "#PWR0101" H 1800 5650 50  0001 C CNN
F 1 "GND" H 1805 5727 50  0000 C CNN
F 2 "" H 1800 5900 50  0001 C CNN
F 3 "" H 1800 5900 50  0001 C CNN
	1    1800 5900
	1    0    0    -1  
$EndComp
Wire Wire Line
	1800 4750 1800 5000
Wire Wire Line
	2700 4750 2700 5000
Wire Wire Line
	2700 5000 1800 5000
Connection ~ 1800 5000
Wire Wire Line
	1800 5000 1800 5350
Wire Wire Line
	1850 3700 1000 3700
Wire Wire Line
	1000 3700 1000 5000
Wire Wire Line
	1000 5000 1800 5000
$Comp
L power:+3.3V #PWR0102
U 1 1 613C99A0
P 2150 4000
F 0 "#PWR0102" H 2150 3850 50  0001 C CNN
F 1 "+3.3V" H 2165 4173 50  0000 C CNN
F 2 "" H 2150 4000 50  0001 C CNN
F 3 "" H 2150 4000 50  0001 C CNN
	1    2150 4000
	1    0    0    -1  
$EndComp
$Comp
L power:+5V #PWR0103
U 1 1 613CA31D
P 3250 4000
F 0 "#PWR0103" H 3250 3850 50  0001 C CNN
F 1 "+5V" H 3265 4173 50  0000 C CNN
F 2 "" H 3250 4000 50  0001 C CNN
F 3 "" H 3250 4000 50  0001 C CNN
	1    3250 4000
	1    0    0    -1  
$EndComp
Wire Wire Line
	3250 4000 3250 4450
Wire Wire Line
	3250 4450 3000 4450
Wire Wire Line
	2150 4000 2150 4450
Wire Wire Line
	2150 4450 2100 4450
$Comp
L Connector:Conn_01x02_Male J1
U 1 1 613D67CF
P 750 5250
F 0 "J1" H 858 5431 50  0000 C CNN
F 1 "Conn_01x02_Male" H 858 5340 50  0000 C CNN
F 2 "Connector_PinHeader_1.00mm:PinHeader_1x02_P1.00mm_Vertical" H 750 5250 50  0001 C CNN
F 3 "~" H 750 5250 50  0001 C CNN
	1    750  5250
	1    0    0    -1  
$EndComp
Wire Wire Line
	950  5250 1500 5250
Wire Wire Line
	2400 5250 2400 4450
Wire Wire Line
	1500 4450 1500 5250
Connection ~ 1500 5250
Wire Wire Line
	1500 5250 2400 5250
Wire Wire Line
	950  5350 1800 5350
Connection ~ 1800 5350
Wire Wire Line
	1800 5350 1800 5900
$Comp
L power:+3.3V #PWR0104
U 1 1 613D9724
P 1850 600
F 0 "#PWR0104" H 1850 450 50  0001 C CNN
F 1 "+3.3V" H 1865 773 50  0000 C CNN
F 2 "" H 1850 600 50  0001 C CNN
F 3 "" H 1850 600 50  0001 C CNN
	1    1850 600 
	1    0    0    -1  
$EndComp
Wire Wire Line
	1850 600  1850 900 
$Comp
L power:+5V #PWR0105
U 1 1 613DADDB
P 4050 850
F 0 "#PWR0105" H 4050 700 50  0001 C CNN
F 1 "+5V" H 4065 1023 50  0000 C CNN
F 2 "" H 4050 850 50  0001 C CNN
F 3 "" H 4050 850 50  0001 C CNN
	1    4050 850 
	1    0    0    -1  
$EndComp
Wire Wire Line
	4050 850  4050 1050
$Comp
L power:GND #PWR0106
U 1 1 613DB3EE
P 4050 1750
F 0 "#PWR0106" H 4050 1500 50  0001 C CNN
F 1 "GND" H 4055 1577 50  0000 C CNN
F 2 "" H 4050 1750 50  0001 C CNN
F 3 "" H 4050 1750 50  0001 C CNN
	1    4050 1750
	1    0    0    -1  
$EndComp
Wire Wire Line
	4050 1650 4050 1750
Wire Wire Line
	2450 1600 3150 1600
Wire Wire Line
	3150 1600 3150 1350
Wire Wire Line
	3150 1350 3750 1350
$EndSCHEMATC
