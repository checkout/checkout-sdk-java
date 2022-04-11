package com.checkout;

import com.checkout.common.CheckoutUtils;
import com.checkout.common.InstrumentType;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentDestinationType;
import com.checkout.workflows.four.actions.WorkflowActionType;
import com.checkout.workflows.four.conditions.WorkflowConditionType;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

class GsonSerializer implements Serializer {

    private static final Type MAP_TYPE_TOKEN = new TypeToken<Map<String, Object>>() {
    }.getType();

    private static final Gson DEFAULT_GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            // Instant type adapters
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (Instant date, Type typeOfSrc, JsonSerializationContext context) ->
                    new JsonPrimitive(date.truncatedTo(ChronoUnit.SECONDS).toString()))
            .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (JsonElement json, Type typeOfSrc, JsonDeserializationContext context) ->
                    Instant.parse(json.getAsString()))
            // Payments DEFAULT - source
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.response.source.ResponseSource.class, CheckoutUtils.TYPE, true, com.checkout.payments.response.source.AlternativePaymentSourceResponse.class)
                    .registerSubtype(com.checkout.payments.response.source.CardResponseSource.class, identifier(PaymentSourceType.CARD)))
            // Payments DEFAULT - destination
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.response.destination.PaymentResponseDestination.class, CheckoutUtils.TYPE, true, com.checkout.payments.response.destination.PaymentResponseAlternativeDestination.class)
                    .registerSubtype(com.checkout.payments.response.destination.PaymentResponseCardDestination.class, identifier(PaymentDestinationType.CARD)))
            // Payments FOUR - source
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.four.response.source.ResponseSource.class, CheckoutUtils.TYPE, true, com.checkout.payments.four.response.source.AlternativePaymentSourceResponse.class)
                    .registerSubtype(com.checkout.payments.four.response.source.CardResponseSource.class, identifier(PaymentSourceType.CARD))
                    .registerSubtype(com.checkout.payments.four.response.source.CurrencyAccountResponseSource.class, identifier(PaymentSourceType.CURRENCY_ACCOUNT)))
            // Payments FOUR - destination
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.four.response.destination.PaymentResponseDestination.class, CheckoutUtils.TYPE, true, com.checkout.payments.four.response.destination.PaymentResponseAlternativeDestination.class)
                    .registerSubtype(com.checkout.payments.four.response.destination.PaymentResponseBankAccountDestination.class, identifier(PaymentDestinationType.BANK_ACCOUNT)))
            // Instruments CS2
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.instruments.four.create.CreateInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.instruments.four.create.CreateInstrumentBankAccountResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(com.checkout.instruments.four.create.CreateInstrumentTokenResponse.class, identifier(InstrumentType.CARD)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.instruments.four.get.GetInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.instruments.four.get.GetBankAccountInstrumentResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(com.checkout.instruments.four.get.GetCardInstrumentResponse.class, identifier(InstrumentType.CARD)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.instruments.four.update.UpdateInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.instruments.four.update.UpdateInstrumentBankAccountResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(com.checkout.instruments.four.update.UpdateInstrumentCardResponse.class, identifier(InstrumentType.CARD)))
            // Workflows CS2
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.workflows.four.actions.response.WorkflowActionResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.workflows.four.actions.response.WebhookWorkflowActionResponse.class, identifier(WorkflowActionType.WEBHOOK)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.workflows.four.conditions.response.WorkflowConditionResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.workflows.four.conditions.response.EventWorkflowConditionResponse.class, identifier(WorkflowConditionType.EVENT))
                    .registerSubtype(com.checkout.workflows.four.conditions.response.EntityWorkflowConditionResponse.class, identifier(WorkflowConditionType.ENTITY))
                    .registerSubtype(com.checkout.workflows.four.conditions.response.ProcessingChannelWorkflowConditionResponse.class, identifier(WorkflowConditionType.PROCESSING_CHANNEL)))
            .create();

    private final Gson gson;

    GsonSerializer() {
        this(DEFAULT_GSON);
    }

    GsonSerializer(final Gson gson) {
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

    @Override
    public Map<String, Object> fromJson(final String json) {
        return gson.fromJson(json, MAP_TYPE_TOKEN);
    }

    private static <E extends Enum<E>> String identifier(final E enumEntry) {
        if (enumEntry == null) {
            throw new IllegalStateException("invalid enum entry");
        }
        return enumEntry.name().toLowerCase();
    }

}
