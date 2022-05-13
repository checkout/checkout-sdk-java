package com.checkout;

import com.checkout.common.CheckoutUtils;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import com.checkout.common.Link;
import com.checkout.common.PaymentSourceType;
import com.checkout.events.EventTypes;
import com.checkout.marketplace.payout.schedule.response.CurrencySchedule;
import com.checkout.marketplace.payout.schedule.response.GetScheduleResponse;
import com.checkout.marketplace.payout.schedule.response.ScheduleFrequencyMonthlyResponse;
import com.checkout.payments.PaymentAction;
import com.checkout.payments.PaymentDestinationType;
import com.checkout.payments.four.sender.Sender;
import com.checkout.payments.four.sender.SenderType;
import com.checkout.webhooks.WebhookResponse;
import com.checkout.workflows.four.actions.WorkflowActionType;
import com.checkout.workflows.four.conditions.WorkflowConditionType;
import com.checkout.workflows.four.events.WorkflowEventTypes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.apache.commons.lang3.EnumUtils;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GsonSerializer implements Serializer {

    private static final Type MAP_TYPE_TOKEN = new TypeToken<Map<String, Object>>() {
    }.getType();
    private static final Type EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<EventTypes>>() {
    }.getType();
    private static final Type WORKFLOWS_EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<WorkflowEventTypes>>() {
    }.getType();
    private static final Type WEBHOOKS_TYPE = new TypeToken<ItemsResponse<WebhookResponse>>() {
    }.getType();
    private static final Type DEFAULT_PAYMENT_ACTIONS_TYPE = new TypeToken<ItemsResponse<com.checkout.payments.PaymentAction>>() {
    }.getType();
    private static final Type FOUR_PAYMENT_ACTIONS_TYPE = new TypeToken<ItemsResponse<com.checkout.payments.four.PaymentAction>>() {
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
            // Payments FOUR - sender
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Sender.class, CheckoutUtils.TYPE, true, com.checkout.payments.four.sender.ResponseAlternativeSender.class)
                    .registerSubtype(com.checkout.payments.four.sender.PaymentCorporateSender.class, identifier(SenderType.CORPORATE))
                    .registerSubtype(com.checkout.payments.four.sender.PaymentIndividualSender.class, identifier(SenderType.INDIVIDUAL))
                    .registerSubtype(com.checkout.payments.four.sender.PaymentInstrumentSender.class, identifier(SenderType.INSTRUMENT)))
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
            // Marketplace CS2 - PayoutSchedules
            .registerTypeAdapter(GetScheduleResponse.class, getScheduleResponseDeserializer())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.marketplace.payout.schedule.response.ScheduleResponse.class, CheckoutUtils.FREQUENCY)
                    .registerSubtype(com.checkout.marketplace.payout.schedule.response.ScheduleFrequencyDailyResponse.class, CheckoutUtils.DAILY)
                    .registerSubtype(com.checkout.marketplace.payout.schedule.response.ScheduleFrequencyWeeklyResponse.class, CheckoutUtils.WEEKLY)
                    .registerSubtype(ScheduleFrequencyMonthlyResponse.class, CheckoutUtils.MONTHLY))
            // Adapters when API returns an array
            .registerTypeAdapter(EVENT_TYPES_TYPE, eventTypesResponseDeserializer())
            .registerTypeAdapter(WORKFLOWS_EVENT_TYPES_TYPE, workflowEventTypesResponseDeserializer())
            .registerTypeAdapter(WEBHOOKS_TYPE, webhooksResponseDeserializer())
            .registerTypeAdapter(DEFAULT_PAYMENT_ACTIONS_TYPE, paymentActionsResponseDeserializer())
            .registerTypeAdapter(FOUR_PAYMENT_ACTIONS_TYPE, paymentActionsResponseFourDeserializer())
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

    private static JsonDeserializer<ItemsResponse<EventTypes>> eventTypesResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<EventTypes> eventTypesResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                eventTypesResponse.setItems(deserializeJsonArray(json, EventTypes.class));
            }
            return eventTypesResponse;
        };
    }

    private static JsonDeserializer<ItemsResponse<WorkflowEventTypes>> workflowEventTypesResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<WorkflowEventTypes> workflowEventTypesResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                workflowEventTypesResponse.setItems(deserializeJsonArray(json, WorkflowEventTypes.class));
            }
            return workflowEventTypesResponse;
        };
    }

    private static JsonDeserializer<ItemsResponse<WebhookResponse>> webhooksResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<WebhookResponse> webhooksResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                webhooksResponse.setItems(deserializeJsonArray(json, WebhookResponse.class));
            }
            return webhooksResponse;
        };
    }

    private static JsonDeserializer<ItemsResponse<PaymentAction>> paymentActionsResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<PaymentAction> paymentActionsResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                paymentActionsResponse.setItems(deserializeJsonArray(json, PaymentAction.class));
            }
            return paymentActionsResponse;
        };
    }

    private static JsonDeserializer<ItemsResponse<com.checkout.payments.four.PaymentAction>> paymentActionsResponseFourDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<com.checkout.payments.four.PaymentAction> paymentActionsResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                paymentActionsResponse.setItems(deserializeJsonArray(json, com.checkout.payments.four.PaymentAction.class));
            }
            return paymentActionsResponse;
        };
    }

    private static <T> List<T> deserializeJsonArray(final JsonElement json, final Class<T> itemsType) {
        final JsonArray jsonArray = json.getAsJsonArray();
        return IntStream
                .range(0, json.getAsJsonArray().size())
                .mapToObj(i -> DEFAULT_GSON.fromJson(jsonArray.get(i), itemsType))
                .collect(Collectors.toList());
    }

    private static JsonDeserializer<GetScheduleResponse> getScheduleResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final JsonObject jsonObject = json.getAsJsonObject();
            final EnumMap<Currency, CurrencySchedule> currency = new EnumMap<>(Currency.class);
            final GetScheduleResponse getScheduleResponse = new GetScheduleResponse();
            jsonObject.keySet().forEach(key -> {
                if (EnumUtils.isValidEnum(Currency.class, key)) {
                    final CurrencySchedule currencySchedule = DEFAULT_GSON.fromJson(jsonObject.get(key), CurrencySchedule.class);
                    currency.put(Currency.valueOf(key), currencySchedule);
                    getScheduleResponse.setCurrency(currency);
                }
                if (key.equalsIgnoreCase("_links")) {
                    final Type type = new TypeToken<Map<String, Link>>() {
                    }.getType();
                    final Map<String, Link> links = DEFAULT_GSON.fromJson(jsonObject.get(key), type);
                    getScheduleResponse.setLinks(links);
                }
            });
            return getScheduleResponse;
        };
    }
}
