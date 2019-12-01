package org.eshishkin.edu.ciphers;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SubstitutionCipher extends AlphabetBasedCipher {

    @Override
    public String encrypt(String message, String key) {
        return process(message, key, (c, mapping) -> mapping.get(ALPHABET.indexOf(c)));
    }

    @Override
    public String decrypt(String message, String key) {
        return process(message, key, (c, mapping) -> ALPHABET.get(mapping.indexOf(c)));
    }

    private String process(String data, String key,
                           BiFunction<Character, List<Character>, Character> mapper) {
        var mapping = toEncryptedAlphabet(key);
        var normalized = normalize(data);
        checkRestrictedLetters(normalized);

        return toChars(normalized)
                .map(c -> mapper.apply(c, mapping))
                .collect(CHAR_TO_STRING_COLLECTOR);
    }
    private List<Character> toEncryptedAlphabet(String key) {
        return Stream.concat(toChars(key).distinct(), ALPHABET.stream()).distinct().collect(toList());
    }
}
