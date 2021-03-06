
package com.skiwi.tcg.model.objects;

import com.skiwi.tcg.model.cards.MonsterCard;
import com.skiwi.tcg.utils.Arguments;
import com.skiwi.tcg.utils.States;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author Frank van Heeswijk
 */
public class Field {
    private final MonsterCard[] monsters;

    public Field(final int monsterCapacity) {
        Arguments.requirePositive(monsterCapacity, "monster capacity");
        this.monsters = new MonsterCard[monsterCapacity];
    }

    public void setMonster(final int index, final MonsterCard monsterCard) {
        Arguments.requireIndexInRange(index, 0, monsters.length);
        Objects.requireNonNull(monsterCard);
        States.requireNull(monsters[index], IllegalStateException::new, "a monster already exists on index: " + index);
        monsters[index] = monsterCard;
    }

    public MonsterCard getMonster(final int index) {
        Arguments.requireIndexInRange(index, 0, monsters.length);
        MonsterCard monsterCard = monsters[index];
        States.requireNonNull(monsterCard, IllegalStateException::new, "no monster exists on index: " + index);
        return monsterCard;
    }

    public boolean hasMonster(final int index) {
        Arguments.requireIndexInRange(index, 0, monsters.length);
        return (monsters[index] != null);
    }

    public MonsterCard destroyMonster(final int index) {
        Arguments.requireIndexInRange(index, 0, monsters.length);
        MonsterCard monsterCard = monsters[index];
        States.requireNonNull(monsterCard, IllegalStateException::new, "no monster exists on index: " + index);
        monsters[index] = null;
        return monsterCard;
    }

    public Stream<MonsterCard> getMonsters() {
        return Arrays.stream(monsters).filter(obj -> obj != null);
    }

    public int getMonsterCapacity() {
        return monsters.length;
    }

    @Override
    public String toString() {
        return Field.class.getSimpleName() + "(" + Arrays.toString(monsters) + ")";
    }
}
