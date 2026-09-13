package com.example.aplicacion_01_puzzle

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Arreglo que representa el tablero
    private var tablero = mutableListOf(
        1, 2, 3, 4,
        5, 6, 7, 8,
        9, 10, 11, 12,
        13, 14, 15, 0
    )

    private lateinit var botones: Array<Button>

    private lateinit var txtPlayer: TextView
    private lateinit var btnRestart: Button
    private lateinit var btnMess: Button
    private lateinit var btnVerify: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // TextView
        txtPlayer = findViewById(R.id.TXTPlayer)

        // Botones inferiores
        btnRestart = findViewById(R.id.BTNRestart)
        btnMess = findViewById(R.id.BTNMess)
        btnVerify = findViewById(R.id.BTNVerify)

        // Arreglo de botones del tablero
        botones = arrayOf(

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

        // Eventos para cada botón del tablero
        for (i in botones.indices) {

            botones[i].setOnClickListener {

                moverFicha(i)

            }
        }

        // Botón reiniciar
        btnRestart.setOnClickListener {

            reiniciarJuego()

        }

        // Botón desordenar
        btnMess.setOnClickListener {

            desordenarTablero()

        }

        // Botón verificar
        btnVerify.setOnClickListener {

            verificarJuego()

        }

        // Mostrar tablero inicial
        actualizarTablero()
    }


    // ==========================================
    // MOVER UNA FICHA
    // ==========================================

    private fun moverFicha(posicion: Int) {

        // Buscar dónde está el espacio vacío
        val posicionVacia = tablero.indexOf(0)

        // Verificar si la ficha está al lado
        if (esMovimientoValido(posicion, posicionVacia)) {

            // Intercambiar posiciones
            val temporal = tablero[posicion]

            tablero[posicion] = tablero[posicionVacia]

            tablero[posicionVacia] = temporal

            // Actualizar pantalla
            actualizarTablero()

            txtPlayer.text = "Movimiento realizado"

            // Verificar automáticamente si ganó
            if (estaOrdenado()) {

                txtPlayer.text = "¡FELICIDADES! GANASTE 🎉"

                Toast.makeText(
                    this,
                    "¡Puzzle completado!",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {

            txtPlayer.text = "Movimiento no válido"

        }
    }


    // ==========================================
    // VERIFICAR SI EL MOVIMIENTO ES VALIDO
    // ==========================================

    private fun esMovimientoValido(
        posicionFicha: Int,
        posicionVacia: Int
    ): Boolean {

        val filaFicha = posicionFicha / 4
        val columnaFicha = posicionFicha % 4

        val filaVacia = posicionVacia / 4
        val columnaVacia = posicionVacia % 4

        // Distancia Manhattan
        val distancia =
            kotlin.math.abs(filaFicha - filaVacia) +
                    kotlin.math.abs(columnaFicha - columnaVacia)

        return distancia == 1
    }


    // ==========================================
    // ACTUALIZAR EL TABLERO
    // ==========================================

    private fun actualizarTablero() {

        for (i in tablero.indices) {

            if (tablero[i] == 0) {

                // Espacio vacío
                botones[i].text = ""
                botones[i].isEnabled = false

            } else {

                botones[i].text = tablero[i].toString()
                botones[i].isEnabled = true

            }
        }
    }


    // ==========================================
    // DESORDENAR TABLERO
    // ==========================================

    private fun desordenarTablero() {

        // Realizamos movimientos válidos aleatorios
        repeat(100) {

            val posicionVacia = tablero.indexOf(0)

            val movimientosPosibles =
                obtenerMovimientosPosibles(posicionVacia)

            if (movimientosPosibles.isNotEmpty()) {

                val posicion =
                    movimientosPosibles.random()

                val temporal = tablero[posicion]

                tablero[posicion] =
                    tablero[posicionVacia]

                tablero[posicionVacia] = temporal
            }
        }

        actualizarTablero()

        txtPlayer.text = "Tablero desordenado"

    }


    // ==========================================
    // OBTENER MOVIMIENTOS POSIBLES
    // ==========================================

    private fun obtenerMovimientosPosibles(
        posicionVacia: Int
    ): MutableList<Int> {

        val movimientos = mutableListOf<Int>()

        val fila = posicionVacia / 4
        val columna = posicionVacia % 4

        // Arriba
        if (fila > 0) {
            movimientos.add(posicionVacia - 4)
        }

        // Abajo
        if (fila < 3) {
            movimientos.add(posicionVacia + 4)
        }

        // Izquierda
        if (columna > 0) {
            movimientos.add(posicionVacia - 1)
        }

        // Derecha
        if (columna < 3) {
            movimientos.add(posicionVacia + 1)
        }

        return movimientos
    }


    // ==========================================
    // REINICIAR JUEGO
    // ==========================================

    private fun reiniciarJuego() {

        tablero = mutableListOf(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12,
            13, 14, 15, 0
        )

        actualizarTablero()

        txtPlayer.text = "Juego reiniciado"
    }


    // ==========================================
    // VERIFICAR JUEGO
    // ==========================================

    private fun verificarJuego() {

        if (estaOrdenado()) {

            txtPlayer.text =
                "¡CORRECTO! EL PUZZLE ESTÁ COMPLETADO 🎉"

            Toast.makeText(
                this,
                "¡Ganaste!",
                Toast.LENGTH_LONG
            ).show()

        } else {

            txtPlayer.text =
                "El puzzle todavía no está ordenado"

            Toast.makeText(
                this,
                "Sigue intentando",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    // ==========================================
    // COMPROBAR SI ESTÁ ORDENADO
    // ==========================================

    private fun estaOrdenado(): Boolean {

        val solucion = listOf(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12,
            13, 14, 15, 0
        )

        return tablero == solucion
    }
}