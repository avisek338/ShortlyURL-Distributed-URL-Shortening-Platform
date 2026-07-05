package avisek.example.urlservice.store;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryCounterStore implements CounterStore {
    private final ConcurrentHashMap<String,Long>store;

    public InMemoryCounterStore() {
        this.store = new ConcurrentHashMap<>();
    }

    @Override
    public Long increment(String key) {
        store.merge(key,1L,Long::sum);
        return store.get(key);
    }

}
