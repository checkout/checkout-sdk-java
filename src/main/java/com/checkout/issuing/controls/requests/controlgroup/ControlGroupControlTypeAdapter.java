package com.checkout.issuing.controls.requests.controlgroup;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * JSON converter for serializing and deserializing ControlGroupControl objects based on their control type properties.
 */
public class ControlGroupControlTypeAdapter implements JsonSerializer<ControlGroupControl>, JsonDeserializer<ControlGroupControl> {

    @Override
    public JsonElement serialize(ControlGroupControl src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }

    @Override
    public ControlGroupControl deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        
        JsonObject jsonObject = json.getAsJsonObject();
        
        // Dynamic type detection using enum mapping
        for (ControlGroupControlTypeMapping mapping : ControlGroupControlTypeMapping.values()) {
            if (jsonObject.has(mapping.getJsonProperty())) {
                return context.deserialize(json, mapping.getControlClass());
            }
        }
        
        throw new JsonParseException("Unable to determine ControlGroupControl type from JSON: " + json);
    }
}