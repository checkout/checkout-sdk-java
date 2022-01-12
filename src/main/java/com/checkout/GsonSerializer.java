package com.checkout;

import com.checkout.common.CheckoutUtils;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.InstrumentType;
import com.checkout.instruments.four.create.CreateInstrumentBankAccountResponse;
import com.checkout.instruments.four.create.CreateInstrumentResponse;
import com.checkout.instruments.four.create.CreateInstrumentTokenResponse;
import com.checkout.instruments.four.get.GetBankAccountInstrumentResponse;
import com.checkout.instruments.four.get.GetCardInstrumentResponse;
import com.checkout.instruments.four.get.GetInstrumentResponse;
import com.checkout.instruments.four.update.UpdateInstrumentBankAccountResponse;
import com.checkout.instruments.four.update.UpdateInstrumentCardResponse;
import com.checkout.instruments.four.update.UpdateInstrumentResponse;
import com.checkout.payments.PaymentDestinationType;
import com.checkout.payments.four.response.destination.PaymentResponseAlternativeDestination;
import com.checkout.payments.four.response.destination.PaymentResponseBankAccountDestination;
import com.checkout.payments.four.response.destination.PaymentResponseDestination;
import com.checkout.payments.four.response.source.CurrencyAccountResponseSource;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
import com.checkout.payments.response.source.ResponseSource;
import com.checkout.workflows.four.actions.WorkflowActionType;
import com.checkout.workflows.four.actions.response.WebhookWorkflowActionResponse;
import com.checkout.workflows.four.actions.response.WorkflowActionResponse;
import com.checkout.workflows.four.conditions.WorkflowConditionType;
import com.checkout.workflows.four.conditions.response.EntityWorkflowConditionResponse;
import com.checkout.workflows.four.conditions.response.EventWorkflowConditionResponse;
import com.checkout.workflows.four.conditions.response.ProcessingChannelWorkflowConditionResponse;
import com.checkout.workflows.four.conditions.response.WorkflowConditionResponse;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class GsonSerializer implements Serializer {

    private static final Type MAP_TYPE_TOKEN = new TypeToken<Map<String, Object>>() {
    }.getType();

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
            // Payments DEFAULT - source
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ResponseSource.class, CheckoutUtils.TYPE, true, AlternativePaymentSourceResponse.class)
                    .registerSubtype(com.checkout.payments.response.source.CardResponseSource.class, identifier(PaymentSourceType.CARD)))
            // Payments DEFAULT - destination
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(PaymentResponseDestination.class, CheckoutUtils.TYPE, true, PaymentResponseAlternativeDestination.class)
                    .registerSubtype(PaymentResponseBankAccountDestination.class, identifier(PaymentDestinationType.BANK_ACCOUNT)))
            // Payments FOUR - source
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.four.response.source.ResponseSource.class, CheckoutUtils.TYPE, true, com.checkout.payments.four.response.source.AlternativePaymentSourceResponse.class)
                    .registerSubtype(com.checkout.payments.four.response.source.CardResponseSource.class, identifier(PaymentSourceType.CARD))
                    .registerSubtype(CurrencyAccountResponseSource.class, identifier(PaymentSourceType.CURRENCY_ACCOUNT)))
            // Payments FOUR - destination
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.four.response.destination.PaymentResponseDestination.class, CheckoutUtils.TYPE, true, com.checkout.payments.four.response.destination.PaymentResponseAlternativeDestination.class)
                    .registerSubtype(PaymentResponseBankAccountDestination.class, identifier(PaymentDestinationType.BANK_ACCOUNT)))
            // Instruments CS2
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(CreateInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(CreateInstrumentBankAccountResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(CreateInstrumentTokenResponse.class, identifier(InstrumentType.CARD)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(GetInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(GetBankAccountInstrumentResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(GetCardInstrumentResponse.class, identifier(InstrumentType.CARD)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(UpdateInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(UpdateInstrumentBankAccountResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(UpdateInstrumentCardResponse.class, identifier(InstrumentType.CARD)))
            // Workflows CS2
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(WorkflowActionResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(WebhookWorkflowActionResponse.class, identifier(WorkflowActionType.WEBHOOK)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(WorkflowConditionResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(EventWorkflowConditionResponse.class, identifier(WorkflowConditionType.EVENT))
                    .registerSubtype(EntityWorkflowConditionResponse.class, identifier(WorkflowConditionType.ENTITY))
                    .registerSubtype(ProcessingChannelWorkflowConditionResponse.class, identifier(WorkflowConditionType.PROCESSING_CHANNEL)))
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
