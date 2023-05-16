package com.example.research.V2.math.core;

import com.example.research.V2.math.core.context.K;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlgorithmUtils {

    public List<Concept> getConcepts(K k){
        List<Concept> result = new ArrayList<>();
        List<Volume> counter = getCounterV(k);
        int iter = 0;
        do {
            ++iter;
            Concept fc = getConceptV(k, new ArrayList<>(counter));
            if (fc != null) {
                fc.setVolume(counter.stream().map(Volume::copy).collect(Collectors.toList()));
                result.add(fc);
            }
            if (iter % 1000000 == 0) System.out.println(iter);
//            System.out.println(counter);
        } while (increaseCounterV(counter));
        result.remove(0);
        result.remove(result.size() - 1);
        return result;
    }

    public Concept getConceptV(K context, List<Volume> volume){
        List<Content> contents = new ArrayList<>();
        context.getM().forEach(prop -> {
            contents.add(new Content(prop, operationA(context, volume, prop)));
        });
        List<Volume> newVolumes = new ArrayList<>();
        context.getG().forEach(sub -> {
            newVolumes.add(new Volume(sub, operationB(context, contents, sub)));
        });
//        System.out.print(1);
//        System.out.println(volume);
//        System.out.print(2);
//        System.out.println(newVolumes);
        if (equalListsOfT(volume, newVolumes)) {
            Concept concept = new Concept(contents, volume);
            System.out.printf("Concept received: %s%n", concept);
            return concept;
        }
        return null;
    }

    public Concept getConceptC(K context, List<Content> content){
        List<Volume> volumes = new ArrayList<>();
        context.getG().forEach(sub -> {
            volumes.add(new Volume(sub, operationB(context, content, sub)));
        });
        List<Content> newContents = new ArrayList<>();
        context.getM().forEach(prop -> {
            newContents.add(new Content(prop, operationA(context, volumes, prop)));
        });
        if (content.equals(newContents)) {
            Concept concept = new Concept(content, volumes);
            System.out.printf("Concept received: %s%n", concept);
            return concept;
        }
        return null;
    }

    private List<Volume> getCounterV(K k){
        List<Volume> counter = new ArrayList<>();
        k.getG().forEach(sub -> counter.add(new Volume(sub, 0d)));
        return counter;
    }

    private List<Content> getCounterC(K k){
        List<Content> counter = new ArrayList<>();
        k.getM().forEach(prop -> counter.add(new Content(prop, 0d)));
        return counter;
    }

    public Double operationA(K context, List<Volume> vol, String prop){
        return context.getG()
                .parallelStream()
                .map(sub -> operationAInternal(context, vol, sub, prop))
                .reduce(this::lukConjunction)
                .orElse(null);
    }

    public Double operationB(K context, List<Content> con, String sub){
        return context.getM()
                .parallelStream()
                .map(prop -> operationBInternal(context, con, sub, prop))
                .reduce(this::lukConjunction)
                .orElse(null);
    }

    private Double operationAInternal(K context, List<Volume> vol, String sub, String prop){
        return lukImplication(
                Objects.requireNonNull(
                        vol
                                .stream()
                                .filter(v -> v.getProperty().equals(sub))
                                .findFirst()
                                .orElse(null)
                ).getValue(),
                operationI(context, sub, prop));
    }

    private Double operationBInternal(K context, List<Content> con, String sub, String prop){
        return lukImplication(
                Objects.requireNonNull(
                        con
                                .stream()
                                .filter(c -> c.getName().equals(prop))
                                .findFirst()
                                .orElse(null)
                ).getValue(),
                operationI(context, sub, prop));
    }

    public Double operationI(K context, String sub, String prop){
        return context.get(sub, prop);
    }

    private boolean increaseCounterV(List<Volume> counter){
        int i = 0;
        while (true){
            counter = new ArrayList<>(counter);
            counter.get(i).setValue(counter.get(i).getValue() + 0.1);
            if (counter.get(i).getValue() > 1d) {
                counter.get(i).setValue(new Double(0d));
                i++;
            } else {
                break;
            }
            if (i == counter.size()) return false;
        }
        return true;
    }

    private boolean increaseCounterC(List<Content> counter){
        int i = 0;
        while (true){
            counter = counter.stream().map(Content::copy).collect(Collectors.toList());
            counter.get(i).setValue(counter.get(i).getValue() + 0.1);
            if (counter.get(i).getValue() > 1d) {
                counter.get(i).setValue(0d);
                i++;
            } else {
                break;
            }
            if (i == counter.size()) return false;
        }
        return true;
    }

    /**
     * Операция Лукасевича нечёткая конъюкция
     */
    private Double lukConjunction(Double a, Double b){
        return Double.max(0, a + b - 1);
    }

    /**
     * Операция Лукасевича нечёткая импликация
     */
    private Double lukImplication(Double a, Double b){
        return Double.min(1, 1 - a + b);
    }

    private boolean eq(Double a, Double b){ return a - b < 0.1; }

    private <T extends Comparable<? super T>> boolean equalListsOfT(Collection<T> l1, Collection<T> l2) {
        return l1.stream().sorted().collect(Collectors.toList())
                .equals(l2.stream().sorted().collect(Collectors.toList()));
    }
}
