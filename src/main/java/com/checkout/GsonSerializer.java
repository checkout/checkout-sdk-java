package com.checkout;

import com.checkout.payments.AlternativePaymentSourceResponse;
import com.checkout.payments.CardSource;
import com.checkout.payments.CardSourceResponse;
import com.checkout.payments.ResponseSource;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class GsonSerializer implements Serializer {

    private static final Gson DEFAULT_GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (LocalDate date, Type typeOfSrc, JsonSerializationContext context) ->
                    new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (Instant date, Type typeOfSrc, JsonSerializationContext context) ->
                    new JsonPrimitive(date.toString()))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (JsonElement json, Type typeOfSrc, JsonDeserializationContext context) ->
                    LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
            .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (JsonElement json, Type typeOfSrc, JsonDeserializationContext context) ->
                    Instant.parse(json.getAsString()))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ResponseSource.class, "type", true, AlternativePaymentSourceResponse.class)
                    .registerSubtype(CardSourceResponse.class, CardSource.TYPE_NAME))
            // TODO Re-enable for customer instruments when related functionality is implemented for CS2
            //.registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(CustomerInstrument.class)
            //        .registerSubtype(CustomerBankAccountInstrument.class)
            //        .registerSubtype(CustomerCardInstrument.class))
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private final Gson gson;

    public GsonSerializer() {
        this(DEFAULT_GSON);
    }

    public GsonSerializer(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> String toJson(final T object) {
        final String result = gson.toJson(object);
        log.debug("toJson: " + result);
        return result;
    }

    @Override
    public <T> T fromJson(final String json, final Class<T> type) {
        log.debug("fromJson: " + json);
        return gson.fromJson(json, type);
    }

    @Override
    public <T> T fromJson(final String json, final Type type) {
        return gson.fromJson(json, type);
    }

}
