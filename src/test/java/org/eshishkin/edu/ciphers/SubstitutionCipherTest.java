package org.eshishkin.edu.ciphers;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstitutionCipherTest {

    private SubstitutionCipher cipher;

    @BeforeEach
    void before() {
        cipher = new SubstitutionCipher();
    }

    @ParameterizedTest
    @MethodSource("encryptionDataGenerator")
    void testEncrypt(String message, String key, String expected) {
        assertEquals(cipher.encrypt(message, key), expected);
    }

    @ParameterizedTest
    @MethodSource("decryptionDataGenerator")
    void testDecrypt(String message, String key, String expected) {
        assertEquals(cipher.decrypt(message, key), expected);
    }


    @TestFactory
    Stream<DynamicTest> dynamicTestsForEncryptionAndDecryption() {
        return DynamicTest.stream(
                IntStream.range(0, 10).mapToObj(i -> Pair.of(i, randomAlphabetic(32))).iterator(),
                input -> String.format("Test %s: %s", input.getKey(), input.getValue()),
                input -> {
                    var key = "somerandomkey";
                    var message = input.getValue();
                    assertEquals(message.toLowerCase(), cipher.decrypt(cipher.encrypt(message, key), key));
                }
        );
    }

    private static Stream<Arguments> encryptionDataGenerator() {
        return Stream.of(
            Arguments.of("helloworld", "message", "dgjjnwnqja"),
                Arguments.of("helloworld", "mysuperkey", "kpddhvhldu"),
                Arguments.of("helloworld", "qwe1qwe2qwe3", "b2ggjsjmg1"),
                Arguments.of(
                        "tobeornottobethatisthequestion"
                                + "whethertisnoblerinthemindtosuffer"
                                + "theslingsandarrowsofoutrageousfortune"
                                + "ortotakearmsagainstaseaoftroubles"
                                + "andbyopposingendthem",
                        "shakespeare",
                        "tlhelojlttlhetbstcqtbenueqtcljwbetbeotcqjlhgeocjtbeicjktlquppeot"
                                + "beqgcjrqsjksoolwqlplutosreluqplotujelotltsfesoiqsrscjq"
                                + "tsqeslptoluhgeqsjkhylmmlqcjrejktbei"
                )
        );
    }

    private static Stream<Arguments> decryptionDataGenerator() {
        return Stream.of(
                Arguments.of("dgjjnwnqja", "message", "helloworld"),
                Arguments.of("kpddhvhldu", "mysuperkey", "helloworld"),
                Arguments.of("b2ggjsjmg1", "qwe1qwe2qwe3", "helloworld"),
                Arguments.of(
                        "tlhelojlttlhetbstcqtbenueqtcljwbetbeotcqjlhgeocjtbeicjktlquppeot"
                                + "beqgcjrqsjksoolwqlplutosreluqplotujelotltsfesoiqsrscjq"
                                + "tsqeslptoluhgeqsjkhylmmlqcjrejktbei",
                        "shakespeare",
                        "tobeornottobethatisthequestion"
                                + "whethertisnoblerinthemindtosuffer"
                                + "theslingsandarrowsofoutrageousfortune"
                                + "ortotakearmsagainstaseaoftroubles"
                                + "andbyopposingendthem"
                )
        );
    }
}