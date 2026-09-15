package com.example.aplicacion_01_puzzle

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var BTNButtons: Array<Button>

    private lateinit var TXVMessage: TextView

    private lateinit var BTNRestart: Button

    private lateinit var BTNDisorder: Button

    private lateinit var BTNVerify: Button

    private var Tablero = arrayOf(
        arrayOf("1", "2", "3", "4"),
        arrayOf("12", "13", "14", "5"),
        arrayOf("11", "", "15", "6"),
        arrayOf("10", "9", "8", "7")
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        TXVMessage = findViewById(R.id.TXTPlayer)

        BTNRestart = findViewById(R.id.BTNRestart)

        BTNDisorder = findViewById(R.id.BTNMess)

        BTNVerify = findViewById(R.id.BTNVerify)

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
        // EVENTOS DE LOS 16 BOTONES
        // ==========================================

        for (i in BTNButtons.indices) {

            BTNButtons[i].setOnClickListener {

                moverFicha(i)

            }
        }

        BTNRestart.setOnClickListener {

            reiniciarJuego()

        }


        BTNDisorder.setOnClickListener {

            desordenarJuego()

        }

        BTNVerify.setOnClickListener {

            verificarJuego()

        }

        actualizarTablero()
    }

    private fun moverFicha(posicion: Int) {

        // Obtener fila y columna
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
        // ABAJO


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
        // IZQUIERDA

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

        // DERECHA


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
    }
    // INTERCAMBIAR

    private fun intercambiar(
        fila1: Int,
        columna1: Int,
        fila2: Int,
        columna2: Int
    ) {

        // Guardar temporalmente
        val temporal = Tablero[fila1][columna1]


        // Intercambiar valores en Tablero
        Tablero[fila1][columna1] =
            Tablero[fila2][columna2]

        Tablero[fila2][columna2] =
            temporal

        actualizarTablero()
    }

    private fun actualizarTablero() {

        for (fila in 0..3) {

            for (columna in 0..3) {

                val posicion = fila * 4 + columna
                if(Tablero[fila][columna]=="0"){
                    BTNButtons[posicion].text=""
                }

                BTNButtons[posicion].text =
                    Tablero[fila][columna]
            }
        }
    }

    // 1. REINICIAR JUEGO
    private fun reiniciarJuego() {

        // Estado inicial ordenado
        Tablero = arrayOf(
            arrayOf("1", "2", "3", "4"),
            arrayOf("12", "13", "14", "5"),
            arrayOf("11", "", "15", "6"),
            arrayOf("10", "9", "8", "7")
        )
        actualizarTablero()
        TXVMessage.text = "Juego Reiniciado"
    }
    // 2. DESORDENAR JUEGO
    private fun desordenarJuego() {

        // Realizar movimientos aleatorios
        repeat(100) {

            // Buscar el 0
            var filaCero = 0
            var columnaCero = 0


            for (fila in 0..3) {

                for (columna in 0..3) {

                    if (Tablero[fila][columna] == "") {

                        filaCero = fila
                        columnaCero = columna
                    }
                }
            }
            // Lista de posiciones posibles
            val movimientos = mutableListOf<Pair<Int, Int>>()


            // Arriba
            if (filaCero > 0) {

                movimientos.add(
                    Pair(filaCero - 1, columnaCero)
                )
            }


            // Abajo
            if (filaCero < 3) {

                movimientos.add(
                    Pair(filaCero + 1, columnaCero)
                )
            }


            // Izquierda
            if (columnaCero > 0) {

                movimientos.add(
                    Pair(filaCero, columnaCero - 1)
                )
            }


            // Derecha
            if (columnaCero < 3) {

                movimientos.add(
                    Pair(filaCero, columnaCero + 1)
                )
            }


            // Elegir movimiento aleatorio
            val movimiento = movimientos.random()


            // Intercambiar con el cero
            val temporal =
                Tablero[movimiento.first][movimiento.second]

            Tablero[movimiento.first][movimiento.second] =
                Tablero[filaCero][columnaCero]

            Tablero[filaCero][columnaCero] =
                temporal
        }


        // Actualizar botones
        actualizarTablero()


        // Mensaje solicitado por el docente
        TXVMessage.text = "Completado"
    }
    // 3. VERIFICAR JUEGO

    private fun verificarJuego() {

        // Estado que el docente considera ordenado
        val tableroOrdenado = arrayOf(
            arrayOf("1", "2", "3", "4"),
            arrayOf("12", "13", "14", "5"),
            arrayOf("11", "", "15", "6"),
            arrayOf("10", "9", "8", "7")
        )
        var ordenado = true

        // Comparar Tablero con tableroOrdenado
        for (fila in 0..3) {

            for (columna in 0..3) {

                if (Tablero[fila][columna] !=
                    tableroOrdenado[fila][columna]
                ) {

                    ordenado = false
                }
            }
        }
        // Mostrar mensaje correspondiente
        if (ordenado) {

            TXVMessage.text = "Juego Ordenado"

        } else {

            TXVMessage.text = "Juego Desordenado"
        }
    }
}