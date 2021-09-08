package com.checkout;

import com.checkout.common.beta.InstrumentType;
import com.checkout.instruments.beta.create.CreateInstrumentBankAccountResponse;
import com.checkout.instruments.beta.create.CreateInstrumentResponse;
import com.checkout.instruments.beta.create.CreateInstrumentTokenResponse;
import com.checkout.instruments.beta.get.GetBankAccountInstrumentResponse;
import com.checkout.instruments.beta.get.GetCardInstrumentResponse;
import com.checkout.instruments.beta.get.GetInstrumentResponse;
import com.checkout.instruments.beta.update.UpdateInstrumentBankAccountResponse;
import com.checkout.instruments.beta.update.UpdateInstrumentCardResponse;
import com.checkout.instruments.beta.update.UpdateInstrumentResponse;
import com.checkout.payments.AlternativePaymentSourceResponse;
import com.checkout.payments.CardSourceResponse;
import com.checkout.payments.ResponseSource;
import com.checkout.workflows.beta.actions.WorkflowActionType;
import com.checkout.workflows.beta.actions.response.WebhookWorkflowActionResponse;
import com.checkout.workflows.beta.actions.response.WorkflowActionResponse;
import com.checkout.workflows.beta.conditions.WorkflowConditionType;
import com.checkout.workflows.beta.conditions.response.EntityWorkflowConditionResponse;
import com.checkout.workflows.beta.conditions.response.EventWorkflowConditionResponse;
import com.checkout.workflows.beta.conditions.response.WorkflowConditionResponse;
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

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonSerializer implements Serializer {

    private static final String TYPE = "type";

    private static final Gson DEFAULT_GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (LocalDate date, Type typeOfSrc, JsonSerializationContext context) ->
                    new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (Instant date, Type typeOfSrc, JsonSerializationContext context) ->
                    new JsonPrimitive(date.toString()))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (JsonElement json, Type typeOfSrc, JsonDeserializationContext context) ->
                    LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
            .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (JsonElement json, Type typeOfSrc, JsonDeserializationContext context) ->
                    Instant.parse(json.getAsString()))
            // Payments CS1
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ResponseSource.class, TYPE, true, AlternativePaymentSourceResponse.class)
                    .registerSubtype(CardSourceResponse.class, "card"))
            // Instruments CS2
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(CreateInstrumentResponse.class, TYPE)
                    .registerSubtype(CreateInstrumentBankAccountResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(CreateInstrumentTokenResponse.class, identifier(InstrumentType.CARD)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(GetInstrumentResponse.class, TYPE)
                    .registerSubtype(GetBankAccountInstrumentResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(GetCardInstrumentResponse.class, identifier(InstrumentType.CARD)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(UpdateInstrumentResponse.class, TYPE)
                    .registerSubtype(UpdateInstrumentBankAccountResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(UpdateInstrumentCardResponse.class, identifier(InstrumentType.CARD)))
            // Workflows CS2
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(WorkflowActionResponse.class, TYPE)
                    .registerSubtype(WebhookWorkflowActionResponse.class, identifier(WorkflowActionType.WEBHOOK)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(WorkflowConditionResponse.class, TYPE)
                    .registerSubtype(EventWorkflowConditionResponse.class, identifier(WorkflowConditionType.EVENT))
                    .registerSubtype(EntityWorkflowConditionResponse.class, identifier(WorkflowConditionType.ENTITY)))
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
        return gson.toJson(object);
    }

    @Override
    public <T> T fromJson(final String json, final Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public <T> T fromJson(final String json, final Type type) {
        return gson.fromJson(json, type);
    }

    private static <E extends Enum<E>> String identifier(final E enumEntry) {
        if (enumEntry == null) {
            throw new IllegalStateException("invalid enum entry");
        }
        return enumEntry.name().toLowerCase();
    }

}
