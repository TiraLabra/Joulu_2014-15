package org.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import persistentDataStructures.PersistentHashMap;
import persistentDataStructures.PersistentVector;

public class PersistentDataStructuresBenchmarks {


    @State(Scope.Benchmark)
    public static class BenchmarkState {
        private PersistentVector<Integer> vec = new PersistentVector<Integer>();
        private PersistentHashMap<Integer, Integer> map = new PersistentHashMap<Integer, Integer>();
        private final ArrayList<Integer> arr = new ArrayList<Integer>();;
        private final HashMap<Integer, Integer> hMap = new HashMap<Integer, Integer>();;

        public BenchmarkState() {
            for (int i = 0; i < 35000; i++) {
                map = map.assoc(i, i);
                vec = vec.conj(i);
                arr.add(i);
                hMap.put(i,i);
            }
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public PersistentHashMap<Integer, Integer> deleteAndAddValueWithPersistentHashMap(BenchmarkState state) {
        state.map.dissoc(1238);
        return state.map.assoc(1238, 1);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int deleteAndAddValueWithStandardHashMap(BenchmarkState state) {
        state.hMap.remove(1238);
        return state.hMap.put(1238,1);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public PersistentVector<Integer> popAndPushWithPersistentVector(BenchmarkState state) {
        return state.vec.pop().conj(1);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public boolean popAndPushWithArrayList(BenchmarkState state) {
        state.arr.remove(state.arr.size() - 1);
        return state.arr.add(1);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int getValuesFromPersistentHashMap(BenchmarkState state) {
        return state.map.get(1238);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int getValuesFromStandardHashMap(BenchmarkState state) {
        return state.hMap.get(1238);
    }
    
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int getValuesFromPersistentVector(BenchmarkState state) {
        return state.vec.get(1238);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int getValuesFromArrayList(BenchmarkState state) {
        return state.arr.get(1238);
    }
}
