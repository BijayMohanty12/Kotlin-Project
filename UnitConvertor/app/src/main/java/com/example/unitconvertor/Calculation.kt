package com.example.unitconvertor



class Calculation {
    companion object {
        fun convert1(inputValue: Double, spinner1: String, spinner2: String): String {
            return when {
                spinner1 == "Square Kilometer" && spinner2 == "Square meter" -> {
                    val area = 1000000.000
                    (inputValue * area).toString()
                }

                spinner1 == "Square Kilometer" && spinner2 == "Square mile" -> {
                    val area = 2.59
                    (inputValue / area).toString()
                }

                spinner1 == "Square meter" && spinner2 == "Square Kilometer" -> {
                    val area = 1000000.000
                    (inputValue / area).toString()
                }

                spinner1 == "Square meter" && spinner2 == "Square mile" -> {
                    val area = 2.590000

                    (inputValue / area).toString()
                }

                spinner1 == "Square mile" && spinner2 == "Square Kilometer" -> {
                    val area = 2.59
                    (inputValue / area).toString()
                }

                spinner1 == "Square mile" && spinner2 == "Square meter" -> {
                    val area = 2.590000
                    (inputValue * area).toString()
                }

                spinner1 == "Kilometer" && spinner2 == "Meter" -> {
                    val length = 1000
                    (inputValue * length).toString()
                }

                spinner1 == "Kilometer" && spinner2 == "Centimeter" -> {
                    val length = 100000
                    (inputValue * length).toString()
                }

                spinner1 == "Kilometer" && spinner2 == "Millimetre" -> {
                    val length = 1000000
                    (inputValue * length).toString()
                }

                spinner1 == "Meter" && spinner2 == "Kilometer" -> {
                    val length = 1000.00
                    (inputValue / length).toString()
                }

                spinner1 == "Meter" && spinner2 == "Centimeter" -> {
                    val length = 100
                    (inputValue * length).toString()
                }

                spinner1 == "Meter" && spinner2 == "Millimetre" -> {
                    val length = 1000
                    (inputValue * length).toString()
                }

                spinner1 == "Centimeter" && spinner2 == "Kilometer" -> {
                    val length = 100000.00

                    (inputValue / length).toString()
                }

                spinner1 == "Centimeter" && spinner2 == "Meter" -> {
                    val length = 100.00
                    (inputValue / length).toString()
                }

                spinner1 == "Centimeter" && spinner2 == "Millimetre" -> {
                    val length = 10

                    (inputValue * length).toString()
                }

                spinner1 == "Millimetre" && spinner2 == "Kilometer" -> {
                    val length = 1000000.000
                    (inputValue / length).toString()
                }

                spinner1 == "Millimetre" && spinner2 == "Meter" -> {
                    val length = 1000.00
                    (inputValue / length).toString()
                }

                spinner1 == "Millimetre" && spinner2 == "Centimeter" -> {
                    val length = 10.00
                    (inputValue / length).toString()
                }

                spinner1 == "Liter" && spinner2 == "Millimeter" -> {
                    val volume = 1000
                    (inputValue * volume).toString()
                }

                spinner1 == "Liter" && spinner2 == "Cubic meter" -> {
                    val volume = 1000.00
                    (inputValue / volume).toString()
                }

                spinner1 == "Millimeter" && spinner2 == "Liter" -> {
                    val volume = 1000.00
                    (inputValue / volume).toString()
                }

                spinner1 == "Millimeter" && spinner2 == "Cubic meter" -> {
                    val volume = 1000000.00
                    (inputValue / volume).toString()
                }

                spinner1 == "Cubic meter" && spinner2 == "Liter" -> {
                    val volume = 1000000.00
                    (inputValue / volume).toString()
                }



                spinner1 == "Second" && spinner2 == "Minute" -> {
                    val time = 60.00
                    (inputValue / time).toString()
                }

                spinner1 == "Second" && spinner2 == "Hour" -> {
                    val time = 3600.00
                    (inputValue / time).toString()
                }

                spinner1 == "Minute" && spinner2 == "Second" -> {
                    val time = 60
                    (inputValue * time).toString()
                }

                spinner1 == "Minute" && spinner2 == "Hour" -> {
                    val time = 60.00
                    (inputValue / time).toString()
                }

                spinner1 == "Hour" && spinner2 == "Minute" -> {
                    val time = 60
                    (inputValue * time).toString()
                }

                spinner1 == "Hour" && spinner2 == "Second" -> {
                    val time = 3600
                    (inputValue * time).toString()
                }

                else -> " "


            }

        }

        fun convert2(inputValue: Double, spinner1: String, spinner2: String): String {
            return when {
                spinner1 == "Square Kilometer" && spinner2 == "Square meter" -> {
                    val area = 1000000.000
                    (inputValue /area).toString()
                }

                spinner1 == "Square Kilometer" && spinner2 == "Square mile" -> {
                    val area = 2.59
                    (inputValue * area).toString()
                }

                spinner1 == "Square meter" && spinner2 == "Square Kilometer" -> {
                    val area = 1000000.000
                    (inputValue * area).toString()
                }

                spinner1 == "Square meter" && spinner2 == "Square mile" -> {
                    val area = 2.590000

                    (inputValue *area).toString()
                }

                spinner1 == "Square mile" && spinner2 == "Square Kilometer" -> {
                    val area = 2.59
                    (inputValue *area).toString()
                }

                spinner1 == "Square mile" && spinner2 == "Square meter" -> {
                    val area = 2.590000
                    (inputValue / area).toString()
                }

                spinner1 == "Kilometer" && spinner2 == "Meter" -> {
                    val length = 1000
                    (inputValue / length).toString()
                }

                spinner1 == "Kilometer" && spinner2 == "Centimeter" -> {
                    val length = 100000
                    (inputValue /length).toString()
                }

                spinner1 == "Kilometer" && spinner2 == "Millimetre" -> {
                    val length = 1000000
                    (inputValue /length).toString()
                }

                spinner1 == "Meter" && spinner2 == "Kilometer" -> {
                    val length = 1000.00
                    (inputValue * length).toString()
                }

                spinner1 == "Meter" && spinner2 == "Centimeter" -> {
                    val length = 100
                    (inputValue / length).toString()
                }

                spinner1 == "Meter" && spinner2 == "Millimetre" -> {
                    val length = 1000
                    (inputValue / length).toString()
                }

                spinner1 == "Centimeter" && spinner2 == "Kilometer" -> {
                    val length = 100000.00

                    (inputValue * length).toString()
                }

                spinner1 == "Centimeter" && spinner2 == "Meter" -> {
                    val length = 100.00
                    (inputValue * length).toString()
                }

                spinner1 == "Centimeter" && spinner2 == "Millimetre" -> {
                    val length = 10

                    (inputValue / length).toString()
                }

                spinner1 == "Millimetre" && spinner2 == "Kilometer" -> {
                    val length = 1000000.000
                    (inputValue * length).toString()
                }

                spinner1 == "Millimetre" && spinner2 == "Meter" -> {
                    val length = 1000.00
                    (inputValue * length).toString()
                }

                spinner1 == "Millimetre" && spinner2 == "Centimeter" -> {
                    val length = 10.00
                    (inputValue * length).toString()
                }

                spinner1 == "Liter" && spinner2 == "Millimeter" -> {
                    val volume = 1000
                    (inputValue / volume).toString()
                }

                spinner1 == "Liter" && spinner2 == "Cubic meter" -> {
                    val volume = 1000.00
                    (inputValue * volume).toString()
                }

                spinner1 == "Millimeter" && spinner2 == "Liter" -> {
                    val volume = 1000.00
                    (inputValue * volume).toString()
                }

                spinner1 == "Millimeter" && spinner2 == "Cubic meter" -> {
                    val volume = 1000000.00
                    (inputValue * volume).toString()
                }

                spinner1 == "Cubic meter" && spinner2 == "Liter" -> {
                    val volume = 1000000.00
                    (inputValue * volume).toString()
                }

                spinner1 == "Second" && spinner2 == "Minute" -> {
                    val time = 60.00
                    (inputValue * time).toString()
                }

                spinner1 == "Second" && spinner2 == "Hour" -> {
                    val time = 3600.00
                    (inputValue * time).toString()
                }

                spinner1 == "Minute" && spinner2 == "Second" -> {
                    val time = 60
                    (inputValue / time).toString()
                }

                spinner1 == "Minute" && spinner2 == "Hour" -> {
                    val time = 60.00
                    (inputValue * time).toString()
                }

                spinner1 == "Hour" && spinner2 == "Minute" -> {
                    val time = 60
                    (inputValue / time).toString()
                }

                spinner1 == "Hour" && spinner2 == "Second" -> {
                    val time = 3600
                    (inputValue / time).toString()
                }

                else -> " "


            }


        }
    }
}
