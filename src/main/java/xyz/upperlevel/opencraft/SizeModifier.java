package xyz.upperlevel.opencraft;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SizeModifier {

    public static final SizeModifier DEFAULT = new SizeModifier(1, 1, 1);

    @Getter @Setter public int xDivisor, yDivisor, zDivisor;

    @Override
    public boolean equals(Object object) {
        if (object instanceof SizeModifier) {
            SizeModifier modifier = ((SizeModifier) object);
            return modifier.xDivisor == xDivisor && modifier.yDivisor == yDivisor && modifier.zDivisor == zDivisor;
        }
        return super.equals(object);
    }
}
