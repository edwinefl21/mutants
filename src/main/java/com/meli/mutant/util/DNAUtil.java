package com.meli.mutant.util;

import com.meli.mutant.model.DNASequence;

import java.util.regex.Pattern;

public class DNAUtil {
    private static final Pattern DNA_CHARACTERS_BASE_PATTERN = Pattern.compile("[atcg]+", Pattern.CASE_INSENSITIVE);
    private static final Pattern DNA_CHARACTERS_PATTERN = Pattern.compile("A{4}|T{4}|C{4}|G{4}", Pattern.CASE_INSENSITIVE);
    public static boolean validateDNA(DNASequence dnaSequence){
        return dnaSequence.getDna().stream()
                .allMatch(str -> DNA_CHARACTERS_BASE_PATTERN.matcher(str).matches());
    }
    public static long CountMutantHorizontalDNA(DNASequence dnaSequence) {
        return dnaSequence.getDna()
                .stream()
                .filter(str -> MatchIsMutant(str)).count();

    }

    public static long CountMutantVerticalDNA(Character[][] dnaMatrix) {
        long counter=0;
        for (int column = 0; column < dnaMatrix.length; column++) {
            StringBuilder stringCompare = new StringBuilder();
            for (int row = 0; row <  dnaMatrix.length; row ++) {
                stringCompare.append(dnaMatrix[row][column]);
            }
            if (DNAUtil.MatchIsMutant(stringCompare.toString())) {
                counter++;
            }
        }
        return counter;
    }
    public static long CountMutantObliqueDNA(Character[][] matrix){
        Integer height = matrix.length, width = matrix[0].length;
        long counter=0;
        for (
            // Recorre los inicios de cada diagonal en los bordes de la matriz.
                Integer diagonal = 1 - width; // Comienza con un número negativo.
                diagonal <= height - 1; // Mientras no llegue a la última diagonal.
                diagonal += 1 // Avanza hasta el comienzo de la siguiente diagonal.
        ) {
            StringBuilder stringDiagonal= new StringBuilder();
            for (
                // Recorre cada una de las diagonales a partir del extremo superior izquierdo.
                    Integer vertical = Math.max(0, diagonal), horizontal = -Math.min(0, diagonal);
                    vertical < height && horizontal < width; // Mientras no excedan los límites.
                    vertical += 1, horizontal += 1 // Avanza en diagonal incrementando ambos ejes.
            ) {
                stringDiagonal.append(matrix[vertical][horizontal]);
                //System.out.println(matriz[vertical][horizontal]);
            }
            if (stringDiagonal.length() >=4 && DNAUtil.MatchIsMutant(stringDiagonal.toString())) {
                counter++;
                System.out.println("Mutant: " + stringDiagonal);
            }
        }
        return counter;
    }
    public static boolean MatchIsMutant(String row) {
        return DNA_CHARACTERS_PATTERN.matcher(row).find();
    }

    public static Character[][] getMatrixByDNASequence(DNASequence dnaSequence) {
        return dnaSequence.getDna().stream()
                .map(line -> line.toUpperCase().chars()
                        .mapToObj(c -> (char) c)
                        .toArray(Character[]::new))
                .toArray(Character[][]::new);
    }
}
