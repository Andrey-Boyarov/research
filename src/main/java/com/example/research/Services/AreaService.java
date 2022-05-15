package com.example.research.Services;

import com.example.research.Entities.Area;
import com.example.research.Repositories.AreaRepository;
import com.example.research.Validation.Context;
import com.example.research.Validation.Quality;
import com.example.research.Validation.ValidQuality;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    public boolean saveContext(Context context){
        Area area = context.convertToArea();
        if (area == null) return false;
        areaRepository.save(area);
        return true;
    }

    public Context getContext(String name){
        Area area = areaRepository.findByName(name);
        ObjectMapper mapper = new ObjectMapper();
        List<String> owners = new ArrayList<>();
        List<ValidQuality> qualities = new ArrayList<>();
        try {
            owners = mapper.readValue(area.getOwners(), new TypeReference<List<String>>() {});
            qualities = mapper.readValue(area.getQualities(), new TypeReference<List<ValidQuality>>() {});
        } catch (IOException e){
            e.printStackTrace();
        }
        return new Context(area.getName(), owners, qualities);
    }
}
