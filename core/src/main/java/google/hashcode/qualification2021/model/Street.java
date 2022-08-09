package google.hashcode.qualification2021.model;

import java.util.List;

public record Street(int start, int end, String name, int length) {
    public Street(List<String> street) {
        this(Integer.parseInt(street.get(0)),
            Integer.parseInt(street.get(1)),
            street.get(2),
            Integer.parseInt(street.get(3)));
    }
}
