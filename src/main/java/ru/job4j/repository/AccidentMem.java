package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {

    private final ConcurrentHashMap<Integer, Accident> store = new ConcurrentHashMap();

    private AccidentMem() {
        store.put(1, new Accident(
                1, "Юрий", "Наезд на пешехода", "Москва, Цветной Бульвар 7")
        );
        store.put(2, new Accident(
                2, "Мария", "ДТП 3 машины", "Москва, МКАД, 78 км, внутренняя сторона")
        );
        store.put(3, new Accident(
                3, "Дмитрий", "ДТП во дворе", "Балашиха, Парковая 11")
        );
        store.put(4, new Accident(
                4, "Светлана", "Угон автомобиля", "Химки, Осипенко 19/2")
        );
    }

    public Collection<Accident> getAll() {
       return store.values();
    }

    public Accident findById(int id) {
       return store.get(id);
    }
}
