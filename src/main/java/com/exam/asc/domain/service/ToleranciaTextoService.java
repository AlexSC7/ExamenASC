package com.exam.asc.domain.service;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.text.Normalizer;
import java.util.Locale;

public class ToleranciaTextoService {
    private static final int LEVENSHTEIN_LIMITE = 2;

    public boolean coincidencia(String recibido, String original) {
        String recibidoNormalizado = normalizar(recibido);
        String originalNormalizado = normalizar(original);

        return recibidoNormalizado.contains(originalNormalizado)
                || coincidenciaLevenshtein(recibidoNormalizado, originalNormalizado);
    }

    private String normalizar(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                //Quitar acentos
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                //Quitar puntuacion
                .replaceAll("\\p{P}", "");
    }

    private boolean coincidenciaLevenshtein(String recibido, String original) {
        return LevenshteinDistance.getDefaultInstance()
                .apply(recibido, original) <= LEVENSHTEIN_LIMITE;
    }
}
