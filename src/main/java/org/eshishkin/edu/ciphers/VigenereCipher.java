package org.eshishkin.edu.ciphers;

import java.util.function.BiFunction;

public class VigenereCipher extends AlphabetBasedCipher {
    @Override
    public String encrypt(String message, String key) {
        return process(message, key, (letterNumber, shift) -> (letterNumber + shift) % ALPHABET.size());
    }

    @Override
    public String decrypt(String message, String key) {
        return process(message, key, (letterNumber, shift) -> {
            // ALPHABET.size() is added only to avoid negative values
            return (ALPHABET.size() + letterNumber - shift) % ALPHABET.size();
        });
    }

    private String process(String message, String key, BiFunction<Integer, Integer, Integer> mapper) {
        var normalized = normalize(message);
        checkRestrictedLetters(normalized);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < normalized.length(); i++) {
            int letterNumber = ALPHABET.indexOf(normalized.charAt(i));
            int shift = ALPHABET.indexOf(key.charAt(i % key.length()));

            builder.append(ALPHABET.get(mapper.apply(letterNumber, shift)));
        }
        return builder.toString();
    }
}
