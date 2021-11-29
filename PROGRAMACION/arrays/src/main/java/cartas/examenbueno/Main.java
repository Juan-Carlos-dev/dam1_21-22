package cartas.examenbueno;

import cartas.servicios.ServiciosCartas;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        ServiciosCartas serviciosCartas = new ServiciosCartas();

        int baraja[] = serviciosCartas.generarBaraja(40, 10);
        int indiceBaraja=0;


        System.out.println("Introduce el número de jugadores:");
        int jugadores = sc.nextInt();
        sc.nextLine();
        //repartir primera mano

        int[][] manos = new int[jugadores][3];
        int [] mesa = new int[jugadores];
        int [] cartasGanadas = new int[jugadores];
        int []posicionesEmpate= new int[jugadores];
        int ronda =1;

        do{
            System.out.println("reparto "+ronda);
            ronda++;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < jugadores; j++) {
                    manos[j][i]  = baraja[indiceBaraja];
                    indiceBaraja++;
                }
            }
            for (int j = 0; j < jugadores; j++) {
                System.out.println("cartas jugador"+j+" son "+ Arrays.toString(manos[j]));
            }
            // echar una carta 3 veces
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < jugadores; j++) {
                    // elegir carta a echar
                    int posicion;
                    do {
                        posicion = r.nextInt(3);
                    } while (manos[j][posicion] == 0);
                    mesa[j] = manos[j][posicion];
                    manos[j][posicion] = 0;
                    System.out.println("carta de jugador "+j+ " es "+mesa[j]);

                }

                //mirar mayor para ver ganador
                int mayor=mesa[0];
                int posicionMayor = 0;


                boolean empate= false;
                for (int j = 1; j < mesa.length; j++) {
                    if (mayor<mesa[j]) {
                        empate = false;
                        mayor = mesa[j];
                        posicionMayor = j;
                    }
                    else if (mayor==mesa[j]){
                       empate = true;
                    }
                }

                if (!empate) {
                    System.out.println("la baza se la lleva " + posicionMayor);
                    cartasGanadas[posicionMayor] += jugadores;
                }
                else
                {
                    System.out.println("hay empate");
                    int posicionEmpate = 0;
                    Arrays.fill(posicionesEmpate,0);
                    for (int j = 0; j < mesa.length; j++) {
                        if (mesa[j] == mayor)
                        {
                            posicionesEmpate[posicionEmpate] = j;
                            posicionEmpate++;
                        }
                    }
                    int cartaMayorDesempate = -1;
                    int posicionGanadorEmpate=0;
                    for (int j = 0; j < posicionEmpate; j++) {
                        int cartaEmpata = baraja[indiceBaraja];
                        indiceBaraja++;
                        System.out.println("carta sacada por jugaador"+posicionesEmpate[j] + " es "+cartaEmpata);
                        if (cartaEmpata>cartaMayorDesempate)
                        {
                            posicionGanadorEmpate = j;
                        }
                    }
                    System.out.println("la baza se la lleva " + posicionesEmpate[posicionGanadorEmpate]);
                    cartasGanadas[posicionMayor] += jugadores+posicionEmpate;
                }
            }
        }while (jugadores*3<=40-indiceBaraja);

        int mayor=cartasGanadas[0];
        int posicionMasCartas = 0;
        for (int j = 1; j < cartasGanadas.length; j++) {
            if (mayor<cartasGanadas[j]) {
                mayor = cartasGanadas[j];
                posicionMasCartas = j;
            }
        }

        System.out.println(Arrays.toString(cartasGanadas));
        System.out.println("el ganador es "+posicionMasCartas);

    }
}
