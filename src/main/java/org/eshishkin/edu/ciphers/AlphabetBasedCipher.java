package org.eshishkin.edu.ciphers;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.containsOnly;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public abstract class AlphabetBasedCipher implements Cipher {
    protected static final List<Character> ALPHABET = Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    );

    protected static final Collector<Character, StringBuilder, String> CHAR_TO_STRING_COLLECTOR = Collector.of(
            StringBuilder::new,
            StringBuilder::append,
            StringBuilder::append,
            StringBuilder::toString
    );

    protected static final String ALPHABET_AS_STRING = ALPHABET.stream().collect(CHAR_TO_STRING_COLLECTOR);


    protected void checkRestrictedLetters(String string) {
        if (!containsOnly(string, ALPHABET_AS_STRING)) {
            throw new IllegalArgumentException("Input string contains restricted characters: " + string);
        }
    }

    protected String normalize(String string) {
        return lowerCase(trimToNull(deleteWhitespace(string)), Locale.US);
    }

    protected Stream<Character> toChars(String string) {
        return string.chars().mapToObj(c -> (char) c);
    }
}
