package com.example.haromszogek;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Pont {
    int x;
    int y;

    public static Pont randomPont(){
        return new Pont((int)(Math.random()*(App.width-0+1)+0),(int)(Math.random()*(App.height-0+1)+0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pont pont = (Pont) o;
        return x == pont.x && y == pont.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
