package org.eshishkin.edu.ciphers;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.containsOnly;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class SubstitutionCipher implements Cipher {
    private static final List<Character> ALPHABET = Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    );

    private static final Collector<Character, StringBuilder, String> CHAR_TO_STRING_COLLECTOR = Collector.of(
            StringBuilder::new,
            StringBuilder::append,
            StringBuilder::append,
            StringBuilder::toString
    );

    private static final String ALPHABET_AS_STRING = ALPHABET.stream().collect(CHAR_TO_STRING_COLLECTOR);

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

    private void checkRestrictedLetters(String string) {
        if (!containsOnly(string, ALPHABET_AS_STRING)) {
            throw new IllegalArgumentException("Input string contains restricted characters: " + string);
        }
    }

    private String normalize(String string) {
        return lowerCase(trimToNull(deleteWhitespace(string)), Locale.US);
    }

    private Stream<Character> toChars(String string) {
        return string.chars().mapToObj(c -> (char) c);
    }
}
