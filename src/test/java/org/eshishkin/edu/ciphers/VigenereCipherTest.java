package org.eshishkin.edu.ciphers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VigenereCipherTest {

    private VigenereCipher cipher;

    @BeforeEach
    void before() {
        cipher = new VigenereCipher();
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

    private static Stream<Arguments> encryptionDataGenerator() {
        return Stream.of(
            Arguments.of("helloworld", "message", "ti33o2s3pv"),
                Arguments.of("helloworld", "mysuperkey", "t2353051p1"),
                Arguments.of("helloworld", "qwe1qwe2qwe3", "x0pc4isj1z"),
                Arguments.of(
                        "tobeornottobethatisthequestion"
                                + "whethertisnoblerinthemindtosuffer"
                                + "theslingsandarrowsofoutrageousfortune"
                                + "ortotakearmsagainstaseaoftroubles"
                                + "andbyopposingendthem",
                        "shakespeare",
                        "bvbos92stastltrebxwtyi81e2x03rwyiboe1x07rospwyixxztqi4hbvs"
                                + "4jxtvtyiasixkaprdrv9vw2sx3yt8eylo4wx3vtbrwvr3sbpo"
                                + "erv4zaqe02wtrwwhopx93yb2iahnnfg3tp5w0ugorv8le3"
                )
        );
    }

    private static Stream<Arguments> decryptionDataGenerator() {
        return Stream.of(
                Arguments.of("ti33o2s3pv", "message", "helloworld"),
                Arguments.of("t2353051p1", "mysuperkey", "helloworld"),
                Arguments.of("x0pc4isj1z", "qwe1qwe2qwe3", "helloworld"),
                Arguments.of(
                        "bvbos92stastltrebxwtyi81e2x03rwyiboe1x07rospwyixxztqi4hbvs"
                                + "4jxtvtyiasixkaprdrv9vw2sx3yt8eylo4wx3vtbrwvr3sbpo"
                                + "erv4zaqe02wtrwwhopx93yb2iahnnfg3tp5w0ugorv8le3",
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