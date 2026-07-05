package avisek.example.urlservice.util;

public final class Base62Encoder {

    private Base62Encoder() {}

    private static final String BASE62 =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final long MAX_COUNTER =
            56_800_235_583L; // 62^6 - 1

    public static String encode(long value) {

        if (value < 0) {
            throw new IllegalArgumentException(
                    "Counter cannot be negative");
        }

        if (value > MAX_COUNTER) {
            throw new IllegalStateException(
                    "Base62 code space exhausted");
        }

        if (value == 0) {
            return "0";
        }

        StringBuilder encoded = new StringBuilder();

        while (value > 0) {
            encoded.append(
                    BASE62.charAt((int) (value % 62))
            );
            value /= 62;
        }

        return encoded.reverse().toString();
    }

    public static long decode(String code) {

        long result = 0;

        for (char c : code.toCharArray()) {

            int index = BASE62.indexOf(c);

            if (index == -1) {
                throw new IllegalArgumentException(
                        "Invalid Base62 character: " + c);
            }

            result = result * 62 + index;
        }

        return result;
    }
}