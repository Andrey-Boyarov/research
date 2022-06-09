package com.example.research.Content.Validation;

import com.example.research.Entities.Area;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validated formal context - ready for work
 */
@Getter
@AllArgsConstructor
public class Context {
    protected String areaName;
    protected List<String> owners;
    protected List<ValidQuality> qualities;

    public Context(IncomingFormalContext i){
        areaName = i.getAreaName();
        owners = i.getOwners();
        qualities = i.getQualities()
                .stream()
                .map(ValidQuality::new)
                .collect(Collectors.toList());
    }

    public Area convertToArea(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            Area area = new Area();
            area.setLabel(areaName);
            area.setName(areaName);
            area.setOwners(mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(owners));
            area.setQualities(mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(qualities));
            return area;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double get(String sub, String prop){
        int index = owners.indexOf(sub);
        ValidQuality quality = getQualityByName(prop);
        return quality.values.get(index);
    };

    public ValidQuality getQualityByName(String name){
        for (ValidQuality quality : qualities) {
            if (quality.name.equals(name)) return quality;
        }
        return null;
    }
}
