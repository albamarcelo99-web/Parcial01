package com.example.aplicacion_01_puzzle

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    // ==========================================
    // DECLARACIÓN DE CONTROLES
    // ==========================================

    // Array de 16 botones
    private lateinit var BTNButtons: Array<Button>

    // TextView para mostrar mensajes
    private lateinit var TXVMessage: TextView

    // Botones de control
    private lateinit var BTNRestart: Button
    private lateinit var BTNDisorder: Button
    private lateinit var BTNVerify: Button


    // ==========================================
    // MATRIZ TABLERO
    // ==========================================

    private var Tablero = arrayOf(
        arrayOf("1", "2", "3", "4"),
        arrayOf("5", "6", "7", "8"),
        arrayOf("9", "10", "11", "12"),
        arrayOf("13", "14", "15", "")
    )


    // ==========================================
    // ON CREATE
    // ==========================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        // ==========================================
        // ASIGNAR CONTROLES
        // ==========================================

        TXVMessage = findViewById(R.id.TXTPlayer)

        BTNRestart = findViewById(R.id.BTNRestart)

        BTNDisorder = findViewById(R.id.BTNMess)

        BTNVerify = findViewById(R.id.BTNVerify)


        // ==========================================
        // ARRAY DE LOS 16 BOTONES
        // ==========================================

        BTNButtons = arrayOf(

            findViewById(R.id.BTN00),
            findViewById(R.id.BTN01),
            findViewById(R.id.BTN02),
            findViewById(R.id.BTN03),

            findViewById(R.id.BTN10),
            findViewById(R.id.BTN11),
            findViewById(R.id.BTN12),
            findViewById(R.id.BTN13),

            findViewById(R.id.BTN20),
            findViewById(R.id.BTN21),
            findViewById(R.id.BTN22),
            findViewById(R.id.BTN23),

            findViewById(R.id.BTN30),
            findViewById(R.id.BTN31),
            findViewById(R.id.BTN32),
            findViewById(R.id.BTN33)
        )


        // ==========================================
        // EVENTO DE LOS 16 BOTONES
        // ==========================================

        for (i in BTNButtons.indices) {

            BTNButtons[i].setOnClickListener {

                moverFicha(i)

            }
        }


        // ==========================================
        // BOTÓN REINICIAR
        // ==========================================

        BTNRestart.setOnClickListener {

            reiniciarJuego()

        }


        // ==========================================
        // BOTÓN DESORDENAR
        // ==========================================

        BTNDisorder.setOnClickListener {

            desordenarTablero()

        }


        // ==========================================
        // BOTÓN VERIFICAR
        // ==========================================

        BTNVerify.setOnClickListener {

            verificarJuego()

        }


        // Mostrar tablero inicial
        actualizarTablero()
    }


    // ==========================================
    // MOVER FICHA
    // ==========================================

    private fun moverFicha(posicion: Int) {

        // Obtener fila y columna del botón presionado

        val fila = posicion / 4
        val columna = posicion % 4


        // ==========================================
        // ARRIBA
        // ==========================================

        if (fila > 0) {

            if (Tablero[fila - 1][columna] == "") {

                intercambiar(
                    fila,
                    columna,
                    fila - 1,
                    columna
                )

                return
            }
        }


        // ==========================================
        // ABAJO
        // ==========================================

        if (fila < 3) {

            if (Tablero[fila + 1][columna] == "") {

                intercambiar(
                    fila,
                    columna,
                    fila + 1,
                    columna
                )

                return
            }
        }


        // ==========================================
        // IZQUIERDA
        // ==========================================

        if (columna > 0) {

            if (Tablero[fila][columna - 1] == "") {

                intercambiar(
                    fila,
                    columna,
                    fila,
                    columna - 1
                )

                return
            }
        }


        // ==========================================
        // DERECHA
        // ==========================================

        if (columna < 3) {

            if (Tablero[fila][columna + 1] == "") {

                intercambiar(
                    fila,
                    columna,
                    fila,
                    columna + 1
                )

                return
            }
        }


        // Si llegó aquí, no encontró un espacio vacío
        TXVMessage.text = "Movimiento no válido"
    }


    // ==========================================
    // INTERCAMBIAR VALORES
    // ==========================================

    private fun intercambiar(
        fila1: Int,
        columna1: Int,
        fila2: Int,
        columna2: Int
    ) {

        // Guardar temporalmente el valor
        val temporal = Tablero[fila1][columna1]

        // Intercambiar en la matriz
        Tablero[fila1][columna1] =
            Tablero[fila2][columna2]

        Tablero[fila2][columna2] =
            temporal


        // Actualizar los botones
        actualizarTablero()


        TXVMessage.text = "Movimiento realizado"
    }


    // ==========================================
    // ACTUALIZAR LOS BOTONES
    // ==========================================

    private fun actualizarTablero() {

        for (fila in 0..3) {

            for (columna in 0..3) {

                // Convertir fila y columna
                // en una posición del array

                val posicion = fila * 4 + columna

                BTNButtons[posicion].text =
                    Tablero[fila][columna]
            }
        }
    }


    // ==========================================
    // DESORDENAR TABLERO
    // ==========================================

    private fun desordenarTablero() {

        // Realizar 100 movimientos válidos
        repeat(100) {

            // Buscar el espacio vacío
            var filaVacia = 0
            var columnaVacia = 0

            for (fila in 0..3) {

                for (columna in 0..3) {

                    if (Tablero[fila][columna] == "") {

                        filaVacia = fila
                        columnaVacia = columna
                    }
                }
            }


            // Crear lista de movimientos posibles
            val movimientos = mutableListOf<Pair<Int, Int>>()


            // Arriba
            if (filaVacia > 0) {
                movimientos.add(
                    Pair(filaVacia - 1, columnaVacia)
                )
            }


            // Abajo
            if (filaVacia < 3) {
                movimientos.add(
                    Pair(filaVacia + 1, columnaVacia)
                )
            }


            // Izquierda
            if (columnaVacia > 0) {
                movimientos.add(
                    Pair(filaVacia, columnaVacia - 1)
                )
            }


            // Derecha
            if (columnaVacia < 3) {
                movimientos.add(
                    Pair(filaVacia, columnaVacia + 1)
                )
            }


            // Elegir un movimiento aleatorio
            val movimiento = movimientos.random()


            // Intercambiar
            intercambiarSinMensaje(
                filaVacia,
                columnaVacia,
                movimiento.first,
                movimiento.second
            )
        }


        actualizarTablero()

        TXVMessage.text = "Tablero desordenado"
    }


    // ==========================================
    // INTERCAMBIAR SIN MOSTRAR MENSAJE
    // ==========================================

    private fun intercambiarSinMensaje(
        fila1: Int,
        columna1: Int,
        fila2: Int,
        columna2: Int
    ) {

        val temporal = Tablero[fila1][columna1]

        Tablero[fila1][columna1] =
            Tablero[fila2][columna2]

        Tablero[fila2][columna2] =
            temporal
    }


    // ==========================================
    // REINICIAR
    // ==========================================

    private fun reiniciarJuego() {

        Tablero = arrayOf(
            arrayOf("1", "2", "3", "4"),
            arrayOf("5", "6", "7", "8"),
            arrayOf("9", "10", "11", "12"),
            arrayOf("13", "14", "15", "")
        )


        actualizarTablero()

        TXVMessage.text = "Juego reiniciado"
    }


    // ==========================================
    // VERIFICAR
    // ==========================================

    private fun verificarJuego() {

        var correcto = true

        var contador = 1


        for (fila in 0..3) {

            for (columna in 0..3) {

                if (fila == 3 && columna == 3) {

                    if (Tablero[fila][columna] != "") {

                        correcto = false
                    }

                } else {

                    if (Tablero[fila][columna] != contador.toString()) {

                        correcto = false
                    }

                    contador++
                }
            }
        }


        if (correcto) {

            TXVMessage.text =
                "¡FELICIDADES! PUZZLE COMPLETADO"

            Toast.makeText(
                this,
                "¡Ganaste!",
                Toast.LENGTH_LONG
            ).show()

        } else {

            TXVMessage.text =
                "El puzzle todavía no está ordenado"
        }
    }
}